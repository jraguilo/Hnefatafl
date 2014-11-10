package edu.csupomona.cs480.hnefatafl.interfaces;

import java.awt.Point;

/** Represents a Move on the Hnefatafl board.
 * 
 * Clients can access the source and destination of
 * the move as Points.
 * 
 * equals() must return true for two Moves with the
 * same source and destination coordinates.
 * 
 * @author aldrich
 */
public interface Move {
	/** Returns the source square of this move */
	Point getSource();
	
	/** Returns the destination square of this move */
	Point getDestination();

}
