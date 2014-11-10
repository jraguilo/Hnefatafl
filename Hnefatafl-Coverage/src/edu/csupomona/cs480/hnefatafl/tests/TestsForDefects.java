package edu.csupomona.cs480.hnefatafl.tests;

import static org.junit.Assert.*;

import java.awt.Point;
import java.util.List;

import org.junit.Test;

import edu.csupomona.cs480.hnefatafl.interfaces.Board;
import edu.csupomona.cs480.hnefatafl.interfaces.Color;
import edu.csupomona.cs480.hnefatafl.interfaces.IllegalMoveException;
import edu.csupomona.cs480.hnefatafl.interfaces.Move;
import edu.csupomona.cs480.hnefatafl.interfaces.Piece;
import edu.csupomona.cs480.hnefatafl.rules.RulesFactory;

/** TODO: implement one test case for each seeded defect in this class.
 * The 5 test cases here should succeed on your main implementation but
 * all 5 should fail on your implementation with the seeded defects.
 * 
 * Note that none of the test cases in SmokeTest.java should fail for
 * either the main or the seeded implementation.
 * 
 * If you haven't written a JUnit test before, see SmokeTest.java.  In
 * Eclipse you can run JUnit tests by right-clicking on the package and
 * selecting Run As..., then choosing JUnit Test.
 * 
 * @author 480 Students
 *
 */
public class TestsForDefects {
   /** Test White wins */
   @Test
   public void testWhiteWins() 
   {
      Board aPosition = RulesFactory.getDefault().makeBoard();
      aPosition.addPieceAt(Piece.WhiteKing, new Point(1,1));
      assertTrue(aPosition.checkForVictory() == Color.White);
      aPosition = RulesFactory.getDefault().makeBoard();
      aPosition.addPieceAt(Piece.WhiteKing, new Point(1,11));
      assertTrue(aPosition.checkForVictory() == Color.White);
      aPosition = RulesFactory.getDefault().makeBoard();
      aPosition.addPieceAt(Piece.WhiteKing, new Point(11,1));
      assertTrue(aPosition.checkForVictory() == Color.White);
      aPosition = RulesFactory.getDefault().makeBoard();
      aPosition.addPieceAt(Piece.WhiteKing, new Point(11, 11));
      assertTrue(aPosition.checkForVictory() == Color.White);
   }

   @Test
   public void testBlackWins() 
   {
      //test black victory with king surrounded on 4 sides
      Board aPosition = RulesFactory.getDefault().makeBoard();
      aPosition.addPieceAt(Piece.WhiteKing, new Point(3,3));
      aPosition.addPieceAt(Piece.BlackWarrior, new Point(3,4));
      aPosition.addPieceAt(Piece.BlackWarrior, new Point(3,2));
      aPosition.addPieceAt(Piece.BlackWarrior, new Point(2,3));
      aPosition.addPieceAt(Piece.BlackWarrior, new Point(4,3));
      assertTrue(aPosition.checkForVictory() == Color.Black);
      
      //test black victory with king 1 point below castle
      aPosition = RulesFactory.getDefault().makeBoard();
      aPosition.addPieceAt(Piece.WhiteKing, new Point(6,5));
      aPosition.addPieceAt(Piece.BlackWarrior, new Point(5,5));
      aPosition.addPieceAt(Piece.BlackWarrior, new Point(7,5));
      aPosition.addPieceAt(Piece.BlackWarrior, new Point(6,4));
      assertTrue(aPosition.checkForVictory() == Color.Black);
      
      //test black victory with king 1 point above castle
      aPosition = RulesFactory.getDefault().makeBoard();
      aPosition.addPieceAt(Piece.WhiteKing, new Point(6,7));
      aPosition.addPieceAt(Piece.BlackWarrior, new Point(5,7));
      aPosition.addPieceAt(Piece.BlackWarrior, new Point(7,7));
      aPosition.addPieceAt(Piece.BlackWarrior, new Point(6,8));
      assertTrue(aPosition.checkForVictory() == Color.Black);
      
      //test black victory with king 1 point right of castle
      aPosition = RulesFactory.getDefault().makeBoard();
      aPosition.addPieceAt(Piece.WhiteKing, new Point(7,6));
      aPosition.addPieceAt(Piece.BlackWarrior, new Point(7,7));
      aPosition.addPieceAt(Piece.BlackWarrior, new Point(8,6));
      aPosition.addPieceAt(Piece.BlackWarrior, new Point(7,5));
      assertTrue(aPosition.checkForVictory() == Color.Black);
      
    //test black victory with king 1 point left of castle
      aPosition = RulesFactory.getDefault().makeBoard();
      aPosition.addPieceAt(Piece.WhiteKing, new Point(5,6));
      aPosition.addPieceAt(Piece.BlackWarrior, new Point(5,5));
      aPosition.addPieceAt(Piece.BlackWarrior, new Point(4,6));
      aPosition.addPieceAt(Piece.BlackWarrior, new Point(5,7));
      assertTrue(aPosition.checkForVictory() == Color.Black);
      
   }
   
