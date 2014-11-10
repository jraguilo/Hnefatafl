package edu.csupomona.cs480.hnefatafl.interfaces;

/** Thrown when an illegal move is attempted
 * 
 * @author aldrich
 *
 */
public class IllegalMoveException extends RuntimeException {

    public IllegalMoveException() {
    	super();
    }

    public IllegalMoveException(String message) {
    	super(message);
    }

	private static final long serialVersionUID = 5957656323860360088L;

}
