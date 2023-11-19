package AdventureModel;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StatisticsTests {

    @AfterEach
    void removeStatsObject(){
        AdventureGameStatistics.resetInstance();
    }

    @Test
    public void initalizesStatsCorrect(){
        AdventureGame model = new AdventureGame("MediumGame");

        AdventureGameStatistics stats = AdventureGameStatistics.getInstance(model);

        // Been in one room: the initial room
        assertEquals(1, stats.getTotalRoomsVisited());
        assertEquals(1, stats.getTotalUniqueRoomsVisited());
        assertEquals(model.getPlayer().getCurrentRoom().getRoomName(), stats.getRoomVisitedMost());
    }

    // Two methods are required to ensure the total rooms/objects in the game is correct
    // This is because the statistics only has one instance, so the second model would not change the stats

    @Test
    public void setsUpGameRooms1(){
        AdventureGame smallGame = new AdventureGame("TinyGame");
        AdventureGameStatistics stats = AdventureGameStatistics.getInstance(smallGame);

        // 10 rooms, 3 objects
        assertEquals(10, stats.getTotalRooms());
        assertEquals(3, stats.getTotalObjects());
    }

    @Test
    public void setsUpGameRooms2(){
        AdventureGame mediumGame = new AdventureGame("MediumGame");
        AdventureGameStatistics stats = AdventureGameStatistics.getInstance(mediumGame);

        // 37 rooms, 12 objects
        assertEquals(37, stats.getTotalRooms());
        assertEquals(12, stats.getTotalObjects());

    }

    @Test
    public void moveRoomUpdatesStats(){
        AdventureGame model = new AdventureGame("MediumGame");
        AdventureGameStatistics stats = AdventureGameStatistics.getInstance(model);

        model.movePlayer("WEST");

        assertEquals(2, stats.getTotalRoomsVisited());
        assertEquals(2, stats.getTotalUniqueRoomsVisited());
        assertEquals(model.getPlayer().getCurrentRoom().getRoomName(), stats.getRoomVisitedMost());
    }

    @Test
    public void changeMostVisited(){
        AdventureGame model = new AdventureGame("TinyGame");
        AdventureGameStatistics stats = AdventureGameStatistics.getInstance(model);

        model.movePlayer("WEST"); //2
        model.movePlayer("WEST"); //3
        model.interpretAction("TAKE BOOK");
        model.movePlayer("XYZZY"); //2

        assertEquals(4, stats.getTotalRoomsVisited());
        assertEquals(3, stats.getTotalUniqueRoomsVisited());
        assertEquals(model.getPlayer().getCurrentRoom().getRoomName(), stats.getRoomVisitedMost());

    }

}
