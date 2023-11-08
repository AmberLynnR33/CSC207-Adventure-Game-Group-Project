package PlayerMovement;

import AdventureModel.Player;

/**
 * Class RegularMovement
 * Game mode that moves the player in the direction they request
 */
public class RegularMovement implements MovementGameMode{
    /**
     * movePlayer
     * Attempt to move the player to the room in direction
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
