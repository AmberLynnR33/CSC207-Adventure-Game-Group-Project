package AdventureModel;

import java.io.Serializable;
import java.util.ArrayList;
import NPC.NPC;
import NPC.Dialogue;

/**
 * This class contains the information about a 
 * room in the Adventure Game.
 */
public class Room implements Serializable {

    private final String adventureName;
    /**
     * The number of the room.
     */
    private int roomNumber;

    /**
     * The name of the room.
     */
    private String roomName;

    /**
     * The description of the room.
     */
    private String roomDescription;

    /**
     * The passage table for the room.
     */
    private PassageTable motionTable = new PassageTable();

    /**
     * The list of objects in the room.
     */
    public ArrayList<AdventureObject> objectsInRoom = new ArrayList<AdventureObject>();

    /**
     * A boolean to store if the room has been visited or not
     */
    private boolean isVisited;

    /**
     * The NPC in the room. null if there is no NPC
     */
    private NPC npc;

    /**
     * AdvGameRoom constructor.
     *
     * @param roomName: The name of the room.
     * @param roomNumber: The number of the room.
     * @param roomDescription: The description of the room.
     */
    public Room(String roomName, int roomNumber, String roomDescription, String adventureName){
        this.roomName = roomName;
        this.roomNumber = roomNumber;
        this.roomDescription = roomDescription;
        this.adventureName = adventureName;
        this.isVisited = false;
        this.npc = null;
    }


    /**
     * Returns a comma delimited list of every
     * object's description that is in the given room,
     * e.g. "a can of tuna, a beagle, a lamp".
     *
     * @return delimited string of object descriptions
     */
    public String getObjectString() {
        String allObj = "";

        //get each direction
        for(AdventureObject curObj: this.objectsInRoom){
            allObj = allObj.concat(", ");
            allObj = allObj.concat(curObj.getDescription());
        }

        return !allObj.isEmpty()?allObj.substring(1):allObj;
    }

    /**
     * Returns a comma delimited list of every
     * move that is possible from the given room,
     * e.g. "DOWN,UP,NORTH,SOUTH".
     *
     * @return delimited string of possible moves
     */
    public String getCommands() {
        String allMoves = "";

        ArrayList<Passage> allPassages = (ArrayList<Passage>) this.motionTable.getDirection();

        //get each direction
        for(Passage curPassage: allPassages){
            allMoves = allMoves.concat(", ");
            allMoves = allMoves.concat(curPassage.getDirection());
        }

        //Add TALK if there is an NPC
        if(this.npc != null) {
            allMoves = allMoves.concat(", TALK");
        }

        //ensure to remove the comma and space at start of string
        return allMoves.isEmpty()?allMoves:allMoves.substring(2);
    }

    /**
     * This method adds a game object to the room.
     *
     * @param object to be added to the room.
     */
    public void addGameObject(AdventureObject object){
        this.objectsInRoom.add(object);
    }

    /**
     * This method adds an NPC to the room.
     *
     * @param npc to be added to the room.
     */
    public void addNPC(NPC npc){
        this.npc = npc;
    }

    /**
     * This method tells whether there is an npc in the room.
     * @return True iff the room has an NPC
     */
    public boolean hasNPC(){
        return this.npc != null;
    }

    /**
     * This method gets the current npc dialogue
     * @return the message most relevent to the player's game progress
     */
    public Dialogue getNPCDialogue(){
        if(hasNPC()) {
            return this.npc.getDialogue();
        }
        return new Dialogue("No NPC Here", "None", "NO NPC", -1);
    }

    /**
     * This method removes a game object from the room.
     *
     * @param object to be removed from the room.
     */
    public void removeGameObject(AdventureObject object){
        this.objectsInRoom.remove(object);
    }

    /**
     * This method checks if an object is in the room.
     *
     * @param objectName Name of the object to be checked.
     * @return true if the object is present in the room, false otherwise.
     */
    public boolean checkIfObjectInRoom(String objectName){
        for(int i = 0; i<objectsInRoom.size();i++){
            if(this.objectsInRoom.get(i).getName().equals(objectName)) return true;
        }
        return false;
    }

    /**
     * Sets the visit status of the room to true.
     */
    public void visit(){
        isVisited = true;
    }

    /**
     * Getter for returning an AdventureObject with a given name
     *
     * @param objectName: Object name to find in the room
     * @return: AdventureObject
     */
    public AdventureObject getObject(String objectName){
        for(int i = 0; i<objectsInRoom.size();i++){
            if(this.objectsInRoom.get(i).getName().equals(objectName)) return this.objectsInRoom.get(i);
        }
        return null;
    }

    /**
     * Getter method for the number attribute.
     *
     * @return: number of the room
     */
    public int getRoomNumber(){
        return this.roomNumber;
    }

    /**
     * Getter method for the description attribute.
     *
     * @return: description of the room
     */
    public String getRoomDescription(){
        return this.roomDescription.replace("\n", " ");
    }


    /**
     * Getter method for the name attribute.
     *
     * @return: name of the room
     */
    public String getRoomName(){
        return this.roomName;
    }


    /**
     * Getter method for the visit attribute.
     *
     * @return: visit status of the room
     */
    public boolean getVisited(){
        return this.isVisited;
    }


    /**
     * Getter method for the motionTable attribute.
     *
     * @return: motion table of the room
     */
    public PassageTable getMotionTable(){
        return this.motionTable;
    }


}
