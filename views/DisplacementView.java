package views;

import AdventureModel.AdventureGame;
import AdventureModel.AdventureGamePath;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DisplacementView {
    private AdventureGameView mainView;
    private AdventureGame model;

    public DisplacementView(AdventureGameView gameView, AdventureGame model) {
        this.mainView = gameView;
        this.model = model;
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(mainView.stage);

        VBox displacementDisplay = new VBox();
        displacementDisplay.setStyle("-fx-background-color: #121212;");
        displacementDisplay.setAlignment(Pos.TOP_LEFT);
        displacementDisplay.setSpacing(10);
        displacementDisplay.setPadding(new Insets(10));

        Label headerLabel = new Label("Your progress thus far:");
        headerLabel.setFont(new Font("Arial", 20));
        headerLabel.setStyle("-fx-background-color: #121212; -fx-text-fill: white;");

        Label displacementPath = new Label(model.gamePath.toString(true));
        displacementPath.setWrapText(true);
        displacementPath.setFont(new Font("Arial", 16));
        displacementPath.setStyle("-fx-background-color: #121212; -fx-text-fill: white;");


        headerLabel.setFocusTraversable(true);
        displacementPath.setFocusTraversable(true);

        displacementDisplay.getChildren().add(headerLabel);
        displacementDisplay.getChildren().add(displacementPath);

        Scene dialogScene = new Scene(displacementDisplay, 500, 500);
        dialog.setScene(dialogScene);
        dialog.show();

    }
}
