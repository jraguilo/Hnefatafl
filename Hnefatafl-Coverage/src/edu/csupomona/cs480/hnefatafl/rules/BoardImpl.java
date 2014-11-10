package edu.csupomona.cs480.hnefatafl.rules;

import edu.csupomona.cs480.hnefatafl.interfaces.Board;
import edu.csupomona.cs480.hnefatafl.interfaces.Color;
import edu.csupomona.cs480.hnefatafl.interfaces.IllegalMoveException;
import edu.csupomona.cs480.hnefatafl.interfaces.Move;
import edu.csupomona.cs480.hnefatafl.interfaces.Piece;

import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

public class BoardImpl implements Board {
	private List<Point> position;
	private List<Piece> gamePiece;
	private List<Point> temp;
	private List<Move> moveList;
	
	public BoardImpl() {
		position = new LinkedList<Point>();
		gamePiece = new LinkedList<Piece>();
		temp = new LinkedList<Point>();
		moveList = new LinkedList<Move>();
	}
	
	/** Checks if this board represents a victory for one side or the other.
	 * 
	 * @return White or Black if that color has won; null if no winner
	 */
	public Color checkForVictory() {
		// Finds position of the King
		Point kingPosition = getPieceLocations(Piece.WhiteKing).get(0);
		
		// White Victory
		if(kingPosition.equals(new Point(1,1)) || 
				kingPosition.equals(new Point(1,11)) || 
				kingPosition.equals(new Point(11,1)) || 
				kingPosition.equals(new Point(11,11)))
			return Color.White;
		
		// King is in a sandwich position
		List<Point> blackPositionList = getPieceLocations(Piece.BlackWarrior);
		Point blackPosition;
		//topPosition = king is above this piece
		//bottomPosition = king is below this piece
		//rightPosition = king is to the right of this piece
		//leftPosition = king is to the left of this piece
		boolean topPosition = false, bottomPosition = false, rightPosition = false, leftPosition = false;
		final int CENTER_POSITION =  6;
		
		for (int i = 0; i < blackPositionList.size(); i++)
		{
			blackPosition = blackPositionList.get(i);
			
			if (blackPosition.getX() == kingPosition.getX() && blackPosition.getY() == kingPosition.getY()-1) 
				topPosition = true;
			
			else if (blackPosition.getX() == kingPosition.getX() && blackPosition.getY() == kingPosition.getY()+1) 
				bottomPosition = true;
			
			else if (blackPosition.getX() == kingPosition.getX()-1 && blackPosition.getY() == kingPosition.getY()) 
				rightPosition = true;
			
			else if (blackPosition.getX() == kingPosition.getX()+1 && blackPosition.getY() == kingPosition.getY()) 
				leftPosition = true;
		}
		
		//King in the center
		if (topPosition && bottomPosition && rightPosition && leftPosition) return Color.Black;
		
		//King with back against his own center castle
		//king below the castle or at top edge
		else if (topPosition && rightPosition && leftPosition)
		{
			if ((kingPosition.getX() == CENTER_POSITION && kingPosition.getY() == CENTER_POSITION-1) ||
			      kingPosition.getY() == 11)
				return Color.Black;
		}	
		//king is above castle or at bottom edge
		else if (bottomPosition && rightPosition && leftPosition)
		{
			if ((kingPosition.getX() == CENTER_POSITION && kingPosition.getY() == CENTER_POSITION+1) ||
			      kingPosition.getY() == 1)
				return Color.Black;
		}
		//king to the right of the castle or at left edge
		else if (topPosition && bottomPosition && leftPosition )
		{
			if ((kingPosition.getX() == CENTER_POSITION+1 && kingPosition.getY() == CENTER_POSITION) ||
			      kingPosition.getX() == 1)
				return Color.Black;
		}
		//king to the left of the castle or at right edge
		else if (topPosition && bottomPosition && rightPosition)
		{
			if ((kingPosition.getX() == CENTER_POSITION-1 && kingPosition.getY() == CENTER_POSITION) ||
			      kingPosition.getX() == 11)
				return Color.Black;
		}
		//king surrounded by attacker on top and right, left edge or bottom edge, and bottom-left castle
		else if (topPosition && rightPosition) {
			if ((kingPosition.getX() == 1 && kingPosition.getY() == 2) ||
				(kingPosition.getX() == 2 && kingPosition.getY() == 1))
				return Color.Black;
		}
		//king surrounded by attacker on bottom and right, left edge or top edge, and top-left castle
		else if (bottomPosition && rightPosition) {
			if ((kingPosition.getX() == 1 && kingPosition.getY() == 10) ||
			    (kingPosition.getX() == 2 && kingPosition.getY() == 11))
				return Color.Black;
		}
		//king surrounded by attacker on top and left, right edge or bottom edge, and bottom-left castle
		else if (topPosition && leftPosition) {
			if ((kingPosition.getX() == 10 && kingPosition.getY() == 1) ||
				(kingPosition.getX() == 11 && kingPosition.getY() == 2))
				return Color.Black;
		}
		//king surrounded by attacker on bottom and left, right edge or top edge, and top-left castle
		else if (bottomPosition && leftPosition) {
			if ((kingPosition.getX() == 10 && kingPosition.getY() == 11) ||
				(kingPosition.getX() == 11 && kingPosition.getY() == 10))
				return Color.Black;
		}
		// No Victory
		return null;
	}
	
	
	/** Changes this board according to the Move specified.
	 *
	 * @exception IllegalMoveException if this move is not legal
	 */
	public void move(Move m) throws IllegalArgumentException {
      // Getting points source and destination
      Point source = m.getSource();
      Point destination = m.getDestination();
            
      // Checks if destination is possible
      if((int)destination.getX() < 1 || (int)destination.getX() > 11 || (int)destination.getY() < 1 || (int)destination.getY() > 11)
         throw new IllegalMoveException("Not within Range");
            
      // Checks what piece is on position source
      Color color;
      if(getPieceAt(source) == Piece.BlackWarrior)
         color = Color.Black;
      else
         color = Color.White;
      
      // Use isLegalFor to see if move is possible
      int index = position.indexOf(source);
      if(isLegalFor(m,color)) {        
         position.set(index,destination);
         
         //handles removals in x direction
         if (destination.x-1>0
               && position.contains(new Point(destination.x-1,destination.y))){
            Point p1=new Point(destination.x-1,destination.y);
            if (pieceExists(p1) && getPieceAt(p1).getColor()!=color){
               Point rem=new Point(destination.x-2,destination.y);
               if (destination.x-2>0 && pieceExists(rem)){
                  if (getPieceAt(rem).getColor()==color){
                     Point p=new Point(rem.x+1,rem.y);
                     int curNdx=position.indexOf(p);
                     position.remove(curNdx);
                     gamePiece.remove(curNdx);
                  }
               }
            }
         }
         if (destination.x+1<=11
               && position.contains(new Point(destination.x+1,destination.y))){
            Point p1=new Point(destination.x+1,destination.y);
            if (pieceExists(p1) && getPieceAt(p1).getColor()!=color){
               Point rem=new Point(destination.x+2,destination.y);
               if (destination.x+2<11 && pieceExists(rem)){
                  if (getPieceAt(rem).getColor()==color){
                     Point p=new Point(rem.x-1,rem.y);
                     int curNdx=position.indexOf(p);
                     position.remove(curNdx);
                     gamePiece.remove(curNdx);
                  }
               }
            }
         }
         
         //handles removal in y direction
         if (destination.y-1>0
               && position.contains(new Point(destination.x,destination.y-1))){
            Point p1=new Point(destination.x,destination.y-1);
            if (pieceExists(p1) && getPieceAt(p1).getColor()!=color){
               Point rem=new Point(destination.x,destination.y-2);
               if (destination.y-2>0 && pieceExists(rem)){
                  if (getPieceAt(rem).getColor()==color){
                     Point p=new Point(rem.x,rem.y+1);
                     int curNdx=position.indexOf(p);
                     position.remove(curNdx);
                     gamePiece.remove(curNdx);
                  }
               }
            }
         }
         if (destination.y+1<=11
               && position.contains(new Point(destination.x,destination.y+1))){
            Point p1=new Point(destination.x,destination.y+1);
            if (pieceExists(p1) && getPieceAt(p1).getColor()!=color)
            {
               Point rem=new Point(destination.x,destination.y+2);
               if (destination.y+2<11 && pieceExists(rem)){
                  if (getPieceAt(rem).getColor()==color){
                     Point p=new Point(rem.x,rem.y-1);
                     int curNdx=position.indexOf(p);
                     position.remove(curNdx);
                     gamePiece.remove(curNdx);
                  }
               }
            }
         }
      }
      else 
         throw new IllegalMoveException("Not within Range");
	}
	/** Helper method for determining if a piece exists in list
	 */
	private boolean pieceExists(Point pt){
		return (getPieceAt(pt)!=null);
	}
	
