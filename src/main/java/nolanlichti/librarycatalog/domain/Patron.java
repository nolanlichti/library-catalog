package nolanlichti.librarycatalog.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Patron {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String checkedOutItems;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCheckedOutItems() {
        return checkedOutItems;
    }

    public void setCheckedOutItems(String checkedOutItems) {
        this.checkedOutItems = checkedOutItems;
    }

    public String checkOutItem(LendableItem item) {
        this.checkedOutItems = this.checkedOutItems + "," + item.getId();
        return item.checkout();
    }

    public double returnItem(LendableItem item) {
        var itemIds = List.of(this.checkedOutItems.split(","));

        if (itemIds.remove(item.getId())) {
            this.checkedOutItems = String.join(",", itemIds);
            return item.returnItem();
        }
        throw new IllegalStateException("Item not checked out by this patron");
    }
}
