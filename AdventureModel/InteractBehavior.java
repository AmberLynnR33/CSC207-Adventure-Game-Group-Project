package AdventureModel;

import views.AdventureGameView;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface InteractBehavior {
    public Boolean interact(Player p, AdventureObject obj, AdventureGameView game) throws IOException;
}
