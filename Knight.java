import java.awt.*;
import java.util.Vector;
public class Knight extends Piece
{
    public Knight(String name, boolean color, Point startPos)
    {
        super(name, color);
        this.type=2;
        this.icon='♞';
        this.startPos=startPos;
        this.pos=startPos;
    }
    @Override
    public Vector<Move> getMoves(Piece[][] board)
    {
        Vector<Move> valid=new Vector<Move>();
        int x=pos.x;
        int y=pos.y;
        //up two, left one
        if(x-1>=0&&y-2>=0)
            if(board[x-1][y-2].type==-1)
                valid.add(new Move(this.pos, new Point(x-1, y-2)));
                //valid[x-1][y-2]=true;
            else if(board[x-1][y-2].color!=color)
                valid.add(new Move(this.pos, new Point(x-1, y-2)));
                //valid[x-1][y-2]=true;
        //up two, right one
        if(x+1<8&&y-2>=0)
            if(board[x+1][y-2].type==-1)
                valid.add(new Move(this.pos, new Point(x+1, y-2)));
                //valid[x+1][y-2]=true;
            else if(board[x+1][y-2].color!=color)
                valid.add(new Move(this.pos, new Point(x+1, y-2)));
                //valid[x+1][y-2]=true;
        //up one, left two
        if(x-2>=0&&y-1>=0)
            if(board[x-2][y-1].type==-1)
                valid.add(new Move(this.pos, new Point(x-2, y-1)));
                //valid[x-2][y-1]=true;
            else if(board[x-2][y-1].color!=color)
                valid.add(new Move(this.pos, new Point(x-2, y-1)));
                //valid[x-2][y-1]=true;
        //up one, right two
        if(x+2<8&&y-1>=0)
            if(board[x+2][y-1].type==-1)
                valid.add(new Move(this.pos, new Point(x+2, y-1)));
                //valid[x+2][y-1]=true;
            else if(board[x+2][y-1].color!=color)
                valid.add(new Move(this.pos, new Point(x+2, y-1)));
                //valid[x+2][y-1]=true;
        //down two, left one
        if(x-1>=0&&y+2<8)
            if(board[x-1][y+2].type==-1)
                valid.add(new Move(this.pos, new Point(x-1, y+2)));
                //valid[x-1][y+2]=true;
            else if(board[x-1][y+2].color!=color)
                valid.add(new Move(this.pos, new Point(x-1, y+2)));
                //valid[x-1][y+2]=true;
        //down two, right one
        if(x+1<8&&y+2<8)
            if(board[x+1][y+2].type==-1)
                valid.add(new Move(this.pos, new Point(x+1, y+2)));
                //valid[x+1][y+2]=true;
            else if(board[x+1][y+2].color!=color)
                valid.add(new Move(this.pos, new Point(x+1, y+2)));
                //valid[x+1][y+2]=true;
        //down one, left two
        if(x-2>=0&&y+1<8)
            if(board[x-2][y+1].type==-1)
                valid.add(new Move(this.pos, new Point(x-2, y+1)));
                //valid[x-2][y+1]=true;
            else if(board[x-2][y+1].color!=color)
                valid.add(new Move(this.pos, new Point(x-2, y+1)));
                //valid[x-2][y+1]=true;
        //down one, right two
        if(x+2<8&&y+1<8)
            if(board[x+2][y+1].type==-1)
                valid.add(new Move(this.pos, new Point(x+2, y+1)));
                //valid[x+2][y+1]=true;
            else if(board[x+2][y+1].color!=color)
                valid.add(new Move(this.pos, new Point(x+2, y+1)));
                //valid[x+2][y+1]=true;
        return valid;
    }
}
