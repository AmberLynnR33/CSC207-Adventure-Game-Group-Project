package AdventureModel;

import views.AdventureGameView;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import NPC.NPC;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.Random;

/**
 * Class AdventureLoader. Loads an adventure from files.
 */
public class AdventureLoader {

    private AdventureGame game; //the game to return
    private String adventureName; //the name of the adventure


    /**
     * Adventure Loader Constructor
     * __________________________
     * Initializes attributes
     * @param game the game that is loaded
     * @param directoryName the directory in which game files live
     */
    public AdventureLoader(AdventureGame game, String directoryName) {
        this.game = game;
        this.adventureName = directoryName;
    }

     /**
     * Load game from directory
     */
    public void loadGame() throws IOException {
        parseRooms();
        parseNPC();
        parseObjects();
        parseSynonyms();
        this.game.setHelpText(parseOtherFile("help"));
    }

    /**
     * NPC file
     */
    private void parseNPC() throws IOException{
        String npcFileName = this.adventureName + "/NPCs.txt";
        BufferedReader buff = new BufferedReader(new FileReader(npcFileName));

        while (buff.ready()) {

            String name = buff.readLine(); // first line is the NPC name
            int roomNumber = Integer.parseInt(buff.readLine());//room the NPC is in

            // now we make the NPC object
            NPC npc = new NPC(name);

            // now we need to get the dialogues
            int id = 0;
            while(buff.ready() && !buff.readLine().equals("-----")){
                //buff.readLine();//read out the ---

                String advice = "";
                String line = buff.readLine();
                while (!line.equals("-")) {
                    advice += line + "\n";
                    line = buff.readLine();
                }
                advice += "\n";
                String completionEvent = buff.readLine();

                npc.addDialogue(advice, completionEvent, id);

                id += 1;
            }
            this.game.getRooms().get(roomNumber).addNPC(npc);
            this.game.subscribe(npc);
        }
    }
     /**
     * Parse Rooms File
     */
    private void parseRooms() throws IOException {

        int roomNumber;

        String roomFileName = this.adventureName + "/rooms.txt";
        BufferedReader buff = new BufferedReader(new FileReader(roomFileName));

        while (buff.ready()) {

            String currRoom = buff.readLine(); // first line is the number of a room

            roomNumber = Integer.parseInt(currRoom); //current room number

            // now need to get room name
            String roomName = buff.readLine();

            // now we need to get the description
            String roomDescription = "";
            String line = buff.readLine();
            while (!line.equals("-----")) {
                roomDescription += line + "\n";
                line = buff.readLine();
            }
            roomDescription += "\n";

            // now we make the room object
            Room room = new Room(roomName, roomNumber, roomDescription, adventureName);

            // now we make the motion table
            line = buff.readLine(); // reads the line after "-----"
            while (line != null && !line.equals("")) {
                String[] part = line.split(" \s+"); // have to use regex \\s+ as we don't know how many spaces are between the direction and the room number
                String direction = part[0];
                String dest = part[1];
                if (dest.contains("/")) {
                    String[] blockedPath = dest.split("/");
                    String dest_part = blockedPath[0];
                    String object = blockedPath[1];
                    Passage entry = new Passage(direction, dest_part, object);
                    room.getMotionTable().addDirection(entry);
                } else {
                    Passage entry = new Passage(direction, dest);
                    room.getMotionTable().addDirection(entry);
                }
                line = buff.readLine();
            }
            this.game.getRooms().put(room.getRoomNumber(), room);
        }

    }

     /**
     * Parse Objects File
     */
    public void parseObjects() throws IOException {

        String objectTypeFileName = this.adventureName + "/objectTypes.txt";
        BufferedReader buff = new BufferedReader(new FileReader(objectTypeFileName));
        ArrayList<String> objectTypes = new ArrayList<>();
        while (buff.ready()) objectTypes.add(buff.readLine());
        Random rand = new Random();
        String objectFileName = this.adventureName + "/objects.txt";
        buff = new BufferedReader(new FileReader(objectFileName));

        while (buff.ready()) {
            String objectName = buff.readLine();
            String objectDescription = buff.readLine();
            String objectLocation = buff.readLine();
            String separator = buff.readLine();
            if (separator != null && !separator.isEmpty())
                System.out.println("Formatting Error!");
            int i = Integer.parseInt(objectLocation);
            Room location = this.game.getRooms().get(i);
            String type = objectTypes.get(rand.nextInt(objectTypes.size()));
            AdventureObject object = null;
            if (Objects.equals(type, "Runner")) object = new AdventureObject(objectName, objectDescription, location, new AdventureObjectRunner());
            else if (Objects.equals(type, "Puzzle")) object = new AdventureObject(objectName, objectDescription, location, new AdventureObjectPuzzle());
            else if (Objects.equals(type, "Basic")) object = new AdventureObject(objectName, objectDescription, location, new AdventureObjectBasic());
            location.addGameObject(object);
        }
    }

     /**
     * Parse Synonyms File
     */
    public void parseSynonyms() throws IOException {
        String synonymsFileName = this.adventureName + "/synonyms.txt";
        BufferedReader buff = new BufferedReader(new FileReader(synonymsFileName));
        String line = buff.readLine();
        while(line != null){
            String[] commandAndSynonym = line.split("=");
            String command1 = commandAndSynonym[0];
            String command2 = commandAndSynonym[1];
            this.game.getSynonyms().put(command1,command2);
            line = buff.readLine();
        }

    }

    /**
     * Parse Files other than Rooms, Objects and Synonyms
     *
     * @param fileName the file to parse
     */
    public String parseOtherFile(String fileName) throws IOException {
        String text = "";
        fileName = this.adventureName + "/" + fileName + ".txt";
        BufferedReader buff = new BufferedReader(new FileReader(fileName));
        String line = buff.readLine();
        while (line != null) { // while not EOF
            text += line+"\n";
            line = buff.readLine();
        }
        return text;
    }

}
