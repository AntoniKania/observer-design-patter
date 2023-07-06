package notification;

import soldier.Soldier;

public class SoldierReport extends WarReport {
    Soldier soldier;

    public SoldierReport(Soldier soldier, String message) {
        super(message);
        this.soldier = soldier;
    }

    public Soldier getSoldier() {
        return soldier;
    }
}
