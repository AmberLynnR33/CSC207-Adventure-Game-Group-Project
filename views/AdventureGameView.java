package views;

import AdventureModel.*;
import NPC.Dialogue;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.layout.*;
import javafx.scene.input.KeyEvent; //you will need these!
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import javafx.event.EventHandler; //you will need this too!
import javafx.scene.AccessibleRole;
import javafx.geometry.Orientation;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class AdventureGameView.
 * This is the Class that will visualize your model.
 * You are asked to demo your visualization via a Zoom
 * recording. Place a link to your recording below.
 * // Please see the following Google Drive link as I cannot cloud share on Zoom. There is no password.
 * ZOOM LINK: https://drive.google.com/file/d/1q6gIRrxTv5tEANpWZYLRDH3_7im41xvk/view?usp=sharing
 * PASSWORD:
 */
public class AdventureGameView {

    /** Model of the game*/
    public AdventureGame model; //model of the game
    /** Stage on which all is rendered*/
    public  Stage stage; //stage on which all is rendered
    /** Buttons*/
    Button saveButton, loadButton, helpButton; //buttons
    Button zoomButton;

    Button distanceButton;
    Button displacementButton;

    Button statsButton;
    Boolean helpToggle = false; //is help on display?

    GridPane gridPane = new GridPane(); //to hold images and buttons
    Label roomDescLabel = new Label(); //to hold room description and/or instructions
    VBox objectsInRoom = new VBox(); //to hold room items
    VBox objectsInInventory = new VBox(); //to hold inventory items
    ImageView roomImageView; //to hold room image
    TextField inputTextField; //for user input
    HBox commandButtons; //for user input by buttons

    private MediaPlayer mediaPlayer; //to play audio
    private boolean mediaPlaying; //to know if the audio is playing

    private PauseTransition forcedTransition; //the transition that occurs when in FORCED room

    private ToggleGroup movementGameModes = new ToggleGroup();
    private VBox gameModePanel;
    private Label gameModeLabel = new Label();

    private final Button TEST_BUTTON = new Button(); //Button for checking class

    /** The current view of the game*/
    public static AdventureGameView game;


    /**
     * Adventure Game View Constructor
     * Initializes attributes
     */
    public AdventureGameView(AdventureGame model, Stage stage) {
        this.model = model;
        this.stage = stage;
        AdventureGameView.game = this;
        intiUI();
    }

