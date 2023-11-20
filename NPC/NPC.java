package NPC;

import java.util.List;

public class NPC implements ProgressionObserver{
    private List<Advice> dialouges; //A priority list of NPC advice. Highest priority is last and Advice is removed if its completionEvent is done.
    public void update(String event){
        dialouges.removeIf(advice -> advice.completionEvent.equals(event));
    }
    public String getAdvice(){
        return dialouges.get(dialouges.size()-1).message;
    }
    class Advice{
        String message;
        String completionEvent;
    }
}
