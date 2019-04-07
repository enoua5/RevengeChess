import java.awt.*;
public class Context
{
    public Point hover;
    public Point click;
    public Point absHover;
    public Point absClick;
    public Context(Point hover, Point click, Point absHover, Point absClick)
    {
        this.hover=hover;
        this.click=click;
        this.absHover=absHover;
        this.absClick=absClick;
    }
}
