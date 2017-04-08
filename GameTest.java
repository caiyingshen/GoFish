import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by bettyshen on 3/7/17.
 */
public class GameTest {

    Game game;
    String[] playerTypes = {"smart", "dumb", "smart", "dumb", "smart", "dumb"};
    ArrayList<Card> hand0;
    ArrayList<Card> hand1;
    ArrayList<Card> hand2;
    ArrayList<Card> hand3;
    ArrayList<Card> hand4;
    ArrayList<Card> hand5;

    @Before
    public void setUp() throws Exception {
        game = new Game(playerTypes);

        hand0 = new ArrayList<>();
        hand0.add(new Card(4, Card.Suit.HEARTS));
        hand0.add(new Card(4, Card.Suit.CLUBS));
        hand0.add(new Card(4, Card.Suit.DIAMONDS));
        hand0.add(new Card(2, Card.Suit.HEARTS));
        hand0.add(new Card(1, Card.Suit.HEARTS));
        game.hands[0] = new Hand(hand0);

        hand1 = new ArrayList<>();
        hand1.add(new Card(9, Card.Suit.HEARTS));
        hand1.add(new Card(12, Card.Suit.CLUBS));
        hand1.add(new Card(12, Card.Suit.DIAMONDS));
        hand1.add(new Card(8, Card.Suit.HEARTS));
        hand1.add(new Card(12, Card.Suit.HEARTS));
        game.hands[1] = new Hand(hand1);

        hand2 = new ArrayList<>();
        hand2.add(new Card(4, Card.Suit.SPADES));
        hand2.add(new Card(2, Card.Suit.CLUBS));
        hand2.add(new Card(3, Card.Suit.DIAMONDS));
        hand2.add(new Card(8, Card.Suit.HEARTS));
        hand2.add(new Card(10, Card.Suit.HEARTS));
        game.hands[2] = new Hand(hand0);

        hand3 = new ArrayList<>();
        hand3.add(new Card(11, Card.Suit.HEARTS));
        hand3.add(new Card(12, Card.Suit.CLUBS));
        hand3.add(new Card(12, Card.Suit.DIAMONDS));
        hand3.add(new Card(2, Card.Suit.HEARTS));
        hand3.add(new Card(5, Card.Suit.HEARTS));
        game.hands[3] = new Hand(hand0);

        hand4 = new ArrayList<>();
        game.hands[4] = new Hand(hand4);

        hand5 = new ArrayList<>();
        hand5.add(new Card(4, Card.Suit.HEARTS));
        hand5.add(new Card(4, Card.Suit.CLUBS));
        hand5.add(new Card(4, Card.Suit.DIAMONDS));
        hand5.add(new Card(4, Card.Suit.SPADES));
        hand5.add(new Card(1, Card.Suit.HEARTS));
        game.hands[5] = new Hand(hand5);


    }

    @Test
    public void initializePlayers() throws Exception {
        assertEquals(6, game.players.length);
        assertEquals(6, game.hands.length);
        assertEquals(6, game.alivePlayers.length);
        assertEquals(6, game.score.length);
    }

    @Test
    public void gameOver() throws Exception {
        ArrayList<Integer> testBooks= new ArrayList<>();
        for (int i = 0; i <= 12; i++) {
            testBooks.add(i);
        }
        game.books.addAll(testBooks);

        assertTrue(game.gameOver());
    }

    @Test
    public void isAlivePlayers() throws Exception {
        for (int i = 0; i < 6; i++) {
            game.alivePlayers[i] = true;
        }
        assertTrue(game.isAlivePlayers());

        for (int j = 0; j < 6; j++) {
            game.alivePlayers[j] = false;
        }
        assertFalse(game.isAlivePlayers());
    }

    @Test
    public void getCardsToMove() throws Exception {
        Play play = new Play(0, 4);
        assertEquals(3, game.getCardsToMove(play).size());

        play = new Play(1, 12);
        assertEquals(3, game.getCardsToMove(play).size());

        play = new Play(2, 12);
        assertEquals(0, game.getCardsToMove(play).size());
    }

    @Test
    public void fixEmptyHand() throws Exception {
        game.fixEmptyHand(4);
        assertEquals(5, game.hands[4].size());
    }

    @Test
    public void hasBook() throws Exception {
        assertTrue(game.hasBook(5, 4));
        assertFalse(game.hasBook(3, 13));
    }

    @Test
    public void removeBookFromHand() throws Exception {
        game.removeBookFromHand(5, 4);
        assertEquals(1, game.books.size());
        assertEquals(1, game.hands[5].size());
    }

}