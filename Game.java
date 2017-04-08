/**
 * Created by bettyshen on 3/5/17.
 *
 * The main class that runs the game GoFish in the method playGame()
 *
 */

import java.util.ArrayList;
import java.util.List;

public class Game {

    //declare variables
    Player[] players;
    Hand[] hands;
    boolean[] alivePlayers;
    int[] score;
    Deck deck;
    ArrayList<Integer> books;
    int counter;

    // constructor based on arguments passed from main method
    public Game(String[] args) {
        deck = new Deck();
        books = new ArrayList<>();
        alivePlayers = new boolean[args.length];
        score = new int[args.length];
        initializePlayers(args);
        for (int i = 0; i < alivePlayers.length; i++)
            alivePlayers[i] = true;
    }

    /** Initializes the players in the game based on the arguments of the main method
     *
     * @param args passed from main method
     */
    public void initializePlayers(String[] args) {
        players = new Player[args.length];
        hands = new Hand[args.length];
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("dumb")) {
                players[i] = new DumbPlayer(i, args.length);
                hands[i] = new Hand(deck.draw(5));
            }
            else if (args[i].equals("smart")) {
                players[i] = new SmartPlayer(i, args.length);
                hands[i] = new Hand(deck.draw(5));
            }
        }
    }

    /** Main method that runs the game
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        Game game = new Game(args);
        game.playGame();
    }


    /** The method that actually runs the game, increments a counter to keep track of the turn
     *  Each turn a player calls its doTurn method to ask for a card
     *  If the card is found, the player's hand receives an ArrayList<</Card>
     *  moveToNextPlayer keeps track of whether or not the game moves to the next player
     *  After the swap is made, the method checks for empty hands
     *  Every round the game updates each player's RecordedPlay with playOccured()
     *  The game is over when gameOver() returns true
     *
     */
    public void playGame() {
        Play currentPlay;
        int currentRank;
        counter = 0;
        int turn;
        RecordedPlay toRecord;
        boolean moveToNextPlayer = true;

        //check if any players have books already
        for (int i = 0; i < players.length; i++) {
            for (int j = 0; j < Card.NUM_RANKS; j++ ) {
                if (hasBook(i, j)) {
                    removeBookFromHand(i, j);
                    score[i]++;
                }
            }
        }

        while (!gameOver()) {
            //update turn
            turn = counter % players.length;

            //check if hand is empty & update if so
            if (hands[turn].size() == 0)
                fixEmptyHand(turn);

            //makes sure player is still in the game
            while (!alivePlayers[turn]) {
                counter++;
                turn = counter % players.length;
            }

            //make play
            currentPlay = players[turn].doTurn(hands[turn]);
            currentRank = currentPlay.getRank();

            //move cards
            ArrayList<Card> cardsToMove = getCardsToMove(currentPlay);
            hands[turn].cards.addAll(cardsToMove);
            printPlay(currentPlay, cardsToMove.size(), turn);

            //record currentPlay
            toRecord = new RecordedPlay(turn, currentPlay.getTargetPlayer(), currentPlay.getRank(), getCardsToMove(currentPlay));
            for (Player p : players)
                p.playOccurred(toRecord);

            //check for books in sourcePlayer's hand
            if (hasBook(turn, currentRank)) {
                removeBookFromHand(turn, currentRank);
                score[turn]++;
                if (hands[turn].size() == 0) {
                    fixEmptyHand(turn);
                }
            }

            //check if targetPlayer's hand is empty
            if (hands[currentPlay.getTargetPlayer()].size() == 0) {
                fixEmptyHand(currentPlay.getTargetPlayer());
            }

            //check if game moves to next players
            moveToNextPlayer = (cardsToMove.size() == 0);
            if (moveToNextPlayer) {
                counter++;
                if (!deck.isEmpty())
                    hands[turn].cards.add(deck.draw());
            }
        }

        //end the game by printing out the scores
        System.out.println("Game over!");
        int winner = 0;
        for (int a = 0; a < score.length; a++) {
            System.out.println("Player" + a + " had " + score[a] + " points.");
            if (score[a] > score[winner])
                winner = a;
        }
        System.out.println("Player" + winner + " is the winner!");
    }

    /** Uses length of books and alivePlayers to determine if the game continues
     *
     * @return
     */
    public boolean gameOver() {
        return books.size() >= 13 || !isAlivePlayers();

    }

    /** Used in gameOver() to check if there are still players in the game
     *
     * @return true if any player is still in the game
     */
    public boolean isAlivePlayers() {
        for (int i = 0; i < alivePlayers.length; i++) {
            if (alivePlayers[i] == true)
                return true;
        }
        return false;
    }

    /** Takes a Play object as an argument and determines which cards to move
     *
     * @param currentPlay
     * @return an ArrayList of cards to move
     */
    public ArrayList<Card> getCardsToMove(Play currentPlay) {
        int targetPlayer = currentPlay.getTargetPlayer();
        int currentRank = currentPlay.getRank();
        Hand targetHand = hands[targetPlayer];
        ArrayList<Card> toMove = new ArrayList<>();

        if (targetHand.hasRank(currentRank)) {
            for (Card c: targetHand.cards) {
                if (c.getRank() == currentRank) {
                    toMove.add(c);
                }
            }
        }
        targetHand.cards.removeAll(toMove);
        return toMove;
    }

    /** Updates the player's hand if it is empty
     *
     * @param player
     */
    public void fixEmptyHand(int player) {
        if (deck.isEmpty())
            alivePlayers[player] = false;
        else if (deck.cards.size() >= 5)
            hands[player] = new Hand(deck.draw(5));
        else if (deck.cards.size() < 5)
            hands[player] = new Hand(deck.draw(deck.cards.size()));
        else
            alivePlayers[player] = false;
    }

    /** Increments the counter by 1, used mostly for testing and exception handling
     *
     */
    public void increment() {
        counter++;
    }

    /** Checks to see if the player at playerIndex has 4 of the same card of specified rank
     *
     * @param playerIndex
     * @param rank
     * @return true if the player does have a book
     */
    public boolean hasBook(int playerIndex, int rank) {
        int rankCounter = 0;
        List<Card> currentHand = hands[playerIndex].cards;
        for (Card c: currentHand) {
            if (c.getRank() == rank)
                rankCounter++;
        }
        return (rankCounter >= 4);
    }

    /** Removes 4 cards from the player's hand, basically putting it on the table
     *
     * @param playerIndex
     * @param rank
     */
    public void removeBookFromHand(int playerIndex, int rank) {
        books.add(rank);
        ArrayList<Card> book = new ArrayList<>();
        for (Card c: hands[playerIndex].cards) {
            if (c.getRank() == rank)
                book.add(c);
        }
        hands[playerIndex].cards.removeAll(book);
        System.out.println("Player" + playerIndex + " made a book of " + Card.CARD_NAMES[rank] + "s!");
    }

    /** Prints the play in the console
     *
     * @param play
     * @param numCardsReceived
     * @param turn
     */
    public void printPlay(Play play, int numCardsReceived, int turn) {
        System.out.println("Player"+turn+" asks Player"+play.getTargetPlayer()+" for "+Card.CARD_NAMES[play.getRank()]+"s and received "+numCardsReceived+" card(s)!");

    }

    /** Prints values in the hand in the console
     * Used mostly for testing
     *
     * @param index
     */
    public void printHand(int index) {
        for (Card c: hands[index].cards) {
            System.out.print(c.getRank()+ " ");
        }
        System.out.println("");
    }

}
