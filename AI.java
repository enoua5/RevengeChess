import java.awt.Point;
import java.util.Vector;
import java.util.Iterator;

public class AI
{
    //+ facors white, - favors black
    public static int static_eval(Board board)
    {
        int PAWN_VALUE=100;
        int BISHOP_VALUE=340;
        int ROOK_VALUE=500;
        int KNIGHT_VALUE=325;
        int QUEEN_VALUE=900;
        int KING_VALUE=5000;
        
        int black_bis_count=0;
        int white_bis_count=0;
            
        int score=0;
        for(int file=0; file<8; file++)
        for(int rank=0; rank<8; rank++)
        {
            Piece p=board.state[file][rank];
            int favor=p.color?-1:1;
            
            int p_value=0;
            switch(p.type)
            {
                case 0: //pawn
                {
                    p_value+=PAWN_VALUE;
                    //check for doubled pawns
                    //start on current rank to avoid redundancy
                    for(int pr=rank+1; pr<8; pr++)
                    {
                        if(board.state[file][pr].type==0 && board.state[file][pr].color==p.color)
                        {
                            p_value-=7;
                            break;
                        }
                    }
                    //check for isolated pawns
                    boolean isIsolated=true;
                    for(int pr=0; pr<8; pr++)
                    {
                        if(file>0)
                        if(board.state[file-1][pr].type==0 && board.state[file-1][pr].color==p.color)
                        {
                            isIsolated=false;
                            break;
                        }
                        if(file<7)
                        if(board.state[file+1][pr].type==0 && board.state[file+1][pr].color==p.color)
                        {
                            isIsolated=false;
                            break;
                        }
                    }
                    if(isIsolated) p_value-=2;
                    //check for passed pawns
                    if(p.color) //if black
                    {
                        boolean isPassed=true;
                        for(int pr=rank+1; pr<8; pr++)
                        for(int pf=0; pf<8; pf++)
                        {
                            if(board.state[pf][pr].type==0 && board.state[pf][pr].color==false)
                            {
                                isPassed=false;
                                break;
                            }
                        }
                        p_value+=rank*(isPassed?4:3);
                    }
                    else //if white
                    {
                        boolean isPassed=true;
                        for(int pr=rank-1; pr>=0; pr--)
                        for(int pf=0; pf<8; pf++)
                        {
                            if(board.state[pf][pr].type==0 && board.state[pf][pr].color==true)
                            {
                                isPassed=false;
                                break;
                            }
                        }
                        p_value+=(7-rank)*(isPassed?4:3);
                    }
                    if(file<=4)
                        p_value+=3.25*file;
                    else
                        p_value+=-3.25*file + 26;
                }
                break;
                case 1: //rook
                {
                    p_value+=ROOK_VALUE;
                    //king tropism
                    Point k=findKing(board, !p.color);
                    p_value+=Math.max(
                        7-Math.abs(file-k.x),
                        7-Math.abs(rank-k.y)
                    );
                    
                    if(p.color && rank==6)
                        p_value+=20;
                    if(!p.color && rank==1)
                        p_value+=20;
                        
                    //check file
                    boolean has_enemies=false;
                    boolean has_friends=false;
                    for(int pf=0; pf<8; pf++)
                    {
                        if(pf==file) continue;
                        
                        if(board.state[pf][rank].type==1)
                            if(board.state[pf][rank].color==p.color)
                                p_value+=7;
                        if(board.state[pf][rank].type==0)
                        {
                            if(board.state[pf][rank].color==p.color)
                                has_friends=true;
                            else has_enemies=true;
                        }
                    }
                    if(!has_enemies && !has_friends)
                        p_value+=10;
                    else if(!has_friends)
                        p_value+=3;
                }
                break;
                case 2: //knight
                {
                    p_value+=KNIGHT_VALUE;
                    //distance from king
                    Point k=findKing(board, !p.color);
                    p_value+=14-((7-Math.abs(file-k.x))+(7-Math.abs(rank-k.y)));
                    //distance from center
                    p_value+=(3*((7-Math.abs(file-3.5))+(7-Math.abs(rank-3.5))))-32;
                }
                break;
                case 3: //bishop
                {
                    p_value+=BISHOP_VALUE;
                    //check for double bishops
                    if(p.color)
                        if(++black_bis_count > 1)
                            p_value+=20;
                    else
                        if(++white_bis_count > 1)
                            p_value+=20;
                    //check for diagonally adj pawns
                    if(file<7 && rank<7)
                        if(board.state[file+1][rank+1].type==0)
                            p_value-=10;
                    if(file<7 && rank>0)
                        if(board.state[file+1][rank-1].type==0)
                            p_value-=10;
                    if(file>0 && rank<7)
                        if(board.state[file-1][rank+1].type==0)
                            p_value-=10;
                    if(file>0 && rank>0)
                        if(board.state[file-1][rank-1].type==0)
                            p_value-=10;
                }
                break;
                case 4: //queen
                {
                    p_value+=QUEEN_VALUE;
                    //king tropism
                    Point k=findKing(board, !p.color);
                    p_value+=5*Math.max(
                        7-Math.abs(file-k.x),
                        7-Math.abs(rank-k.y)
                    );
                }
                break;
                case 5: //king
                {
                    p_value+=KING_VALUE;
                    //eval king safety
                    int rs=0;
                    int re=8;
                    int fs=0;
                    int fe=8;
                    if(rank<=3)
                    {
                        rs=0;
                        re=4;
                    }
                    else
                    {
                        rs=4;
                        re=8;
                    }
                    if(file<=3)
                    {
                        fs=0;
                        fe=4;
                    }
                    else
                    {
                        fs=4;
                        fe=8;
                    }
                    int friends_in_quad=0;
                    int enemies_in_quad=0;
                    for(int pf=fs; pf<fe; pf++)
                    for(int pr=rs; pr<re; pr++)
                    {
                        if(pf==file && pr==rank) continue;
                        
                        if(board.state[pf][pr].type!=-1)
                        {
                            if(board.state[pf][pr].color==p.color)
                                friends_in_quad++;
                            else
                            {
                                enemies_in_quad++;
                                if(board.state[pf][pr].type==4)//queen
                                    enemies_in_quad+=2;
                            }
                        }
                    }
                    if(enemies_in_quad>friends_in_quad)
                        p_value-=5*(enemies_in_quad-friends_in_quad);
                }
                break;
            }
            score+=p_value*favor;
        }
        
        return score;
    }
    
