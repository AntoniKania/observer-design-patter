import general.General;
import secretary.PresidentsSecretary;
import soldier.Rank;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        General bob = new General("John", 100);
        General alice = new General("Alice", 100);
        PresidentsSecretary presidentsSecretary = new PresidentsSecretary();

        bob.subscribe(presidentsSecretary);
        alice.subscribe(presidentsSecretary);

        bob.buySoldier(Rank.PRIVATE);
        bob.saveGame("test.json");
        General loadedGeneral = General.loadGame("./test.json");
        System.out.println(loadedGeneral.getGold());
        bob.buySoldier(Rank.PRIVATE);
        alice.buySoldier(Rank.PRIVATE);
        bob.attack(alice);
        bob.attack(alice);
        alice.increaseGold(300);
        Thread.sleep(1000);
        presidentsSecretary.onComplete();
        Thread.sleep(1000);
        System.exit(0);
    }
}
