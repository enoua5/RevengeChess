import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.geom.*;
public class LogoCanvas extends JPanel
{
    private double ratio;
    private int xOffset;
    private int yOffset;
    private int width;
    private int height;
    public LogoCanvas(double ratio)
    {
        this.ratio=ratio;
    }
    public void drawLogo(Graphics g, int x, int y, int width, int height)
    {
        g.setColor(new Color(0, 0, 128));
        g.fillRect(x, y, width, height/8);
        g.fillRect(x, y, width/8, height);
        g.fillRect(x, y+(7*(width/16)), width, height/8);
        g.fillRect(x, (y+height)-(height/8), width, height/8);
        g.fillRect((x+width)-(width/8), y+(height/2), width/8, height/2);
        g.fillRect(x+(7*(height/16)), y, width/8, height/2);
    }
    public void paint(Graphics g)
    {
        int h=getSize().height;
        int w=getSize().width;
        g.setColor(new Color(0x40,0x40,0x40));
        g.fillRect(0,0,w,h);
        if(w*ratio<h)
        {
            width=w;
            height=(int)(w*ratio);
        }
        else
        {
            height=h;
            width=(int)(h/ratio);
        }
        xOffset=(w-width)/2;
        yOffset=(h-height)/2;
        //start the actual drawing
        g.setColor(new Color(0x00, 0x00, 0x00));
        g.fillRect(xOffset, yOffset, width, height);
        //draw logo
        drawLogo(g, (width-(height/3))/2, height/5, height/3, height/3);
        g.setColor(new Color(0xff, 0, 0));
        Font font=new Font("serif", Font.BOLD, height/24);
        g.setFont(font);
        FontMetrics metrics = g.getFontMetrics(font);
        int strWidth=metrics.stringWidth("Foxrabbit Games");
        g.drawString("Foxrabbit Games", (width-strWidth)/2, 4*(height/7)+(height/24));
        //draw url
        g.setColor(new Color(0x80, 0x80, 0x80));
        g.setFont(new Font("serif", Font.BOLD, height/30));
        g.drawString("https://enoua5.github.io", width/48, height-(height/48));
    }
    
    //getters
    public Point getOffset(){return new Point(xOffset, yOffset);}
    public Dimension getRealSize(){return new Dimension(width, height);}
}