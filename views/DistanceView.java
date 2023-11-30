package views;

import AdventureModel.AdventureGame;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DistanceView {
    private AdventureGameView mainView;
    private AdventureGame model;

    public DistanceView(AdventureGameView gameView, AdventureGame model) {
        this.mainView = gameView;
        this.model = model;
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(mainView.stage);
    }
}