    /**
     * Initialize the UI
     */
    public void intiUI() {

        // setting up the stage
        this.stage.setTitle("Group 65's Adventure Game");

        //Inventory + Room items
        objectsInInventory.setSpacing(10);
        objectsInInventory.setAlignment(Pos.TOP_CENTER);
        objectsInRoom.setSpacing(10);
        objectsInRoom.setAlignment(Pos.TOP_CENTER);

        // GridPane, anyone?
        gridPane.setPadding(new Insets(20));
        gridPane.setBackground(new Background(new BackgroundFill(
                Color.valueOf("#000000"),
                new CornerRadii(0),
                new Insets(0)
        )));

        //Three columns, three rows for the GridPane
        ColumnConstraints column1 = new ColumnConstraints(150);
        ColumnConstraints column2 = new ColumnConstraints(650);
        ColumnConstraints column3 = new ColumnConstraints(150);
        ColumnConstraints column4 = new ColumnConstraints(10);
        ColumnConstraints column5 = new ColumnConstraints(200);
        column3.setHgrow( Priority.SOMETIMES ); //let some columns grow to take any extra space
        column1.setHgrow( Priority.SOMETIMES );

        Separator separator = new Separator(Orientation.VERTICAL);
        gridPane.add(separator, 3, 0, 1, GridPane.REMAINING);

        // Row constraints
        RowConstraints row1 = new RowConstraints();
        RowConstraints row2 = new RowConstraints( 550 );
        RowConstraints row3 = new RowConstraints();
        row1.setVgrow( Priority.SOMETIMES );
        row3.setVgrow( Priority.SOMETIMES );

        gridPane.getColumnConstraints().addAll( column1 , column2 , column1 , column4);
        gridPane.getRowConstraints().addAll( row1 , row2 , row1 );

        // Buttons
        saveButton = new Button("Save");
        saveButton.setId("Save");
        customizeButton(saveButton, 100, 50);
        makeButtonAccessible(saveButton, "Save Button", "This button saves the game.", "This button saves the game. Click it in order to save your current progress, so you can play more later.");
        addSaveEvent();

        loadButton = new Button("Load");
        loadButton.setId("Load");
        customizeButton(loadButton, 100, 50);
        makeButtonAccessible(loadButton, "Load Button", "This button loads a game from a file.", "This button loads the game from a file. Click it in order to load a game that you saved at a prior date.");
        addLoadEvent();

        helpButton = new Button("Instructions");
        helpButton.setId("Instructions");
        customizeButton(helpButton, 200, 50);
        makeButtonAccessible(helpButton, "Help Button", "This button gives game instructions.", "This button gives instructions on the game controls. Click it to learn how to play.");
        addInstructionEvent();

        zoomButton = new Button("Zoom");
        zoomButton.setId("Zoom");
        zoomButton.setPrefSize(60, 60);
        zoomButton.setFont(new Font("Arial", 17));
        zoomButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");
        Image zoomIcon = new Image("views/zoom-icon.png");
        ImageView zoomIconView = new ImageView(zoomIcon);
        zoomIconView.setFitHeight(60);
        zoomIconView.setPreserveRatio(true);
        zoomButton.setText("Zoom Option");
        zoomButton.setGraphic(zoomIconView);
        zoomButton.setAlignment(Pos.BASELINE_CENTER);
        zoomButton.setWrapText(true);
        zoomButton.setContentDisplay(ContentDisplay.TOP);
        makeButtonAccessible(this.zoomButton, "Zoom Button", "This button gives zoom view of currrent room image", "This button gives zoom-able view of room image that player is currently in.");
        addZoomEvent();

        distanceButton = new Button("Distance");
        distanceButton.setId("distance");
        distanceButton.setPrefSize(60, 60);
        distanceButton.setFont(new Font("Arial", 17));
        distanceButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");
        Image distanceIcon = new Image("visualPaths/distance.png");
        ImageView distanceIconView = new ImageView(distanceIcon);
        distanceIconView.setFitHeight(60);
        distanceIconView.setPreserveRatio(true);
        distanceButton.setText("Journey thus far");
        distanceButton.setGraphic(distanceIconView);
        distanceButton.setAlignment(Pos.BASELINE_CENTER);
        distanceButton.setWrapText(true);
        distanceButton.setContentDisplay(ContentDisplay.TOP);
        makeButtonAccessible(distanceButton, "Distance Button", "This button displays view of all the rooms traveled thus far, repetition included.", "This button displays view of all the rooms traveled thus far, repetition included.");
        addDistanceEvent();

        displacementButton = new Button("Show progress thus far");
        displacementButton = new Button("Displacement");
        displacementButton.setId("displacement");
        displacementButton.setPrefSize(49, 49);
        displacementButton.setFont(new Font("Arial", 17));
        displacementButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");
        Image displacementIcon = new Image("visualPaths/displacement.png");
        ImageView displacementIconView = new ImageView(displacementIcon);
        displacementIconView.setFitHeight(49);
        displacementIconView.setPreserveRatio(true);
        displacementButton.setText("Progress thus far");
        displacementButton.setGraphic(displacementIconView);
        displacementButton.setAlignment(Pos.BASELINE_CENTER);
        displacementButton.setWrapText(true);
        displacementButton.setContentDisplay(ContentDisplay.TOP);
        makeButtonAccessible(displacementButton, "Displacement Button", "This button displays view of all the rooms traveled thus far, repetition not included.", "This button displays view of all the rooms traveled thus far, repetition not included.");
        addDisplacementEvent();

        // statistics button
        statsButton = new Button("Statistics");
        statsButton.setId("Statistics");
        statsButton.setPrefSize(120,60);
        statsButton.setFont(new Font("Arial", 20));
        statsButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");
        makeButtonAccessible(this.statsButton, "Statistics Button", "This button gives statistics of the current game.", "This button gives statistics related to the overall game and visited rooms. Click it to see these numbers.");
        addStatsEvent();

        HBox topButtons = new HBox();
        topButtons.getChildren().addAll(saveButton, helpButton, loadButton);
        topButtons.setSpacing(10);
        topButtons.setAlignment(Pos.CENTER);

        inputTextField = new TextField();
        inputTextField.setFont(new Font("Arial", 16));
        inputTextField.setFocusTraversable(true);

        inputTextField.setAccessibleRole(AccessibleRole.TEXT_AREA);
        inputTextField.setAccessibleRoleDescription("Text Entry Box");
        inputTextField.setAccessibleText("Enter commands in this box.");
        inputTextField.setAccessibleHelp("This is the area in which you can enter commands you would like to play.  Enter a command and hit return to continue.");
        addTextHandlingEvent(); //attach an event to this input field

        //Command buttons
        commandButtons = new HBox();
        commandButtons.setSpacing(10);
        commandButtons.setAlignment(Pos.CENTER);
        updateCommandButtons();

        //labels for inventory and room items
        Label objLabel =  new Label("Objects in Room");
        objLabel.setAlignment(Pos.CENTER);
        objLabel.setStyle("-fx-text-fill: white;");
        objLabel.setFont(new Font("Arial", 18));

        Label invLabel =  new Label("Your Inventory");
        invLabel.setAlignment(Pos.CENTER);
        invLabel.setStyle("-fx-text-fill: white;");
        invLabel.setFont(new Font("Arial", 18));

        //add all the widgets to the GridPane
        gridPane.add( objLabel, 0, 0, 1, 1 );  // Add label
        gridPane.add( topButtons, 1, 0, 1, 1 );  // Add buttons
        gridPane.add( invLabel, 2, 0, 1, 1 );  // Add label

        Label commandLabel = new Label("What would you like to do?");
        commandLabel.setStyle("-fx-text-fill: white;");
        commandLabel.setFont(new Font("Arial", 16));

        updateScene(""); //method displays an image and whatever text is supplied
        updateItems(); //update items shows inventory and objects in rooms

        this.objectsInRoom.setFocusTraversable(true);
        this.objectsInInventory.setFocusTraversable(true);

        //make object boxes traversable
        this.objectsInRoom.setAccessibleRole(AccessibleRole.SCROLL_PANE);
        this.objectsInRoom.setAccessibleRoleDescription("Panel containing objects in this room");
        this.objectsInRoom.setAccessibleText("This panel contains the objects in this room");
        this.objectsInRoom.setAccessibleHelp("The following buttons are the objects in this room. Continue traversing to hear more about these objects.");

        this.objectsInInventory.setAccessibleRole(AccessibleRole.SCROLL_PANE);
        this.objectsInInventory.setAccessibleRoleDescription("Panel containing objects in your inventory");
        this.objectsInInventory.setAccessibleText("This panel contains the objects in your inventory");
        this.objectsInInventory.setAccessibleHelp("The following buttons are the objects in your inventory. Continue traversing to hear more about these objects.");

        this.setTraversablePath(this.loadButton, this.gameModeLabel);

        // adding the text area and submit button to a VBox
        VBox textEntry = new VBox();
        textEntry.setStyle("-fx-background-color: #000000;");
        textEntry.setPadding(new Insets(20, 20, 20, 20));
        textEntry.getChildren().addAll(commandLabel, commandButtons, inputTextField);
        textEntry.setSpacing(10);
        textEntry.setAlignment(Pos.CENTER);
        gridPane.add( textEntry, 0, 2, 3, 1 );

        // adding extra features panel
        VBox extraFeatures = new VBox();
        extraFeatures.getChildren().add(zoomButton);
        extraFeatures.getChildren().add(distanceButton);
        extraFeatures.getChildren().add(displacementButton);
        extraFeatures.getChildren().add(statsButton);
        extraFeatures.setAlignment(Pos.CENTER_LEFT);
        extraFeatures.setSpacing(10);
        gridPane.add(extraFeatures, 4,1,1,1);

        // Render everything
        var scene = new Scene( gridPane,  1210, 800);
        scene.setFill(Color.BLACK);
        this.stage.setScene(scene);
        this.stage.setResizable(false);
        this.stage.show();

    }

