import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Properties;


public class Server {

    private static String version = "can not read version";
    private static ServerDiscoveringThread serverDiscoveringThread;
    private static ServerSocketWaitingThread serverSocketThread;
    private static boolean isInterupted = false;
    private static BufferedReader br;

    public static void main(String[] args)
    {
        try{
            final Properties properties = new Properties();
            properties.load(Server.class.getClassLoader().getResourceAsStream("project.properties"));
            version = properties.getProperty("version");
        }
        catch (IOException e)
        {
            System.out.println("Can not read version!");
        }

        try
        {
            System.out.println("DistantMouse server running...");
            System.out.println("Current version is " + version);
            System.out.println("Current IP Addresses are ");

            Iterator<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces().asIterator();

            interfaces.forEachRemaining((NetworkInterface i) -> i.getInterfaceAddresses().iterator().forEachRemaining((InterfaceAddress a) ->
            {if (a.getAddress().isSiteLocalAddress()) System.out.println("----------------- " + a.getAddress().getHostAddress() + " -----------------");}));


            serverSocketThread = new ServerSocketWaitingThread();
            serverSocketThread.setDaemon(false);
            serverSocketThread.start();

            serverDiscoveringThread = new ServerDiscoveringThread();
            serverDiscoveringThread.start();


            System.out.println("Run successful.");
            System.out.println("Waiting for connection...");


            br = new BufferedReader(new InputStreamReader(System.in));
            String str;

            while (!isInterupted && (str = br.readLine()) != null)
            {
                if (str.equals("CATATA"))
                {
                    return;
                }

                if (str.equals("exit") || str.equals("bye") || str.equals("quit"))
                {
                    System.out.println("Exit signal got");
                    System.out.println("Shutting down...");

                    serverSocketThread.interruptDeb();
                    serverDiscoveringThread.interrupt();
                    return;
                }
                else if (str.equals("help") || str.equals("?"))
                {
                    System.out.println(
                            "exit || bye || quit - shut down server\n" +
                            "disconnect - disconnect current remote device\n" +
                                    "help || ? - print this message");
                }
                else
                {
                    System.out.println(
                            "exit || bye || quit - shut down server\n" +
                                    "disconnect - disconnect current remote device\n" +
                                    "help || ? - print this message");
                }

                if (str.equals("disconnect"))
                {
                    serverSocketThread.closeCurrentConnection();
                }
            }

        }
        catch (IOException e)
        {
            System.out.println("Port already in use!");
            System.out.println("Shutting down...");
        }
    }

    public static void interruptAllThreads(){
        serverDiscoveringThread.interrupt();
        serverSocketThread.interruptDeb();
        System.exit(1);
    }
}
