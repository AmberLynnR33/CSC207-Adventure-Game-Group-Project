package AdventureModel;

import NPC.NPCRoom;
import NPC.ProgressionObserver;
import NPC.ProgressionPublisher;
import PlayerMovement.MovementGameMode;
import PlayerMovement.MovementGameModeFactory;
import PlayerMovement.RegularMovement;

import java.io.*;
import java.util.*;

/**
 * Class AdventureGame.  Handles all the necessary tasks to run the Adventure game.
 */
public class AdventureGame implements Serializable {
    private final String directoryName; //An attribute to store the Introductory text of the game.
    private String helpText; //A variable to store the Help text of the game. This text is displayed when the user types "HELP" command.
    private final HashMap<Integer, Room> rooms; //A list of all the rooms in the game.
    private HashMap<String,String> synonyms = new HashMap<>(); //A HashMap to store synonyms of commands.
    private final String[] actionVerbs = {"QUIT","INVENTORY","TAKE","DROP","TALK"}; //List of action verbs (other than motions) that exist in all games. Motion vary depending on the room and game.

    public Player player; //The Player of the game.

    private MovementGameMode movementType; //the game mode for player movement
    private boolean actionMade = false; //checks if the player can set game mode

    private final ProgressionPublisher progressionPublisher = new ProgressionPublisher(); //publishes when the player picks up items or enters rooms

    /**
     * Adventure Game Constructor
     * __________________________
     * Initializes attributes
     *
     * @param name the name of the adventure
     */
    public AdventureGame(String name){
        this.synonyms = new HashMap<>();
        this.rooms = new HashMap<>();
        this.directoryName = "Games/" + name; //all games files are in the Games directory!
        try {
            setUpGame();
        } catch (IOException e) {
            throw new RuntimeException("An Error Occurred: " + e.getMessage());
        }
    }

