import java.io.IOException;
import java.net.*;
import java.util.Arrays;
import java.util.Iterator;

public class ServerDiscoveringThread extends Thread {

    DatagramSocket datagramSocket = null;

    private static final byte[] checkBuff = new byte[] {1, 6, 3, 127, -7, 34, 42, 43, 0, 0, 0, 0};


    @Override
    public void run() {

        super.run();
        try {
            datagramSocket = new DatagramSocket(1337);
            DatagramPacket datagramPacket = new DatagramPacket(new byte[12], 12);

        try {
            System.out.println("Waiting for scan request...");
            while (true)
            {
                datagramSocket.receive(datagramPacket);

                if (Arrays.equals(datagramPacket.getData(), 0, 7, checkBuff, 0, 7))
                {
                    sendResponse(datagramPacket.getAddress());
                    System.out.println("Waiting for scan request...");
                }

            }
        } catch (IOException e) {

        }
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendResponse(InetAddress address) throws IOException {

        System.out.println("Got scan request from " + address.getHostName());

        Iterator<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces().asIterator();

        interfaces.forEachRemaining((NetworkInterface i) -> i.getInterfaceAddresses().iterator().forEachRemaining((InterfaceAddress a) ->
        {
            if (a.getAddress().isSiteLocalAddress())
            {
                checkBuff[8] = a.getAddress().getAddress()[0];
                checkBuff[9] = a.getAddress().getAddress()[1];
                checkBuff[10] = a.getAddress().getAddress()[2];
                checkBuff[11] = a.getAddress().getAddress()[3];
            }
        }));

        DatagramSocket datagramSocket = new DatagramSocket();
        DatagramPacket datagramPacket = new DatagramPacket(checkBuff, checkBuff.length, address, 1336);

        datagramSocket.send(datagramPacket);

        System.out.println("Sent scan response to " + address.getHostName() + " with local address " +
                Byte.toUnsignedInt(checkBuff[8]) + "." +
                Byte.toUnsignedInt(checkBuff[9]) + "." +
                Byte.toUnsignedInt(checkBuff[10]) + "." +
                Byte.toUnsignedInt(checkBuff[11]));

    }

    @Override
    public void interrupt() {
        datagramSocket.close();
        super.interrupt();
    }
}