   @Test
   public void edgeCapture()
   {
    //test black victory with king on left edge
      Board aPosition = RulesFactory.getDefault().makeBoard();
      aPosition.addPieceAt(Piece.WhiteKing, new Point(1,6));
      aPosition.addPieceAt(Piece.BlackWarrior, new Point(1,5));
      aPosition.addPieceAt(Piece.BlackWarrior, new Point(1,7));
      aPosition.addPieceAt(Piece.BlackWarrior, new Point(2,6));
      assertTrue(aPosition.checkForVictory() == Color.Black);
      
    //test black victory with king on right edge
      aPosition = RulesFactory.getDefault().makeBoard();
      aPosition.addPieceAt(Piece.WhiteKing, new Point(11,6));
      aPosition.addPieceAt(Piece.BlackWarrior, new Point(11,5));
      aPosition.addPieceAt(Piece.BlackWarrior, new Point(11,7));
      aPosition.addPieceAt(Piece.BlackWarrior, new Point(10,6));
      assertTrue(aPosition.checkForVictory() == Color.Black);
      
    //test black victory with king on bottom edge
      aPosition = RulesFactory.getDefault().makeBoard();
      aPosition.addPieceAt(Piece.WhiteKing, new Point(6,1));
      aPosition.addPieceAt(Piece.BlackWarrior, new Point(5,1));
      aPosition.addPieceAt(Piece.BlackWarrior, new Point(7,1));
      aPosition.addPieceAt(Piece.BlackWarrior, new Point(6,2));
      assertTrue(aPosition.checkForVictory() == Color.Black);
      
    //test black victory with king on top edge
      aPosition = RulesFactory.getDefault().makeBoard();
      aPosition.addPieceAt(Piece.WhiteKing, new Point(6,11));
      aPosition.addPieceAt(Piece.BlackWarrior, new Point(5,11));
      aPosition.addPieceAt(Piece.BlackWarrior, new Point(7,11));
      aPosition.addPieceAt(Piece.BlackWarrior, new Point(6,10));
      assertTrue(aPosition.checkForVictory() == Color.Black);
   }
   