    /**
     * Save the current state of the game to a file
     * 
     * @param file pointer to file to write to
     */
    public void saveModel(File file) {
        try {
            FileOutputStream outfile = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(outfile);
            oos.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * setUpGame
     * __________________________
     *
     * @throws IOException in the case of a file I/O error
     */
    public void setUpGame() throws IOException {

        String directoryName = this.directoryName;
        AdventureLoader loader = new AdventureLoader(this, directoryName);
        loader.loadGame();

        // set up the player's current location
        this.player = new Player(this.rooms.get(1));

        //reset game modes
        this.movementType = null;
        this.actionMade = false;
    }

    /**
     * tokenize
     * __________________________
     *
     * @param input string from the command line
     * @return a string array of tokens that represents the command.
     */
    public String[] tokenize(String input){

        input = input.toUpperCase();
        String[] commandArray = input.split(" ");

        int i = 0;
        while (i < commandArray.length) {
            if(this.synonyms.containsKey(commandArray[i])){
                commandArray[i] = this.synonyms.get(commandArray[i]);
            }
            i++;
        }
        return commandArray;

    }

    /**
     * movePlayer
     * __________________________
     * Moves the player in the given direction, if possible.
     * Return false if the player wins or dies as a result of the move.
     *
     * @param direction the move command
     * @return false, if move results in death or a win (and game is over).  Else, true.
     */
    public boolean movePlayer(String direction) {

        // in event that no game mode has been set up, assume regular movement occurs
        if (this.movementType == null) {
            this.movementType = new RegularMovement();
        }

        // move player based on game mode
        return this.movementType.movePlayer(direction, this.player, this.rooms);

    }

    /**
     * setMovementGameMode
     * Sets up the game mode that the player has requested
     * @param movementID the ID corresponding to the requested game mode
     */
    public void setMovementGameMode(String movementID){
        this.actionMade = true;
        this.movementType = MovementGameModeFactory.getMovementGameMode(movementID);
    }

    /**
     * getActionMade
     * getter method for actionMade
     * @return the value stored in actionMade
     */
    public boolean getActionMade(){
        return this.actionMade;
    }

    /**
     * getGameMode
     * @return the name of the game mode this model is using, or null if no game mode is set
     */
    public String getGameMode(){
        if (this.movementType == null){
            return null;
        }
        return this.movementType.gameModeName();
    }

    /**
     * interpretAction
     * interpret the user's action.
     *
     * @param command String representation of the command.
     */
    public String interpretAction(String command){

        String[] inputArray = tokenize(command); //look up synonyms

        PassageTable motionTable = this.player.getCurrentRoom().getMotionTable(); //where can we move?

        if (motionTable.optionExists(inputArray[0])) {
            if (!movePlayer(inputArray[0])) {
                if (this.player.getCurrentRoom().getMotionTable().getDirection().get(0).getDestinationRoom() == 0)
                    return "GAME OVER";
                else return "FORCED";
            } //something is up here! We are dead or we won.
            this.progressionPublisher.notifyAll("VISITED ROOM "+this.player.getCurrentRoom().getRoomNumber());// update that the player has reached a room
            return null;
        }
        else if(Arrays.asList(this.actionVerbs).contains(inputArray[0])) {
            if(inputArray[0].equals("QUIT")) { return "GAME OVER"; } //time to stop!
            else if(inputArray[0].equals("INVENTORY") && this.player.getInventory().size() == 0) return "INVENTORY IS EMPTY";
            else if(inputArray[0].equals("INVENTORY") && this.player.getInventory().size() > 0) return "THESE OBJECTS ARE IN YOUR INVENTORY:\n" + this.player.getInventory().toString();
            else if(inputArray[0].equals("TAKE") && inputArray.length < 2) return "THE TAKE COMMAND REQUIRES AN OBJECT";
            else if(inputArray[0].equals("DROP") && inputArray.length < 2) return "THE DROP COMMAND REQUIRES AN OBJECT";
            else if(inputArray[0].equals("TAKE") && inputArray.length == 2) {
                if(this.player.getCurrentRoom().checkIfObjectInRoom(inputArray[1])) {
                    this.player.takeObject(inputArray[1]);
                    progressionPublisher.notifyAll("TAKEN "+ inputArray[1]);     //publish the take
                    return "YOU HAVE TAKEN:\n " + inputArray[1];
                } else {
                    return "THIS OBJECT IS NOT HERE:\n " + inputArray[1];
                }
            }
            else if(inputArray[0].equals("DROP") && inputArray.length == 2) {
                if(this.player.checkIfObjectInInventory(inputArray[1])) {
                    this.player.dropObject(inputArray[1]);
                    return "YOU HAVE DROPPED:\n " + inputArray[1];
                } else {
                    return "THIS OBJECT IS NOT IN YOUR INVENTORY:\n " + inputArray[1];
                }
            }
            else if(inputArray[0].equals("TALK")){
                if(this.player.getCurrentRoom().hasNPC()){
                    return this.player.getCurrentRoom().getNPCDialogue();
                }
                else{
                    return "THERE IS NOBODY TO TALK TO.\n";
                }
            }
        }
        return "INVALID COMMAND.";
    }

    /**
     * getDirectoryName
     * __________________________
     * Getter method for directory 
     * @return directoryName
     */
    public String getDirectoryName() {
        return this.directoryName;
    }

    /**
     * getInstructions
     * __________________________
     * Getter method for instructions 
     * @return helpText
     */
    public String getInstructions() {
        return helpText;
    }

    /**
     * getPlayer
     * __________________________
     * Getter method for Player 
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * getRooms
     * __________________________
     * Getter method for rooms 
     * @return map of key value pairs (integer to room)
     */
    public HashMap<Integer, Room> getRooms() {
        return this.rooms;
    }

    /**
     * getSynonyms
     * __________________________
     * Getter method for synonyms 
     * @return map of key value pairs (synonym to command)
     */
    public HashMap<String, String> getSynonyms() {
        return this.synonyms;
    }

    /**
     * setHelpText
     * __________________________
     * Setter method for helpText
     * @param help which is text to set
     */
    public void setHelpText(String help) {
        this.helpText = help;
    }

    /**
     * setHelpText
     * __________________________
     * Getter method for helpText
     */
    public String getHelpText(){return this.helpText;}

    /**
     * method for subscribing to progressionPublisher
     * @param sub ProgressionObserver
     */
    public void subscribeProgression(ProgressionObserver sub){
        if(this.progressionPublisher == null){
            throw new RuntimeException("NO PROGRESSION OBSERVER");
        }
        this.progressionPublisher.subscribe(sub);
    }


}
