package PlayerMovement;

import AdventureModel.Passage;
import AdventureModel.Player;
import AdventureModel.Room;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Class ChaoticMovement
 * Game mode that moves the player to a randomized room instead of the room they specify
 */
public class ChaoticMovement implements MovementGameMode{

    private RegularMovement movingRooms; //Moves the player after the random direction is selected

    public ChaoticMovement(){
        super();
        this.movingRooms = new RegularMovement();
    }

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
    public boolean movePlayer(String direction, Player player, HashMap<Integer, Room> roomMap) {

        List<Passage> directionsCanMove = new ArrayList<>();

        //if a forced room, should use RegularMovement's implementation
        if (player.getCurrentRoom().getMotionTable().passageTable.get(0).getDirection().equals("FORCED")){
            return this.movingRooms.movePlayer(direction, player, roomMap);
        }

        //get all possible directions the player can go
        for (Passage curPasssage: player.getCurrentRoom().getMotionTable().passageTable) {
            if (curPasssage.getIsBlocked()) {
                if (player.checkIfObjectInInventory(curPasssage.getKeyName())) {
                    directionsCanMove.add(curPasssage);
                }
            } else {
                directionsCanMove.add(curPasssage);
            }
        }

        Double indexMovingTo = Math.floor(Math.random() * (directionsCanMove.size()));

        return this.movingRooms.movePlayer(directionsCanMove.get(indexMovingTo.intValue()).getDirection(),
                player, roomMap);

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
