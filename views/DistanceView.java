package views;

import AdventureModel.AdventureGame;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

public class DistanceView {
    private AdventureGameView mainView;
    private AdventureGame model;

    public DistanceView(AdventureGameView gameView, AdventureGame model) {
        this.mainView = gameView;
        this.model = model;
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(mainView.stage);

        VBox distanceDisplay = new VBox();
        distanceDisplay.setStyle("-fx-background-color: #121212;");
        distanceDisplay.setAlignment(Pos.TOP_LEFT);
        distanceDisplay.setSpacing(10);
        distanceDisplay.setPadding(new Insets(10));

        javafx.scene.control.Label headerLabel = new javafx.scene.control.Label("Your progress thus far:");
        headerLabel.setFont(new javafx.scene.text.Font("Arial", 20));
        headerLabel.setStyle("-fx-background-color: #121212; -fx-text-fill: white;");

        javafx.scene.control.Label distancePath = new javafx.scene.control.Label(model.gamePath.toString(false));
        distancePath.setWrapText(true);
        distancePath.setFont(new javafx.scene.text.Font("Arial", 16));
        distancePath.setStyle("-fx-background-color: #121212; -fx-text-fill: white;");


        headerLabel.setFocusTraversable(true);
        distancePath.setFocusTraversable(true);

        distanceDisplay.getChildren().add(headerLabel);
        distanceDisplay.getChildren().add(distancePath);

        Scene dialogScene = new Scene(distanceDisplay, 500, 500);
        dialog.setScene(dialogScene);
        dialog.show();

    }
}