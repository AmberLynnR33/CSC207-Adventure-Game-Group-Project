package NPC;

import AdventureModel.Room;

public class NPCRoom extends Room {
    private NPC npc;
    public NPCRoom(String roomName, int roomNumber, String roomDescription, String adventureName){
        super(roomName, roomNumber, roomDescription, adventureName);
        this.npc = new NPC();
    }

}
