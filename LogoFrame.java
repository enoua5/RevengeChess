import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.geom.*;
public class LogoFrame extends JFrame
{
    public LogoCanvas c;
    JLabel label;
    public LogoFrame()
    {
        super("Revenge Chess");
        setUndecorated(true);
        c=new LogoCanvas(0.5);
        c.setPreferredSize(new Dimension(600, 300));
        this.setResizable(false);
        this.add(c);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        centerFrame();
        this.setVisible(true);
    }
    private void centerFrame()
    {
        Dimension windowSize = getSize();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Point centerPoint = ge.getCenterPoint();
        int dx = centerPoint.x - windowSize.width / 2;
        int dy = centerPoint.y - windowSize.height / 2;    
        setLocation(dx, dy);
    }
}
