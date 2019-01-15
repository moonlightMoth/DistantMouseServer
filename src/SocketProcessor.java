import sun.net.ConnectionResetException;

import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class SocketProcessor extends Thread
{
    private BufferedWriter bw;
    private Socket socket;
    private BufferedReader br;


    String sr;

    private Robot robot;

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
            bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
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
        robot.setAutoDelay(0);

        try
        {
            System.out.println(socket.getInetAddress() + " connected...");

            String inputLine;

            while ((inputLine = br.readLine()) != null)
            {
                spitOut("got-----------" + inputLine);

                makeDes(inputLine);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void makeDes(String str) throws IOException
    {
        if (str.equals("4c"))
            robot.mouseWheel(-1);
        if (str.equals("5c"))
            robot.mouseWheel(1);

        spitOut("done----------" + str);

        if (str.equals("closeConnection"))
        {
            System.out.println(socket.getInetAddress() + " disconnected...");
            socket.shutdownInput();
            socket.shutdownOutput();
            socket.close();
            interrupt();
        }
    }

    private void spitOut(String str) throws IOException
    {
        //System.out.println("\"" + str + "\" -- spittedOut");
        bw.write(str);
        bw.newLine();
        bw.flush();
        System.out.flush();
    }

}
