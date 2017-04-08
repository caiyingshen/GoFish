import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by bettyshen on 3/7/17.
 */
public class PlayerTest {

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
    public void doTurn() throws Exception {
        Play currentTurn = game.players[1].doTurn(game.hands[1]);
        assertTrue(game.hands[1].hasRank(currentTurn.getRank()));
        assertTrue(currentTurn.getTargetPlayer() < playerTypes.length);
    }

}