    /**
     * private method to handle event for the Displacement Button
     */
    private void addDisplacementEvent() {
        displacementButton.setOnAction(e -> {
            gridPane.requestFocus();
            try{
                for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException |
                     IllegalAccessException ex) {
                throw new RuntimeException(ex);
            }
            EventQueue.invokeLater(() -> new DisplacementView(getPath(true)).setVisible(true));
        });
    }


    /**
     * private method to handle event for the Distance Button
     */private void addDistanceEvent() {
        distanceButton.setOnAction(e -> {
            gridPane.requestFocus();
            try{
                for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException |
                     IllegalAccessException ex) {
                throw new RuntimeException(ex);
            }
            EventQueue.invokeLater(() -> new DistanceView(getPath(false)).setVisible(true));
        });
    }
    /**
     * updateCommandButtons
     *
     */
    private void updateCommandButtons() {
        //clear out old buttons
        this.commandButtons.getChildren().clear();
        //add buttons for current commands
        String[] possibleMoves = this.model.player.getCurrentRoom().getCommands().split(", ");
        List<String> moves = new ArrayList<>(Arrays.stream(possibleMoves).distinct().toList());
        moves.remove("FORCED");
        for(String command: moves){
            Button commandButton = new Button(command);
            commandButton.setId(command);
            customizeButton(commandButton, 100, 50);
            makeButtonAccessible(commandButton, command + " Button", "This button uses the command "+command, "This button uses the command "+command);
            commandButton.setOnAction(e -> {
                stopArticulation(); //if speaking, stop
                submitEvent(command);
            });
            this.commandButtons.getChildren().add(commandButton);
        }
    }


    /**
     * makeButtonAccessible
     * For information about ARIA standards, see
     * https://www.w3.org/WAI/standards-guidelines/aria/
     *
     * @param inputButton the button to add screenreader hooks to
     * @param name ARIA name
     * @param shortString ARIA accessible text
     * @param longString ARIA accessible help text
     */
    public static void makeButtonAccessible(Button inputButton, String name, String shortString, String longString) {
        inputButton.setAccessibleRole(AccessibleRole.BUTTON);
        inputButton.setAccessibleRoleDescription(name);
        inputButton.setAccessibleText(shortString);
        inputButton.setAccessibleHelp(longString);
        inputButton.setFocusTraversable(true);
    }

