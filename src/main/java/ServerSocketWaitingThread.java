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
            System.out.println("Socket closed.");
        }
    }

    private void performConnection() throws IOException
    {

        Socket socket = serverSocket.accept();

        System.out.println(socket.getRemoteSocketAddress() + " connecting...");

        if (thread != null)
            thread.interruptDeb();
        thread = new SocketProcessor(socket);
        thread.start();
    }

    void interruptDeb()
    {
        try {
            serverSocket.close();
            isInter = true;
            if (thread != null)
                thread.interruptDeb();
        }
        catch (Exception e)
        {
            //default exit
        }
    }

    public void closeCurrentConnection() throws IOException {
        try
        {
            thread.interruptDeb();
            System.out.println("Current device disconnected.");
        }
        catch (NullPointerException e)
        {
            System.out.println("No current connection.");
        }
    }
}
