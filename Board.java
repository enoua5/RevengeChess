import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.geom.*;
import java.util.Vector;
import java.util.Iterator;
public class Board implements Cloneable
{
    public Piece[][] state=new Piece[8][8];
    public Piece[][] idontactuallyknowwhatimdoing=new Piece[8][8];
    public Piece[][] prevState=new Piece[8][8];
    public boolean turn; //0 is white
    
    public boolean moverSelected=false;
    public Point mover=new Point(8,0);
    public boolean winnerFound=false;
    public int winner=-1;
    
    public boolean thinking=false;
    
    public Board()
    {
        
        turn=false;
        for(int x=0; x<8; x++)
        for(int y=0; y<8; y++)
        {
            state[x][y]=new Empty();
            idontactuallyknowwhatimdoing[x][y]=new Empty();
            prevState[x][y]=new Empty();
        }
        for(int x=0; x<8; x++)
        {
            state[x][1]=new Pawn("Black Pawn "+((7-x)+1), true, new Point(x, 1));
            state[x][6]=new Pawn("White Pawn "+(x+1), false, new Point(x, 6));
        }
        state[0][0]=new Rook("Black Rook 2", true, new Point(0,0));
        state[1][0]=new Knight("Black Knight 2", true, new Point(1,0));
        state[2][0]=new Bishop("Black Bishop 2", true, new Point(2,0));
        state[4][0]=new King("Black King", true, new Point(4,0));
        state[3][0]=new Queen("Black Queen", true, new Point(3,0));
        state[5][0]=new Bishop("Black Bishop 1", true, new Point(5,0));
        state[6][0]=new Knight("Black Knight 1", true, new Point(6,0));
        state[7][0]=new Rook("Black Rook 1", true, new Point(7,0));
        
        state[0][7]=new Rook("White Rook 1", false, new Point(0,7));
        state[1][7]=new Knight("White Knight 1", false, new Point(1,7));
        state[2][7]=new Bishop("White Bishop 1", false, new Point(2,7));
        state[4][7]=new King("White King", false, new Point(4,7));
        state[3][7]=new Queen("White Queen", false, new Point(3,7));
        state[5][7]=new Bishop("White Bishop 2", false, new Point(5,7));
        state[6][7]=new Knight("White Knight 2", false, new Point(6,7));
        state[7][7]=new Rook("White Rook 2", false, new Point(7,7));
    }
    public Piece[] concat(Piece[] arr1, Piece[] arr2)
    {
        Piece[] newArr=new Piece[arr1.length+arr2.length];
        for(int i=0; i<arr1.length; i++)
            newArr[i]=arr1[i];
        for(int i=0; i<arr2.length; i++)
            newArr[i+arr1.length]=arr2[i];
        return newArr;
    }
    public void draw(Graphics g, Point off, Dimension size, Context context)
    {
        Color[] colors;
        switch(Main.f.theme)
        {
            //board
            //color schemes are in order of how good they look
            //cyan
            case 0:
                colors=new Color[]{new Color(0x00, 0x80, 0x80), new Color(0x80, 0xff, 0xff)};
                break;
            //blue
            case 1:
                colors=new Color[]{new Color(0x00, 0x00, 0x80), new Color(0x80, 0x80, 0xff)};
                break;
            //magenta
            case 2:
                colors=new Color[]{new Color(0x80, 0x00, 0x80), new Color(0xff, 0x80, 0xff)};
                break;
            //red
            case 3:
                colors=new Color[]{new Color(0x80, 0x00, 0x00), new Color(0xff, 0x80, 0x80)};
                break;
            //green
            case 4:
                colors=new Color[]{new Color(0x00, 0x80, 0x00), new Color(0x80, 0xff, 0x80)};
                break;
            //yellow
            case 5:
                colors=new Color[]{new Color(0x80, 0x80, 0x00), new Color(0xff, 0xff, 0x80)};
                break;
            //crissa
            default:
                colors=new Color[]{new Color(128, 0, 128), new Color(6, 115, 21)};
        }
        float sqrSize=size.height/8;
        int c=1;
        for(int x=0; x<8; x++)
        {
            for(int y=0; y<8; y++)
            {
                g.setColor(colors[c]);
                g.fillRect((int)(off.x+(x*sqrSize)), (int)(off.y+(y*sqrSize)),
                    (int)sqrSize, (int)sqrSize);
                //binary flip
                c=c==0?1:0;
            }
            //again so we have chessboard rather than stripes
            c=c==0?1:0;
        }
        g.setColor(new Color(0x40,0x40,0x40));
        g.fillRect(off.x, off.y+(int)(sqrSize*8), size.width, 1000);
        
        //pieces
        Color[] pcolors={new Color(0xff, 0xff, 0xff), new Color(0x00, 0x00, 0x00)};
        Font font=new Font("serif", Font.BOLD, (int)sqrSize);
        g.setFont(font);
        FontMetrics metrics=g.getFontMetrics(font);
        for(int x=0; x<8; x++)
        {
            for(int y=0; y<8; y++)
            {
                 g.setColor(state[x][y].color?pcolors[1]:pcolors[0]);
                 int strWidth=metrics.stringWidth(state[x][y].icon+"");
                 int hgt=metrics.getHeight();
                 g.drawString(state[x][y].icon+"",
                    (int)(off.x+(x*sqrSize)+((sqrSize-strWidth)/2)), (int)(off.y+(((y+1)*sqrSize)+((sqrSize-hgt)/2))));
            }
        }
        //turn indicator
        g.setColor(turn?pcolors[1]:pcolors[0]);
        g.fillRect(off.x+(int)(8*sqrSize), off.y, size.width-(int)(8*sqrSize), (int)sqrSize/2);
        g.setFont(new Font("serif", Font.BOLD, (int)(sqrSize/4)));
        g.setColor(turn?pcolors[0]:pcolors[1]);
        if(!winnerFound)
            g.drawString((turn?"Black":"White")+" to play", off.x+(int)(8*sqrSize), off.y+(int)(sqrSize/4));
        else
        {
            if(winner==-1)
                g.drawString("The game has ended in stalemate", off.x+(int)(8*sqrSize), off.y+(int)(sqrSize/4));
            else
                g.drawString((winner==1?"Black":"White")+" has won!", off.x+(int)(8*sqrSize), off.y+(int)(sqrSize/4));
            boolean hov=(context.absHover.x>=size.width-(int)(sqrSize)
                &&context.absHover.x<=size.width
                &&context.absHover.y>=0
                &&context.absHover.y<=(int)(sqrSize/2));
            boolean click=(context.absClick.x>=size.width-(int)(sqrSize)
                &&context.absClick.x<=size.width
                &&context.absClick.y>=0
                &&context.absClick.y<=(int)(sqrSize/2));
            Font btnFont=new Font("serif", Font.BOLD, (int)(sqrSize/5));
            g.setFont(btnFont);
            metrics=g.getFontMetrics(btnFont);
            Color btnColor=new Color(hov?128:255,0,0);
            g.setColor(btnColor);
            g.fillRect(off.x+size.width-(int)(sqrSize), off.y, (int)(sqrSize), (int)(sqrSize/2));
            g.setColor(new Color(0,0,0));
            int strWidth=metrics.stringWidth("reset");
            int hgt=metrics.getHeight();
            g.drawString("reset", off.x+size.width-(int)(sqrSize)+((int)(sqrSize)-strWidth)/2,
                off.y+((int)(sqrSize/2)-((int)(sqrSize/2)-hgt)/2)-hgt/2);
            if(click)
                Main.running=false;
        }
        //piece stats
        if(-1<context.hover.x && context.hover.x<8 &&
           -1<context.hover.y && context.hover.y<8 )
        {
            int statx=size.height+off.x;
            g.setColor(new Color(0,0,0));
            g.setFont(new Font("serif", Font.BOLD, (int)(sqrSize/2)));
            g.drawString(state[context.hover.x][context.hover.y].name, statx, (int)sqrSize+off.y);
            g.setFont(new Font("serif", Font.BOLD, (int)(sqrSize/4)));
            for(int i=0; i<state[context.hover.x][context.hover.y].kills.length; i++)
            {
                g.setColor(new Color(0,0,0));
                g.drawString(state[context.hover.x][context.hover.y].kills[i].name,
                statx,
                ((int)(sqrSize/3))*(i+4)+off.y);
                g.setColor(new Color(0xff, 0x00, 0x00, 0x80));
                g.fillOval(
                    (int)(off.x+(state[context.hover.x][context.hover.y].kills[i].startPos.x
                    *sqrSize)+((sqrSize*3)/8)),
                    (int)(off.y+(state[context.hover.x][context.hover.y].kills[i].startPos.y
                    *sqrSize)+((sqrSize*3)/8)),
                    (int)sqrSize/4, (int)sqrSize/4);
            }
        }
        //hover highlight
        if(context.hover.x>=0&&context.hover.x<8
            &&context.hover.y>=0&&context.hover.y<8)
        {
            g.setColor(new Color(0x00, 0xff, 0x00, 0x80));
            //g.fillOval((int)(off.x+(context.hover.x*sqrSize)+((sqrSize*3)/8)),
            //    (int)(off.y+(context.hover.y*sqrSize)+((sqrSize*3)/8)),
            //    (int)sqrSize/4, (int)sqrSize/4);
        }
        //valid moves
        if(!winnerFound)
        {
            if(Main.f.AILevel[turn?0:1]==0)
            {
                if(context.click.x>=0&&context.click.x<8
                    &&context.click.y>=0&&context.click.y<8)
                {
                    if(!moverSelected&&turn==state[context.click.x][context.click.y].color)
                    {
                        moverSelected=true;
                        mover=new Point(context.click.x, context.click.y);
                    }
                }
                Vector<Move> validMoves=new Vector<Move>();
                if(mover.x!=8)
                    validMoves=state[mover.x][mover.y].getMoves(state);
                if(mover.x!=8&&moverSelected)
                {
                    Iterator move_iter=validMoves.iterator();
                    while(move_iter.hasNext())
                    {
                        Point p=((Move)move_iter.next()).to;
                        
                        g.setColor(new Color(0xff, 0x00, 0xff, 0x80));
                        g.fillOval((int)(off.x+(p.x*sqrSize)+((sqrSize*3)/8)),
                            (int)(off.y+(p.y*sqrSize)+((sqrSize*3)/8)),
                            (int)sqrSize/4, (int)sqrSize/4);
                    }
                    /*
                    for(int x=0; x<8; x++)
                    for(int y=0; y<8; y++)
                    {
                        if(validMoves[x][y])
                        {
                            g.setColor(new Color(0xff, 0x00, 0xff, 0x80));
                            g.fillOval((int)(off.x+(x*sqrSize)+((sqrSize*3)/8)),
                                (int)(off.y+(y*sqrSize)+((sqrSize*3)/8)),
                                (int)sqrSize/4, (int)sqrSize/4);
                            
                        }
                    }
                    */
                }
                //make move
                if(moverSelected&&(mover.x!=context.click.x||mover.y!=context.click.y))
                {
                    //deselect mover
                    //dont reset position yet, we need it!
                    moverSelected=false;
                    //check if the move was valid
                    Vector<Move> mvs=state[mover.x][mover.y].getMoves(state);
                    Iterator move_iter=mvs.iterator();
                    while(move_iter.hasNext())
                    {
                        Move m=(Move)move_iter.next();
                        Point p=m.to;
                        if(p.x==context.click.x && p.y==context.click.y)
                        {
                            try
                            {
                                Main.f.c.board=makeMove(m);
                            }
                            catch(CloneNotSupportedException e){}
                            
                            //reset mover position
                            mover=new Point(8,0);
                            Main.f.c.click=new Point(8,0);
                            break;
                        }
                    }
                }
            }
            else
            {
                new Thread()
                {
                    public void run()
                    {
                        try
                        {
                            if(!Main.f.c.board.thinking)
                            {
                                Main.f.c.board.thinking=true;
                                Main.f.c.board=makeMove(AI.getAIMove(Main.f.c.board, Main.f.AILevel[turn?0:1], !turn));
                                Main.f.c.board.thinking=false;
                            }
                        }
                        catch(CloneNotSupportedException e){}
                    }
                }.start();
            }
        }
    }
    /*
    Board clone()
    {
        Board c=new Board();
        return c;
    }
    */
    Board makeMove(Move move)
    throws CloneNotSupportedException
    {
        Board b=deep_clone();
        
        boolean killThisTurn=false;
        boolean moveMade=false;
        Piece[] respawn=new Piece[0];
        
        //flip turn
        b.turn=!b.turn;
        //if we have captured
        if(b.state[move.to.x][move.to.y].type!=-1)
        {
            b.state[move.from.x][move.from.y].addKill(b.state[move.to.x][move.to.y]);
            killThisTurn=true;
            respawn=concat(respawn,b.state[move.to.x][move.to.y].kills);
        }
        //check if we captured via en passant
        try
        {
            if(b.state[move.from.x][move.from.y].type==0
                &&b.state[move.to.x][move.to.y+(b.state[move.from.x][move.from.y].color?-1:1)].type==0
                &&b.state[move.to.x][move.to.y+(b.state[move.from.x][move.from.y].color?-1:1)].name
                .equals(
                prevState[move.to.x][move.to.y+(b.state[move.from.x][move.from.y].color?1:-1)].name))
            {
                b.state[move.from.x][move.from.y].addKill(b.state[move.to.x][move.to.y+(state[move.from.x][move.from.y].color?-1:1)]);
                killThisTurn=true;
                //line not needed?
                respawn=concat(respawn,b.state[move.to.x][move.to.y+(state[move.from.x][move.from.y].color?-1:1)].kills);
                b.state[move.to.x][move.to.y+(b.state[move.from.x][move.from.y].color?-1:1)]=new Empty();
            }
        }
        catch(ArrayIndexOutOfBoundsException e){}
        //actually move the piece
        b.state[move.to.x][move.to.y]=b.state[move.from.x][move.from.y];
        b.state[move.to.x][move.to.y].pos=
            new Point(move.to.x,move.to.y);
        b.state[move.to.x][move.to.y].movesMade++;
        b.state[move.from.x][move.from.y]=new Empty();
        
        for(int i=0; i<respawn.length; i++)
        {
            //System.out.println(respawn[i].kills.length);
            if(b.state[respawn[i].startPos.x][respawn[i].startPos.y].type!=-1)
            {
                //we killed something by spawning in
                respawn=concat(respawn, b.state[respawn[i].startPos.x][respawn[i].startPos.y].kills);
                respawn[i].kills=new Piece[0];
                respawn[i].addKill(b.state[respawn[i].startPos.x][respawn[i].startPos.y]);
                //state[respawn[i].startPos.x][respawn[i].startPos.y].kills=new Piece[0];
                respawn[i].alreadyClearedKillList=true;
            }
            b.state[respawn[i].startPos.x][respawn[i].startPos.y]=respawn[i];
            b.state[respawn[i].startPos.x][respawn[i].startPos.y].pos=b.state[respawn[i].startPos.x][respawn[i].startPos.y].startPos;
            if(!b.state[respawn[i].startPos.x][respawn[i].startPos.y].alreadyClearedKillList)
                b.state[respawn[i].startPos.x][respawn[i].startPos.y].kills=new Piece[0];
            b.state[respawn[i].startPos.x][respawn[i].startPos.y].alreadyClearedKillList=false;
            b.state[respawn[i].startPos.x][respawn[i].startPos.y].movesMade=0;
        }
        //update previous state
        if(moveMade)
        for(int ix=0; ix<8; ix++)
        for(int iy=0; iy<8; iy++)
        {
            b.prevState[ix][iy]=b.idontactuallyknowwhatimdoing[ix][iy];
            b.idontactuallyknowwhatimdoing[ix][iy]=b.state[ix][iy];
        }
        
        b.checkWin();
        
        return b;
    }
    
