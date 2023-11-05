package views;

import AdventureModel.*;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
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

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Class AdventureGameView.
 *
 * This is the Class that will visualize your model.
 * You are asked to demo your visualization via a Zoom
 * recording. Place a link to your recording below.
 *
 * // Please see the following Google Drive link as I cannot cloud share on Zoom. There is no password.
 * ZOOM LINK: https://drive.google.com/file/d/1q6gIRrxTv5tEANpWZYLRDH3_7im41xvk/view?usp=sharing
 * PASSWORD:
 */
public class AdventureGameView {

    AdventureGame model; //model of the game
    Stage stage; //stage on which all is rendered
    Button saveButton, loadButton, helpButton; //buttons
    Boolean helpToggle = false; //is help on display?

    GridPane gridPane = new GridPane(); //to hold images and buttons
    Label roomDescLabel = new Label(); //to hold room description and/or instructions
    VBox objectsInRoom = new VBox(); //to hold room items
    VBox objectsInInventory = new VBox(); //to hold inventory items
    ImageView roomImageView; //to hold room image
    TextField inputTextField; //for user input

    private MediaPlayer mediaPlayer; //to play audio
    private boolean mediaPlaying; //to know if the audio is playing

    private PauseTransition forcedTransition; //the transition that occurs when in FORCED room

    private HashMap<AdventureObject, Integer> seenObjects = new HashMap<>();
    //Integer corresponds to index of the button in the following list
    private ArrayList<Button> seenObjectButtons = new ArrayList<>();

    private final Button TEST_BUTTON = new Button(); //Button for checking class

    /**
     * Adventure Game View Constructor
     * __________________________
     * Initializes attributes
     */
    public AdventureGameView(AdventureGame model, Stage stage) {
        this.model = model;
        this.stage = stage;
        intiUI();
    }

    /**
     * Initialize the UI
     */
    public void intiUI() {

        // setting up the stage
        this.stage.setTitle("RICHA814's Adventure Game"); //Replace <YOUR UTORID> with your UtorID

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
        column3.setHgrow( Priority.SOMETIMES ); //let some columns grow to take any extra space
        column1.setHgrow( Priority.SOMETIMES );

        // Row constraints
        RowConstraints row1 = new RowConstraints();
        RowConstraints row2 = new RowConstraints( 550 );
        RowConstraints row3 = new RowConstraints();
        row1.setVgrow( Priority.SOMETIMES );
        row3.setVgrow( Priority.SOMETIMES );

        gridPane.getColumnConstraints().addAll( column1 , column2 , column1 );
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

        //labels for inventory and room items
        Label objLabel =  new Label("Objects in Room");
        objLabel.setAlignment(Pos.CENTER);
        objLabel.setStyle("-fx-text-fill: white;");
        objLabel.setFont(new Font("Arial", 16));

        Label invLabel =  new Label("Your Inventory");
        invLabel.setAlignment(Pos.CENTER);
        invLabel.setStyle("-fx-text-fill: white;");
        invLabel.setFont(new Font("Arial", 16));

        //add all the widgets to the GridPane
        gridPane.add( objLabel, 0, 0, 1, 1 );  // Add label
        gridPane.add( topButtons, 1, 0, 1, 1 );  // Add buttons
        gridPane.add( invLabel, 2, 0, 1, 1 );  // Add label

        Label commandLabel = new Label("What would you like to do?");
        commandLabel.setStyle("-fx-text-fill: white;");
        commandLabel.setFont(new Font("Arial", 16));

        updateScene(""); //method displays an image and whatever text is supplied
        updateItems(); //update items shows inventory and objects in rooms

        // adding the text area and submit button to a VBox
        VBox textEntry = new VBox();
        textEntry.setStyle("-fx-background-color: #000000;");
        textEntry.setPadding(new Insets(20, 20, 20, 20));
        textEntry.getChildren().addAll(commandLabel, inputTextField);
        textEntry.setSpacing(10);
        textEntry.setAlignment(Pos.CENTER);
        gridPane.add( textEntry, 0, 2, 3, 1 );

        // Render everything
        var scene = new Scene( gridPane ,  1000, 800);
        scene.setFill(Color.BLACK);
        this.stage.setScene(scene);
        this.stage.setResizable(false);
        this.stage.show();

    }


    /**
     * makeButtonAccessible
     * __________________________
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
     * customizeButton
     * __________________________
     *
     * @param inputButton the button to make stylish :)
     * @param w width
     * @param h height
     */
    private void customizeButton(Button inputButton, int w, int h) {
        inputButton.setPrefSize(w, h);
        inputButton.setFont(new Font("Arial", 16));
        inputButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");
    }

