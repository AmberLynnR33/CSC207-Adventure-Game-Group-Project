package AdventureModel;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import java.io.BufferedReader;
import java.io.FileReader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import views.AdventureGameView;
/**
 * Class AdventureObjectPuzzle
 * Object types that needs the player to solve a word puzzle to obtain
 */
public class AdventureObjectPuzzle implements InteractBehavior, Serializable {
    /**
     * interact
     * Attempt to pick up object
     * @param obj the object they are trying to pick up
     * @param player the player that is picking up an object
     * @param view the AdventureGameView object use for gui
     * @return true if the player can pick up the object false otherwise
     */
    public Boolean interact(Player player, AdventureObject obj, AdventureGameView view){
        ArrayList<String> puzzles = new ArrayList<>();
        try {
            BufferedReader buff = new BufferedReader(new FileReader(view.model.getDirectoryName() + "/puzzles.txt"));
            while (buff.ready()) puzzles.add(buff.readLine());
        } catch (Exception ignored){}
        Random rand = new Random();
        char [] puzzle = puzzles.get(rand.nextInt(puzzles.size())).toCharArray();
        char [] scramblePuzzle = new char[puzzle.length];
        for (char letter: puzzle) {
            while (true){
                int num = rand.nextInt(puzzle.length);
                if (scramblePuzzle[num] == '\u0000'){
                    scramblePuzzle[num] = letter;
                    break;
                }
            }
        }
        final boolean[] solved = {false};
        final Stage chest = new Stage();
        chest.initModality(Modality.APPLICATION_MODAL);
        chest.initOwner(view.stage);
        Button close = new Button("Close Window");
        close.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");
        close.setPrefSize(200, 25);
        close.setFont(new Font(16));
        close.setOnAction(e -> chest.close());
        view.makeButtonAccessible(close, "close window", "This is a button to close the puzzle game window", "Use this button to close the puzzle game window.");
        Label scrambledWord = new Label(new String(scramblePuzzle));
        scrambledWord.setWrapText(true);
        scrambledWord.setStyle("-fx-background-color: transparent;");
        scrambledWord.setTextFill(Color.web("#ffffff"));
        scrambledWord.setTextAlignment(TextAlignment.CENTER);
        scrambledWord.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        Label text = new Label("The " + obj.getName() + " is locked in a chest you must decode the word to open it.");
        text.setWrapText(true); text.setPrefHeight(50);
        text.setStyle("-fx-background-color: transparent;");
        text.setTextFill(Color.web("#ffffff"));
        text.setTextAlignment(TextAlignment.CENTER);
        text.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        TextField answer = new TextField(""); answer.setPrefHeight(50);
        ImageView tressureChest = new ImageView(new Image(view.model.getDirectoryName() + "/tressureChest.png"));
        tressureChest.setPreserveRatio(true); tressureChest.setFitWidth(400); tressureChest.setFitHeight(350);
        VBox box = new VBox(20);
        box.setPadding(new Insets(20, 20, 20, 20));
        box.setStyle("-fx-background-color: #121212;");
        box.setAlignment(Pos.CENTER);
        box.getChildren().addAll(close, text, scrambledWord, answer, tressureChest);
        Scene chestScene = new Scene(box, 450, 550);
        chest.setScene(chestScene);
        answer.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent key) -> {
            if (key.getCode().equals(KeyCode.ENTER)) {
                if (answer.getText().strip().equalsIgnoreCase(new String(puzzle))){
                    solved[0] = true;
                    chest.close();
                }
                answer.setText("");
            }
        });
        chest.showAndWait();
        if (solved[0]){
            obj.setInteractBehavior(new AdventureObjectBasic());
        }
        return solved[0];
    }
}
