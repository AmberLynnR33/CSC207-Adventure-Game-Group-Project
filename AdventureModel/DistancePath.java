package AdventureModel;

import java.util.ArrayList;

/**
 * public class DistancePath inherits from abstract class AdventureGamePath
 * handles all operations relating to the Journey thus far command logic
 */
public class DistancePath extends AdventureGamePath{
    private static DistancePath instance;
    public DistancePath(AdventureGame model) {
        super(model);
    }
    /**
     * getInstance
     * get the current instance of the DistancePath object
     */
    public static DistancePath getInstance(AdventureGame model){
        if(instance == null){
            instance = new DistancePath(model);
        }
        return instance;
    }

    /**
     * reset the Journey of player whenever a new game is loaded
     */
    public static void resetPathInstance() {
        DistancePath.instance = null;
    }
    /**
     * getDistance
     *
     * getter method for the path attribute of DistancePath
     * @return an ArrayList containing all room objects
     * the player has been in so far (i.e., if a move brings a player to a room they
     * have previously been in, these will all be included in order of visiting).
     */
    public ArrayList<Room> getDistance(){
        return super.getPath();
    }
    /**
     * toString
     * return the string representation of the Journey
     * in format compatible with the JLabel popup windows newline and alignment format
     */
    public String toString(){
        String output = super.toString(this.getPath());
        output = "<HTML>Your Journey Thus Far: <br>" + output;
        return output;
    }

}