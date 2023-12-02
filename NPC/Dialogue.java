package NPC;

import java.io.Serializable;

/**
 * This class contains information about a dialogue said by an NPC
 */
public class Dialogue implements Serializable {
    /**
     * id number for the dialogue.
     * NPCName + id is used for sound files and must be unique.
     */
    public int id;
    /**
     * name of the NPC
     */
    public String NPCName;
    /**
     * message said to the player
     */
    public String message;
    /**
     * event string indicating when the message is no longer relevent
     * Used for ProgressionObserver and ProgressionPublisher Interraction
     */
    public String completionEvent;

    public Dialogue(String message, String completionEvent, String NPCName, int id) {
        this.id = id;
        this.NPCName = NPCName;
        this.message = message;
        this.completionEvent = completionEvent;
    }
}
