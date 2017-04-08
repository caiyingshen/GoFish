import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by bettyshen on 3/7/17.
 */
public class SmartPlayerTest {

    Game game;
    String[] playerTypes = {"smart", "dumb", "smart"};
    ArrayList<Card> hand0;
    ArrayList<Card> hand1;
    ArrayList<Card> hand2;

    @Before
    public void setUp() throws Exception {
        game = new Game(playerTypes);

        hand0 = new ArrayList<>();
        hand0.add(new Card(4, Card.Suit.HEARTS));
        hand0.add(new Card(8, Card.Suit.CLUBS));
        hand0.add(new Card(3, Card.Suit.DIAMONDS));
        hand0.add(new Card(2, Card.Suit.HEARTS));
        hand0.add(new Card(9, Card.Suit.HEARTS));
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
        hand2.add(new Card(8, Card.Suit.SPADES));
        hand2.add(new Card(10, Card.Suit.HEARTS));
        game.hands[2] = new Hand(hand0);

        ArrayList<Card> toAdd = new ArrayList<>();
        toAdd.add(new Card(4, Card.Suit.SPADES) );
        RecordedPlay one = new RecordedPlay(0, 2, 4, toAdd);
        game.players[0].playOccurred(one);
        game.players[2].playOccurred(one);

        toAdd.remove(new Card(4, Card.Suit.SPADES));
        toAdd.add(new Card(2, Card.Suit.HEARTS));
        RecordedPlay two = new RecordedPlay(2, 0, 2, toAdd);
        game.players[0].playOccurred(two);
        game.players[2].playOccurred(two);

        toAdd.remove(new Card(2, Card.Suit.HEARTS));
        toAdd.add(new Card(8, Card.Suit.HEARTS));
        RecordedPlay three = new RecordedPlay(1, 2, 8, toAdd);
        game.players[0].playOccurred(three);
        game.players[2].playOccurred(three);

        toAdd.remove(new Card(8, Card.Suit.HEARTS));
        toAdd.add(new Card(9, Card.Suit.HEARTS));
        RecordedPlay four = new RecordedPlay(0, 1, 9, toAdd);
        game.players[0].playOccurred(four);
        game.players[2].playOccurred(four);

        toAdd.remove(new Card(9, Card.Suit.HEARTS));
        toAdd.add(new Card(8, Card.Suit.CLUBS));
        RecordedPlay five = new RecordedPlay(0, 2, 8, toAdd);

    }

    @Test
    public void doTurn() throws Exception {
        Play currentTurn = game.players[1].doTurn(game.hands[1]);
        assertTrue(game.hands[1].hasRank(currentTurn.getRank()));
        assertTrue(currentTurn.getTargetPlayer() < playerTypes.length);

    }

    @Test
    public void cardWasTaken() throws Exception {
        assertTrue(game.players[0].cardWasTaken(2, 8));
        assertFalse(game.players[0].cardWasTaken(2, 2));
    }

}