package PlayerMovement;

import AdventureModel.Player;
import AdventureModel.Room;

import java.util.HashMap;

/**
 * Class AlwaysTrolled
 */
public class AlwaysTrolled implements MovementGameMode{
    /**
     * movePlayer
     *
     *
     * @param direction the direction the player requests to move
     * @param player    the player that is moving rooms
     * @param roomMap a mapping of all rooms in the game by their room numbers
     * @return false, if move results in death or a win (and game is over).  Else, true.
     */
    @Override
    public boolean movePlayer(String direction, Player player, HashMap<Integer, Room> roomMap) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
