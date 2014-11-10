package edu.csupomona.cs480.hnefatafl.rules;

import java.awt.Point;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import edu.csupomona.cs480.hnefatafl.interfaces.Color;
import edu.csupomona.cs480.hnefatafl.interfaces.IllegalMoveException;
import edu.csupomona.cs480.hnefatafl.interfaces.Move;
import edu.csupomona.cs480.hnefatafl.interfaces.Piece;
import edu.csupomona.cs480.hnefatafl.interfaces.Board;

public class BoardImpl implements Board {
	/*
	 * Stores pieces on the board. Null represents an empty location.
	 */
	private Piece[][] board;
	int boardSize;
	int center;
	private Map<Piece, List<Point>> pieceLocations;
	
	/**
	 * Makes an initial board with the pieces in the correct starting locations.
	 */
	public BoardImpl(int boardSize) {
		board = new Piece[boardSize][boardSize];
		this.boardSize = boardSize;
		this.center = boardSize / 2 + 1;
		pieceLocations = new HashMap<Piece, List<Point>>();
			
		pieceLocations.put(Piece.BlackWarrior, new LinkedList<Point>());
		pieceLocations.put(Piece.WhiteWarrior, new LinkedList<Point>());
		pieceLocations.put(Piece.WhiteKing, new LinkedList<Point>());
	}
	
	private void checkPointRange(Point point) {
		if (point.x < 1 || point.x > boardSize)
			throw new IllegalArgumentException("Illegal x point");
		if (point.y < 1 || point.y > boardSize)
			throw new IllegalArgumentException("Illegal y point");
	}
	
	@Override
	public void addPieceAt(Piece kindOfPiece, Point location) {
		checkPointRange(location);
		
		board[location.x - 1][location.y - 1] = kindOfPiece;
		pieceLocations.get(kindOfPiece).add(location);
	}

	@Override
	public Piece getPieceAt(Point p) {
		checkPointRange(p);
		return board[p.x - 1][p.y - 1];
	}

	@Override
	public List<Point> getPieceLocations(Piece kindOfPiece) {
		return new LinkedList<Point>(pieceLocations.get(kindOfPiece));
	}

	@Override
	public void move(Move m) throws IllegalMoveException {
		Piece piece = getPieceAt(m.getSource());
		
		if (!isLegalFor(m, piece.getColor()))
			throw new IllegalMoveException("Not a legal move");
		
		//move the piece
		board[m.getSource().x - 1][m.getSource().y - 1] = null;
		board[m.getDestination().x - 1][m.getDestination().y - 1] = piece;
		pieceLocations.get(piece).remove(m.getSource());
		pieceLocations.get(piece).add(m.getDestination());
		
		//capture pieces in each of four directions.
		Point capture, friend;
		
		//check right
		capture = new Point(m.getDestination().x + 1, m.getDestination().y);
		if (pieceOfOppositeColor(capture, piece.getColor())) {
			friend = new Point(m.getDestination().x + 2, m.getDestination().y);
			if (pieceOfMyColorOrCastle(friend, piece.getColor())) {
					Piece kindCaptured = board[capture.x - 1][capture.y - 1] ;
					board[capture.x - 1][capture.y - 1] = null;
					pieceLocations.get(kindCaptured).remove(capture);
			}
		}			
		
		//check left
		capture = new Point(m.getDestination().x - 1, m.getDestination().y);
		if (pieceOfOppositeColor(capture, piece.getColor())) {
			friend = new Point(m.getDestination().x - 2, m.getDestination().y);
			if (pieceOfMyColorOrCastle(friend, piece.getColor())) {
					Piece kindCaptured = board[capture.x - 1][capture.y - 1] ;
					board[capture.x - 1][capture.y - 1] = null;
					pieceLocations.get(kindCaptured).remove(capture);
			}
		}			
		
		//check up
		capture = new Point(m.getDestination().x, m.getDestination().y + 1);
		if (pieceOfOppositeColor(capture, piece.getColor())) {
			friend = new Point(m.getDestination().x, m.getDestination().y + 2);
			if (pieceOfMyColorOrCastle(friend, piece.getColor())) {
					Piece kindCaptured = board[capture.x - 1][capture.y - 1] ;
					board[capture.x - 1][capture.y - 1] = null;
					pieceLocations.get(kindCaptured).remove(capture);
			}
		}			
		
		//check down
		capture = new Point(m.getDestination().x, m.getDestination().y - 1);
		if (pieceOfOppositeColor(capture, piece.getColor())) {
			friend = new Point(m.getDestination().x, m.getDestination().y - 2);
			if (pieceOfMyColorOrCastle(friend, piece.getColor())) {
					Piece kindCaptured = board[capture.x - 1][capture.y - 1] ;
					board[capture.x - 1][capture.y - 1] = null;
					pieceLocations.get(kindCaptured).remove(capture);
			}
		}			
	}