   @Test
   public void testInitialState() 
   {
      // 1 king in the center
      Board initialPosition = RulesFactory.getDefault().makeInitialBoard();
      List<Point> kingPoints = initialPosition.getPieceLocations(Piece.WhiteKing);
      assertTrue(kingPoints.size() == 1);
      assertTrue(kingPoints.get(0).equals(new Point(6,6)));
      assertTrue(initialPosition.getPieceAt(kingPoints.get(0)) == Piece.WhiteKing);
           
      // 12 white warriors, all in correct Points
      List<Point> whiteWarriorPoints = initialPosition.getPieceLocations(Piece.WhiteWarrior);
      assertTrue(whiteWarriorPoints.size() == 12);
      for (Point currentWarriorPoint : whiteWarriorPoints) {
         assertTrue((currentWarriorPoint.x == 4 && currentWarriorPoint.y == 6) ||
                    (currentWarriorPoint.x == 8 && currentWarriorPoint.y == 6) ||
                    (currentWarriorPoint.x == 5 && currentWarriorPoint.y == 5) ||
                    (currentWarriorPoint.x == 5 && currentWarriorPoint.y == 6) ||
                    (currentWarriorPoint.x == 5 && currentWarriorPoint.y == 7) ||
                    (currentWarriorPoint.x == 7 && currentWarriorPoint.y == 5) ||
                    (currentWarriorPoint.x == 7 && currentWarriorPoint.y == 6) ||
                    (currentWarriorPoint.x == 7 && currentWarriorPoint.y == 7) ||
                    (currentWarriorPoint.x == 6 && currentWarriorPoint.y == 4) ||
                    (currentWarriorPoint.x == 6 && currentWarriorPoint.y == 5) ||
                    (currentWarriorPoint.x == 6 && currentWarriorPoint.y == 7) ||
                    (currentWarriorPoint.x == 6 && currentWarriorPoint.y == 8));
         assertTrue(initialPosition.getPieceAt(currentWarriorPoint) == Piece.WhiteWarrior);
      }
           
      // 12 black warriors, all in correct Points
      List<Point> blackWarriorPoints = initialPosition.getPieceLocations(Piece.BlackWarrior);
      assertTrue(blackWarriorPoints.size() == 24);
      for (Point currentWarriorPoint : blackWarriorPoints) {
         assertTrue((currentWarriorPoint.x == 1 && currentWarriorPoint.y == 4) ||
                    (currentWarriorPoint.x == 1 && currentWarriorPoint.y == 5) ||
                    (currentWarriorPoint.x == 1 && currentWarriorPoint.y == 6) ||
                    (currentWarriorPoint.x == 1 && currentWarriorPoint.y == 7) ||
                    (currentWarriorPoint.x == 1 && currentWarriorPoint.y == 8) ||
                    (currentWarriorPoint.x == 11 && currentWarriorPoint.y == 4) ||
                    (currentWarriorPoint.x == 11 && currentWarriorPoint.y == 5) ||
                    (currentWarriorPoint.x == 11 && currentWarriorPoint.y == 6) ||
                    (currentWarriorPoint.x == 11 && currentWarriorPoint.y == 7) ||
                    (currentWarriorPoint.x == 11 && currentWarriorPoint.y == 8) ||
                    (currentWarriorPoint.x == 2 && currentWarriorPoint.y == 6) ||
                    (currentWarriorPoint.x == 10 && currentWarriorPoint.y == 6) ||
                    (currentWarriorPoint.x == 4 && currentWarriorPoint.y == 1) ||
                    (currentWarriorPoint.x == 5 && currentWarriorPoint.y == 1) ||
                    (currentWarriorPoint.x == 6 && currentWarriorPoint.y == 1) ||
                    (currentWarriorPoint.x == 7 && currentWarriorPoint.y == 1) ||
                    (currentWarriorPoint.x == 8 && currentWarriorPoint.y == 1) ||
                    (currentWarriorPoint.x == 4 && currentWarriorPoint.y == 11) ||
                    (currentWarriorPoint.x == 5 && currentWarriorPoint.y == 11) ||
                    (currentWarriorPoint.x == 6 && currentWarriorPoint.y == 11) ||
                    (currentWarriorPoint.x == 7 && currentWarriorPoint.y == 11) ||
                    (currentWarriorPoint.x == 8 && currentWarriorPoint.y == 11) ||
                    (currentWarriorPoint.x == 6 && currentWarriorPoint.y == 2) ||
                    (currentWarriorPoint.x == 6 && currentWarriorPoint.y == 10));
         assertTrue(initialPosition.getPieceAt(currentWarriorPoint) == Piece.BlackWarrior);
      }
   }
   
   @Test
   public void correctMove() 
   {
           Board aPosition = RulesFactory.getDefault().makeBoard();
           aPosition.addPieceAt(Piece.BlackWarrior, new Point(1,3));
           aPosition.addPieceAt(Piece.WhiteKing, new Point(1,4));
           aPosition.addPieceAt(Piece.WhiteWarrior, new Point(2,3));
           List<Move> moveList = aPosition.getLegalMovesFor(Color.Black);
           assertTrue(moveList.size() == 1);
           Move checkMove = moveList.remove(0);
           assertTrue(checkMove.getDestination().equals(new Point (1, 2)));
   }
   
