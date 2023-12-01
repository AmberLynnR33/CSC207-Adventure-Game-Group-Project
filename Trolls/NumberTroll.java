package Trolls;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.util.Random;
import java.util.Scanner;

public class NumberTroll implements Troll{
    public int num; //current Num
    public int guessLimit;
    public String instructions;
    public Label lab;

    public TextField text;
    public Integer count = 0;
    public Integer guess = -1;
    int[] pass = {0};
    public Stage box;

    public NumberTroll (javafx.scene.control.Label dialogue, TextField answer, Stage box) {
        Random r = new Random();
        this.num = r.nextInt(1001);
        this.guessLimit = 10;
        this.instructions = "I am a NUMBER TROLL. You must beat me at my game to pass.\n"
                + "I will pick a random number from 1 to 1000.\n"
                + "You will have " + this.guessLimit + " tries to guess it correctly.";
        this.lab = dialogue;
        this.text = answer;
        this.box = box;
        giveInstructions(lab);
        answer.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent key) -> {
            if (key.getCode().equals(KeyCode.ENTER)) {
                if (pass[0] == 0) {
                    try {
                        guess = Integer.parseInt(text.getText().strip());
                    } catch (Exception e) {
                        lab.setText("Silly human that is not a number, I would know. " +
                                "But it still counts as one of your guesses.");
                    }
                    if (guess == this.num) {
                        lab.setText("Curses, you got lucky this time. \n You MAY PASS.");
                        pass[0] = 1;
                        PauseTransition pause = new PauseTransition(Duration.seconds(3));
                        pause.setOnFinished(event -> {
                            box.close();
                        });
                        pause.play();
                    } else if (guess < this.num) {
                        lab.setText(guess + ": WRONG! \n My number is bigger.");
                    } else {
                        lab.setText(guess + ": WRONG! \n My number is smaller.");
                    }
                    if (count == guessLimit && pass[0] == 0) {
                        lab.setText("You have no more guesses. The correct number was: " + this.num +
                                " \n Better luck next time ... this time you MAY NOT PASS!!");
                        pass[0] = 2;
                        PauseTransition pause = new PauseTransition(Duration.seconds(3));
                        pause.setOnFinished(event -> {
                            box.close();
                        });
                        pause.play();
                    }
                    count++;
                }
                answer.setText("");
            }
        });
    }
    /**
     * giveInstructions
     * _________________________
     * All Trolls should explain how their game is played
     */
    public void giveInstructions (Label lab) { lab.setText(this.instructions); }

    /**
     * playGame
     * _________________________
     * Play the Trolls game
     *
     * @return true if player wins the game, else false
     */
    public Boolean playGame () { return pass[0] == 1; }
}
