package edu.csupomona.cs480.hnefatafl.rules;

import java.awt.Point;

import edu.csupomona.cs480.hnefatafl.interfaces.Move;

public class MoveImpl implements Move {
	private Point src, dest;
	
	public MoveImpl(Point source, Point destination) {
		src = source;
		dest = destination;
	}
	
	@Override
	public Point getDestination() {
		return dest;
	}

	@Override
	public Point getSource() {
		return src;
	}

}