   @Test
   public void testIllegalMove()
   {
      //test if moving through units throws an exception
      boolean thrown=false;
      try
      {
         Board aPosition = RulesFactory.getDefault().makeBoard();
         aPosition.addPieceAt(Piece.BlackWarrior, new Point(1,3));
         aPosition.addPieceAt(Piece.WhiteKing, new Point(1,4));
         Move checkMove = RulesFactory.getDefault().makeMove(new Point(1,3), new Point(1,5));
         aPosition.move(checkMove);         
      }
      catch(IllegalMoveException ex)
      {
         thrown = true;
      }
      assertTrue(thrown);
      //test if making a move with an illegal source below board throws an exception
      thrown = false;
      try
      {
         Board aPosition = RulesFactory.getDefault().makeBoard();
         Move checkMove = RulesFactory.getDefault().makeMove(new Point(2,0), new Point(2,2));
         aPosition.move(checkMove);
      }
      catch(Exception ex)
      {
         thrown = true;
      }
      assertTrue(thrown);
    //test if making a move with an illegal source above board throws an exception
      thrown = false;
      try
      {
         Board aPosition = RulesFactory.getDefault().makeBoard();
         Move checkMove = RulesFactory.getDefault().makeMove(new Point(2,12), new Point(2,2));
         aPosition.move(checkMove);
      }
      catch(IllegalMoveException ex)
      {
         thrown = true;
      }
      assertTrue(thrown);
    //test if making a move with an illegal source to the left of board throws an exception
      thrown = false;
      try
      {
         Board aPosition = RulesFactory.getDefault().makeBoard();
         Move checkMove = RulesFactory.getDefault().makeMove(new Point(0,2), new Point(2,2));
         aPosition.move(checkMove);
      }
      catch(IllegalArgumentException ex)
      {
         thrown = true;
      }
      assertTrue(thrown);
    //test if making a move with an illegal source to the right of board throws an exception
      thrown = false;
      try
      {
         Board aPosition = RulesFactory.getDefault().makeBoard();
         Move checkMove = RulesFactory.getDefault().makeMove(new Point(12,2), new Point(2,2));
         aPosition.move(checkMove);
      }
      catch(IllegalMoveException ex)
      {
         thrown = true;
      }
      assertTrue(thrown);
      //test if moving piece below board boundary throws an exception
      thrown = false;
      try
      {
         Board aPosition = RulesFactory.getDefault().makeBoard();
         aPosition.addPieceAt(Piece.BlackWarrior, new Point(2,1));
         Move checkMove = RulesFactory.getDefault().makeMove(new Point(2,1), new Point(2,0));
         aPosition.move(checkMove);
      }
      catch(IllegalMoveException ex)
      {
         thrown = true;
      }
      assertTrue(thrown);
    //test if moving piece above board boundary throws an exception
      thrown = false;
      try
      {
         Board aPosition = RulesFactory.getDefault().makeBoard();
         aPosition.addPieceAt(Piece.BlackWarrior, new Point(2,11));
         Move checkMove = RulesFactory.getDefault().makeMove(new Point(2,11), new Point(2,12));
         aPosition.move(checkMove);
      }
      catch(IllegalMoveException ex)
      {
         thrown = true;
      }
      assertTrue(thrown);
    //test if moving piece to the left of board boundary throws an exception
      thrown = false;
      try
      {
         Board aPosition = RulesFactory.getDefault().makeBoard();
         aPosition.addPieceAt(Piece.BlackWarrior, new Point(1,2));
         Move checkMove = RulesFactory.getDefault().makeMove(new Point(1,2), new Point(0,2));
         aPosition.move(checkMove);
      }
      catch(IllegalMoveException ex)
      {
         thrown = true;
      }
      assertTrue(thrown);
    //test if moving piece to the right board boundary throws an exception
      thrown = false;
      try
      {
         Board aPosition = RulesFactory.getDefault().makeBoard();
         aPosition.addPieceAt(Piece.BlackWarrior, new Point(1,2));
         Move checkMove = RulesFactory.getDefault().makeMove(new Point(11,2), new Point(12,2));
         aPosition.move(checkMove);
      }
      catch(IllegalMoveException ex)
      {
         thrown = true;
      }
      assertTrue(thrown);
   }
   