    static Point findKing(Board board, boolean color)
    {
        for(int f=0; f<8; f++)
        for(int r=0; r<8; r++)
            if(board.state[f][r].type==5 && board.state[f][r].color==color)
                return new Point(f,r);
        return new Point(-1, -1);
    }
    
    static Move getAIMove(Board b, int depth, boolean maximizingPlayer)
    {
        Vector<Move> poss=b.getAllMoves();
        Move best_move=new Move(new Point(-1,-1), new Point(-1,-1));
        int best_score=maximizingPlayer?Integer.MIN_VALUE:Integer.MAX_VALUE;
        
        Iterator move=poss.iterator();
        while(move.hasNext())
        {
            Move testing_move=(Move)move.next();
            int score;
            try
            {
                score=minimax(b.makeMove(testing_move), depth-1, !maximizingPlayer);
            }
            catch(CloneNotSupportedException e)
            {
                score = maximizingPlayer?Integer.MIN_VALUE:Integer.MAX_VALUE;
            }
            if(maximizingPlayer)
            {
                if(score > best_score)
                {
                    best_score=score;
                    best_move=testing_move;
                }
            }
            else
            {
                if(score < best_score)
                {
                    best_score=score;
                    best_move=testing_move;
                }
            }
        }
        return best_move;
    }
    static int minimax(Board b, int depth, boolean maximizingPlayer)
    {
        return minimax(b, depth, maximizingPlayer, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
    static int minimax(Board b, int depth, boolean maximizingPlayer, int alpha, int beta)
    {
        if(depth<=0)
            return static_eval(b);
        if(b.winner!=-1)
            return static_eval(b)+(int)((depth*1.1)*(b.winner==0?10000:-10000));
        
        if(maximizingPlayer)
        {
            int maxScore=Integer.MIN_VALUE;
            
            Vector<Move> poss=b.getAllMoves();
            Iterator move=poss.iterator();
            while(move.hasNext())
            {
                Move testing_move=(Move)move.next();
                int score;
                try
                {
                    score=minimax(b.makeMove(testing_move), depth-1, false, alpha, beta);
                }
                catch(CloneNotSupportedException e)
                {
                    score=0;
                }
                maxScore=Math.max(maxScore, score);
                alpha=Math.max(alpha, maxScore);
                if(beta <= alpha) break;
                //break if we find "the one"
                if(maxScore >= 10000) break;
            }
            return maxScore;
        }
        
        else
        {
            int minScore=Integer.MAX_VALUE;
            
            Vector<Move> poss=b.getAllMoves();
            Iterator move=poss.iterator();
            while(move.hasNext())
            {
                Move testing_move=(Move)move.next();
                int score;
                try
                {
                    score=minimax(b.makeMove(testing_move), depth-1, true, alpha, beta);
                }
                catch(CloneNotSupportedException e)
                {
                    score=0;
                }
                minScore=Math.min(minScore, score);
                beta=Math.min(beta, minScore);
                if(beta <= alpha) break;
                //break if we find "the one"
                if(minScore <= -10000) break;
            }
            return minScore;
        }
    }
}
