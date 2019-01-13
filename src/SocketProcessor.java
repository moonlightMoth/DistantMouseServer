import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class SocketProcessor implements Runnable
{
    BufferedWriter bw;
    private static final HashMap commandsMap = new CommandsMap();
    private Socket socket;
    private InputStream is;
    private OutputStream os;

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
    public void run()
    {
        try {
            Scanner sc = new Scanner(is, "UTF-8");
            String inputLine = "";

            while (true) {
                if (sc.hasNextLine()) {
                    inputLine = sc.nextLine();
                    bw.write("got-----------" + inputLine);
                    bw.newLine();
                    bw.flush();

                    makeDecisionForInputLine(inputLine);
                } else {
                    return;
                }
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void makeDecisionForInputLine(String str) throws IOException
    {
        if (commandsMap.containsKey(str))
        {
            run((String) commandsMap.get(str));
            spitOut("done----------" + str);
        }
        if (str.equals("asd"))
        {
            startHell();
            spitOut("done----------" + str);
        }
    }

    private void run(String str)
    {
        try {
            spitOut("exec----------" + str);
            Runtime.getRuntime().exec(str);
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
                Runtime.getRuntime().exec(string + x + " " + y);
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
        bw.write(str);
        bw.newLine();
        bw.flush();
    }

}
