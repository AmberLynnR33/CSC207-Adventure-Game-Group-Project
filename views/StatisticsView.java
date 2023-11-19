package views;

import AdventureModel.AdventureGame;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StatisticsView {

    private AdventureGameView mainView;
    private AdventureGame model;

    public StatisticsView(AdventureGameView gameView, AdventureGame model) {
        this.mainView = gameView;
        this.model = model;
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(mainView.stage);


        VBox statsInfo = new VBox();
        statsInfo.setStyle("-fx-background-color: #121212;");
        statsInfo.setAlignment(Pos.CENTER_LEFT);

        Label headerLabel = new Label("Your Current Statistics");
        Label roomsVisited = new Label("Rooms visited: " + model.gameStats.getTotalRoomsVisited());
        Label uniqueRoomsVisited = new Label("Unique Rooms Visited: " + model.gameStats.getTotalUniqueRoomsVisited());
        Label mostVisited = new Label("Room Most Visited: " + model.gameStats.getRoomVisitedMost());

        Label gameStats = new Label("Statistics about the Game Your Playing:");
        Label gameRooms = new Label("Rooms in this Game: " + model.gameStats.getTotalRooms());
        Label gameObj = new Label("Objects in this Game: " + model.gameStats.getTotalObjects());


        //Add accessibility
        headerLabel.setFocusTraversable(true);



        //add to GUI
        statsInfo.getChildren().add(headerLabel);
        statsInfo.getChildren().add(roomsVisited);
        statsInfo.getChildren().add(uniqueRoomsVisited);
        statsInfo.getChildren().add(mostVisited);
        statsInfo.getChildren().add(gameStats);
        statsInfo.getChildren().add(gameRooms);
        statsInfo.getChildren().add(gameObj);



        Scene dialogScene = new Scene(statsInfo, 400, 400);
        dialog.setScene(dialogScene);
        dialog.show();
    }

}
