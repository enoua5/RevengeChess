import java.awt.*;
import java.util.Vector;
public class Pawn extends Piece
{
    private Piece promote;
    public Pawn(String name, boolean color, Point startPos)
    {
        super(name, color);
        this.type=0;
        this.icon='♟';
        this.startPos=startPos;
        this.pos=startPos;
    }
    @Override
    public Vector<Move> getMoves(Piece[][] board)
    {
        Vector<Move> valid=new Vector<Move>();
        int x=pos.x;
        int y=pos.y;
        boolean canMoveTwo=(movesMade==0);
        int dir=color?1:-1;
        if(pos.y!=0&&pos.y!=7)
        {
            if(board[x][y+dir].type==-1)
            {
                valid.add(new Move(this.pos, new Point(x, y+dir)));
                //valid[x][y+dir]=true;
                if(canMoveTwo&&board[x][y+(dir*2)].type==-1)
                    valid.add(new Move(this.pos, new Point(x, y+(dir*2))));
                    //valid[x][y+(dir*2)]=true;
            }
            if(x<7)
            if(board[x+1][y+dir].color!=color&&board[x+1][y+dir].type!=-1)
                valid.add(new Move(this.pos, new Point(x+1, y+dir)));
                //valid[x+1][y+dir]=true;
            if(x>0)
            if(board[x-1][y+dir].color!=color&&board[x-1][y+dir].type!=-1)
                valid.add(new Move(this.pos, new Point(x-1, y+dir)));
                //valid[x-1][y+dir]=true;
            //en passant
            Piece[][] prev=Main.f.c.board.prevState;
            if((y==3&&!color)||(y==4&&color))
            {
                if(x<7)
                if(board[x+1][y].color!=color&&board[x+1][y].type==0
                    &&board[x+1][y].name.equals(prev[x+1][y+(2*dir)].name))
                {
                    valid.add(new Move(this.pos, new Point(x+1, y+dir)));
                    //valid[x+1][y+dir]=true;
                }
                if(x>0)
                if(board[x-1][y].color!=color&&board[x-1][y].type==0
                    &&board[x-1][y].name.equals(prev[x-1][y+(2*dir)].name))
                {
                    valid.add(new Move(this.pos, new Point(x-1, y+dir)));
                    //valid[x-1][y+dir]=true;
                }
            }
        }
        return valid;
    }
}
