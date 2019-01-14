import java.io.IOException;
import java.io.InputStream;
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
            ServerSocket serverSocket = new ServerSocket(1488);

            while (true)
            {
                Socket socket = serverSocket.accept();
                Thread thread = new SocketProcessor(socket);
                thread.start();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
