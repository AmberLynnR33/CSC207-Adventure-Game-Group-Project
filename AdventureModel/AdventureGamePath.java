package AdventureModel;

import java.io.Serializable;
import java.util.ArrayList;

/**
<<<<<<< HEAD
 * Abstract class AdventureGamePath
 * Handles all the relating operations for a game path that player has been on
 */
abstract class AdventureGamePath implements Serializable {
=======
 * Class AdventureGamePath.  Handles all the necessary tasks to store, update and generate user's
 * Journey and Progress thus far.
 */
public class AdventureGamePath implements Serializable {
>>>>>>> main
    private AdventureGame model;
    private static AdventureGamePath instance;
    private ArrayList<Room> path;

<<<<<<< HEAD
    public AdventureGamePath(AdventureGame model){
=======
    /**
     * Constructor
     * Creates an AdventureGamePath object assuming the player is in the starting room
     */
    private AdventureGamePath(AdventureGame model){
>>>>>>> main
        this.model = model;
        this.path = new ArrayList<>();
        instance = null;
        updatePath();

    }
<<<<<<< HEAD

    public void updatePath(){
        this.path.add(this.model.getPlayer().getCurrentRoom());
    }

    public ArrayList<Room> getPath(){

        return this.path;
    }


    public String toString(ArrayList<Room> path){
=======
    /**
     * getInstance
     *
     * Handles construction of a single AdventureGamePath instance if not already created.
     * @param model the game that the path reflects
     * @return the current object
     */
    public static AdventureGamePath getInstance(AdventureGame model){
        if(instance == null){
            instance = new AdventureGamePath(model);
        }
        return instance;
    }
    /**
     * resetPathInstance
     *
     * Resets the path so a new game can have both the Journey and Progress empty.
     */
    protected static void resetPathInstance(){ AdventureGamePath.instance = null;}

    /**
     * updatePath
     *
     * Update the distance attribute everytime a player move.
     */
    public void updatePath() {
        this.distance.add(this.model.getPlayer().getCurrentRoom());
    }

    /**
     * getDistance
     *
     * getter method for the distance attribute
     * @return an ArrayList containing all room objects
     * the player has been in so far, in order and with repetitions
     */
    public ArrayList<Room> getDistance(){

        return this.distance;
    }
    /**
     * getDisplacement
     *
     * getter method for the displacement attribute
     * @return an ArrayList containing all room objects
     * the player has progress to so far (i.e., if a move brings a player to a room they
     * have previously been in, all the rooms they have been in between the previous and current
     * duplicated rooms will not be included).
     */
    public ArrayList<Room> getDisplacement(){
        for (int i = 0; i <= this.distance.size() - 1; i ++) {
            Room room = this.distance.get(i);
            if (!this.displacement.contains(room)) {
                this.displacement.add(room);
            } else {
                Room to_rm = this.displacement.remove(this.displacement.size() - 1);
                while (!(to_rm == room)) {
                    to_rm = this.displacement.remove(this.displacement.size() - 1);
                }
                this.displacement.add(to_rm);
            }
        }
        return this.displacement;
    }

    /**
     * toString
     *
     * toString method for both the Journey/distance and Progress/Displacement buttons
     * @param isDisplacement true if the method is intended for the Progress/Displacement button,
     *                       false otherwise
     * return a string representation of the path compatible with the JLabel associated with
     *                       the Journey and Progress buttons
     */
    public String toString(boolean isDisplacement){
>>>>>>> main
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