	private boolean pieceOfMyColorOrCastle(Point point, Color color) {
		try {
			checkPointRange(point);
		}
		catch (IllegalArgumentException err) {
			return false;
		}
		
		Piece piece = getPieceAt(point);
		if (piece != null)
			return piece.getColor() == color;
		else
			return isCastle(point);
	}

	private boolean isCastle(Point point) {
		return (point.x == point.y && point.x == center) || isEscapeCastle(point);
	}

	private boolean isEscapeCastle(Point point) {
		return (point.x == 1 || point.x == boardSize) &&
			   (point.y == 1 || point.y == boardSize);
	}

 	private boolean pieceOfOppositeColor(Point point, Color color) {
		try {
			checkPointRange(point);
		}
		catch (IllegalArgumentException err) {
			return false;
		}
		
		Piece piece = getPieceAt(point);
		return piece != null && piece.getColor() != color && !piece.isKing();
	}

	@Override
	public Color checkForVictory() {
		Point king = getPieceLocations(Piece.WhiteKing).get(0);
		
		//king in edge castle: white won
		if (isEscapeCastle(king))
			return Color.White;

		//no more king moves: black won
		if (getOpenSpacesForKing(king).isEmpty())
			return Color.Black;
		
		return null;
	}

	@Override
	public List<Move> getLegalMovesFor(Color color) {
		List<Move> allMoves = new LinkedList<Move>();
		Piece warriorKind = color == Color.Black ? Piece.BlackWarrior : Piece.WhiteWarrior;
		List<Point> warriors = pieceLocations.get(warriorKind);
		
		for (Point warrior : warriors)
			allMoves.addAll(getOpenSpacesForWarrior(warrior));

		if (color == Color.White) {
			Point king = pieceLocations.get(Piece.WhiteKing).get(0);
			allMoves.addAll(getOpenSpacesForKing(king));
		}
		
		return allMoves;
	}

	@Override
	public boolean isLegalFor(Move m, Color color) {
		//check that position is legal
		try {
			checkPointRange(m.getSource());
			checkPointRange(m.getDestination());
		}
		catch (IllegalArgumentException err) {
			return false;
		}
		
		boolean sideways = m.getSource().y == m.getDestination().y;
		//check that the move is only up, down, left, or right
		if (m.getSource().x != m.getDestination().x && !sideways)
			return false;
		
		//check to make sure nothing is in the way
		if (sideways) {
			if (m.getDestination().x > m.getSource().x) { //move right
				for (int x = m.getSource().x + 1; x <= m.getDestination().x; x++)
					if (getPieceAt(new Point(x, m.getDestination().y)) != null)
						return false;
			}
			else {  //move left
				for (int x = m.getSource().x - 1; x >= m.getDestination().x; x--)
					if (getPieceAt(new Point(x, m.getDestination().y)) != null)
						return false;
			}
		}
		else {
			if (m.getDestination().y > m.getSource().y) { //move up
				for (int y = m.getSource().y + 1; y <= m.getDestination().y; y++)
					if (getPieceAt(new Point(m.getDestination().x, y)) != null)
						return false;
			}
			else {  //move left
				for (int y = m.getSource().y - 1; y >= m.getDestination().y; y--)
					if (getPieceAt(new Point(m.getDestination().x, y)) != null)
						return false;
			}
		}
		
		//if not a king, make sure this isn't a castle
		if (!getPieceAt(m.getSource()).isKing()) {
			if (m.getDestination().x == 1 && m.getDestination().y == 1)
				return false;
			if (m.getDestination().x == 1 && m.getDestination().y == boardSize)
				return false;
			if (m.getDestination().x == boardSize && m.getDestination().y == 1)
				return false;
			if (m.getDestination().x == boardSize && m.getDestination().y == boardSize)
				return false;
			if (m.getDestination().x == center)
				return m.getDestination().y != center;			
		}
		return true;
	}
	
