package PlayerMovement;

/**
 * Class MovementGameModeFactory
 * Creates the appropriate class to move the player throughout their playthrough of the game
 */
public class MovementGameModeFactory {

    /**
     * getMovementGameMode
     * Generates the class that will handle player movement
     * @param movementID the ID corresponding to the game mode the user requested to play on
     * @return the class that will handle player movement
     */
    public MovementGameMode getMovementGameMode(String movementID){
        //IDs: 00 for RegularMovement
        //     01 for ChaoticMovement
        //     02 for AlwaysTrolled
        //All other codes default to RegularMovement

        switch (movementID){
            case "01":
                return new ChaoticMovement();
            case "02":
                return new AlwaysTrolled();
            default:
                return new RegularMovement();
        }
    }
}
