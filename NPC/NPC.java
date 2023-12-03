package NPC;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NPC implements ProgressionObserver, Serializable {
    String name;
    private List<Dialogue> dialouges; //A priority list of NPC advice. Highest priority is last and Advice is removed if its completionEvent is done.
    public NPC(String name){
        this.name = name;
        this.dialouges = new ArrayList<Dialogue>();
    }
    public void update(String event){
        dialouges.removeIf(advice -> advice.completionEvent.equals(event));
    }
    public Dialogue getDialogue(){
        return dialouges.get(dialouges.size()-1);
    }

    public void addDialogue(String message, String completionEvent, int id){
        Dialogue newDialogue = new Dialogue(message, completionEvent, this.name, id);
        dialouges.add(0, newDialogue);
    }
}
