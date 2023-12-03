package NPC;

import AdventureModel.Room;

import java.io.Serializable;

//GOING TO TRY TO NOT USE THIS
public class NPCRoom extends Room implements Serializable {
    private NPC npc;
    public NPCRoom(String roomName, int roomNumber, String roomDescription, String adventureName){
        super(roomName, roomNumber, roomDescription, adventureName);
        this.npc = null;
    }
    public String getNPCDialogue() {
        return npc.getAdvice();
    }
}
