package AdventureModel;

import java.io.Serializable;
import java.util.HashMap;
public class AdventureGameStatistics implements Serializable {

    private final AdventureGame model; //game connected too

    private static AdventureGameStatistics instance; //this object

    private int totalRoomsVisited; //total rooms the player has been inside, including visiting multiple times

    private int totalUniqueRoomsVisited; //total rooms player has been inside at least once

    private HashMap<Room, Integer> roomVisitedAmount; //the amount of times the player has been in each room

    private Room roomVisitedMost; //the room that the player has been inside the most number of times

    private int totalRooms; //total rooms in the game associated with the model
    private int totalObjects; //the total objects in the game associated with the model

    private Room lastVisited; //the room last visited, to check if stats need to be updated

    /**
     * Constructor
     * __________________________
     * Creates an AdventureGameStatistics object assuming the player is in the starting room
     */
    private AdventureGameStatistics(AdventureGame model){
        this.model = model;
        this.totalRoomsVisited = 0;
        this.roomVisitedMost = model.getPlayer().getCurrentRoom();

        this.roomVisitedAmount = new HashMap<>();
        this.totalObjects = 0;

        this.setUpRoomsVisited();
        this.updateStatistics();
    }

    /**
     * getInstance
     * __________________________
     * Handles construction of a single AdventureGameStatistics model if not already created, and returns the
     * current statistics of the game being played.
     * @param model the game that the statistics relate to
     * @return the current object
     */
    public static AdventureGameStatistics getInstance(AdventureGame model){
        if (instance == null){
            instance = new AdventureGameStatistics(model);
        }
        return instance;
    }

    /**
     * setUpRoomsVisited
     * __________________________
     * Sets up the attributes relating to visiting rooms
     */
    private void setUpRoomsVisited(){
        HashMap<Integer, Room> allRooms = this.model.getRooms();

        this.totalRooms = allRooms.size();

        // All rooms have not been visited yet
        // Adding the objects in each room
        for (Integer curRoomNum: allRooms.keySet()){
            Room curRoom = allRooms.get(curRoomNum);

            roomVisitedAmount.put(curRoom, 0);
            this.totalObjects += curRoom.objectsInRoom.size();
        }

    }

    /**
     * updateStatistics
     * __________________________
     * Update the statistics of the AdventureGame associated with this object
     * Precondition: the player is in a different room then they were when this method was last called.
     */
    public void updateStatistics(){

        Room curRoom = this.model.getPlayer().getCurrentRoom();

        // only need to update stats if setting up or moved rooms
        if (this.lastVisited == null || this.lastVisited != curRoom) {

            //update this room visit
            this.totalRoomsVisited += 1;

            int curTimesVisited = this.roomVisitedAmount.get(curRoom) + 1;
            this.roomVisitedAmount.put(curRoom, curTimesVisited);

            //check if this is the new most visited room
            if (this.roomVisitedAmount.get(this.roomVisitedMost) < curTimesVisited) {
                this.roomVisitedMost = curRoom;
            }

            //check if this was a new room visited
            if (curTimesVisited == 1) {
                this.totalUniqueRoomsVisited += 1;
            }

            //lastly, make this the last visited room
            this.lastVisited = curRoom;
        }

    }

    /**
     * getTotalRooms
     * __________________________
     * Returns the rooms the player has been in. This number includes multiple visits to a single room.
     * @return the total rooms the player has been inside
     */
    public int getTotalRoomsVisited(){
        return this.totalRoomsVisited;
    }

    /**
     * getTotalUniqueRoomsVisited
     * __________________________
     * Returns the rooms the player has been in. This number is not impacted by visiting one room multiple times.
     * @return the unique rooms the player has been inside
     */
    public int getTotalUniqueRoomsVisited(){
        return this.totalUniqueRoomsVisited;
    }

    /**
     * getRoomVisitedMost
     * __________________________
     * Returns the room name associated with the room the player has entered the most number of times.
     * If multiple rooms have been entered the same most number of times x,
     * the first room that was visited x times is listed as the one visited the most.
     * @return the name of the room that the player has entered the most number of times.
     */
    public String getRoomVisitedMost(){
        return this.roomVisitedMost.getRoomName();
    }

    /**
     * getTotalRooms
     * __________________________
     * Returns the number of rooms the game associated with these statistics has.
     * @return the total number of rooms in the game.
     */
    public int getTotalRooms(){
        return this.totalRooms;
    }

    /**
     * getTotalObjects
     * __________________________
     * Returns the number of objects across all rooms in the game associated with these statistics.
     * @return the total number of objects in the game.
     */
    public int getTotalObjects(){
        return this.totalObjects;
    }

    /**
     * resetInstance
     * __________________________
     * Resets the statistics so a new game can have appropriate statistics.
     */
    protected static void resetInstance(){
        AdventureGameStatistics.instance = null;
    }

}
