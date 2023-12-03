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
        this.displacement = new ArrayList<Room>();
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

    public ArrayList<Room> getDistance(){

        return this.distance;
    }
    public ArrayList<Room> getDisplacement(){
        for (int i = 0; i <= this.distance.size() - 1; i ++) {
            Room room = this.distance.get(i);
            if (!this.displacement.contains(room)) {
                this.displacement.add(room);
            } else {
                Room to_rm = this.displacement.remove(this.displacement.size() - 1);
                while (!(to_rm == room)) {
                    to_rm = this.displacement.remove(this.displacement.size() - 1);
                }
                this.displacement.add(to_rm);
            }
        }
        return this.displacement;
    }

    public String toString(boolean isDisplacement){
        ArrayList<Room> source;
        if (isDisplacement) {
            source = this.getDisplacement();}
        else {source = this.getDistance();}

        StringBuilder outputBuilder = new StringBuilder();
        for (Room room: source){
            outputBuilder.append(room.getRoomName()).append("\n" + " ---> ");
        }
        String output = outputBuilder.toString();
        output = output.substring(0, output.length() - 6);
        return output;
    }
}