    /**
     * makeRadioButtonAccessible
     * Makes a radiobutton accessible for those using a screenreader
     * @param inputButton radio button that will be traversed or read by a screenreader
     * @param name the name of the radiobutton
     * @param shortString short description of the button
     * @param longString long description of the button
     */
    private static void makeRadioButtonAccessible(RadioButton inputButton, String name, String shortString, String longString){
        inputButton.setAccessibleRole(AccessibleRole.RADIO_BUTTON);
        inputButton.setAccessibleRoleDescription(name);
        inputButton.setAccessibleText(shortString);
        inputButton.setAccessibleHelp(longString);
        inputButton.setFocusTraversable(true);
    }

    /**
     * customizeButton
     *
     *
     * @param inputButton the button to make stylish :)
     * @param w width
     * @param h height
     */
    private void customizeButton(Button inputButton, int w, int h) {
        inputButton.setPrefSize(w, h);
        inputButton.setFont(new Font("Arial", 18));
        inputButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");
    }

    /**
     * setUpGameModes
     * sets up the game mode panel and adds it to the GUI
     * */
    private void setUpGameModes(){
        // game mode buttons
        RadioButton regMoveGameMode = new RadioButton("Regular Movement");
        regMoveGameMode.setFont(new Font("Arial", 15));
        regMoveGameMode.setId("00");

        RadioButton chaoticMoveGameMode = new RadioButton("Curse of the Lost");
        chaoticMoveGameMode.setFont(new Font("Arial", 15));
        chaoticMoveGameMode.setId("01");

        RadioButton trollGameMode = new RadioButton("Curse of the Troll");
        trollGameMode.setFont(new Font("Arial", 15));
        trollGameMode.setId("02");

        regMoveGameMode.fire();

        //make accessible
        makeRadioButtonAccessible(regMoveGameMode, "Regular Movement", "This button sets the game mode to Regular Movement", "This button enables the Regular Movement game mode. Select it to play your game with the standard movement of rooms.");
        makeRadioButtonAccessible(chaoticMoveGameMode, "Curse of the Lost Movement", "This button sets the game mode to Curse of the Lost", "This button enables the Curse of the Lost game mode. Select it to play your game with random room movement.");
        makeRadioButtonAccessible(trollGameMode, "Curse of the Troll", "This button sets the game mode to Curse of the Troll", "This button enables the Curse of the Troll game mode. Select it to encounter trolls when you move rooms.");
        this.gameModeLabel.setFocusTraversable(true);

        this.setTraversablePath(this.gameModeLabel, regMoveGameMode);
        this.setTraversablePath(regMoveGameMode, chaoticMoveGameMode);
        this.setTraversablePath(chaoticMoveGameMode, trollGameMode);
        this.setTraversablePath(trollGameMode, this.objectsInRoom);

        //turn into toggles so only one can be selected
        this.movementGameModes = new ToggleGroup();
        regMoveGameMode.setToggleGroup(this.movementGameModes);
        chaoticMoveGameMode.setToggleGroup(this.movementGameModes);
        trollGameMode.setToggleGroup(this.movementGameModes);

        //game mode changing text colour
        this.gameModeLabel.setStyle("-fx-text-fill: white;");
        this.gameModeLabel.setFont(new Font("Arial", 18));
        regMoveGameMode.setStyle("-fx-text-fill: white;");
        chaoticMoveGameMode.setStyle("-fx-text-fill: white;");
        trollGameMode.setStyle("-fx-text-fill: white;");

        this.gameModeLabel.setWrapText(true);

        // add game mode selection to it's panel
        this.gameModePanel = new VBox();
        this.gameModePanel.getChildren().add(this.gameModeLabel);
        this.gameModePanel.getChildren().add(regMoveGameMode);
        this.gameModePanel.getChildren().add(chaoticMoveGameMode);
        this.gameModePanel.getChildren().add(trollGameMode);
        this.gameModePanel.setAlignment(Pos.CENTER_LEFT);

        this.gridPane.add(this.gameModePanel, 4,0,1,1);

    }

