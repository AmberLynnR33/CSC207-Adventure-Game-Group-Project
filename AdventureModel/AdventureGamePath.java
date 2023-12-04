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

        StringBuilder outputBuilder = new StringBuilder();
        ArrayList<Room> source;
        if (isDisplacement) {
            outputBuilder.append("<HTML>Your Progress Thus Far: <br>");
            source = this.getDisplacement();}
        else {
            outputBuilder.append("<HTML>Your Journey Thus Far: <br>");
            source = this.getDistance();}

        for (int i = 0; i < source.size(); i++){
            if (i== 0){
                outputBuilder.append(source.get(i).getRoomName()).append("<br>");
            }
            else {
                outputBuilder.append("--->").append(source.get(i).getRoomName()).append("<br>");
            }
        }
        outputBuilder.append("</HTML>");
        return outputBuilder.toString();
    }
}
