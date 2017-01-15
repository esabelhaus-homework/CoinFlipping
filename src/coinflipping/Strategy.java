/*
@AUTHOR: Associate Professor (Adjunct) Mark A. Wireman
@COURSE: CMSC325, Intro to Game Development, UMUC
@CREDITTO: Michael C. Semeniuk
*/
package coinflipping;

import java.util.ArrayList;
 
public class Strategy extends Object
 
   {
 
  /**
 
   * Encoding for a strategy.
 
   */
 

 
   int opponentLastMove = 1;
 
   int myLastMove = 1;
   
   
 
   String name;
 
   
 
  // 0 = defect, 1 = cooperate
 

 
   public Strategy()
 
      {
 
      }  /* Strategy */
 

 
   public int nextMove()
 
      {
 
      return 0;
 
      }  /* nextMove */
 
   
 
   public void saveOpponentMove(int move)  { opponentLastMove = move; }
 
   public int getOpponentLastMove()  { return opponentLastMove; }
 
   public void saveMyMove(int move)  { myLastMove = move; }
 
   public int getMyLastMove()  { return myLastMove; }
 
   public String getName()  { return name; }
 
   }  /* class Strategy */
 

 