	private List<Move> getOpenSpacesForWarrior(Point source) {
		List<Move> moves = new LinkedList<Move>();
		int rightDistance = source.y == 1 || source.y == 11 ? 10 : 11;
		int leftDistance = source.y == 1 || source.y == 11 ? 2 : 1;
		int upDistance = source.x == 1 || source.x == 11 ? 10 : 11;
		int downDistance = source.x == 1 || source.x == 11 ? 2 : 1;
		
		//Check to the right!
		for (int x = source.x + 1; x <= rightDistance; x++) {
			if (board[x - 1][source.y - 1] != null)
				break;
			if (x != center || source.y != center)
				moves.add(new MoveImpl(source, new Point(x, source.y)));
		}
			
		//Check to the left!
		for (int x = source.x - 1; x >= leftDistance; x--) {
			if (board[x - 1][source.y - 1] != null)
				break;
			if (x != center || source.y != center)
				moves.add(new MoveImpl(source, new Point(x, source.y)));
		}
		
		//Check up!
		for (int y = source.y + 1; y <= upDistance; y++) {
			if (board[source.x - 1][y - 1] != null)
				break;
			if (source.x != center || y != center)
				moves.add(new MoveImpl(source, new Point(source.x, y)));
		}
	
		//Check down!
		for (int y = source.y - 1; y >= downDistance; y--) {
			if (board[source.x - 1][y - 1] != null)
				break;
			if (source.x != center || y != center)
				moves.add(new MoveImpl(source, new Point(source.x, y)));
		}
		return moves;
	}
	
	private List<Move> getOpenSpacesForKing(Point king) {
		List<Move> moves = new LinkedList<Move>();
		
		//Check to the right!
		for (int x = king.x + 1; x < boardSize; x++) {
			if (board[x - 1][king.y - 1] != null)
				break;
			moves.add(new MoveImpl(king, new Point(x, king.y)));
		}
			
		//Check to the left!
		for (int x = king.x - 1; x >= 1; x--) {
			if (board[x - 1][king.y - 1] != null)
				break;
			moves.add(new MoveImpl(king, new Point(x, king.y)));
		}
		
		//Check up!
		for (int y = king.y + 1; y < boardSize; y++) {
			if (board[king.x - 1][y - 1] != null)
				break;
			moves.add(new MoveImpl(king, new Point(king.x, y)));
		}
	
		//Check down!
		for (int y = king.y - 1; y >= 1; y--) {
			if (board[king.x - 1][y - 1] != null)
				break;
			moves.add(new MoveImpl(king, new Point(king.x, y)));
		}
		return moves;
	}
	
	@Override
	public Board clone() {
		BoardImpl pos = null;
		try {
			pos = (BoardImpl) super.clone();
		} catch (CloneNotSupportedException e) {
			//implements cloneable; can't happen
		}
		pos.boardSize = this.boardSize;
		pos.center = this.center;
		pos.board = new Piece[boardSize][boardSize];
		
		for (int x = 0; x < boardSize; x++)
			for (int y = 0; y < boardSize; y++)
				pos.board[x][y] = this.board[x][y];
		
		pos.pieceLocations = new HashMap<Piece, List<Point>>(this.pieceLocations);
		
		return pos;
	}
}