   @Test
   public void testIllegalAddPiece()
   {
      boolean thrown = false;
      //add piece below board
      try{
      Board aPosition = RulesFactory.getDefault().makeBoard();
      aPosition.addPieceAt(Piece.BlackWarrior, new Point (2,0));
      }
      catch(IllegalArgumentException ex)
      {
         thrown = true;
      }
      assertTrue(thrown);
      //add piece above board
      thrown = false;
      try{
      Board aPosition = RulesFactory.getDefault().makeBoard();
      aPosition.addPieceAt(Piece.BlackWarrior, new Point (2,12));
      }
      catch(IllegalArgumentException ex)
      {
         thrown = true;
      }
      assertTrue(thrown);
      //add piece below board
      thrown = false;
      try{
      Board aPosition = RulesFactory.getDefault().makeBoard();
      aPosition.addPieceAt(Piece.BlackWarrior, new Point (0,2));
      }
      catch(IllegalArgumentException ex)
      {
         thrown = true;
      }
      assertTrue(thrown);
      //add piece below board
      thrown = false;
      try{
      Board aPosition = RulesFactory.getDefault().makeBoard();
      aPosition.addPieceAt(Piece.BlackWarrior, new Point (12,2));
      }
      catch(IllegalArgumentException ex)
      {
         thrown = true;
      }
      assertTrue(thrown);
   }
   @Test
   public void testMoveCount() {
            //Black Moves
           Board aPosition = RulesFactory.getDefault().makeBoard();
           aPosition.addPieceAt(Piece.BlackWarrior, new Point(3,3));
           aPosition.addPieceAt(Piece.WhiteWarrior, new Point(3,5));
           aPosition.addPieceAt(Piece.WhiteWarrior, new Point(3,1));
           aPosition.addPieceAt(Piece.WhiteWarrior, new Point(5,3));
           aPosition.addPieceAt(Piece.WhiteWarrior, new Point(1,3));
           assertTrue(aPosition.getLegalMovesFor(Color.Black).size() == 1/*down to 3,2*/ + 1/*up to 3,4*/ + 1/*left to 1,3*/ + 1/*right to 4,3*/);
           //White Warrior Moves
           aPosition=RulesFactory.getDefault().makeBoard();
           aPosition.addPieceAt(Piece.WhiteWarrior, new Point(1,3));
           aPosition.addPieceAt(Piece.BlackWarrior, new Point(1,5));
           assertTrue(aPosition.getLegalMovesFor(Color.White).size() == 1/*down to 1,2*/ + 1/*up to 1,4*/ + 10/*across to (2..11),3*/);
           //White King Moves
           aPosition=RulesFactory.getDefault().makeBoard();
           aPosition.addPieceAt(Piece.WhiteKing, new Point(1,3));
           aPosition.addPieceAt(Piece.BlackWarrior, new Point(1,5));
           assertTrue(aPosition.getLegalMovesFor(Color.White).size() == 2/*down to 1,1*/ + 1/*up to 1,4*/ + 10/*across to (2..11),3*/);
   }
   
   @Test
   public void testPieceCount(){
      Board aPosition = RulesFactory.getDefault().makeBoard();
      aPosition.addPieceAt(Piece.BlackWarrior, new Point(2,2));
      aPosition.addPieceAt(Piece.WhiteKing, new Point(3,3));
      aPosition.addPieceAt(Piece.WhiteWarrior, new Point(4,4));
      assertTrue(aPosition.getPieceLocations(null).size() == 3);
      assertTrue(aPosition.getPieceLocations(Piece.BlackWarrior).size() == 1);
      assertTrue(aPosition.getPieceLocations(Piece.WhiteKing).size() == 1);
      assertTrue(aPosition.getPieceLocations(Piece.WhiteWarrior).size() == 1);
   }
   
   @Test
   public void testCapture()
   {
      //capture in y- direction
      Board aPosition = RulesFactory.getDefault().makeBoard();
      aPosition.addPieceAt(Piece.WhiteWarrior, new Point(2,3));
      aPosition.addPieceAt(Piece.BlackWarrior, new Point(2,4));
      aPosition.addPieceAt(Piece.WhiteKing, new Point(2,6));
      Move captureMove = RulesFactory.getDefault().makeMove(2, 6, 2, 5);
      assertTrue(aPosition.isLegalFor(captureMove, Color.White));
      aPosition.move(captureMove);
      assertTrue(aPosition.getPieceAt(new Point(2,4)) == null);
      assertTrue(aPosition.getPieceAt(new Point(2,3)) == Piece.WhiteWarrior);
      assertTrue(aPosition.getPieceAt(new Point(2,5)) == Piece.WhiteKing);
      assertTrue(aPosition.checkForVictory() == null);
      //capture in y+ direction
      aPosition = RulesFactory.getDefault().makeBoard();
      aPosition.addPieceAt(Piece.WhiteWarrior, new Point(2,5));
      aPosition.addPieceAt(Piece.BlackWarrior, new Point(2,4));
      aPosition.addPieceAt(Piece.WhiteKing, new Point(2,2));
      captureMove = RulesFactory.getDefault().makeMove(2, 2, 2, 3);
      assertTrue(aPosition.isLegalFor(captureMove, Color.White));
      aPosition.move(captureMove);
      assertTrue(aPosition.getPieceAt(new Point(2,4)) == null);
      assertTrue(aPosition.getPieceAt(new Point(2,5)) == Piece.WhiteWarrior);
      assertTrue(aPosition.getPieceAt(new Point(2,3)) == Piece.WhiteKing);
      assertTrue(aPosition.checkForVictory() == null);
      //capture in x- direction
      aPosition = RulesFactory.getDefault().makeBoard();
      aPosition.addPieceAt(Piece.WhiteWarrior, new Point(3,2));
      aPosition.addPieceAt(Piece.BlackWarrior, new Point(4,2));
      aPosition.addPieceAt(Piece.WhiteKing, new Point(6,2));
      captureMove = RulesFactory.getDefault().makeMove(6, 2, 5, 2);
      assertTrue(aPosition.isLegalFor(captureMove, Color.White));
      aPosition.move(captureMove);
      assertTrue(aPosition.getPieceAt(new Point(4,2)) == null);
      assertTrue(aPosition.getPieceAt(new Point(3,2)) == Piece.WhiteWarrior);
      assertTrue(aPosition.getPieceAt(new Point(5,2)) == Piece.WhiteKing);
      assertTrue(aPosition.checkForVictory() == null);
      //capture in x+ direction
      aPosition = RulesFactory.getDefault().makeBoard();
      aPosition.addPieceAt(Piece.WhiteWarrior, new Point(5,2));
      aPosition.addPieceAt(Piece.BlackWarrior, new Point(4,2));
      aPosition.addPieceAt(Piece.WhiteKing, new Point(2,2));
      captureMove = RulesFactory.getDefault().makeMove(2, 2, 3, 2);
      assertTrue(aPosition.isLegalFor(captureMove, Color.White));
      aPosition.move(captureMove);
      assertTrue(aPosition.getPieceAt(new Point(4,2)) == null);
      assertTrue(aPosition.getPieceAt(new Point(5,2)) == Piece.WhiteWarrior);
      assertTrue(aPosition.getPieceAt(new Point(3,2)) == Piece.WhiteKing);
      assertTrue(aPosition.checkForVictory() == null);
      
      
   }
   
