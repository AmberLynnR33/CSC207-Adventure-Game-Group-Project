package NPC;

import java.util.ArrayList;
import java.util.List;

public class ProgressionPublisher {
    private List<ProgressionObserver> subscribers;
    public ProgressionPublisher(){
        subscribers = new ArrayList<NPC.ProgressionObserver>();
    }
    public void subscribe(ProgressionObserver newObserver){
        subscribers.add(newObserver);
    }
    public void unsubscribe(ProgressionObserver exObserver){
        subscribers.remove(exObserver);
    }
    public void notifyAll(String event){
        for(ProgressionObserver observer: subscribers){
            observer.update(event);
        }
    }
}
