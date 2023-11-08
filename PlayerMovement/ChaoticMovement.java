package PlayerMovement;

import AdventureModel.Player;

/**
 * Class ChaoticMovement
 * Game mode that moves the player to a randomized room instead of the room they specify
 */
public class ChaoticMovement implements MovementGameMode{
    /**
     * movePlayer
     * Attempt to move the player to another room
     *
     * @param direction the direction the player requests to move
     * @param player    the player that is moving rooms
     * @return false, if move results in death or a win (and game is over).  Else, true.
     */
    @Override
    public boolean movePlayer(String direction, Player player) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
