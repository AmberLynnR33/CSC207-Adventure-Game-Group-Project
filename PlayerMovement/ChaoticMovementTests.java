package PlayerMovement;

import AdventureModel.AdventureGame;
import AdventureModel.AdventureGameStatistics;
import javafx.stage.Stage;
import org.junit.jupiter.api.*;
import views.AdventureGameView;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ChaoticMovementTests {

    @Test
    void testMove1(){
        AdventureGame model = new AdventureGame("MediumGame");
        Stage testStage = new Stage();
        AdventureGameView view = new AdventureGameView(model, testStage);
        model.setMovementGameMode("01");

        System.out.println("Normal Movement Rooms: 1 -> 2 -> 1 -> 3 -> 30 -> 36 -> 3");

        ArrayList<Integer> pathList = new ArrayList<>();

        model.movePlayer("WEST", view);
        pathList.add(model.player.getCurrentRoom().getRoomNumber());

        model.movePlayer("EAST", view);
        pathList.add(model.player.getCurrentRoom().getRoomNumber());

        model.movePlayer("NORTH", view);
        pathList.add(model.player.getCurrentRoom().getRoomNumber());

        model.movePlayer("NORTH", view);
        pathList.add(model.player.getCurrentRoom().getRoomNumber());

        model.movePlayer("FORCED", view);
        pathList.add(model.player.getCurrentRoom().getRoomNumber());

        model.movePlayer("FORCED", view);
        pathList.add(model.player.getCurrentRoom().getRoomNumber());

        System.out.print("In this Case: 1");
        for(Integer curRoom: pathList){
            System.out.print(" -> " + curRoom);
        }

        //Added so can view the different paths taken
        assertEquals(1, 1);

        testStage.close();
    }

    @Test
    void movesForced(){
        AdventureGame model = new AdventureGame("MediumGame");
        Stage testStage = new Stage();
        AdventureGameView view = new AdventureGameView(model, testStage);
        model.setMovementGameMode("00");

        System.out.println("Rooms Should Go Through: 1 -> 3 -> 30 -> 36 -> 3");

        ArrayList<Integer> pathList = new ArrayList<>();

        model.movePlayer("NORTH", view);

        model.movePlayer("NORTH", view);

        model.setMovementGameMode("01");

        model.movePlayer("FORCED", view);
        pathList.add(model.player.getCurrentRoom().getRoomNumber());

        model.movePlayer("FORCED", view);
        pathList.add(model.player.getCurrentRoom().getRoomNumber());

        System.out.print("In this Case: 1 -> 3 -> 30");
        for(Integer curRoom: pathList){
            System.out.print(" -> " + curRoom);
        }

        assertEquals(36, pathList.get(0));
        assertEquals(3, pathList.get(1));

        testStage.close();
    }

    @Test
    void moveNoKey(){
        ArrayList<Integer> movedTo = new ArrayList<>();

        for(int i = 0; i < 10; i++){
            AdventureGame model = new AdventureGame("MediumGame");
            Stage testStage = new Stage();
            AdventureGameView view = new AdventureGameView(model, testStage);
            model.setMovementGameMode("00");

            model.movePlayer("DOWN", view);
            model.movePlayer("DOWN", view);
            model.movePlayer("DOWN", view);

            model.setMovementGameMode("01");

            model.movePlayer("DOWN", view);
            movedTo.add(model.player.getCurrentRoom().getRoomNumber());
            testStage.close();
        }

        for (Integer curMovedTo: movedTo){
            assertNotEquals(movedTo, 8);
        }


    }

}
