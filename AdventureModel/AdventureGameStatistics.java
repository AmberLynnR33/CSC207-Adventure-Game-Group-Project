package AdventureModel;

import java.io.Serializable;
import java.util.HashMap;
public class AdventureGameStatistics implements Serializable {

    private final AdventureGame model; //game connected too

    private AdventureGameStatistics instance; //this object

    private int totalRoomsVisited; //total rooms the player has been inside, including visiting multiple times

    private int totalUniqueRoomsVisited; //total rooms player has been inside at least once

    private HashMap<Room, Integer> roomVisitedAmount; //the amount of times the player has been in each room

    private Room roomVisitedMost; //the room that the player has been inside the most number of times

    private int totalRooms; //total rooms in the game associated with the model
    private int totalObjects; //the total objects in the game associated with the model

    /**
     * Constructor
     * __________________________
     * Creates an AdventureGameStatistics object assuming the player is in the starting room
     */
    public AdventureGameStatistics(AdventureGame model){
        this.model = model;
        this.totalRoomsVisited = 0;
        this.roomVisitedMost = model.getPlayer().getCurrentRoom();

        this.roomVisitedAmount = new HashMap<>();
        this.totalObjects = 0;

        this.setUpRoomsVisited();
        this.updateStatistics();
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

        //update this room visit
        this.totalRoomsVisited += 1;

        int curTimesVisited = this.roomVisitedAmount.get(curRoom) + 1;
        this.roomVisitedAmount.put(curRoom, curTimesVisited);

        //check if this is the new most visited room
        if (this.roomVisitedAmount.get(this.roomVisitedMost) < curTimesVisited){
            this.roomVisitedMost = curRoom;
        }

        //check if this was a new room visited
        if (curTimesVisited == 1){
            this.totalUniqueRoomsVisited += 1;
        }

    }


}
