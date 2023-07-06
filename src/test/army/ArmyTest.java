package army;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import soldier.Rank;
import soldier.Soldier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class ArmyTest {
    private Army army;

    @BeforeEach
    public void setup() {
        army = new Army();
    }

    @Test
    void trainSoldiersShouldntWorkWithInsufficientGold() {
        int generalsGold = 0;
        Soldier soldier = new Soldier(Rank.PRIVATE, 1);
        army.addSoldier(soldier);

        army.trainSoldiers(generalsGold);

        assertEquals(1, soldier.getExperience());
    }

    @Test
    void trainSoldiersWithSufficientGold() {
        int generalsGold = 10;
        Soldier soldier = new Soldier(Rank.PRIVATE, 1);
        army.addSoldier(soldier);

        army.trainSoldiers(generalsGold);

        assertEquals(2, soldier.getExperience());
    }

    @Test
    void testExecuteRandomSoldier_ArmyNotEmpty() {
        Soldier soldier1 = new Soldier(Rank.CAPTAIN, 1);
        Soldier soldier2 = new Soldier(Rank.PRIVATE, 1);
        army.addSoldier(soldier1);
        army.addSoldier(soldier2);

        army.executeRandomSoldier();

        assertEquals(1, army.getSoldiers().size());
    }

    @Test
    void testIncreaseSoldiersExperience() {
        Soldier soldier1 = new Soldier(Rank.PRIVATE, 1);
        Soldier soldier2 = new Soldier(Rank.PRIVATE, 1);
        army.addSoldier(soldier1);
        army.addSoldier(soldier2);

        army.increaseSoldiersExperience();

        assertEquals(2, soldier1.getExperience());
        assertEquals(2, soldier2.getExperience());
    }

    @Test
    void testDecreaseSoldiersExperience() {
        Soldier soldier1 = new Soldier(Rank.PRIVATE, 2);
        Soldier soldier2 = new Soldier(Rank.PRIVATE, 0);
        army.addSoldier(soldier1);
        army.addSoldier(soldier2);

        army.decreaseSoldiersExperience();

        assertEquals(1, soldier1.getExperience());
        assertFalse(army.getSoldiers().contains(soldier2));
    }
}
