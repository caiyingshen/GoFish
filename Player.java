import java.util.ArrayList;

/**
 * Created by bettyshen on 3/5/17.
 *
 * An abstract class used as a basis or framework for DumbPlayer and SmartPlayer
 */
public abstract class Player implements PlayerStrategy{

    int playerNumber;
    int totalPlayers;
    int targetPlayer;
    ArrayList<RecordedPlay> recordedPlays;

    public Player(int playerNumber, int totalNumberOfPlayers) {
        initialize(playerNumber, totalNumberOfPlayers);
        recordedPlays = new ArrayList<>();
    }
    // initializes objects
    public void initialize(int yourPlayerNumber, int totalNumberOfPlayers) {
        playerNumber = yourPlayerNumber;
        totalPlayers = totalNumberOfPlayers;
    }

    /** Uses random numbers to generate the current player's next move
     *
     * @param hand of the current player
     * @return Play object
     */
    public Play doTurn(Hand hand) {
        int random = (int)(Math.random() * hand.size());
        while (random > hand.size())
            random = (int)(Math.random() * hand.size());
        Card randomCard =  hand.getCard(random);
        int rank = randomCard.getRank();
        targetPlayer = (int)(Math.random() * totalPlayers);
        while (targetPlayer == playerNumber){
            targetPlayer = (int)(Math.random() * totalPlayers);
        }

        return new Play(targetPlayer, rank);
    }

    /** Keeps track of the plays in the game
     *  Basically the player's memory
     *
     * @param recordedPlay object that keeps track of play
     */
    public void playOccurred(RecordedPlay recordedPlay) {
        recordedPlays.add(recordedPlay);
    }

    /** method used for SmartPlayers to check if card actually exists in expected place
     *
     * @return whether the card was taken
     */
    public boolean cardWasTaken(int player, int rank) {
        return false;
    }


}
