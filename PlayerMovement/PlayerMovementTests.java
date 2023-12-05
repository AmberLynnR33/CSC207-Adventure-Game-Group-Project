package PlayerMovement;

import AdventureModel.AdventureGame;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PlayerMovementTests {

    @Test
    public void noInitialGameMode(){
        AdventureGame model = new AdventureGame("MediumGame");
        assertNull(model.getGameMode());
    }

    @Test
    public void setsCorrectGameMode(){
        AdventureGame model1 = new AdventureGame("MediumGame");
        model1.setMovementGameMode("00");
        assertEquals("Regular Movement", model1.getGameMode());

        AdventureGame model2 = new AdventureGame("MediumGame");
        model2.setMovementGameMode("01");
        assertEquals("Curse of the Lost", model2.getGameMode());

        AdventureGame model3 = new AdventureGame("MediumGame");
        model3.setMovementGameMode("02");
        assertEquals("Curse of the Troll", model3.getGameMode());

    }

    @Test
    public void setsRegularIfIncorrectCode(){
        AdventureGame model = new AdventureGame("MediumGame");
        model.setMovementGameMode("djiwandka");
        assertEquals(model.getGameMode(), "Regular Movement");
    }

    @Test
    public void regularMovementStillWorks(){
        AdventureGame model = new AdventureGame("MediumGame");
        model.setMovementGameMode("00");

        //model.movePlayer("west");
        assertEquals(2, model.getPlayer().getCurrentRoom().getRoomNumber());
    }


}
