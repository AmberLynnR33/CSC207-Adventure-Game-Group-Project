package AdventureModel;

import java.util.ArrayList;

public class DistancePath extends AdventureGamePath{
    private static DistancePath instance;
    public DistancePath(AdventureGame model) {
        super(model);
    }
    public static DistancePath getInstance(AdventureGame model){
        if(instance == null){
            instance = new DistancePath(model);
        }
        return instance;
    }

    public static void resetPathInstance() {
        DistancePath.instance = null;
    }

    public ArrayList<Room> getDistance(){
        return super.getPath();
    }
    public String toString(){
        String output = super.toString(this.getPath());
        output = "<HTML>Your Journey Thus Far: <br>" + output;
        return output;
    }

}
