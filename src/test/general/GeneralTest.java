package general;

import army.Army;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import soldier.Rank;
import soldier.Soldier;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class GeneralTest {
    private static final String SAVE_FILE_NAME = "testSaveGame.json";
    private General general;
    private General otherGeneral;
    @Mock
    private Army army;
    @Mock
    Army otherArmy;

    @BeforeEach
    public void setup() {
        army = mock(Army.class);
        general = new General("Test General", army, 100);
        otherArmy = mock(Army.class);
        otherGeneral = new General("Enemy General", otherArmy, 50);
        File saveFile = new File(SAVE_FILE_NAME);
        if (saveFile.exists()) {
            saveFile.delete();
        }
    }

    @Test
    void testConductManeuvers() {
        doNothing().when(army).trainSoldiers(anyInt());

        general.conductManeuvers();

        verify(army).trainSoldiers(general.getGold());
    }

    @Test
    void attackScenarioTest() {
        when(army.calculateTotalStrength()).thenReturn(100);
        when(otherArmy.calculateTotalStrength()).thenReturn(50);

        general.attack(otherGeneral);

        assertEquals(105, general.getGold());
        assertEquals(45, otherGeneral.getGold());
    }

    @Test
    void drawScenarioTest() {
        when(army.calculateTotalStrength()).thenReturn(100);
        when(otherArmy.calculateTotalStrength()).thenReturn(100);

        general.attack(otherGeneral);

        verify(army).executeRandomSoldier();
        verify(otherArmy).executeRandomSoldier();
    }

    @Test
    void testBuySoldier() {
        Rank rank = Rank.PRIVATE;

        general.buySoldier(rank);

        verify(army).addSoldier(any());
    }

    @Test
    void cantBuySoldierIfInsufficientGold() {
        Rank rank = Rank.CAPTAIN;
        general.decreaseGold(general.getGold());
        general.increaseGold(2);

        general.buySoldier(rank);

        verify(army, never()).addSoldier(any());
    }

    @Test
    void testSaveAndLoadingGame() {
        General originalGeneral = new General("Test General", new Army(), 100);
        originalGeneral.buySoldier(Rank.PRIVATE);
        originalGeneral.buySoldier(Rank.CORPORAL);
        originalGeneral.saveGame(SAVE_FILE_NAME);

        General loadedGeneral = General.loadGame(SAVE_FILE_NAME);

        assertNotNull(loadedGeneral);
        assertEquals(originalGeneral.getName(), loadedGeneral.getName());
        assertEquals(originalGeneral.getGold(), loadedGeneral.getGold());
        assertEquals(originalGeneral.getArmy().getSoldiers().size(), loadedGeneral.getArmy().getSoldiers().size());
    }
}
