package ca.cmpt276.as2.model;

public class PlayerScore {
    private int numOfCards;
    private int sumOfPoints;
    private int numOfWagerCards;


    private int totalScore;

    public PlayerScore() {
    }

    public PlayerScore(int numOfCards, int sumOfPoints, int numOfWagerCards) {
        this.numOfCards = numOfCards;
        this.sumOfPoints = sumOfPoints;
        this.numOfWagerCards = numOfWagerCards;
        if (numOfCards < 0) {
            throw new IllegalArgumentException("Number of cards must be 0 or more.");
        }
        if (sumOfPoints < 0) {
            throw new IllegalArgumentException("Sum of points must be 0 or more.");
        }
        if (numOfWagerCards < 0) {
            throw new IllegalArgumentException("Number of wagers must be 0 or more.");
        }
        if (numOfCards == 0) {
            if (sumOfPoints > 0) {
                throw new IllegalArgumentException("If there are zero cards, sum of points must be 0.");
            }
            if (numOfWagerCards > 0) {
                throw new IllegalArgumentException("if there are zero cards, number of wagers must be 0.");
            }
        }
        this.totalScore = calcScore(numOfCards, sumOfPoints, numOfWagerCards);
    }

    public void setNumOfCards(int numOfCards) {
        if (numOfCards < 0)
            throw new IllegalArgumentException("Number of cards must be 0 or more.");
        else
            this.numOfCards = numOfCards;
    }

    public void setSumOfPoints(int sumOfPoints) {
        if (numOfCards < 0)
            throw new IllegalArgumentException("Sum of points must be 0 or more.");
        else
            this.sumOfPoints = sumOfPoints;
    }

    public void setNumOfWagerCards(int numOfWagerCards) {
        if (numOfCards < 0)
            throw new IllegalArgumentException("Number of wager cards must be 0 or more");
        else
            this.numOfWagerCards = numOfWagerCards;
    }

    public static int calcScore(int numOfCards, int sumOfPoints, int numOfWagerCards) {
        int totalScore = 0;
        if (numOfCards >= 8) {
            totalScore = (sumOfPoints - 20) * (numOfWagerCards + 1) + 20;
            return totalScore;
        } else if (numOfCards > 0) {
            totalScore = (sumOfPoints - 20) * (numOfWagerCards + 1);
            return totalScore;
        } else if (numOfCards == 0)
            return totalScore;
        else
            throw new IllegalArgumentException("Number of cards must be 0 or more");
    }


    public int getTotalScore() {
        return totalScore;
    }

    public int getNumOfCards() {
        return numOfCards;
    }

    public int getSumOfPoints() {
        return sumOfPoints;
    }

    public int getNumOfWagerCards() {
        return numOfWagerCards;
    }
}
