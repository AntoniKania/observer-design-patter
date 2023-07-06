package general;

import army.Army;
import notification.GeneralReport;
import notification.WarReport;
import soldier.Rank;
import soldier.Soldier;
import subscription.Subscription;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Flow;

public class General implements Flow.Publisher<WarReport>, Serializable {
    private final List<Flow.Subscriber<? super WarReport>> subscribers = new ArrayList<>();
    private final String name;
    private final Army army;
    private int gold;

    public General(String name, int initialGold) {
        this.name = name;
        this.army = new Army();
        this.gold = initialGold;
    }

    public General(String name, Army army, int initialGold) {
        this.name = name;
        this.army = army;
        this.gold = initialGold;
    }

    public void saveGame(String fileName) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
            outputStream.writeObject(this);
            System.out.println("Game saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving game: " + e.getStackTrace());
        }
    }

    public static General loadGame(String fileName) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fileName))) {
            General general = (General) inputStream.readObject();
            System.out.println("Game loaded successfully.");
            return general;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading game: " + e.getMessage());
            return null;
        }
    }

    public void conductManeuvers() {
        army.trainSoldiers(gold);
        sendReport("conducted maneuvers");
    }

    public void attack(General otherGeneral) {
        int myArmyStrength = army.calculateTotalStrength();
        int otherArmyStrength = otherGeneral.getArmy().calculateTotalStrength();

        if (myArmyStrength > otherArmyStrength) {
            calculateValuesWhenWon(otherGeneral);
        } else if (myArmyStrength < otherArmyStrength) {
            calculateValuesWhenLost(otherGeneral);
        } else {
            executeRandomSoldiersInBothArmies(otherGeneral);
        }
        sendReport("attack");
    }

    public void buySoldier(Rank rank) {
        int cost = (rank.ordinal() + 1) * 10;
        if (gold >= cost) {
            gold -= cost;
            army.addSoldier(new Soldier(rank, subscribers));
            sendReport("bought soldier");
        } else {
            System.out.println("Insufficient gold to purchase soldier.");
        }
    }

    public void increaseGold(int gold) {
        this.gold += gold;
    }

    public void decreaseGold(int gold) {
        this.gold -= gold;
        if (this.gold < 0) {
            generateEndOfGameReport();
        }
    }

    private void calculateValuesWhenLost(General otherGeneral) {
        paySpoils(otherGeneral, this);
        army.decreaseSoldiersExperience();
        otherGeneral.getArmy().decreaseSoldiersExperience();
    }

    private void calculateValuesWhenWon(General otherGeneral) {
        paySpoils(this, otherGeneral);
        army.increaseSoldiersExperience();
        otherGeneral.getArmy().decreaseSoldiersExperience();
    }

    private void paySpoils(General winner, General loser) {
        int spoils = (int) (0.1 * loser.getGold());
        loser.decreaseGold(spoils);
        winner.increaseGold(spoils);
    }

    private void executeRandomSoldiersInBothArmies(General otherGeneral) {
        army.executeRandomSoldier();
        otherGeneral.getArmy().executeRandomSoldier();
    }

    private void sendReport(String message) {
        for (Flow.Subscriber<? super WarReport> subscriber : subscribers) {
            subscriber.onNext(new GeneralReport(this, message));
        }
    }

    private void generateEndOfGameReport() {
        for (Flow.Subscriber<? super WarReport> subscriber : subscribers) {
            subscriber.onNext(new GeneralReport(this, "General bankrupted."));
            subscriber.onComplete();
        }
    }

    @Override
    public void subscribe(Flow.Subscriber<? super WarReport> subscriber) {
        subscribers.add(subscriber);
        subscriber.onSubscribe(new Subscription(subscriber, Executors.newSingleThreadExecutor()));
    }

    public Army getArmy() {
        return army;
    }

    public int getGold() {
        return gold;
    }

    public String getName() {
        return name;
    }
}
