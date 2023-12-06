package AdventureModel;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Abstract class AdventureGamePath
 * Handles all the relating operations for a game path that player has been on
 */
abstract class AdventureGamePath implements Serializable {
    private AdventureGame model;
    private static AdventureGamePath instance;
    private ArrayList<Room> path;

    public AdventureGamePath(AdventureGame model){
        this.model = model;
        this.path = new ArrayList<>();
        instance = null;
        updatePath();

    }

    public void updatePath(){
        this.path.add(this.model.getPlayer().getCurrentRoom());
    }

    public ArrayList<Room> getPath(){

        return this.path;
    }


    public String toString(ArrayList<Room> path){
        StringBuilder outputBuilder = new StringBuilder();
        for (int i = 0; i < path.size(); i++){
            if (i== 0){
                outputBuilder.append(path.get(i).getRoomName()).append("<br>");
            }
            else {
                outputBuilder.append("--->").append(path.get(i).getRoomName()).append("<br>");
            }
        }
        outputBuilder.append("</HTML>");
        return outputBuilder.toString();
    }
}
