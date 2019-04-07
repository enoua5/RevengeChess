import java.awt.*;
import java.util.Vector;
public class Bishop extends Piece
{
    public Bishop(String name, boolean color, Point startPos)
    {
        super(name, color);
        this.type=3;
        this.icon='♝';
        this.startPos=startPos;
        this.pos=startPos;
    }
    @Override
    public Vector<Move> getMoves(Piece[][] board)
    {
        Vector<Move> valid=new Vector<Move>();
        int x=pos.x;
        int y=pos.y;
        //down and right
        for(int i=1; x+i<8&&y+i<8; i++)
        {
            if(board[x+i][y+i].type==-1)
                valid.add(new Move(this.pos, new Point(x+i, y+i)));
                //valid[x+i][y+i]=true;
            else
            {
                if(board[x+i][y+i].color!=color)
                    valid.add(new Move(this.pos, new Point(x+i, y+i)));
                    //valid[x+i][y+i]=true;
                i=8;
            }
        }
        //up and left
        for(int i=-1; x+i>=0&&y+i>=0; i--)
        {
            if(board[x+i][y+i].type==-1)
                valid.add(new Move(this.pos, new Point(x+i, y+i)));
                //valid[x+i][y+i]=true;
            else
            {
                if(board[x+i][y+i].color!=color)
                    valid.add(new Move(this.pos, new Point(x+i, y+i)));
                    //valid[x+i][y+i]=true;
                i=-8;
            }
        }
        //down and left
        int mx=-1;
        int my=1;
        while(x+mx>=0&&y+my<8)
        {
            if(board[x+mx][y+my].type==-1)
                valid.add(new Move(this.pos, new Point(x+mx, y+my)));
                //valid[x+mx][y+my]=true;
            else
            {
                if(board[x+mx][y+my].color!=color)
                    valid.add(new Move(this.pos, new Point(x+mx, y+my)));
                    //valid[x+mx][y+my]=true;
                mx=-8;
            }
            mx--;
            my++;
        }
        //up and right
        my=-1;
        mx=1;
        while(x+mx<8&&y+my>=0)
        {
            if(board[x+mx][y+my].type==-1)
                valid.add(new Move(this.pos, new Point(x+mx, y+my)));
                //valid[x+mx][y+my]=true;
            else
            {
                if(board[x+mx][y+my].color!=color)
                    valid.add(new Move(this.pos, new Point(x+mx, y+my)));
                    //valid[x+mx][y+my]=true;
                mx=8;
            }
            mx++;
            my--;
        }
        return valid;
    }
}