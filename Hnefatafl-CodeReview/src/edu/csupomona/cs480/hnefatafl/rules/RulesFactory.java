package edu.csupomona.cs480.hnefatafl.rules;

import java.awt.Point;

import edu.csupomona.cs480.hnefatafl.interfaces.Move;
import edu.csupomona.cs480.hnefatafl.interfaces.Piece;
import edu.csupomona.cs480.hnefatafl.interfaces.Board;

public class RulesFactory {
	private static RulesFactory defaultInstance = new RulesFactory();

	public static final int BOARD_SIZE = 11;
	
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
	public Move makeMove(Point source, Point destination) throws IllegalArgumentException {
		if (source.x < 1 || source.x > BOARD_SIZE)
			throw new IllegalArgumentException("Illegal x point on source");
		if (source.y < 1 || source.y > BOARD_SIZE)
			throw new IllegalArgumentException("Illegal y point on source");
		if (destination.x < 1 || destination.x > BOARD_SIZE)
			throw new IllegalArgumentException("Illegal x point on destination");
		if (destination.y < 1 || destination.y > BOARD_SIZE)
			throw new IllegalArgumentException("Illegal y point on destination");
		
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
	public Move makeMove(int x1, int y1, int x2, int y2) {
		return makeMove(new Point(x1, y1), new Point(x2, y2));
	}
	
	/** Creates the initial Position for a Hnefatafl game, with all pieces in
	 * their initial locations
	 */
	public Board makeInitialBoard() {
		Board startPos = makeBoard();
		int center = BOARD_SIZE / 2 + 1;
		
		//set the white warriors
		for (int row = center - 1; row <= center + 1; row++)
			for (int col = center - 1; col <= center + 1; col++)
				if (row != center || row != col)
					startPos.addPieceAt(Piece.WhiteWarrior, new Point(row, col));
		startPos.addPieceAt(Piece.WhiteWarrior, new Point(center - 2, center));
		startPos.addPieceAt(Piece.WhiteWarrior, new Point(center + 2, center));
		startPos.addPieceAt(Piece.WhiteWarrior, new Point(center, center - 2));
		startPos.addPieceAt(Piece.WhiteWarrior, new Point(center, center + 2));
		
		//set the king
		startPos.addPieceAt(Piece.WhiteKing, new Point(center, center));
		
		//set the black warriors
		for (int ndx = center - 2; ndx <= center + 2; ndx++) {
			startPos.addPieceAt(Piece.BlackWarrior, new Point(1, ndx));
			startPos.addPieceAt(Piece.BlackWarrior, new Point(BOARD_SIZE, ndx));
			startPos.addPieceAt(Piece.BlackWarrior, new Point(ndx, 1));
			startPos.addPieceAt(Piece.BlackWarrior, new Point(ndx, BOARD_SIZE));
		}
		startPos.addPieceAt(Piece.BlackWarrior, new Point(2, center));
		startPos.addPieceAt(Piece.BlackWarrior, new Point(BOARD_SIZE - 1, center));
		startPos.addPieceAt(Piece.BlackWarrior, new Point(center, 2));
		startPos.addPieceAt(Piece.BlackWarrior, new Point(center, BOARD_SIZE - 1));
		
		return startPos;
	}

	/** Creates an empty Position, with no pieces in it.  Pieces must be added
	 * before the position is playable.  We provide this method separately from
	 * makeInitialPosition to facilitate testability.
	 */
	public Board makeBoard() {
		return new BoardImpl(BOARD_SIZE);
	}

}
