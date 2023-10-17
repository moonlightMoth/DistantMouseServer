import java.awt.*;
import java.awt.event.InputEvent;
import java.io.*;
import java.net.Socket;

import static java.lang.Math.abs;
import static java.lang.Math.max;

public class SocketProcessor extends Thread
{
//    private BufferedWriter bw;
    private Socket socket;
    private InputStream is;
    private Point curPos;
    private boolean isInter = false;
    private byte[] bytes;

    private final byte MV_UP = 1;
    private final byte MV_DW = 2;
    private final byte MV_RT = 3;
    private final byte MV_LT = 4;
    private final byte SC_UP = 5;
    private final byte SC_DW = 6;
    private final byte CL_LB = 7;
    private final byte DR_ON = 8;
    private final byte DR_OF = 9;
    private final byte CL_RB = 10;
    private final byte CL_MB = 11;
    private final byte MV_XY = 12;
    private final byte SC_UD = 13;
    private final byte EXIT_SIGNAL = 127;

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
            this.socket.setTcpNoDelay(false);
            is = socket.getInputStream();
//            bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            robot.setAutoDelay(0);
            curPos = getCurPos();
            bytes = new byte[3];
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
    }

    @Override
    public void run()
    {
        try
        {
            System.out.println(socket.getInetAddress() + " connected.");

            try
            {
                while (!isInter && (is.read(bytes)) != -1)
                {
                    makeDesByte();
                }
            }
            catch (IOException e)
            {
                //do nothing
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void makeDesByte() throws IOException
    {
        switch (bytes[0])
        {
            case MV_XY: moveXY(); break;
            case SC_UD: scrollFreely(); break;
            case CL_LB: clickLmb(); break;
            case DR_ON: dragOn(); break;
            case DR_OF: dragOff(); break;
            case CL_RB: clickRmb(); break;
            case CL_MB: clickMmb(); break;
            case EXIT_SIGNAL: exitSignal(); break;
        }
    }

    private void scrollFreely()
    {
        int signedYUnit = sing(bytes[1]);

        for (int i = 0; i < abs(bytes[1]); i++)
        {
            robot.mouseWheel(signedYUnit);
        }
    }

    private void exitSignal() throws IOException
    {
        System.out.println(socket.getInetAddress() + " disconnected...");
        socket.shutdownInput();
        socket.shutdownOutput();
        socket.close();
        interrupt();
    }

    private void clickMmb()
    {
        robot.mousePress(InputEvent.BUTTON2_MASK);
        robot.mouseRelease(InputEvent.BUTTON2_MASK);
    }

    private void clickRmb()
    {
        robot.mousePress(InputEvent.BUTTON3_MASK);
        robot.mouseRelease(InputEvent.BUTTON3_MASK);
    }

    private void dragOff()
    {
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
    }

    private void dragOn()
    {
        robot.mousePress(InputEvent.BUTTON1_MASK);
    }

    private void clickLmb()
    {
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
    }

    private void moveXY()
    {
        curPos = getCurPos();

        robot.mouseMove(curPos.x + bytes[1], curPos.y + bytes[2]);

//        int x = curPos.x;
//        int y = curPos.y;

//        int signedXUnit = sing(bytes[1]);
//        int signedYUnit = sing(bytes[2]);

//        for (int i = 0; i < max(abs(bytes[1]), abs(bytes[2])); i++)
//        {
//            if (i < abs(bytes[1]))
//                x = x + signedXUnit;
//            if (i < abs(bytes[2]))
//                y = y + signedYUnit;
//
//            robot.mouseMove(x, y);
//        }
    }

    private Point getCurPos()
    {
        return MouseInfo.getPointerInfo().getLocation();
    }

    private int sing(byte b)
    {
        if (b > 0) return 1;
        if (b < 0) return -1;
        return 0;
    }

//    private void spitOut(String str) throws IOException
//    {
//        bw.write(str);
//        bw.newLine();
//        bw.flush();
//        System.out.flush();
//    }

    void interruptDeb() throws IOException
    {
        isInter = true;
        is.close();
        socket.close();
    }
}
