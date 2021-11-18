package nolanlichti.librarycatalog.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Entity
public abstract class LendableItem {
    @Id
    @GeneratedValue
    private Integer id;
    private String checkoutDate;

    public abstract int daysUntilDue();

    public abstract double overdueChargePerDay();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(String checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

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
