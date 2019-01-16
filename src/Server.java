import java.io.IOException;
import java.io.InputStream;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class Server {

    public static void main(String[] args)
    {
        try
        {
            System.out.println("InpCtrl server running...");
            ServerSocket serverSocket = new ServerSocket(1488);
            System.out.println("Run successful.");

            while (true)
            {
                Socket socket = serverSocket.accept();
                Thread thread = new SocketProcessor(socket);
                thread.start();
            }
        }
        catch (IOException e)
        {
            System.out.println("Port already in use!");
            System.out.println("Shutting down...");
        }
    }
}
