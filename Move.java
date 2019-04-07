import java.awt.Point;

public class Move
{
    public Point from;
    public Point to;
    public int eval=0;
    public Move(Point from, Point to)
    {
        this.from=from;
        this.to=to;
    }
}
