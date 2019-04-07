import java.awt.*;
import java.util.Vector;
public class Rook extends Piece
{
    public Rook(String name, boolean color, Point startPos)
    {
        super(name, color);
        this.type=1;
        this.icon='♜';
        this.startPos=startPos;
        this.pos=startPos;
    }
    @Override
    public Vector<Move> getMoves(Piece[][] board)
    {
        Vector<Move> valid=new Vector<Move>();
        int x=pos.x;
        int y=pos.y;
        for(int mx=x+1; mx<8; mx++)
        {
            if(board[mx][y].type==-1)
                valid.add(new Move(this.pos, new Point(mx, y)));
                //valid[mx][y]=true;
            else
            {
                if(board[mx][y].color!=color)
                valid.add(new Move(this.pos, new Point(mx, y)));
                //valid[mx][y]=true;
                mx=8;
            }
        }
        for(int mx=x-1; mx>=0; mx--)
        {
            if(board[mx][y].type==-1)
                valid.add(new Move(this.pos, new Point(mx, y)));
                //valid[mx][y]=true;
            else
            {
                if(board[mx][y].color!=color)
                valid.add(new Move(this.pos, new Point(mx, y)));
                //valid[mx][y]=true;
                mx=-1;
            }
        }
        for(int my=y+1; my<8; my++)
        {
            if(board[x][my].type==-1)
                valid.add(new Move(this.pos, new Point(x, my)));
                //valid[x][my]=true;
            else
            {
                if(board[x][my].color!=color)
                    valid.add(new Move(this.pos, new Point(x, my)));
                    //valid[x][my]=true;
                my=8;
            }
        }
        for(int my=y-1; my>=0; my--)
        {
            if(board[x][my].type==-1)
                valid.add(new Move(this.pos, new Point(x, my)));
                //valid[x][my]=true;
            else
            {
                if(board[x][my].color!=color)
                    valid.add(new Move(this.pos, new Point(x, my)));
                    //valid[x][my]=true;
                my=-1;
            }
        }
        return valid;
    }
}
