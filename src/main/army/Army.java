package army;

import soldier.Soldier;

import java.io.Serializable;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Army implements Serializable {
    private List<Soldier> soldiers;

    public Army() {
        this.soldiers = new ArrayList<>();
    }

    public Army(List<Soldier> soldiers) {
        this.soldiers = soldiers;
    }

    public void trainSoldiers(int generalsGold) {
        if (generalsGold < calculateTrainingCost()) {
            System.out.println("Not enough gold to train soldier");
            return;
        }
        increaseSoldiersExperience();
    }

    public void executeRandomSoldier() {
        if (soldiers.isEmpty()) {
            System.out.println("Execution of random soldier failed! Army doesn't have any soldier");
            return;
        }
        int randomIndex = (int) (Math.random() * soldiers.size() - 1);
        removeSoldier(soldiers.get(randomIndex));
    }

    public void increaseSoldiersExperience() {
        soldiers = soldiers.stream()
                .map(this::trainSoldier)
                .collect(Collectors.toList());
    }

    public void decreaseSoldiersExperience() {
        List<Soldier> soldiersToRemove = new ArrayList<>();
        for (Soldier soldier : soldiers) {
            soldier.decreaseExperience();
            if (!soldier.isAlive()) {
                soldiersToRemove.add(soldier);
            }
        }
        soldiersToRemove.stream().forEach(this::removeSoldier);
    }

    private int calculateTrainingCost() {
        int cost = 0;
        for (Soldier soldier : soldiers) {
            cost += soldier.getRank().ordinal() + 1;
        }
        return cost;
    }

    private Soldier trainSoldier(Soldier soldier) {
        soldier.increaseExperience();
        return soldier;
    }

    public int calculateTotalStrength() {
        int totalStrength = 0;
        for (Soldier soldier : soldiers) {
            totalStrength += soldier.getStrength();
        }
        return totalStrength;
    }

    public List<Soldier> getSoldiers() {
        return soldiers;
    }

    public void addSoldier(Soldier soldier) {
        soldiers.add(soldier);
    }

    public void removeSoldier(Soldier soldier) throws InvalidParameterException {
        try {
            soldiers.remove(soldier);
        } catch (Exception e) {
            throw new InvalidParameterException(e.getMessage());
        }
    }
}