    /**
     * addTextHandlingEvent
     *
     * Add an event handler to the myTextField attribute
     *
     * Your event handler should respond when users
     * hits the ENTER or TAB KEY. If the user hits
     * the ENTER Key, strip white space from the
     * input to myTextField and pass the stripped
     * string to submitEvent for processing.
     *
     * If the user hits the TAB key, move the focus
     * of the scene onto any other node in the scene
     * graph by invoking requestFocus method.
     */
    private void addTextHandlingEvent() {

        EventHandler<KeyEvent> pressedButton = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if(keyEvent.getCode() == KeyCode.ENTER){
                    //This means there is a new command to try
                    String commandStr = inputTextField.getText().trim();
                    submitEvent(commandStr);

                    //clear the box for new input
                    inputTextField.setText("");
                }else if(keyEvent.getCode() == KeyCode.TAB){
                    saveButton.requestFocus();
                }
                keyEvent.consume();
            }
        };

        this.inputTextField.setOnKeyPressed(pressedButton);

    }

    /**
     * Set the path tab traversal should take from atNode to toNode
     *
     * @param atNode the node that currently has focus when using tab traversing
     * @param toNode the node that should next need focused on when tab traversing is used
     */
    private void setTraversablePath(Node atNode, Node toNode){
        EventHandler<KeyEvent> pressedButton = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if(keyEvent.getCode() == KeyCode.TAB){
                    toNode.requestFocus();
                }
                keyEvent.consume();
            }
        };
        atNode.setOnKeyPressed(pressedButton);
    }


    /**
     * submitEvent
     *
     *
     * @param text the command that needs to be processed
     */
    private void submitEvent(String text) {

        text = text.strip(); //get rid of white space
        stopArticulation(); //if speaking, stop

        if (text.equalsIgnoreCase("LOOK") || text.equalsIgnoreCase("L")) {
            String roomDesc = this.model.getPlayer().getCurrentRoom().getRoomDescription();
            String objectString = this.model.getPlayer().getCurrentRoom().getObjectString();
            if (!objectString.isEmpty()) roomDescLabel.setText(roomDesc + "\n\nObjects in this room:\n" + objectString);
            articulateRoomDescription(); //all we want, if we are looking, is to repeat description.
            return;
        } else if (text.equalsIgnoreCase("HELP") || text.equalsIgnoreCase("H")) {
            showInstructions();
            return;
        } else if (text.equalsIgnoreCase("COMMANDS") || text.equalsIgnoreCase("C")) {
            showCommands(); //this is new!  We did not have this command in A1
            return;
        }

        // check if movement needs to be set up
        if (!this.model.getActionMade()){
            String gameModeID = ((RadioButton) this.movementGameModes.getSelectedToggle()).getId();
            this.model.setMovementGameMode(gameModeID);
            this.setTraversablePath(this.gameModeLabel, this.objectsInRoom);
        }
        String output = this.model.interpretAction(text, this); //process the command!

        if (output == null || (!output.equals("GAME OVER") && !output.equals("FORCED") && !output.equals("HELP"))) {
            updateScene(output);
            updateItems();
        } else if (output.equals("GAME OVER")) {
            updateScene("");
            updateItems();
            this.lockCommands();
            PauseTransition pause = new PauseTransition(Duration.seconds(10));
            pause.setOnFinished(event -> {
                Platform.exit();
            });
            pause.play();
        } else if (output.equals("FORCED")) {
            handleForced();
        }
    }

    /**
     * Create the pause between forced rooms so the audio has time to play
     */
    private void instantiatePause(){
        this.forcedTransition = new PauseTransition(Duration.seconds(3));
        this.forcedTransition.setOnFinished(event ->{
            this.forcedMoveRoom();
        });
    }

    /**
     * Update the necessary features when a forced room is encountered
     */
    private void handleForced(){
        this.updateScene("");
        this.updateItems();
        this.lockCommands();
        this.instantiatePause();
        this.forcedTransition.play();
    }

    /**
     * Move to the appropriate room when a forced room is encountered
     */
    private void forcedMoveRoom(){

        List<Passage> allMoves = this.model.getPlayer().getCurrentRoom().getMotionTable().passageTable;

        boolean encounterAnotherForced = false;
        int curRoom = this.model.getPlayer().getCurrentRoom().getRoomNumber();

        for (Passage curPassage : allMoves) {
            String actionDetails = this.model.interpretAction(curPassage.getDirection(), this);

            if (actionDetails == null){
                //check if moved rooms
                if (curRoom != this.model.getPlayer().getCurrentRoom().getRoomNumber()){
                    break;
                }
            }else if (actionDetails.equals("GAME OVER")){
                break;
            }else if (actionDetails.equals("FORCED")){
                encounterAnotherForced = true;
                break;
            }
        }

        if (encounterAnotherForced){
            handleForced();
        }else {
            this.updateScene("");
            this.updateItems();
            if (this.model.getPlayer().getCurrentRoom().getMotionTable().passageTable.get(0).getDestinationRoom() == 0){
                this.lockCommands();
            }else{
                this.inputTextField.setEditable(true);
            }
        }

    }

    /**
     * stop the player's ability to enter commands or take/drop objects during forced rooms, or when the game is over
     */
    private void lockCommands(){

        this.inputTextField.setEditable(false);

        for (Node curNode: this.objectsInInventory.getChildren()){
            if (curNode.getClass() == this.TEST_BUTTON.getClass()){
                ((Button) curNode).setOnAction(e ->{

                });
            }
        }
        for (Node curNode: this.objectsInRoom.getChildren()){
            if (curNode.getClass() == this.TEST_BUTTON.getClass()){
                ((Button) curNode).setOnAction(e ->{

                });
            }
        }

    }


    /**
     * showCommands
     *
     *
     * update the text in the GUI (within roomDescLabel)
     * to show all the moves that are possible from the
     * current room.
     */
    private void showCommands() {
        String[] possibleMoves = this.model.player.getCurrentRoom().getCommands().split(", ");
        String listOfMoves = "Possible moves: ";
        for (int i = 0; i < possibleMoves.length; i++){
            listOfMoves = listOfMoves.concat(possibleMoves[i] + ", ");
        }
        listOfMoves = listOfMoves.substring(0, listOfMoves.length()-2);

        this.roomDescLabel.setText(listOfMoves);
    }


    /**
     * updateScene
     *
     *
     * Show the current room, and print some text below it.
     * If the input parameter is not null, it will be displayed
     * below the image.
     * Otherwise, the current room description will be dispplayed
     * below the image.
     *
     * @param textToDisplay the text to display below the image.
     */
    public void updateScene(String textToDisplay) {
        updateCommandButtons();
        getRoomImage(); //get the image of the current room
        formatText(textToDisplay); //format the text to display
        roomDescLabel.setPrefWidth(500);
        roomDescLabel.setPrefHeight(500);
        roomDescLabel.setTextOverrun(OverrunStyle.CLIP);
        roomDescLabel.setWrapText(true);
        VBox roomPane = new VBox(roomImageView,roomDescLabel);
        roomPane.setPadding(new Insets(10));
        roomPane.setAlignment(Pos.TOP_CENTER);
        roomPane.setStyle("-fx-background-color: #000000;");

        //check if player can change their game mode currently
        if(this.model.getActionMade()){
            for (Toggle curToggle: this.movementGameModes.getToggles()){
                this.gameModePanel.getChildren().remove((RadioButton) curToggle);
            }
            this.gameModeLabel.setText("Game Mode: " + this.model.getGameMode());
        }else{
            this.removeCell(0, 4);
            this.setUpGameModes();
            this.gameModeLabel.setText("Select Your Game Mode:");
        }

        //update traverse for the features to the command buttons
        if (this.commandButtons.getChildren().isEmpty()){
            this.setTraversablePath(this.statsButton, this.inputTextField);
        }else{
            this.setTraversablePath(this.statsButton, this.commandButtons.getChildren().get(0));
        }

        gridPane.add(roomPane, 1, 1);
        stage.sizeToScene();

        //finally, articulate the description
        if (textToDisplay == null || textToDisplay.isBlank()) articulateRoomDescription();
    }

    /**
     * formatText
     *
     *
     * Format text for display.
     *
     * @param textToDisplay the text to be formatted for display.
     */
    private void formatText(String textToDisplay) {
        if (textToDisplay == null || textToDisplay.isBlank()) {
            String roomDesc = this.model.getPlayer().getCurrentRoom().getRoomDescription() + "\n";
            String objectString = this.model.getPlayer().getCurrentRoom().getObjectString();
            if (objectString != null && !objectString.isEmpty()) roomDescLabel.setText(roomDesc + "\n\nObjects in this room:\n" + objectString);
            else roomDescLabel.setText(roomDesc);
        } else roomDescLabel.setText(textToDisplay);
        roomDescLabel.setStyle("-fx-text-fill: white;");
        roomDescLabel.setFont(new Font("Arial", 16));
        roomDescLabel.setAlignment(Pos.CENTER);
    }

    /**
     * getRoomImage
     *
     *
     * Get the image for the current room and place
     * it in the roomImageView
     */
    private void getRoomImage() {

        int roomNumber = this.model.getPlayer().getCurrentRoom().getRoomNumber();
        String roomImage = this.model.getDirectoryName() + "/room-images/" + roomNumber + ".png";

        Image roomImageFile = new Image(roomImage);
        roomImageView = new ImageView(roomImageFile);
        roomImageView.setPreserveRatio(true);
        roomImageView.setFitWidth(400);
        roomImageView.setFitHeight(400);

        //set accessible text
        roomImageView.setAccessibleRole(AccessibleRole.IMAGE_VIEW);
        roomImageView.setAccessibleText("Image description: " + this.model.getPlayer().getCurrentRoom().getRoomDescription());
        roomImageView.setFocusTraversable(true);

        this.setTraversablePath(this.roomImageView, this.objectsInInventory);
    }
    /**
     * Get directory of image of current room
     */
    private String getRoomImageDir(){
        int roomNumber = this.model.getPlayer().getCurrentRoom().getRoomNumber();
        return this.model.getDirectoryName() + "/room-images/" + roomNumber + ".png";
    }
    /**
     * Get path to visualize for the Displacement and Distance Buttons
     * @param isDisplacement true if the path is intended for the Displacement Button, false otherwise
     */
    private String getPath(boolean isDisplacement){
        if (isDisplacement) return this.model.displacementPath.toString();
        else return this.model.distancePath.toString();}

    /**
     * updateItems
     *
     *
     * This method is partially completed, but you are asked to finish it off.
     *
     * The method should populate the objectsInRoom and objectsInInventory Vboxes.
     * Each Vbox should contain a collection of nodes (Buttons, ImageViews, you can decide)
     * Each node represents a different object.
     *
     * Images of each object are in the assets
     * folders of the given adventure game.
     */
    public void updateItems() {

        //reset!
        this.objectsInRoom.getChildren().clear();
        this.objectsInInventory.getChildren().clear();

        //write some code here to add images of objects in a given room to the objectsInRoom Vbox
        ArrayList<AdventureObject> roomObjects = this.model.getPlayer().getCurrentRoom().objectsInRoom;

        //configure each button
        if (!roomObjects.isEmpty()){
            for (AdventureObject curObj: roomObjects){
                Button curButton;

                //configure button
                curButton = this.configureButton(curObj, false);
                this.objectsInRoom.getChildren().add(curButton);

                makeButtonAccessible(curButton, curObj.getName() + " Button",
                        "This button corresponds to a " + curObj.getName() + " object in the current room.",
                        "Clicking this button adds " + curObj.getName() + " to your inventory.");
            }
        }
        //write some code here to add images of objects in a player's inventory room to the objectsInInventory Vbox
        ArrayList<AdventureObject> invenObjects = this.model.getPlayer().inventory;

        if (!invenObjects.isEmpty()){
            for (AdventureObject curObj: invenObjects){
                Button curButton;

                //configure each button
                curButton = this.configureButton(curObj, true);
                this.objectsInInventory.getChildren().add(curButton);

                makeButtonAccessible(curButton, curObj.getName() + " Button",
                        "This button corresponds to a " + curObj.getName() + " object in your inventory.",
                        "Clicking this button removes " + curObj.getName() + " from your inventory.");
            }
        }

        if (!this.objectsInRoom.getChildren().isEmpty()){
            Node firstButton = this.objectsInRoom.getChildren().get(0);
            Node lastButton = this.objectsInRoom.getChildren().get(this.objectsInRoom.getChildren().size() - 1);
            this.setTraversablePath(this.objectsInRoom, firstButton);
            this.setTraversablePath(lastButton, this.roomImageView);
        }else{
            this.setTraversablePath(this.objectsInRoom, this.roomImageView);
        }

        if (!this.objectsInInventory.getChildren().isEmpty()){
            Node firstButton = this.objectsInInventory.getChildren().get(0);
            Node lastButton = this.objectsInInventory.getChildren().get(this.objectsInInventory.getChildren().size() - 1);
            this.setTraversablePath(this.objectsInInventory, firstButton);
            this.setTraversablePath(lastButton, this.zoomButton);
        }else{
            this.setTraversablePath(this.objectsInInventory, this.zoomButton);
        }

        ScrollPane scO = new ScrollPane(objectsInRoom);
        scO.setPadding(new Insets(10));
        scO.setStyle("-fx-background: #000000; -fx-background-color:transparent;");
        scO.setFitToWidth(true);
        gridPane.add(scO,0,1);

        ScrollPane scI = new ScrollPane(objectsInInventory);
        scI.setFitToWidth(true);
        scI.setStyle("-fx-background: #000000; -fx-background-color:transparent;");
        gridPane.add(scI,2,1);

    }

    /**
     * Create a new button corresponding to an object in the room or inventory
     * @param curObject the object that the button corresponds to
     * @param inInven true if object is in the inventory, false if the object is in the current room
     * @return the button corresponding to curObject
     */
    private Button configureButton(AdventureObject curObject, boolean inInven){
        Image objImage = new Image(this.model.getDirectoryName() +
                "/objectImages/" + curObject.getName().toUpperCase() + ".jpg",
                100, 100, true, false);
        ImageView storeImage = new ImageView(objImage);
        storeImage.setFitWidth(100);
        Button objectButton = new Button(curObject.getName(), storeImage);
        objectButton.setAlignment(Pos.BASELINE_CENTER);
        objectButton.setWrapText(true);
        objectButton.setGraphicTextGap(10);
        objectButton.setContentDisplay(ContentDisplay.TOP);

        //please use setAccessibleText to add "alt" descriptions to your images!

        if (inInven){
            this.setInventoryButtonHandler(objectButton);
        }else{
            this.setRoomButtonHandler(objectButton);
        }
        return objectButton;
    }

    /**
     * Handles event when dropping an object
     * @param inventoryButton the object to add the handler to
     */
    private void setInventoryButtonHandler(Button inventoryButton){
        inventoryButton.setOnAction(e -> {
            this.submitEvent("DROP " + inventoryButton.getText());
            this.updateItems();
        });
    }

    /**
     * Handles event when picking up an object
     * @param roomButton the object to add the handler to
     */
    private void setRoomButtonHandler(Button roomButton){
        roomButton.setOnAction(e -> {
            this.submitEvent("TAKE " + roomButton.getText());
            this.updateItems();
        });
    }

    /**
     * Show the game instructions.
     *
     * If helpToggle is FALSE:
     * -- display the help text in the CENTRE of the gridPane (i.e. within cell 1,1)
     * -- use whatever GUI elements to get the job done!
     * -- set the helpToggle to TRUE
     * -- REMOVE whatever nodes are within the cell beforehand!
     *
     * If helpToggle is TRUE:
     * -- redraw the room image in the CENTRE of the gridPane (i.e. within cell 1,1)
     * -- set the helpToggle to FALSE
     * -- Again, REMOVE whatever nodes are within the cell beforehand!
     */
    public void showInstructions() {
        //clear cell for writing
        this.removeCell(1, 1);

        if (helpToggle){
            this.updateScene(null);

            //update traverse
            if (!this.objectsInRoom.getChildren().isEmpty()){
                Node lastButton = this.objectsInRoom.getChildren().get(this.objectsInRoom.getChildren().size() - 1);
                this.setTraversablePath(lastButton, this.roomImageView);
            }else{
                this.setTraversablePath(this.objectsInRoom, this.roomImageView);
            }

            this.setTraversablePath(this.roomImageView, this.objectsInInventory);

        }else{
            Label helpText = new Label(this.model.getHelpText());

            //formatting
            helpText.setFont(new Font("Arial", 16));
            helpText.setWrapText(true);
            helpText.setAlignment(Pos.CENTER);

            //Setup pane to add to the grid
            ScrollPane instructionsBox = new ScrollPane(helpText);
            instructionsBox.setFitToWidth(true);
            instructionsBox.setStyle("-fx-background: #000000; -fx-background-color:transparent;");

            //traverse for scrollpane
            if (!this.objectsInRoom.getChildren().isEmpty()){
                Node lastButton = this.objectsInRoom.getChildren().get(this.objectsInRoom.getChildren().size() - 1);
                this.setTraversablePath(lastButton, helpText);
            }else{
                this.setTraversablePath(this.objectsInRoom, helpText);
            }

            this.setTraversablePath(helpText, this.objectsInInventory);

            gridPane.add(instructionsBox, 1, 1);
            stage.sizeToScene();

        }

        helpToggle = !helpToggle;
    }

    /**
     * removeCell
     *
     * Removes all nodes currently in cell (a, b) of the grid pane
     * This allows for redrawing
     */
    private void removeCell(int a, int b){
        ObservableList<Node> allGridPaneItems = this.gridPane.getChildren();
        ArrayList<Node> allInCell = new ArrayList<>();

        for (Node curItem: allGridPaneItems){
            if (GridPane.getRowIndex(curItem) == a && GridPane.getColumnIndex(curItem) == b){
                allInCell.add(curItem);
            }
        }
        for(Node curItem: allInCell){
            this.gridPane.getChildren().remove(curItem);
        }
    }

    /**
     * This method handles the event related to the
     * help button.
     */
    public void addInstructionEvent() {
        helpButton.setOnAction(e -> {
            stopArticulation(); //if speaking, stop
            showInstructions();
        });
    }

    /**
     * This method handles the event related to the
     * save button.
     */
    public void addSaveEvent() {
        saveButton.setOnAction(e -> {
            gridPane.requestFocus();
            SaveView saveView = new SaveView(this);
        });
    }

    /**
     * This method handles the event related to the
     * load button.
     */
    public void addLoadEvent() {
        loadButton.setOnAction(e -> {
            gridPane.requestFocus();
            LoadView loadView = new LoadView(this);
        });
    }

    /**
     * Handles the event related to the statistics button
     */
    public void addStatsEvent(){
        statsButton.setOnAction(e ->{
            gridPane.requestFocus();
            StatisticsView statsView = new StatisticsView(this, this.model);
        });
    }


    /**
     * This method articulates Room Descriptions
     */
    public void articulateRoomDescription() {
        String musicFile;
        String adventureName = this.model.getDirectoryName();
        String roomName = this.model.getPlayer().getCurrentRoom().getRoomName();

        if (!this.model.getPlayer().getCurrentRoom().getVisited()) musicFile = "./" + adventureName + "/sounds/" + roomName.toLowerCase() + "-long.mp3" ;
        else musicFile = "./" + adventureName + "/sounds/" + roomName.toLowerCase() + "-short.mp3" ;
        musicFile = musicFile.replace(" ","-");

        Media sound = new Media(new File(musicFile).toURI().toString());

        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
        mediaPlaying = true;

    }

    /**
     * This method articulates the NPC message
     */
    public void articulateNPC(Dialogue dialogue) {
        String adventureName = this.model.getDirectoryName();

        String musicFile = "./" + adventureName + "/sounds/" + dialogue.NPCName+ dialogue.id + ".wav";
        musicFile = musicFile.replace(" ","-");
        //System.out.println(musicFile);

        Media sound = new Media(new File(musicFile).toURI().toString());

        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
        mediaPlaying = true;
    }

    /**
     * This method stops articulations
     * (useful when transitioning to a new room or loading a new game)
     */
    public void stopArticulation() {
        if (mediaPlaying) {
            mediaPlayer.stop(); //shush!
            mediaPlaying = false;
        }
    }

    /**
     * Handles the event related to the zoom button
     */
    public void addZoomEvent() {
        zoomButton.setOnMouseClicked(e -> {
            try{
                for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException |
                     IllegalAccessException ex) {
                throw new RuntimeException(ex);
            }

            EventQueue.invokeLater(() -> new ZoomFrame(getRoomImageDir()).setVisible(true));

        });
    }
}
