/**
 * This class controls the in game scoring with the values being printed to the screen
 *
 * @author Luke
 */

public class Scoring {
  private String playerName;
  private int score = 0;
  private int currentStreak = 0;
  private int currentMultiplier = 1;
  private int inGameCurrency = 0;

  /**
   * Increments the current streak and then adds on the appropiate score in regards to the current
   * multipler value.
   */
  public void noteHit(){
    currentStreak += 1;
    if(checkStreak()){ currentMultiplier *= Constants.SCORE_MULTIPLIER;}
    score += currentMultiplier * Constants.NOTE_VALUE;
    if (checkScore()) {
      addInGameCurrency();
    }
  }

  /**
   * Resets the current streak and multipler as a note has been missed
   */
  public void noteMissed(){
    currentStreak = 0;
    currentMultiplier = 1;
  }

  /**
   * Check if the current streak is a multiple of streak multiple
   * @return a boolean of whether it is a multiple or not
   */
  private boolean checkStreak(){
    return currentStreak % Constants.STREAK_MULTIPLE == 0;
  }

  /**
   * Check if the current score is a multiple of the score multiple
   * @return
   */
  private boolean checkScore(){
    return score % Constants.SCORE_MULTIPLE == 0;
  }

  /**
   * Increase the in game currency if it has not reached its limit
   */
  private void addInGameCurrency(){
    if(inGameCurrency < Constants.MAX_IN_GAME_CURRENCY){
      inGameCurrency += 1;
    }
  }

  /**
   * @return the current streak
   */
  public int getCurrentStreak(){
    return this.currentStreak;
  }

  /**
   * @return the current score
   */
  public int getScore(){
    return this.score;
  }

  /**
   * @return the current in game currency
   */
  public int getInGameCurrency(){
    return this.inGameCurrency;
  }

  /**
   * @return the current multiplier
   */
  public int getCurrentMultiplier() {
    return this.currentMultiplier;
  }
}
