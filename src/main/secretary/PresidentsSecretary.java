package secretary;

import common.ConsoleColors;
import notification.GeneralReport;
import notification.SoldierReport;
import notification.WarReport;

import java.io.Serializable;
import java.util.concurrent.Flow;

public class PresidentsSecretary implements Flow.Subscriber<WarReport>, Serializable {
    private transient Flow.Subscription subscription;

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1);
    }

    @Override
    public void onNext(WarReport warReport) {
        writeReport(warReport);
        subscription.request(1);
    }

    public void onError(Throwable throwable) {
        System.out.println("Error details: " + throwable.getMessage());
    }

    @Override
    public void onComplete() {
        System.out.println("Secretary: End of war, thx for your attention \uD83E\uDD13");
    }

    public void writeReport(WarReport warReport) {
        if (warReport instanceof GeneralReport generalReport) {
            System.out.println(ConsoleColors.CYAN + "Secretary: General " + generalReport.getGeneral().getName() + " " +  warReport.getMessage() + ConsoleColors.RESET);
        } else if (warReport instanceof SoldierReport soldierReport) {
            System.out.println(ConsoleColors.GREEN + "Secretary: Soldier with rank " + soldierReport.getSoldier().getRank() + ": " +  warReport.getMessage() + ConsoleColors.RESET);
        } else {
            System.out.println("Secretary: " + warReport.getMessage());
        }
    }
}
