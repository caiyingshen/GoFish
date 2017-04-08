import java.util.ArrayList;

/**
 * Created by bettyshen on 3/5/17.
 *
 * The "smart" player which extends Player
 */
public class SmartPlayer extends Player {

    public SmartPlayer(int yourPlayerNumber, int totalNumberofPlayers) {
        super(yourPlayerNumber, totalNumberofPlayers);
        recordedPlays = new ArrayList<>();
    }

    /** The method that strategically asks for cards based on what their opponents previously asked
     *  Method uses the plays stored in recordedPlays to figure out which player has which card
     *
     * @param hand The current state of the player's hand when they are to act
     * @return the smartPlayer's play
     */
    @Override
    public Play doTurn(Hand hand) {
        Play play;
        // recordedPlays must be certain size before it serves a use
        if (recordedPlays.size() > totalPlayers) {

            for (RecordedPlay r : recordedPlays) {

                // ignore the play if it belongs to the current player
                if (r.getSourcePlayer() == playerNumber)
                    continue;
                else if (hand.hasRank(r.getRank()) && !cardWasTaken(r.getSourcePlayer(), r.getRank())) {
                    play = new Play(r.getSourcePlayer(), r.getRank());
                    return play;
                }
            }
        }

        // if none of the above conditions work, randomly generate a move
        int randomRank = hand.getCard((int)(Math.random() * hand.size())).getRank();
        int randomPlayer = (int)(Math.random() * totalPlayers);

        // make sure randomPlayer is not the currentPlayer
        while (randomPlayer == playerNumber)
            randomPlayer = (int)(Math.random() * totalPlayers);
        play = new Play(randomPlayer, randomRank);
        return play;
    }

    /** When a card is found using getRank() for a play, it may no longer be in the sourcePlayer's hand
     *  If another player asked for the card from the sourcePlayer, this method returns true
     *
     * @param player whose hand you think the card is it
     * @param rank of the card youre looking for
     * @return boolean if the card is no longer in the said place
     */
    public boolean cardWasTaken(int player, int rank) {
        for (RecordedPlay r : recordedPlays) {
            if (r.getTargetPlayer() == player && r.getRank() == rank)
                return true;
        }
        return false;
    }

}
