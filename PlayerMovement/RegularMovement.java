package PlayerMovement;

import AdventureModel.Passage;
import AdventureModel.PassageTable;
import AdventureModel.Player;
import AdventureModel.Room;
import views.AdventureGameView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class RegularMovement
 * Game mode that moves the player in the direction they request
 */
public class RegularMovement implements MovementGameMode {

    /**
     * movePlayer
     * Attempt to move the player to the room in direction
     *
     * @param direction the direction the player requests to move
     * @param player    the player that is moving rooms
     * @param roomMap a mapping of all rooms in the game by their room numbers
     * @return false, if move results in death or a win (and game is over).  Else, true.
     */
    @Override
    public boolean movePlayer(String direction, Player player, HashMap<Integer, Room> roomMap, AdventureGameView view) {
        direction = direction.toUpperCase();
        PassageTable motionTable = player.getCurrentRoom().getMotionTable(); //where can we move?
        if (!motionTable.optionExists(direction)) return true; //no move

        ArrayList<Passage> possibilities = new ArrayList<>();
        for (Passage entry : motionTable.getDirection()) {
            if (entry.getDirection().equals(direction)) { //this is the right direction
                possibilities.add(entry); // are there possibilities?
            }
        }

        //try the blocked passages first
        Passage chosen = null;
        for (Passage entry : possibilities) {
            System.out.println(entry.getIsBlocked());
            System.out.println(entry.getKeyName());

            if (chosen == null && entry.getIsBlocked()) {
                if (player.getInventory().contains(entry.getKeyName())) {
                    chosen = entry; //we can make it through, given our stuff
                    break;
                }
            } else { chosen = entry; } //the passage is unlocked
        }

        if (chosen == null) return true; //doh, we just can't move.

        int roomNumber = chosen.getDestinationRoom();
        Room room = roomMap.get(roomNumber);
        player.setCurrentRoom(room);
        return !player.getCurrentRoom().getMotionTable().getDirection().get(0).getDirection().equals("FORCED");
    }

    /**
     * gameModeName
     * @return the name of the game mode
     */
    @Override
    public String gameModeName() {
        return "Regular Movement";
    }
}
