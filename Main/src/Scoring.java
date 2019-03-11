public class Scoring {
  private String playerName;
  private int score = 0;
  private int currentStreak = 0;
  private int currentMultiplier = 1;
  private int inGameCurrency = 0;

  public void noteHit(){
    currentStreak += 1;
    if(checkStreak()){ currentMultiplier *= 2;}
    score += currentMultiplier * Constants.noteValue;
    if (checkScore()) {
      addInGameCurrency();
    }
  }

  public void noteMissed(){
    currentStreak = 0;
    currentMultiplier = 1;
  }

  private boolean checkStreak(){
    return currentStreak % Constants.streakMultiple == 0;
  }

  private boolean checkScore(){
    return score % Constants.scoreMultiple == 0;
  }

  private void addInGameCurrency(){
    if(inGameCurrency < Constants.maxInGameCurrency){
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
}
