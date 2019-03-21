/**
 * @author Luke
 */

public class Scoring {
  private String playerName;
  private int score = 0;
  private int currentStreak = 0;
  private int currentMultiplier = 1;
  private int inGameCurrency = 0;

  public void noteHit(){
    currentStreak += 1;
    if(checkStreak()){ currentMultiplier *= 2;}
    score += currentMultiplier * Constants.NOTE_VALUE;
    if (checkScore()) {
      addInGameCurrency();
    }
  }

  public void noteMissed(){
    currentStreak = 0;
    currentMultiplier = 1;
  }

  // If current streak is multiple of streakMultiple, multiply multiplier by 2
  private boolean checkStreak(){
    return currentStreak % Constants.STREAK_MULTIPLE == 0;
  }

  private boolean checkScore(){
    return score % Constants.SCORE_MULTIPLE == 0;
  }

  private void addInGameCurrency(){
    if(inGameCurrency < Constants.MAX_IN_GAME_CURRENCY){
      inGameCurrency += 1;
    }
  }

  public int getCurrentStreak(){
    return this.currentStreak;
  }

  public int getScore(){
    return this.score;
  }

  public int getInGameCurrency(){
    return this.inGameCurrency;
  }

  public int getCurrentMultiplier() { return this.currentMultiplier; }
}
