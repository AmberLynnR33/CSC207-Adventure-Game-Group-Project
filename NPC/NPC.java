package NPC;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NPC implements ProgressionObserver, Serializable {
    String name;
    private List<Advice> dialouges; //A priority list of NPC advice. Highest priority is last and Advice is removed if its completionEvent is done.
    public NPC(String name){
        this.name = name;
        this.dialouges = new ArrayList<Advice>();
    }
    public void update(String event){
        dialouges.removeIf(advice -> advice.completionEvent.equals(event));
    }
    public String getAdvice(){
        return dialouges.get(dialouges.size()-1).message;
    }

    public void addAdvice(String message, String completionEvent){
        Advice newAdvice = new Advice(message, completionEvent);
        dialouges.add(0, newAdvice);
    }
    class Advice implements Serializable {
        String message;
        String completionEvent;

        public Advice(String message, String completionEvent) {
            this.message = message;
            this.completionEvent = completionEvent;
        }
    }
}
