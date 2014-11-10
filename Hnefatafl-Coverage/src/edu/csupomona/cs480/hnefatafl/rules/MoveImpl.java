package edu.csupomona.cs480.hnefatafl.rules;

import java.awt.Point;

import edu.csupomona.cs480.hnefatafl.interfaces.Move;

public class MoveImpl implements Move
{
	Point source;
	Point destination;
	
	public MoveImpl (Point src, Point dest)
	{
		source = src;
		destination = dest;
	}
	
	/** Returns the source square of this move */
	public Point getSource()
	{
		return source;
	}
	
	/** Returns the destination square of this move */
	public Point getDestination()
	{
		return destination;
	}
}
