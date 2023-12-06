package AdventureModel;

import java.util.ArrayList;

/**
 * public class DisplacementPath inherits from abstract class AdventureGamePath
 * handles all operations relating to the Progress thus far command logic
 */
public class DisplacementPath extends AdventureGamePath{
    private ArrayList<Room> displacement;
    private static DisplacementPath instance;
    public DisplacementPath(AdventureGame model) {
        super(model);
        this.displacement = new ArrayList<>();
    }
    /**
     * getInstance
     * get the current instance of the DisplacementPath object
     */
    public static DisplacementPath getInstance(AdventureGame model){
        if(instance == null){
            instance = new DisplacementPath(model);
        }
        return instance;
    }
    /**
     * reset the Progress of player whenever a new game is loaded
     */
    public static void resetPathInstance(){
        DisplacementPath.instance = null;
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
        ArrayList<Room> source = this.getPath();
        for (int i = 0; i <= source.size() - 1; i ++) {
            Room room = source.get(i);
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
     * return the string representation of the Progress
     * in format compatible with the JLabel popup windows newline and alignment format
     */
    public String toString(){
        String output = super.toString(this.getDisplacement());
        output = "<HTML>Your Progress Thus Far: <br>" + output;
        return output;
    }
}