package NPC;

public class Dialogue{
    public int id;
    public String NPCName;
    public String message;
    public String completionEvent;

    public Dialogue(String message, String completionEvent, String NPCName, int id) {
        this.id = id;
        this.NPCName = NPCName;
        this.message = message;
        this.completionEvent = completionEvent;
    }
}
