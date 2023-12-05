package AdventureModel;

import views.AdventureGameView;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Interface InteractBehavior
 * Provides necessary method that any AdventureObject related to pick must contain
 */
public interface InteractBehavior {
    /**
     * interact
     * Attempt to pick up object
     * @param obj the object they are trying to pick up
     * @param player the player that is picking up an object
     * @param view the AdventureGameView object use for gui
     * @return true if the player can pick up the object false otherwise
     */
    public Boolean interact(Player player, AdventureObject obj, AdventureGameView view) throws IOException;
}
