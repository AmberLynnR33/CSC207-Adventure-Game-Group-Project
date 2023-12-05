package AdventureModel;

import views.AdventureGameView;

import java.io.Serializable;
/**
 * Class AdventureObjectPuzzle
 * Object types that does nothing
 */
public class AdventureObjectBasic implements InteractBehavior, Serializable {
    /**
     * interact
     * Attempt to pick up object
     * @param obj the object they are trying to pick up
     * @param player the player that is picking up an object
     * @param view the AdventureGameView object use for gui
     * @return true if the player can pick up the object false otherwise
     */
    public Boolean interact(Player player, AdventureObject obj, AdventureGameView view){ return true; }
}
