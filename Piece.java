import java.awt.*;
import java.util.Vector;
public abstract class Piece implements Cloneable
{
    public String name;
    public int movesMade;
    public boolean color; //0 is white
    public int type;
    public char icon=' ';
    public Point startPos;
    public Point pos;
    public Piece[] kills;
    public boolean alreadyClearedKillList=false;
    public Vector<Move> getMoves(Piece[][] board){return new Vector<Move>();}
    public Piece(String name, boolean color)
    {
        this.name=name;
        this.color=color;
        this.movesMade=0;
        this.kills=new Piece[0];
    }
    public Piece deep_clone()
    throws CloneNotSupportedException
    {
        Piece p=(Piece)this.clone();
        if(pos!=null)
            p.pos=new Point(pos.x, pos.y);
        for(int i=0; i<p.kills.length; i++)
            p.kills[i]=p.kills[i].deep_clone();
        return p;
    }
    public void addKill(Piece p)
    {
        Piece[] newKills=new Piece[kills.length+1];
        for(int i=0; i<kills.length; i++)
            newKills[i]=kills[i];
        newKills[kills.length]=p;
        kills=newKills;
        //spawn killed pieces back in
        /*
        Piece[][] state=Main.f.c.board.state;
        for(int i=0; i<p.kills.length; i++)
        {
            p.kills[i].pos=p.kills[i].startPos;
            //we killed something by spawning in
            if(state[p.kills[i].startPos.x][p.kills[i].startPos.y].type!=-1)
            {
                p.kills[i].addKill(state[p.kills[i].startPos.x][p.kills[i].startPos.y]);
                //heh, forgetting this caused bad crashes...
                state[p.kills[i].startPos.x][p.kills[i].startPos.y].kills=new Piece[0];
            }
            state[p.kills[i].startPos.x][p.kills[i].startPos.y]=p.kills[i];
        }
        */
    }
}
