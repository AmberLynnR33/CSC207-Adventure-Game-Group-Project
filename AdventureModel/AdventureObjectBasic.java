package AdventureModel;

import views.AdventureGameView;

import java.io.Serializable;

public class AdventureObjectBasic implements InteractBehavior, Serializable {
    public Boolean interact(Player p, AdventureObject obj, AdventureGameView game){ return true; }
}
