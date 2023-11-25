package AdventureModel;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import views.AdventureGameView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.Serializable;
import java.util.Objects;
import java.util.Random;

public class AdventureObjectRunner implements InteractBehavior, Serializable {
    public Boolean interact(Player p, AdventureObject obj, AdventureGameView game){
        Random rand = new Random();
        Integer num = rand.nextInt(5);
        if (num <= 1) return true;
        PassageTable paths = p.getCurrentRoom().getMotionTable();
        while (true){
            num = rand.nextInt(paths.passageTable.size());
            Passage path = paths.passageTable.get(num);
            if (path.getIsBlocked()) if (!p.getInventory().contains(path.getKeyName())) continue;
            if (Objects.equals(game.model.getRooms().get(path.getDestinationRoom()).getMotionTable().passageTable.get(0).getDirection(), "FORCED")) continue;
            p.getCurrentRoom().removeGameObject(obj);
            game.model.getRooms().get(path.getDestinationRoom()).addGameObject(obj);
            final Stage goblin = new Stage();
            goblin.initModality(Modality.APPLICATION_MODAL);
            goblin.initOwner(game.stage);
            Label text = new Label("AS you reached for the " + obj.getName() + " a small goblin grabbed the "
                    + obj.getName() + " and ran " + path.getDirection() +".");
            text.setWrapText(true);
            text.setStyle("-fx-background-color: transparent;");
            text.setTextFill(Color.web("#ffffff"));
            text.setTextAlignment(TextAlignment.CENTER);
            text.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
            text.setPrefHeight(50);
            num = rand.nextInt(3); num ++;
            ImageView goblinBoy = new ImageView(new Image(game.model.getDirectoryName() + "/goblins/goblin" + num + ".png"));
            goblinBoy.setPreserveRatio(true); goblinBoy.setFitWidth(400); goblinBoy.setFitHeight(400);
            VBox box = new VBox(20);
            box.setPadding(new Insets(20, 20, 20, 20));
            box.setStyle("-fx-background-color: #121212;");
            box.setAlignment(Pos.CENTER);
            box.getChildren().addAll(text, goblinBoy);
            Scene goblinScene = new Scene(box, 450, 500);
            goblin.setScene(goblinScene);
            goblin.show();
            return false;
        }
    }
}
