package soldier;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SoldierTest {
    @Test
    @DisplayName("Test increaseExperience() method")
    void testIncreaseExperience() {
        Soldier soldier = new Soldier(Rank.PRIVATE, 1);
        soldier.increaseExperience();

        assertEquals(2, soldier.getExperience());
        assertEquals(2, soldier.getStrength());
    }

    @Test
    @DisplayName("Test decreaseExperience() method")
    void testDecreaseExperience() {
        Soldier soldier = new Soldier(Rank.CORPORAL, 5);
        soldier.decreaseExperience();

        assertEquals(4, soldier.getExperience());
        assertEquals(8, soldier.getStrength());
    }

    @Test
    @DisplayName("Test isAlive() method")
    void testIsAlive() {
        Soldier soldier = new Soldier(Rank.PRIVATE, 2);

        assertTrue(soldier.isAlive());

        soldier.decreaseExperience();
        assertTrue(soldier.isAlive());

        soldier.decreaseExperience();
        assertFalse(soldier.isAlive());
    }
}
