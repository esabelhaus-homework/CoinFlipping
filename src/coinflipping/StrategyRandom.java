/*
@AUTHOR: Associate Professor (Adjunct) Mark A. Wireman
@COURSE: CMSC325, Intro to Game Development, UMUC
@CREDITTO: Michael C. Semeniuk
*/
package coinflipping;

public class StrategyRandom extends Strategy
 
   {
 
  /**
 
   * Encoding for a strategy.
 
   */
 
  // 0 = Heads, 1 = Tails
 
  
 
   public StrategyRandom()
 
      {
 
      name = "Random";
 
      }  /* StrategyRandom */
 

 
   public int nextMove()
 
      {
 
      if (Math.random() < 0.5)  return 1;
 
      return 0;
 
      }  /* nextMove */
 
   }  /* class StrategyRandom */
 

 

