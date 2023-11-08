package PlayerMovement;

import AdventureModel.Player;
import AdventureModel.Room;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Interface PlayerMovement
 * Provides necessary methods that any game modes related to player movement must contain
 */
public interface MovementGameMode {

    /**
     * movePlayer
     * Attempt to move the player to another room
     * @param direction the direction the player requests to move
     * @param player the player that is moving rooms
     * @param roomMap a mapping of all rooms in the game by their room numbers
     * @return false, if move results in death or a win (and game is over).  Else, true.
     */
    public boolean movePlayer(String direction, Player player, HashMap<Integer, Room> roomMap);
}
