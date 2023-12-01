package PlayerMovement;
import Trolls.*;
import AdventureModel.Passage;
import AdventureModel.PassageTable;
import AdventureModel.Player;
import AdventureModel.Room;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import views.AdventureGameView;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * Class AlwaysTrolled
 */
public class AlwaysTrolled implements MovementGameMode{
    /**
     * movePlayer
     *
     *
     * @param direction the direction the player requests to move
     * @param player    the player that is moving rooms
     * @param roomMap a mapping of all rooms in the game by their room numbers
     * @return false, if move results in death or a win (and game is over).  Else, true.
     */
    @Override
    public boolean movePlayer(String direction, Player player, HashMap<Integer, Room> roomMap, AdventureGameView view) {
        direction = direction.toUpperCase();
        PassageTable motionTable = player.getCurrentRoom().getMotionTable(); //where can we move?
        if (!motionTable.optionExists(direction)) return true; //no move
        ArrayList<Passage> possibilities = new ArrayList<>();
        for (Passage entry : motionTable.getDirection()) {
            if (entry.getDirection().equals(direction)) possibilities.add(entry); // are there possibilities?
        }
        Passage chosen = null;
        for (Passage entry : possibilities) {
            if(chosen == null && entry.getIsBlocked()) {
                if (player.getInventory().contains(entry.getKeyName())) {
                    chosen = entry; //we can make it through, given our stuff
                    break;
                }

            } else chosen = entry; //the passage is unlocked
        }
        if (chosen == null) return true; //doh, we just can't move.
        final boolean[] pass = {true};
        if (!direction.equals("FORCED")) {
            final Stage troll = new Stage();
            troll.initModality(Modality.APPLICATION_MODAL);
            troll.initOwner(view.stage);
            Label dialogue = new Label();
            dialogue.setWrapText(true);
            dialogue.setStyle("-fx-background-color: transparent;");
            dialogue.setTextFill(Color.web("#ffffff"));
            dialogue.setTextAlignment(TextAlignment.CENTER);
            dialogue.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
            TextField answer = new TextField("");
            answer.setPrefHeight(50);
            ImageView trollMan = new ImageView(new Image(view.model.getDirectoryName() + "/troll.png"));
            trollMan.setPreserveRatio(true);
            trollMan.setFitWidth(350);
            trollMan.setFitHeight(350);
            VBox box = new VBox(20);
            box.setPadding(new Insets(20, 20, 20, 20));
            box.setStyle("-fx-background-color: #121212;");
            box.setAlignment(Pos.CENTER);
            box.getChildren().addAll(trollMan, dialogue, answer);
            Scene trollScene = new Scene(box, 450, 550);
            troll.setScene(trollScene);
            Troll tom = new NumberTroll(dialogue, answer, troll);
            troll.showAndWait();
            pass[0] = tom.playGame();
        }
        if (pass[0]){
            int roomNumber = chosen.getDestinationRoom();
            Room room = roomMap.get(roomNumber);
            player.setCurrentRoom(room);
            return !player.getCurrentRoom().getMotionTable().getDirection().get(0).getDirection().equals("FORCED");
        } else return true;
    }

    /**
     * gameModeName
     * @return the name of the game mode
     */
    @Override
    public String gameModeName() {
        return "Curse of the Troll";
    }
}
