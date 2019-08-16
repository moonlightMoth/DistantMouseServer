import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;

public class Server {

    private static String version = "TCP.0.21";

    public static void main(String[] args)
    {
        try
        {
            System.out.println("InpCtrl server running...");
            System.out.println("Current version is " + version);


            ServerSocketWaitingThread serverSocketThread = new ServerSocketWaitingThread();
            serverSocketThread.setDaemon(false);
            serverSocketThread.start();

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
