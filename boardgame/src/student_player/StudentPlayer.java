package student_player;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import boardgame.Move;
import coordinates.Coord;
import coordinates.Coordinates;
import tablut.TablutBoardState;
import tablut.TablutBoardState.Piece;
import tablut.TablutMove;
import tablut.TablutPlayer;

/** A player file submitted by a student. */
public class StudentPlayer extends TablutPlayer {

    /**
     * You must modify this constructor to return your student number. This is
     * important, because this is what the code that runs the competition uses to
     * associate you with your agent. The constructor should do nothing else.
     */
    public StudentPlayer() {
        super("260622641");
    }
    
    public StudentPlayer(String name) {
    	super(name);
    }
    TablutMove bestMove;
    int DEPTH = 3;

    /**
     * This is the primary method that you need to implement. The ``boardState``
     * object contains the current state of the game, which your agent must use to
     * make decisions.
     */
    public Move chooseMove(TablutBoardState boardState) {
        
    	List<TablutMove> options = boardState.getAllLegalMoves();

        
        bestMove = options.get(((int) MyTools.getSomething())% (options.size()));

        int depth = DEPTH;
        int opponent = boardState.getOpponent();
        boolean isMax;
        if(opponent==1){
        	isMax = true;
        	//System.out.println("I'm max");
        }
        else{
        	isMax = false;
        	//System.out.println("I'm min");
        }
        //bestMove = 
        
        int bestvalue = minimax(boardState,isMax, depth,-10000,10000);
        
        
        //int minNumberOfOpponentPieces = boardState.getNumberPlayerPieces(opponent);
        //boolean moveCaptures = false;
        //System.out.println("minimax return value: "+bestvalue);
       
        
        
        
        return bestMove;
    }

	public int minimax(TablutBoardState boardState, boolean isMax, int depth, int alpha, int beta) {
		//System.out.println("isMax: "+isMax+" depth: "+depth+" alpha: "+alpha+" beta: "+beta);
		if (boardState.gameOver()){
			//System.out.println("reach endgame in minimax");
			if (boardState.getWinner() == boardState.MUSCOVITE) {
	            return 1000;
	        }else if (boardState.getWinner()==boardState.SWEDE){
	        	return -1000;
	        }else{
	        	return 0;
	        }
		}
		if (depth == 0){
			//System.out.println("reach bottom in minimax");
            return heuristic(boardState);
        }
		//int bestValue;

		if(isMax){
			int bestValue = Integer.MIN_VALUE;
			HashMap<Integer,TablutMove> moveScore = new HashMap<>();
			for(TablutMove move : boardState.getAllLegalMoves()){
				TablutBoardState cloneBS = (TablutBoardState) boardState.clone();
				cloneBS.processMove(move);
				int val = minimax(cloneBS,false,depth-1,alpha,beta);
				moveScore.put(new Integer(val),move); 
				bestValue = max(bestValue,val);
				alpha = max(alpha,bestValue);
				//System.out.println("alpha: "+alpha+", beta: "+beta);
				if (beta <= alpha){
					//System.out.println("prunning: alpha: "+alpha+" and beta: "+beta);
					break;
				}	
			}
			Integer index= Collections.max(moveScore.keySet());
			if(depth == DEPTH){
				//System.out.println("update move from max");
				bestMove = moveScore.get(index);
			}
			
            moveScore = null;
			return bestValue;
			
		}else{
			int bestValue = Integer.MAX_VALUE;
			HashMap<Integer,TablutMove> moveScore = new HashMap<>();
			for(TablutMove move : boardState.getAllLegalMoves()){
				TablutBoardState cloneBS = (TablutBoardState) boardState.clone();
				cloneBS.processMove(move);
				int val = minimax(cloneBS,true,depth-1,alpha,beta);
				moveScore.put(new Integer(val),move); 
				bestValue = min(bestValue,val);
				beta = min(beta,bestValue);
				//System.out.println("alpha: "+alpha+", beta: "+beta);
				if(beta<= alpha){
					//System.out.println("prunning: alpha: "+alpha+" and beta: "+beta);
					break;
				}
				
			}
			Integer index= Collections.min(moveScore.keySet());
			if(depth == DEPTH){
				//System.out.println("update move from min");
				bestMove = moveScore.get(index);
			}
            moveScore = null;
			return bestValue;
			
		}
		
		
		//return 0;
	}

	private int min(int a, int b) {
		
		return Math.min(a, b);
	}

	private int max(int a, int b) {
		
		return Math.max(a, b);
	}

	private int heuristic(TablutBoardState boardState) {
		int heuristic = 0;
		int Muscovite = boardState.getNumberPlayerPieces(TablutBoardState.MUSCOVITE);
		heuristic += Muscovite*5;
		int Swede = boardState.getNumberPlayerPieces(TablutBoardState.SWEDE);
		heuristic += Swede*(-5);
		Coord kingPos = boardState.getKingPosition();
        int dist = Coordinates.distanceToClosestCorner(kingPos);
        heuristic += dist*4;
        /*
        if(!Coordinates.isCenterOrNeighborCenter(kingPos)){
        	//heuristic += -10;
        	for(Coord neighbor : Coordinates.getNeighbors(kingPos)){
        		if(Piece.BLACK==boardState.getPieceAt(neighbor)){
        			heuristic += 20;
        		}
        			
        		
        	}
        }
        */
        //System.out.println("king is: "+dist);
		return heuristic;
	}
}