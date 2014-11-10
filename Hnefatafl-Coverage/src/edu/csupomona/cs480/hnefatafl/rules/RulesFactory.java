package edu.csupomona.cs480.hnefatafl.rules;

import java.awt.Point;

import edu.csupomona.cs480.hnefatafl.rules.MoveImpl;
import edu.csupomona.cs480.hnefatafl.rules.BoardImpl;

import edu.csupomona.cs480.hnefatafl.interfaces.*;

public class RulesFactory {
	public static final int MAX_SIZE = 11;
	public static final int MIN_SIZE = 0;
	
	private static RulesFactory defaultInstance = new RulesFactory();
	
	/** Allows test cases and others to get at the default RulesFactory */
	public static RulesFactory getDefault() { return defaultInstance; }

	/** Creates a Move from the given Point representing source and destination
	 * 
	 * Precondition: source and destination must have number values
	 * in the range 1..11 inclusive (Note that we are not starting from zero;
	 * this is the board game convention).
	 *
	 * @exception IllegalArgumentException if either of the Points is not in range
	 */
	public MoveImpl makeMove(Point source, Point destination) throws IllegalMoveException
	{
		if (source.x < MIN_SIZE || source.x > MAX_SIZE)
			throw new IllegalMoveException("Invalid x point on source!");
        if (source.y < MIN_SIZE || source.y > MAX_SIZE)
			throw new IllegalMoveException("Invalid y point on source!");
               
        if (destination.x < MIN_SIZE || destination.x > MAX_SIZE) 
			throw new IllegalMoveException("Invalid x point on destination!");
        if (destination.y < MIN_SIZE || destination.y > MAX_SIZE)
			throw new IllegalMoveException("Invalid y point on destination!");
                      
        return new MoveImpl(source, destination);
	}
	
	/** Creates a Move from the point at x1,y1 to the point at x2, y2
	 * 
	 * Precondition: all arguments must be in the range 1..11 inclusive
	 * (Note that we are not starting from zero;
	 * this is the board game convention).
	 *
	 * @exception IllegalArgumentException if either of the Points is not in range
	 */
	public MoveImpl makeMove(int x1, int y1, int x2, int y2) {
		return makeMove(new Point(x1, y1), new Point(x2, y2));
	}
	
	/** Creates the initial Board for a Hnefatafl game, with all pieces in
	 * their initial locations
	 */
	public BoardImpl makeInitialBoard() {
		BoardImpl emptyBoard = makeBoard();
		
		//Black pieces in top area
		emptyBoard.addPieceAt(Piece.BlackWarrior, new Point(4,1));
		emptyBoard.addPieceAt(Piece.BlackWarrior, new Point(5,1));
		emptyBoard.addPieceAt(Piece.BlackWarrior, new Point(6,1));
		emptyBoard.addPieceAt(Piece.BlackWarrior, new Point(7,1));
		emptyBoard.addPieceAt(Piece.BlackWarrior, new Point(8,1));
		emptyBoard.addPieceAt(Piece.BlackWarrior, new Point(6,2));
                
        //Black pieces in left area
        emptyBoard.addPieceAt(Piece.BlackWarrior, new Point(1,4));
        emptyBoard.addPieceAt(Piece.BlackWarrior, new Point(1,5));
        emptyBoard.addPieceAt(Piece.BlackWarrior, new Point(1,6));
        emptyBoard.addPieceAt(Piece.BlackWarrior, new Point(1,7));
        emptyBoard.addPieceAt(Piece.BlackWarrior, new Point(1,8));
        emptyBoard.addPieceAt(Piece.BlackWarrior, new Point(2,6));
                
        //Black pieces in right area
        emptyBoard.addPieceAt(Piece.BlackWarrior, new Point(11,4));
        emptyBoard.addPieceAt(Piece.BlackWarrior, new Point(11,5));
        emptyBoard.addPieceAt(Piece.BlackWarrior, new Point(11,6));
        emptyBoard.addPieceAt(Piece.BlackWarrior, new Point(11,7));
        emptyBoard.addPieceAt(Piece.BlackWarrior, new Point(11,8));
        emptyBoard.addPieceAt(Piece.BlackWarrior, new Point(10,6));
                
        //Black pieces in bottom area
        emptyBoard.addPieceAt(Piece.BlackWarrior, new Point(4,11));
        emptyBoard.addPieceAt(Piece.BlackWarrior, new Point(5,11));
        emptyBoard.addPieceAt(Piece.BlackWarrior, new Point(6,11));
        emptyBoard.addPieceAt(Piece.BlackWarrior, new Point(7,11));
        emptyBoard.addPieceAt(Piece.BlackWarrior, new Point(8,11));
        emptyBoard.addPieceAt(Piece.BlackWarrior, new Point(6,10));
                
        //White pieces
        emptyBoard.addPieceAt(Piece.WhiteWarrior, new Point(6,4));
        emptyBoard.addPieceAt(Piece.WhiteWarrior, new Point(5,5));
        emptyBoard.addPieceAt(Piece.WhiteWarrior, new Point(6,5));
        emptyBoard.addPieceAt(Piece.WhiteWarrior, new Point(7,5));
        emptyBoard.addPieceAt(Piece.WhiteWarrior, new Point(4,6));
        emptyBoard.addPieceAt(Piece.WhiteWarrior, new Point(5,6));
        emptyBoard.addPieceAt(Piece.WhiteKing, new Point(6,6));
        emptyBoard.addPieceAt(Piece.WhiteWarrior, new Point(7,6));
        emptyBoard.addPieceAt(Piece.WhiteWarrior, new Point(8,6));
        emptyBoard.addPieceAt(Piece.WhiteWarrior, new Point(5,7));
        emptyBoard.addPieceAt(Piece.WhiteWarrior, new Point(6,7));
        emptyBoard.addPieceAt(Piece.WhiteWarrior, new Point(7,7));
        emptyBoard.addPieceAt(Piece.WhiteWarrior, new Point(6,8));
                
        return emptyBoard;
	}
			

	/** Creates an empty Board, with no pieces on it.  Pieces must be added
	 * before the board is playable.  We provide this method separately from
	 * makeInitialBoard to facilitate testability.
	 */
	public BoardImpl makeBoard() {
		return new BoardImpl();
	}

}
