import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class SocketProcessor extends Thread
{
    private final HashMap commandsMap = new CommandsMap();
    BufferedWriter bw;
    private Socket socket;
    private InputStream is;
    private OutputStream os;
    private Runtime runtime = Runtime.getRuntime();
    private String[] args = new String[]{"/bin/bash", "-c", "export DISPLAY=:0; export XAUTHORITY=/home/moonlightmoth/.Xauthority; ", ""};
    private ProcessBuilder pb = new ProcessBuilder();

    Robot robot;

    {
        try
        {
            robot = new Robot();
        }
        catch (AWTException e)
        {
            e.printStackTrace();
        }
    }

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
        try
        {
            System.out.println(socket.getInetAddress() + " connected...");
            try
            {
                Scanner sc = new Scanner(is, "UTF-8");
                String inputLine = "";

                while (sc.hasNextLine())
                {
                    inputLine = sc.nextLine();
                    spitOut("got-----------" + inputLine);

                    //makeDecisionForInputLine(inputLine);
                    makeDes(inputLine);
                    sc.reset();
                }
            }
            finally
            {
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

    private void makeDes(String str)
    {
        robot.setAutoDelay(10);
        if (str.equals("4c"))
            for (int i = 0; i < 10; i++)
            {
                robot.mouseWheel(-1);
            }
        if (str.equals("5c"))
            for (int i = 0; i < 10; i++)
                robot.mouseWheel(1);    }

    private void runCmd(String str)
    {

        try
        {
            spitOut("exec----------" + str);

            args[3] = str;

            pb.command(args).start();

            spitOut("execed--------" + str);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void startHell()
    {
        try
        {
            String string = "xdotool mousemove_relative ";
            Random rn = new Random();

            int x = rn.nextInt(10);
            int y = rn.nextInt(10);

            spitOut("exec----------asd");

            for (int i = 0; i < 10000; i++)
            {
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
        System.out.flush();
    }

}
