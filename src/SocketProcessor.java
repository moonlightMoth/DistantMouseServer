import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class SocketProcessor extends Thread
{
    BufferedWriter bw;
    private static final HashMap commandsMap = new CommandsMap();
    private Socket socket;
    private InputStream is;
    private OutputStream os;
    private Runtime runtime = Runtime.getRuntime();

    SocketProcessor(Socket socket)
    {
        try
        {
            this.socket = socket;
            this.is = socket.getInputStream();
            this.os = socket.getOutputStream();
            bw = new BufferedWriter(new OutputStreamWriter(os));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }




    @Override
    public synchronized void start()
    {
        super.start();
        try {
            System.out.println(socket.getInetAddress() + " connected...");
            try {
                Scanner sc = new Scanner(is, "UTF-8");
                String inputLine = "";

                while (sc.hasNextLine()) {
                    if (sc.hasNextLine()) {
                        inputLine = sc.nextLine();
                        spitOut("got-----------" + inputLine);

                        makeDecisionForInputLine(inputLine);
                        sc.reset();
                    }
                }
            } finally {
                //TODO?
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void makeDecisionForInputLine(String str) throws IOException
    {
        if (commandsMap.containsKey(str))
        {
            runCmd((String) commandsMap.get(str));
            spitOut("done----------" + str);
        }
        if (str.equals("asd"))
        {
            startHell();
            spitOut("done----------" + str);
        }
        if (str.equals("closeConnection"))
        {
            System.out.println(socket.getInetAddress() + " disconnected...");
            socket.close();
            interrupt();
        }
    }

    private void runCmd(String str)
    {
        try {
            spitOut("exec----------" + str);
            runtime.exec(str);
            spitOut("execed--------" + str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startHell()
    {
        try {
            String string = "xdotool mousemove_relative ";
            Random rn = new Random();

            int x = rn.nextInt(10);
            int y = rn.nextInt(10);

            spitOut("exec----------asd");

            for (int i = 0; i < 10000; i++) {
                runtime.exec(string + x + " " + y);
                x = rn.nextInt(10) - rn.nextInt(25);
                y = rn.nextInt(10) - rn.nextInt(10);
            }
            spitOut("execed--------asd");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void spitOut(String str) throws IOException
    {
        System.out.println("\"" + str + "\" -- spittedOut");
        bw.write(str);
        bw.newLine();
        bw.flush();
    }

}
