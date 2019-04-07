import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class Canvas extends JPanel implements MouseListener, MouseMotionListener
{
    private double ratio;
    private int xOffset;
    private int yOffset;
    private int width;
    private int height;
    
    public Point hover=new Point(8,0);
    public Point click=new Point(8,0);
    public Point absHover=new Point(0,0);
    public Point absClick=new Point(0,0);
    
    public Board board;
    public Canvas(double ratio)
    {
        board=new Board();
        this.ratio=ratio;
        addMouseMotionListener(this);
        addMouseListener(this);
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
        g.setColor(new Color(0xef, 0xcc, 0x95));
        g.fillRect(xOffset, yOffset, width, height);
        
        Context context=new Context(hover, click, absHover, absClick);
        board.draw(g, getOffset(), getRealSize(), context);
    }
    
    //getters
    public Point getOffset(){return new Point(xOffset, yOffset);}
    public Dimension getRealSize(){return new Dimension(width, height);}
    // https://docs.oracle.com/javase/tutorial/uiswing/events/mouselistener.html
    @Override
    public void mousePressed(MouseEvent e)
    {
        int x=e.getX()-xOffset;
        int y=e.getY()-yOffset;
        int sqrSize=height/8;
        int tx=x/sqrSize;
        int ty=y/sqrSize;
        //System.out.println(tx+", "+ty);
        click=new Point(tx, ty);
        absClick=new Point(x,y);
    }
    @Override
    public void mouseReleased(MouseEvent e){}
    @Override
    public void mouseEntered(MouseEvent e){}
    @Override
    public void mouseExited(MouseEvent e)
    {
        hover=new Point(8, 0);
    }
    @Override
    public void mouseClicked(MouseEvent e){}
    
    @Override
    public void mouseMoved(MouseEvent e)
    {
        int x=e.getX()-xOffset;
        int y=e.getY()-yOffset;
        int sqrSize=height/8;
        int tx=x/sqrSize;
        int ty=y/sqrSize;
        //System.out.println(tx+", "+ty);
        hover=new Point(tx, ty);
        absHover=new Point(x,y);
    }
    @Override
    public void mouseDragged(MouseEvent e){}
}