    /**
     * addTextHandlingEvent
     * __________________________
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
     * submitEvent
     * __________________________
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

        //try to move!
        String output = this.model.interpretAction(text); //process the command!

        if (output == null || (!output.equals("GAME OVER") && !output.equals("FORCED") && !output.equals("HELP"))) {
            updateScene(output);
            updateItems();
        } else if (output.equals("GAME OVER")) {
            updateScene("");
            updateItems();
            PauseTransition pause = new PauseTransition(Duration.seconds(10));
            pause.setOnFinished(event -> {
                Platform.exit();
            });
            pause.play();
        } else if (output.equals("FORCED")) {
            handleForced();
        }
    }

    private void instantiatePause(){
        this.forcedTransition = new PauseTransition(Duration.seconds(6));
        this.forcedTransition.setOnFinished(event ->{
            this.forcedMoveRoom();
        });
    }

    private void handleForced(){
        this.updateScene("");
        this.updateItems();
        this.lockCommands();
        this.instantiatePause();
        this.forcedTransition.play();
    }

    private void forcedMoveRoom(){

        List<Passage> allMoves = this.model.getPlayer().getCurrentRoom().getMotionTable().passageTable;

        boolean encounterAnotherForced = false;
        int curRoom = this.model.getPlayer().getCurrentRoom().getRoomNumber();

        for (Passage curPassage : allMoves) {
            String actionDetails = this.model.interpretAction(curPassage.getDirection());

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

    //lockCommands
    //stop the ability for the player to enter commands or take/drop objects during forced rooms,
    //or when the game is over
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
     * __________________________
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
     * __________________________
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

        gridPane.add(roomPane, 1, 1);
        stage.sizeToScene();

        //finally, articulate the description
        if (textToDisplay == null || textToDisplay.isBlank()) articulateRoomDescription();
    }

    /**
     * formatText
     * __________________________
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
     * __________________________
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
        roomImageView.setAccessibleText(this.model.getPlayer().getCurrentRoom().getRoomDescription());
        roomImageView.setFocusTraversable(true);
    }

    /**
     * updateItems
     * __________________________
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

        if (!roomObjects.isEmpty()){
            for (AdventureObject curObj: roomObjects){
                Integer indObj = this.seenObjects.get(curObj);
                Button curButton;

                if (indObj == null){
                    //New button, need to configure

                    curButton = this.configureButton(curObj, false);
                    this.seenObjectButtons.add(curButton);
                    this.seenObjects.put(curObj, this.seenObjectButtons.size() - 1);
                }else{
                    curButton = this.seenObjectButtons.get(indObj);
                    this.setRoomButtonHandler(curButton);
                }

                this.objectsInRoom.getChildren().add(curButton);
            }
        }
        //write some code here to add images of objects in a player's inventory room to the objectsInInventory Vbox
        ArrayList<AdventureObject> invenObjects = this.model.getPlayer().inventory;

        if (!invenObjects.isEmpty()){
            for (AdventureObject curObj: invenObjects){
                Integer indObj = this.seenObjects.get(curObj);
                Button curButton;

                if (indObj == null){
                    //New button, need to configure

                    curButton = this.configureButton(curObj, true);
                    this.seenObjectButtons.add(curButton);
                    this.seenObjects.put(curObj, this.seenObjectButtons.size() - 1);
                }else{
                    curButton = this.seenObjectButtons.get(indObj);
                    this.setInventoryButtonHandler(curButton);
                }

                this.objectsInInventory.getChildren().add(curButton);
            }
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
            makeButtonAccessible(objectButton, curObject.getName() + " Button",
                    "This button corresponds to a " + curObject.getName() + " object in your inventory.",
                    "Clicking this button removes " + curObject.getName() + "from your inventory.");
            this.setInventoryButtonHandler(objectButton);
        }else{
            makeButtonAccessible(objectButton, curObject.getName() + " Button",
                    "This button corresponds to a " + curObject.getName() + " object in the current room.",
                    "Clicking this button adds " + curObject.getName() + "to your inventory.");
            this.setRoomButtonHandler(objectButton);
        }
        return objectButton;
    }

    private void setInventoryButtonHandler(Button inventoryButton){
        inventoryButton.setOnAction(e -> {
            gridPane.requestFocus();
            this.objectsInInventory.getChildren().remove(inventoryButton);
            this.objectsInRoom.getChildren().add(inventoryButton);
            this.submitEvent("DROP " + inventoryButton.getText());

            //Update alt text of button
            makeButtonAccessible(inventoryButton, inventoryButton.getText() + " Button",
                    "This button corresponds to a " + inventoryButton.getText() + " object in the current room.",
                    "Clicking this button adds " + inventoryButton.getText() + "to your inventory.");
        });
    }

    private void setRoomButtonHandler(Button roomButton){
        roomButton.setOnAction(e -> {
            gridPane.requestFocus();
            this.objectsInRoom.getChildren().remove(roomButton);
            this.objectsInInventory.getChildren().add(roomButton);
            this.submitEvent("TAKE " + roomButton.getText());

            //Update alt text of button
            makeButtonAccessible(roomButton, roomButton.getText() + " Button",
                    "This button corresponds to a " + roomButton.getText() + " object in your inventory.",
                    "Clicking this button removes " + roomButton.getText() + "from your inventory.");
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
        this.removeCell11();

        if (helpToggle){
            this.updateScene(null);
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

            gridPane.add(instructionsBox, 1, 1);
            stage.sizeToScene();

        }

        helpToggle = !helpToggle;
    }

    /**
     * removeCell11
     *
     * Removes all nodes currently in cell (1,1) of the grid pane
     * This allows for redrawing
     */
    private void removeCell11(){
        ObservableList<Node> allGridPaneItems = this.gridPane.getChildren();
        ArrayList<Node> allInCell11 = new ArrayList<>();

        for (Node curItem: allGridPaneItems){
            if (GridPane.getRowIndex(curItem) == 1 && GridPane.getColumnIndex(curItem) == 1){
                allInCell11.add(curItem);
            }
        }
        for(Node curItem: allInCell11){
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
     * This method stops articulations 
     * (useful when transitioning to a new room or loading a new game)
     */
    public void stopArticulation() {
        if (mediaPlaying) {
            mediaPlayer.stop(); //shush!
            mediaPlaying = false;
        }
    }
}
