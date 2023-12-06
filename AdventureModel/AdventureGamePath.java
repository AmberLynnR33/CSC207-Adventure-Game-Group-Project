package AdventureModel;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class AdventureGamePath.  Handles all the necessary tasks to store, update and generate user's
 * Journey and Progress thus far.
 */
/**
 * Abstract class AdventureGamePath
 * Handles all the relating operations for a game path that player has been on
 */
abstract class AdventureGamePath implements Serializable {
    private AdventureGame model;
    private static AdventureGamePath instance;
    private ArrayList<Room> path;

    /**
     * Constructor
     * Creates an AdventureGamePath object assuming the player is in the starting room
     */
    public AdventureGamePath(AdventureGame model){
        this.model = model;
        this.path = new ArrayList<>();
        instance = null;
        updatePath();

    }
    /**
     * updatePath
     *
     * Update the distance attribute everytime a player move.
     */
    public void updatePath() {
        this.path.add(this.model.getPlayer().getCurrentRoom());
    }

    /**
     * getDistance
     *
     * getter method for the distance attribute
     * @return an ArrayList containing all room objects
     * the player has been in so far, in order and with repetitions
     */
    public ArrayList<Room> getPath(){

        return this.path;
    }
    /**
     * toString
     * return the string representation of the path users have been on
     * in format compatible with the JLabel popup windows newline and alignment format
     */
    public String toString(ArrayList<Room> path){

        StringBuilder outputBuilder = new StringBuilder();
        for (int i = 0; i < path.size(); i++){
            if (i== 0){
                outputBuilder.append(path.get(i).getRoomName()).append("<br>");
            }
            else {
                outputBuilder.append("--->").append(path.get(i).getRoomName()).append("<br>");
            }
        }
        outputBuilder.append("</HTML>");
        return outputBuilder.toString();
    }
}
