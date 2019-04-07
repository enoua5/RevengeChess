import java.awt.*;
public class Main
{
    public static final int FRAMERATE=20;
    
    public static boolean running=true;
    static Frame f;
    static LogoFrame l;
    public static void main(String[] args)
    throws InterruptedException
    {
        l=new LogoFrame();
        l.c.repaint();
        Thread.sleep(1000);
        l.setVisible(false);
        
        f=new Frame();
        while(true)
        {
            while(running)
            {
                f.c.repaint();
                Thread.sleep(1000/FRAMERATE);
            }
            f.reset();
            running=true;
        }
    }
    public void debug()
    throws InterruptedException
    {
        main(new String[] {});
    }
}
