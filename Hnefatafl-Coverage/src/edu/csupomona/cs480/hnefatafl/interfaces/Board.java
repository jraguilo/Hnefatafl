package edu.csupomona.cs480.hnefatafl.interfaces;

import java.awt.Point;
import java.util.List;

/** Represents a particular configuration of a Hnefatafl board.
 *
 * Since Positions are mutated by move(), we need a way to copy them.
 * Therefore Position must be Cloneable.
 */ 
public interface Board extends Cloneable {
	/** Checks if this board represents a victory for one side or the other.
	 * 
	 * @return White or Black if that color has won; null if no winner
	 */
	public Color checkForVictory();
	
	
	/** Changes this board according to the Move specified.
	 *
	 * @exception IllegalMoveException if this move is not legal
	 */
	public void move(Move m) throws IllegalMoveException;
	
	/** Tests if a move is legal from this board for the color given
	 */
	public boolean isLegalFor(Move m, Color color);
	
	/** Returns the Piece at the given location on the board, or null
	 * if no piece is present
	 *
	 * @exception IllegalArgumentException if either coordinate of the point
	 * is not in the range 1..11
	 */
	public Piece getPieceAt(Point p);

	/** Returns a list of the locations of the kind of piece passed
	 * in by the client.  If kindOfPiece is null, locations for pieces of
	 * ALL types should be returned.
	 */
	public List<Point> getPieceLocations(Piece kindOfPiece);
	
	/** Add a piece to the board; useful for setting up a board in
	 * testing.
	 * 
	 * @exception IllegalArgumentException if either coordinate of the point
	 * is not in the range 1..11
	 */
	public void addPieceAt(Piece kindOfPiece, Point location);
	
	/** Returns all the legal moves for Color from this board.
	 * 
	 * Note that if we just called addPieceAt to do a custom board
	 * setup, getLegalMovesFor should consider moves of both colors,
	 * not just black (which moves first at the game's beginning).
	 * This helps to facilitate testing.
	 * 
	 * Invariant: for any member of the set returned by getLegalMovesFor
	 * on a Color, isLegalFor should return true on that Move and Color.
	 * 
	 * Note: in retrospect this should have returned a Set<Move> as there
	 * is no obvious ordering on the moves returned.  The course staff
	 * should clearly have done a better design inspection!
	 */
	public List<Move> getLegalMovesFor(Color color);
	
	/** Makes a deep copy of this board */
	public Board clone();
}
