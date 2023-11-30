package AdventureModel;

import java.io.Serializable;
import java.util.ArrayList;

public class AdventureGamePath implements Serializable {
    private AdventureGame model;
    private static AdventureGamePath instance;
    private ArrayList<Room> distance;
    private ArrayList<Room> displacement;

    private AdventureGamePath(AdventureGame model){
        this.model = model;
        this.distance = new ArrayList<Room>();
        this.distance.add(model.getPlayer().getCurrentRoom());
        updatePath();

    }
    public static AdventureGamePath getInstance(AdventureGame model){
        if(instance == null){
            instance = new AdventureGamePath(model);
        }
        return instance;
    }
    protected static void resetPathInstance(){ AdventureGamePath.instance = null;}

    public void updatePath(){
        this.distance.add(this.model.getPlayer().getCurrentRoom());
    }

    public ArrayList getDistance(){
        return this.distance;
    }
    public ArrayList getDisplacement(){
        for (int i = 0; i <= this.distance.size(); i ++){
            Room room = this.distance.get(i);
            if (!this.displacement.contains(room)){
                this.displacement.add(room);
            }
            Room to_rm = this.displacement.remove(this.displacement.size()-1);
            while (!(to_rm == room)){
                to_rm = this.displacement.remove(this.displacement.size()-1);
            }
            this.displacement.add(to_rm);
        }
        return this.displacement;
    }
}
