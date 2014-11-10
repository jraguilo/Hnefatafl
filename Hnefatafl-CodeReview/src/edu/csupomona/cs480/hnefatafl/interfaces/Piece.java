package edu.csupomona.cs480.hnefatafl.interfaces;

/** Represents the 3 kinds of piece in Hnefatafl--white pieces
 * and the Black team (attackers).
 * 
 * @author aldrich
 */
public enum Piece {
	BlackWarrior(Color.Black),
	WhiteWarrior(Color.White),
	WhiteKing(Color.White);
	
	private final Color color;
	Piece(Color c) { color = c; }
	
	/** Returns the Color of this Piece */
	public Color getColor() { return color; }
	
	/** Tests if this is a King */
	public boolean isKing() { return this == WhiteKing; }
}