	/** Tests if a move is legal from this board for the color given
	 */
    public boolean isLegalFor(Move m, Color color) {
        // Getting points for source and destination
        Point source = m.getSource();
        Point destination = m.getDestination();
       
        // Checks if piece is moving into the CASTLE spots
        if(getPieceAt(source) != Piece.WhiteKing) {
                if(destination.equals(new Point(1,1)) ||
                                destination.equals(new Point(1,11)) ||
                                destination.equals(new Point(11,1)) ||
                                destination.equals(new Point(11,11)) ||
                                destination.equals(new Point(6,6)))
                        return false;
        }
       
        // Checks if path is clear for moving
        boolean emptySpot = true;
		
        // Checks the spaces between source and destination that are on the same x-axis
        if(source.getX() == destination.getX()) {
			if(source.getY() < destination.getY()) {
				for(int i = ((int)source.getY() + 1) ; i <= (int)destination.getY() ; i++) {
                                if(getPieceAt(new Point((int)source.getX(), i)) != null) {
                                        emptySpot = false;
                                        break;
                                }
                        }
                }
                else {
				for(int i = ((int)source.getY() - 1) ; i >= (int)destination.getY() ; i--) {
                                if(getPieceAt(new Point((int)source.getX(), i)) != null) {
                                        emptySpot = false;
                                        break;
                                }
                        }
                }
        }
        // Checks the spaces between source and destination that are on the same y-axis
		else if(source.getY() == destination.getY()) {
			if(source.getX() < destination.getX()) {
				for(int i = ((int)source.getX() + 1) ; i <= (int)destination.getX() ; i++) {
                                if(getPieceAt(new Point(i, (int)source.getY())) != null) {
                                        emptySpot = false;
                                        break;
                                }
                        }
                }
                else {
				for(int i = ((int)source.getX() - 1) ; i >= (int)destination.getX() ; i--) {
                                if(getPieceAt(new Point(i, (int)source.getY())) != null) {
                                        emptySpot = false;
                                        break;
                                }
                        }
                }
        }
        if(!emptySpot) {
                return false;
        }
       
        return true;
}
	
	
	/** Returns the Piece at the given location on the board, or null
	 * if no piece is present
	 *
	 * @exception IllegalArgumentException if either coordinate of the point
	 * is not in the range 1..11
	 */
	public Piece getPieceAt(Point p) {
		// Checks if location is within range of 1-11
		if(p.getX() < 1 || p.getX() > 11 || p.getY() < 1 || p.getY() > 11)
			throw new IllegalArgumentException("Not within Range");
		
		// Returns piece at Point p
		int index = position.indexOf(p);
		if(index == -1)
			return null;
		return gamePiece.get(index);
	}

	
	/** Returns a list of the locations of the kind of piece passed
	 * in by the client.  If kindOfPiece is null, locations for pieces of
	 * ALL types should be returned.
	 */
	public List<Point> getPieceLocations(Piece kindOfPiece) {
		temp.clear();
		for(int i = 0 ; i < gamePiece.size() ; i++) {
			if(gamePiece.get(i).equals(kindOfPiece) || kindOfPiece == null)
				temp.add(position.get(i));
		}
		return temp;
	}
	
	
	/** Add a piece to the board; useful for setting up a board in
	 * testing.
	 * 
	 * @exception IllegalArgumentException if either coordinate of the point
	 * is not in the range 1..11
	 */
	public void addPieceAt(Piece kindOfPiece, Point location) {
		// Checks if location is within range of 1-11
		if(location.getX() < 1 || location.getX() > 11 || location.getY() < 1 || location.getY() > 11)
			throw new IllegalArgumentException("Not within Range");
		
		// Adding the piece and location to the parallel Lists
		position.add(location);
		gamePiece.add(kindOfPiece);
	}
	
	
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
	public List<Move> getLegalMovesFor(Color color) {
		// Makes a MoveList for all Black Pieces
		if(color == Color.Black) {
			for(int i = 0 ; i < gamePiece.size(); i++) {
				if(gamePiece.get(i) == Piece.BlackWarrior) {
					for(int j = 1 ; j <= 11 ; j++) {
						Point source = position.get(i);
						if(j != (int)source.getY()) {
							MoveImpl vertMove = new MoveImpl(source, new Point((int)source.getX(), j));
							if(isLegalFor(vertMove, color))
								moveList.add(vertMove);
						}
						if(j != (int)source.getX()) {
							MoveImpl horiMove = new MoveImpl(source, new Point(j, (int)source.getY()));
							if(isLegalFor(horiMove, color))
								moveList.add(horiMove);
						}
					}
				}
			}
		}
		// Makes a MoveList for all White Pieces
		else {
			for(int i = 0 ; i < gamePiece.size(); i++) {
				if(gamePiece.get(i) == Piece.WhiteWarrior || gamePiece.get(i) == Piece.WhiteKing) {
					for(int j = 1 ; j <= 11 ; j++) {
						Point source = position.get(i);
						if(j != (int)source.getY()) {
							MoveImpl vertMove = new MoveImpl(source, new Point((int)source.getX(), j));
							if(isLegalFor(vertMove, color))
								moveList.add(vertMove);
						}
						if(j != (int)source.getX()) {
							MoveImpl horiMove = new MoveImpl(source, new Point(j, (int)source.getY()));
							if(isLegalFor(horiMove, color))
								moveList.add(horiMove);
						}
					}
				}
			}
		}
		return moveList;
	}
	
	
	/** Makes a deep copy of this board */
	public Board clone() 
	{
		BoardImpl cloneBoard = new BoardImpl();
		
		List<Point> copyPos = new LinkedList<Point>();
		List<Piece> copyPiece = new LinkedList<Piece>();
		position.get(0);
		
		for (int i = 0; i < position.size(); i++)
		{
			copyPos.add(i, position.get(i));
			copyPiece.add(i, gamePiece.get(i));
		}
		cloneBoard.position = copyPos;
		cloneBoard.gamePiece = copyPiece;
		
		return cloneBoard;

	}
}