package soldier;

import notification.SoldierReport;
import notification.WarReport;
import subscription.Subscription;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Flow;

public class Soldier implements Flow.Publisher<WarReport>, Serializable {
    private Rank rank;
    private int experience;
    private int strength;
    private transient List<Flow.Subscriber<? super WarReport>> subscribers = new ArrayList<>();

    public Soldier(Rank rank, List<Flow.Subscriber<? super WarReport>> subscribers) {
        this.rank = rank;
        this.subscribers = subscribers;
        this.experience = 1;
        this.strength = 0;
        sendReport("New soldier created with rank: " + rank);
    }

    public Soldier(Rank rank, int experience) {
        this.rank = rank;
        this.experience = experience;
        this.strength = calculateStrength();
        sendReport("New soldier created with rank: " + rank);
    }

    public void increaseExperience() {
        experience++;
        sendReport("Soldier's experience increased. Current experience: " + experience);
        if (experience == 5 * (rank.ordinal() + 1)) {
            promoteRank();
        }
        strength = calculateStrength();
    }

    public void decreaseExperience() {
        experience--;
        sendReport("Soldiers experience decreased :c Current experience: " + experience);
        if (experience <= 0) {
            sendReport("Whoopsie, soldier kinda died");
        }
        strength = calculateStrength();
    }

    public boolean isAlive() {
        return experience > 0;
    }

    private void sendReport(String message) {
        for (Flow.Subscriber<? super SoldierReport> subscriber : subscribers) {
            subscriber.onNext(new SoldierReport(this, message));
        }
    }

    private void promoteRank() {
        rank = rank.getNextRank();
        sendReport("Soldier got promoted! \uD83E\uDD11 Current rank: " + rank);
    }

    private int calculateStrength() {
        return (rank.ordinal() + 1) * experience;
    }

    public Rank getRank() {
        return rank;
    }

    public int getExperience() {
        return experience;
    }

    public int getStrength() {
        return strength;
    }

    @Override
    public void subscribe(Flow.Subscriber<? super WarReport> subscriber) {
        subscribers.add(subscriber);
        subscriber.onSubscribe(new Subscription(subscriber, Executors.newSingleThreadExecutor()));
    }
}
