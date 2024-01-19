package ca.cmpt276.gamemodule.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class  PlayerScoreTest {


    @Test
    void PlayerScorePositiveNumOfCardsWithNegativeSumOfPoints_ThrowException() {
        //Test positive number of cards and negative sum of points
        Assertions.assertThrows(IllegalArgumentException.class, () -> new PlayerScore(1, -1, 0));
    }
    @Test
    void PlayerScoreNegativeNumberOfCards_ThrowException() {
        //Test negative number of cards and zero other cards
        Assertions.assertThrows(IllegalArgumentException.class, () -> new PlayerScore(-1, 0, 0));
    }

    @Test
    void PlayerScoreNegativeNumberOfWagers_ThrowException() {
        //Test positive sum of points and positive number of wager card with zero number of card
        Assertions.assertThrows(IllegalArgumentException.class, () -> new PlayerScore(0, 1, -1));
    }

    @Test
    void PlayerScoreNegativeCardsWithPositiveSumOfPoints_ThrowException() {
        //Test negative number of cards and positive sum of points
        Assertions.assertThrows(IllegalArgumentException.class, () -> new PlayerScore(-1, 1, 0));
    }

    @Test
    void PlayerScoreZeroCardsWithPositiveSumOfPoints_ThrowException() {
        //Test positive sum of points and zero number of cards
        Assertions.assertThrows(IllegalArgumentException.class, () -> new PlayerScore(0, 1, 0));
    }

    @Test
    void PlayerScoreZeroCardsWithPositiveNumberOfWagers_ThrowException() {
        //Test positive number of wagers card and zero number of cards
        Assertions.assertThrows(IllegalArgumentException.class, () -> new PlayerScore(0, 0, 1));
    }

    @Test
    void PlayerScoreSetNegativeNumOfCards_ThrowException(){
        //set negative number of cards
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            PlayerScore playerScore = new PlayerScore();
            playerScore.setNumOfCards(-1);
            assertEquals(0, playerScore.countScore());
        });
    }

    @Test
    void PlayerScoreSetNegativeSumOfPoints_ThrowException(){
        //Test setting negative sum of points
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            PlayerScore playerScore = new PlayerScore();
            playerScore.setNumOfCards(-1);
            assertEquals(0, playerScore.countScore());
        });
    }

    @Test
    void PlayerScoreSetNegativeNumberOfWagers_ThrowException(){
        //Test setting negative number of wagers
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            PlayerScore playerScore = new PlayerScore();
            playerScore.setNumOfCards(-1);
            assertEquals(0, playerScore.countScore());
        });
    }

    @Test
    void PlayerScoreZeroCards_SetExpectedScoreValue() {
        //Test 0 cards, 0 points and 0 wager cards would get score 0;
        PlayerScore score = new PlayerScore(0, 0, 0);
        assertEquals(0, score.countScore());
    }

    @Test
    void PlayerScoreValidArguments_SetExpectedScoreValue() {
        //Test 4 cards, 15 points and 1 wager cards would get score -10;
        PlayerScore score = new PlayerScore(4, 15, 1);
        assertEquals(-10, score.countScore());
    }

    @Test
    void PlayerScoreMoreThanSevenCards_SetExpectedScoreValue() {
        //Test 10 cards, a sum of 30 points and 2 wager cards would score 50
        PlayerScore score = new PlayerScore(10, 30, 2);
        assertEquals(50, score.countScore());
    }




}