package views;

import AdventureModel.AdventureGame;
import AdventureModel.AdventureGamePath;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DisplacementView {
    private AdventureGameView mainView;
    private AdventureGame model;
    private AdventureGamePath path;

    public DisplacementView(AdventureGameView gameView, AdventureGame model) {
        this.mainView = gameView;
        this.model = model;
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(mainView.stage);


    }
}
