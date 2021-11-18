package nolanlichti.librarycatalog.domain;

import nolanlichti.librarycatalog.repositories.BookRepository;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Patron {
    @Id
    @GeneratedValue
    private Integer id;
    private String firstName;
    private String lastName;
    private String checkedOutItems;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String checkOutItem(LendableItem item, BookRepository bookRepository) {
        this.checkedOutItems = this.checkedOutItems + "," + item.getId();
        var dueDate = item.checkout();
        if (item instanceof Book) {
            bookRepository.save((Book) item);
        }
        return dueDate;
    }

    public double returnItem(LendableItem item) {
        var itemIds = new ArrayList<>(List.of(this.checkedOutItems.split(",")));

        if (itemIds.remove(item.getId().toString())) {
            this.checkedOutItems = String.join(",", itemIds);
            return item.returnItem();
        }
        throw new IllegalStateException("Item not checked out by this patron");
    }
}
