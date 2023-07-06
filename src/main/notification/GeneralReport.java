package notification;

import general.General;

public class GeneralReport extends WarReport {
    General general;

    public GeneralReport(General general, String message) {
        super(message);
        this.general = general;
    }

    public General getGeneral() {
        return general;
    }
}
