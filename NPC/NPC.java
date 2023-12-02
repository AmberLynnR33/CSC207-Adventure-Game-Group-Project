package NPC;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 * This class contains information about an NPC
 */
public class NPC implements ProgressionObserver, Serializable {
    /**
     * name of the NPC
     */
    String name;
    /**
     * A priority queue of NPC advice. Highest priority is last and Advice is removed if its completionEvent is done.
     */
    private List<Dialogue> dialouges;
    public NPC(String name){
        this.name = name;
        this.dialouges = new ArrayList<Dialogue>();
    }

    /**
     * Update the NPC on the player's progression. Affects what the NPC says.
     * @param event completion event. e.g. "TAKEN KEYS"
     */
    public void update(String event){
        dialouges.removeIf(advice -> advice.completionEvent.equals(event));
    }

    /**
     * Gets the dialogue most relevent to the player at the moment.
     * @return A dialogue object to be shown as text and have audio played
     */
    public Dialogue getDialogue(){
        return dialouges.get(dialouges.size()-1);
    }

    /**
     * populates the dialogues list
     * @param message message said to the player
     * @param completionEvent string indicating when the message is no longer relevent
     * @param id id number for sound file
     */
    public void addDialogue(String message, String completionEvent, int id){
        Dialogue newDialogue = new Dialogue(message, completionEvent, this.name, id);
        dialouges.add(0, newDialogue);
    }
}
