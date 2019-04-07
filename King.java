import java.awt.*;
import java.util.Vector;
public class King extends Piece
{
    public King(String name, boolean color, Point startPos)
    {
        super(name, color);
        this.type=5;
        this.icon='♚';
        this.startPos=startPos;
        this.pos=startPos;
    }
    @Override
    public Vector<Move> getMoves(Piece[][] board)
    {
        Vector<Move> valid=new Vector<Move>();
        int x=pos.x;
        int y=pos.y;
        //up
        if(y>0)
            if(board[x][y-1].type==-1)
                valid.add(new Move(this.pos, new Point(x, y-1)));
                //valid[x][y-1]=true;
            else if(board[x][y-1].color!=color)
                valid.add(new Move(this.pos, new Point(x, y-1)));
                //valid[x][y-1]=true;
        //right
        if(x<7)
            if(board[x+1][y].type==-1)
                valid.add(new Move(this.pos, new Point(x+1, y)));
                //valid[x+1][y]=true;
            else if(board[x+1][y].color!=color)
                valid.add(new Move(this.pos, new Point(x+1, y)));
                //valid[x+1][y]=true;
        //down
        if(y<7)
            if(board[x][y+1].type==-1)
                valid.add(new Move(this.pos, new Point(x, y+1)));
                //valid[x][y+1]=true;
            else if(board[x][y+1].color!=color)
                valid.add(new Move(this.pos, new Point(x, y+1)));
                //valid[x][y+1]=true;
        //left
        if(x>0)
            if(board[x-1][y].type==-1)
                valid.add(new Move(this.pos, new Point(x-1, y)));
                //valid[x-1][y]=true;
            else if(board[x-1][y].color!=color)
                valid.add(new Move(this.pos, new Point(x-1, y)));
                //valid[x-1][y]=true;
        //up right
        if(y>0&&x<7)
            if(board[x+1][y-1].type==-1)
                valid.add(new Move(this.pos, new Point(x+1, y-1)));
                //valid[x+1][y-1]=true;
            else if(board[x+1][y-1].color!=color)
                valid.add(new Move(this.pos, new Point(x+1, y-1)));
                //valid[x+1][y-1]=true;
        //down right
        if(y<7&&x<7)
            if(board[x+1][y+1].type==-1)
                valid.add(new Move(this.pos, new Point(x+1, y+1)));
                //valid[x+1][y+1]=true;
            else if(board[x+1][y+1].color!=color)
                valid.add(new Move(this.pos, new Point(x+1, y+1)));
                //valid[x+1][y+1]=true;
        //up left
        if(y>0&&x>0)
            if(board[x-1][y-1].type==-1)
                //valid[x-1][y-1]=true;
                valid.add(new Move(this.pos, new Point(x-1, y-1)));
            else if(board[x-1][y-1].color!=color)
                valid.add(new Move(this.pos, new Point(x-1, y-1)));
                //valid[x-1][y-1]=true;
        //down left
        if(y<7&&x>0)
            if(board[x-1][y+1].type==-1)
                valid.add(new Move(this.pos, new Point(x-1, y+1)));
                //valid[x-1][y+1]=true;
            else if(board[x-1][y+1].color!=color)
                valid.add(new Move(this.pos, new Point(x-1, y+1)));
                //valid[x-1][y+1]=true;
        return valid;
    }
}
