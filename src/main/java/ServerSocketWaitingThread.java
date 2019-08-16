import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketWaitingThread extends Thread
{
    private ServerSocket serverSocket;
    private boolean isInter = false;
    private SocketProcessor thread;
    private volatile String name = "ServerSocketWaitingThread";

    @Override
    public synchronized void start()
    {
        super.start();
    }

    @Override
    public synchronized void run()
    {
        try
        {
            serverSocket = new ServerSocket(1488);

            while (!isInter)
            {
                performConnection();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void performConnection() throws IOException
    {
        DatagramSocket receiverDatagramSocket;
        DatagramPacket packet;
        DatagramSocket senderDatagramSocket;
        receiverDatagramSocket = new DatagramSocket(1488);
        packet = new DatagramPacket(new byte[1], 1);
        receiverDatagramSocket.receive(packet);
        receiverDatagramSocket.disconnect();
        receiverDatagramSocket.close();
        senderDatagramSocket = new DatagramSocket();
        packet = new DatagramPacket(new byte[1], 1, packet.getAddress(), 1488);
        senderDatagramSocket.send(packet);
        System.out.println(packet.getAddress().toString() + " connecting...");
        senderDatagramSocket.disconnect();
        senderDatagramSocket.close();

        Socket socket = serverSocket.accept();
        thread = new SocketProcessor(socket);
        thread.start();
    }

    void interruptDeb() throws IOException
    {
        serverSocket.close();
        isInter = true;
        if (thread != null)
            thread.interruptDeb();
    }
}
