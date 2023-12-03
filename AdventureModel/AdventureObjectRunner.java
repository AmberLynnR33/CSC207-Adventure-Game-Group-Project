package AdventureModel;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
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
    public Boolean interact(Player p, AdventureObject obj, AdventureGameView view){
        Random rand = new Random();
        Integer num = rand.nextInt(10);
        if (num <= 3) return true;
        PassageTable paths = p.getCurrentRoom().getMotionTable();
        while (true){
            num = rand.nextInt(paths.passageTable.size());
            Passage path = paths.passageTable.get(num);
            if (path.getIsBlocked()) if (!p.getInventory().contains(path.getKeyName())) continue;
            if (Objects.equals(view.model.getRooms().get(path.getDestinationRoom()).getMotionTable().passageTable.get(0).getDirection(), "FORCED")) continue;
            p.getCurrentRoom().removeGameObject(obj);
            view.model.getRooms().get(path.getDestinationRoom()).addGameObject(obj);
            final Stage goblin = new Stage();
            goblin.initModality(Modality.APPLICATION_MODAL);
            goblin.initOwner(view.stage);
            Button close = new Button("Close Window");
            close.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");
            close.setPrefSize(200, 25);
            close.setFont(new Font(16));
            close.setOnAction(e -> goblin.close());
            view.makeButtonAccessible(close, "close window", "This is a button to close the goblin window", "Use this button to close the goblin window.");
            Label text = new Label("AS you reached for the " + obj.getName() + " a small goblin grabbed the "
                    + obj.getName() + " and ran " + path.getDirection() +".");
            text.setWrapText(true);
            text.setStyle("-fx-background-color: transparent;");
            text.setTextFill(Color.web("#ffffff"));
            text.setTextAlignment(TextAlignment.CENTER);
            text.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
            text.setPrefHeight(50);
            num = rand.nextInt(3); num ++;
            ImageView goblinBoy = new ImageView(new Image(view.model.getDirectoryName() + "/goblins/goblin" + num + ".png"));
            goblinBoy.setPreserveRatio(true); goblinBoy.setFitWidth(400); goblinBoy.setFitHeight(400);
            VBox box = new VBox(20);
            box.setPadding(new Insets(20, 20, 20, 20));
            box.setStyle("-fx-background-color: #121212;");
            box.setAlignment(Pos.CENTER);
            box.getChildren().addAll(close, text, goblinBoy);
            Scene goblinScene = new Scene(box, 450, 550);
            goblin.setScene(goblinScene);
            goblin.show();
            return false;
        }
    }
}
