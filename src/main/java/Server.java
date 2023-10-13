import java.io.*;
import java.net.*;
import java.util.Enumeration;
import java.util.Iterator;

public class Server {

    private static String version = "TCP.0.30";

    public static void main(String[] args)
    {
        try
        {
            System.out.println("InpCtrl server running...");
            System.out.println("Current version is " + version);
            System.out.println("Current IP Addresses are ");

            Iterator<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces().asIterator();

            interfaces.forEachRemaining((NetworkInterface i) -> i.getInterfaceAddresses().iterator().forEachRemaining((InterfaceAddress a) ->
            {if (a.getAddress().isSiteLocalAddress()) System.out.println("----------------- " + a.getAddress().getHostAddress() + " -----------------");}));


            ServerSocketWaitingThread serverSocketThread = new ServerSocketWaitingThread();
            serverSocketThread.setDaemon(false);
            serverSocketThread.start();

            ServerDiscoveringThread serverDiscoveringThread = new ServerDiscoveringThread();
            serverDiscoveringThread.start();

            System.out.println("Run successful.");
            System.out.println("Waiting for connection...");


            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String str;

            while ((str = br.readLine()) != null)
            {
                if (str.equals("exit") || str.equals("bye") || str.equals("quit"))
                {
                    System.out.println("Exit signal got");
                    System.out.println("Shutting down...");

                    serverSocketThread.interruptDeb();
                    return;
                }
            }

        }
        catch (IOException e)
        {
            System.out.println("Port already in use!");
            System.out.println("Shutting down...");
        }
    }
}
