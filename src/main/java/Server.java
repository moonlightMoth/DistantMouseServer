import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

import java.io.*;
import java.net.*;
import java.util.Enumeration;
import java.util.Iterator;

public class Server {

    private static String version = "can not read version";

    public static void main(String[] args)
    {
        try{
            MavenXpp3Reader reader = new MavenXpp3Reader();
            Model model = reader.read(new FileReader("pom.xml"));
            version = model.getVersion();
        }
        catch (IOException | XmlPullParserException e)
        {
            System.out.println("Can not read version!");
        }

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

            try {
                serverDiscoveringThread.start();
            }
            catch (RuntimeException e)
            {
                serverSocketThread.interruptDeb();
            }

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
                    serverDiscoveringThread.interrupt();
                    return;
                }

                if (str.equals("help") || str.equals("?"))
                {
                    System.out.println(
                            "exit || bye || quit - shut down server\n" +
                            "disconnect - disconnect current remote device\n" +
                                    "help || ? - print this message");
                }

                if (str.equals("disconnect"))
                {
                    serverSocketThread.closeCurrentConnection();
                    System.out.println("Current device disconnected.");
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