    Board deep_clone()
    throws CloneNotSupportedException
    {
        Board b=(Board)this.clone();
        b.mover=(Point)b.mover.clone();
        
        b.state=b.state.clone();
        b.idontactuallyknowwhatimdoing=b.idontactuallyknowwhatimdoing.clone();
        b.prevState=b.prevState.clone();
        
        for(int f=0; f<8; f++)
        {
            b.state[f]=b.state[f].clone();
            b.idontactuallyknowwhatimdoing[f]=b.idontactuallyknowwhatimdoing[f].clone();
            b.prevState[f]=b.prevState[f].clone();
            for(int r=0; r<8; r++)
            {
                b.state[f][r]=b.state[f][r].deep_clone();
                b.idontactuallyknowwhatimdoing[f][r]=b.idontactuallyknowwhatimdoing[f][r].deep_clone();
                b.prevState[f][r]=b.prevState[f][r].deep_clone();
            }
        }
        return b;
    }
    
    int checkWin()
    {
        boolean whiteKingFound=false;
        boolean blackKingFound=false;
        for(int x=0; x<8; x++)
        for(int y=0; y<8; y++)
        {
            if(state[x][y].type==5)//5==king
            {
                if(state[x][y].color)
                    blackKingFound=true;
                else
                    whiteKingFound=true;
            }
        }
        if(!(blackKingFound&&whiteKingFound))
        {
            winnerFound=true;
            if(blackKingFound)
            {
                winner=1;
                return 1;
            }
            if(whiteKingFound)
            {
                winner=0;
                return 0;
            }
        }
        return -1;
    }
    Vector<Move> getAllMoves()
    {
        Vector<Move> valid=new Vector<Move>();
        
        for(int x=0; x<8; x++)
        for(int y=0; y<8; y++)
            if(state[x][y].type!=-1 && state[x][y].color==turn)
                valid.addAll(state[x][y].getMoves(state));
        return valid;
    }
}
