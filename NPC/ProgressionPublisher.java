package NPC;

import java.util.ArrayList;
import java.util.List;

public interface ProgressionPublisher {
    //private List<ProgressionObserver> subscribers;
    //public abstract ProgressionPublisher();
        //subscribers = new ArrayList<ProgressionObserver>();

    public void subscribe(ProgressionObserver newObserver);
        //subscribers.add(newObserver);

    public void unsubscribe(ProgressionObserver exObserver);
        //subscribers.remove(exObserver);

    public void notifyAll(String event);
        //for(ProgressionObserver observer: subscribers) {
        //    observer.update(event);
        //}
}