   @Test
   public void doesCloneDeepCopy() 
   {
           Board aPosition = RulesFactory.getDefault().makeBoard();
           aPosition.addPieceAt(Piece.WhiteKing, new Point(1,4));
           Board bPosition = aPosition.clone();
           aPosition.addPieceAt(Piece.WhiteWarrior, new Point(2,3));
           assertTrue(!(bPosition.getPieceAt(new Point(2,3)) == Piece.WhiteWarrior));
   }
   
   @Test
   public void moveBlackToCastle()
   {
      boolean thrown = false;
      //move black warrior to bottom left corner
      try
      {
         Board aPosition = RulesFactory.getDefault().makeBoard();
         aPosition.addPieceAt(Piece.BlackWarrior, new Point(1,2));
         Move checkMove = RulesFactory.getDefault().makeMove(new Point(1,2), new Point(1,1));
         aPosition.move(checkMove);
      }
      catch(Exception ex)
      {
         thrown = true;
      }
      assertTrue(thrown);
      //move black warrior to bottom right corner
      thrown = false;
      try
      {
         Board aPosition = RulesFactory.getDefault().makeBoard();
         aPosition.addPieceAt(Piece.BlackWarrior, new Point(10,1));
         Move checkMove = RulesFactory.getDefault().makeMove(new Point(10,1), new Point(11,1));
         aPosition.move(checkMove);
      }
      catch(Exception ex)
      {
         thrown = true;
      }
      assertTrue(thrown);
      //move black warrior to top left corner
      thrown = false;
      try
      {
         Board aPosition = RulesFactory.getDefault().makeBoard();
         aPosition.addPieceAt(Piece.BlackWarrior, new Point(1,10));
         Move checkMove = RulesFactory.getDefault().makeMove(new Point(1,10), new Point(1,11));
         aPosition.move(checkMove);
      }
      catch(Exception ex)
      {
         thrown = true;
      }
      assertTrue(thrown);
      //move black warrior to top right corner
      thrown = false;
      try
      {
         Board aPosition = RulesFactory.getDefault().makeBoard();
         aPosition.addPieceAt(Piece.BlackWarrior, new Point(1,2));
         Move checkMove = RulesFactory.getDefault().makeMove(new Point(10,11), new Point(11,11));
         aPosition.move(checkMove);
      }
      catch(Exception ex)
      {
         thrown = true;
      }
      assertTrue(thrown);
      //move black warrior to center castle
      thrown = false;
      try
      {
         Board aPosition = RulesFactory.getDefault().makeBoard();
         aPosition.addPieceAt(Piece.BlackWarrior, new Point(5,6));
         Move checkMove = RulesFactory.getDefault().makeMove(new Point(5,6), new Point(6,6));
         aPosition.move(checkMove);
      }
      catch(Exception ex)
      {
         thrown = true;
      }
      assertTrue(thrown);
      
   }

}