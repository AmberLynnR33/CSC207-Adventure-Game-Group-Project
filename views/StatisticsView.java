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

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StatisticsView implements Serializable {

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
        statsInfo.setSpacing(10);
        statsInfo.setPadding(new Insets(10));

        Label headerLabel = new Label("Your Current Statistics:");
        headerLabel.setFont(new Font("Arial", 20));
        headerLabel.setStyle("-fx-background-color: #121212; -fx-text-fill: white;");

        Label roomsVisited = new Label("Rooms visited: " + model.gameStats.getTotalRoomsVisited());
        roomsVisited.setFont(new Font("Arial", 16));
        roomsVisited.setStyle("-fx-background-color: #121212; -fx-text-fill: white;");

        Label uniqueRoomsVisited = new Label("Unique Rooms Visited: " + model.gameStats.getTotalUniqueRoomsVisited());
        uniqueRoomsVisited.setFont(new Font("Arial", 16));
        uniqueRoomsVisited.setStyle("-fx-background-color: #121212; -fx-text-fill: white;");

        Label mostVisited = new Label("Room Most Visited: " + model.gameStats.getRoomVisitedMost());
        mostVisited.setFont(new Font("Arial", 16));
        mostVisited.setStyle("-fx-background-color: #121212; -fx-text-fill: white;");

        Label gameStats = new Label("Statistics about the Game Your Playing:");
        gameStats.setFont(new Font("Arial", 20));
        gameStats.setStyle("-fx-background-color: #121212; -fx-text-fill: white;");

        Label gameRooms = new Label("Rooms in this Game: " + model.gameStats.getTotalRooms());
        gameRooms.setFont(new Font("Arial", 16));
        gameRooms.setStyle("-fx-background-color: #121212; -fx-text-fill: white;");

        Label gameObj = new Label("Objects in this Game: " + model.gameStats.getTotalObjects());
        gameObj.setFont(new Font("Arial", 16));
        gameObj.setStyle("-fx-background-color: #121212; -fx-text-fill: white;");

        Button closeStatsPanel = new Button("Close Window");
        AdventureGameView.makeButtonAccessible(closeStatsPanel, "Close Statistics Panel.", "This button closes the Statistics popup.", "This button closes the statistics window. Click it to go back to your game.");
        closeStatsPanel.setFont(new Font("Arial", 16));
        closeStatsPanel.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");
        closeStatsPanel.setOnAction(e -> dialog.close());

        //Add accessibility
        headerLabel.setFocusTraversable(true);
        roomsVisited.setFocusTraversable(true);
        uniqueRoomsVisited.setFocusTraversable(true);
        mostVisited.setFocusTraversable(true);
        gameStats.setFocusTraversable(true);
        gameRooms.setFocusTraversable(true);
        gameObj.setFocusTraversable(true);


        //add to GUI
        statsInfo.getChildren().add(closeStatsPanel);
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
