package nolanlichti.librarycatalog.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public abstract class LendableItem {
    private String checkoutDate;

    public abstract String getId();

    public abstract int daysUntilDue();

    public abstract double overdueChargePerDay();

    /**
     * Checks out the lendable item, setting the checkout date to today
     *
     * @return due date as a String
     */
    public String checkout() {
        var today = LocalDate.now();
        this.checkoutDate = today.format(DateTimeFormatter.ISO_LOCAL_DATE);
        return today.plusDays(daysUntilDue()).format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    /**
     * Returns item, calculating any overdue charges
     *
     * @return overdue charges
     */
    public double returnItem() {
        var daysOverdue = ChronoUnit.DAYS.between(LocalDate.parse(checkoutDate, DateTimeFormatter.ISO_LOCAL_DATE), LocalDate.now());
        if (daysOverdue < 0) {
            daysOverdue = 0;
        }
        this.checkoutDate = null;
        return daysOverdue * overdueChargePerDay();
    }
}
