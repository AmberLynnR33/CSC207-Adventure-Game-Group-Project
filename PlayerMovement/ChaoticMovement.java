package PlayerMovement;

import AdventureModel.Player;
import AdventureModel.Room;
import views.AdventureGameView;

import java.util.HashMap;

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
     * @param roomMap a mapping of all rooms in the game by their room numbers
     * @return false, if move results in death or a win (and game is over).  Else, true.
     */
    @Override
    public boolean movePlayer(String direction, Player player, HashMap<Integer, Room> roomMap, AdventureGameView view) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * gameModeName
     * @return the name of the game mode
     */
    @Override
    public String gameModeName() {
        return "Curse of the Lost";
    }
}
