package AdventureModel;

import java.util.ArrayList;

public class DisplacementPath extends AdventureGamePath{
    private ArrayList<Room> displacement;
    private static DisplacementPath instance;
    public DisplacementPath(AdventureGame model) {
        super(model);
        this.displacement = new ArrayList<>();
    }
    public static DisplacementPath getInstance(AdventureGame model){
        if(instance == null){
            instance = new DisplacementPath(model);
        }
        return instance;
    }
    public static void resetPathInstance(){
        DisplacementPath.instance = null;
    }
    public ArrayList<Room> getDisplacement(){
        ArrayList<Room> source = this.getPath();
        for (int i = 0; i <= source.size() - 1; i ++) {
            Room room = source.get(i);
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

    public String toString(){
        String output = super.toString(this.getDisplacement());
        output = "<HTML>Your Progress Thus Far: <br>" + output;
        return output;
    }
}
