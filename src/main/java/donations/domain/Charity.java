package donations.domain;

import java.util.Objects;

public class Charity extends Entity<Integer> {
    /*
    Charity{
int id (pk auto increment),
varchar(50) nume (not null);
}
     */
    String description;

    public Charity(Integer id, String nume) {
        super(id);
        this.description = nume;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Charity charity = (Charity) o;
        return description.equals(charity.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), description);
    }

    @Override
    public String toString() {
        return "Charity{" +
                "id=" + getId() +
                ", nume='" + description + '\'' +
                '}';
    }
}
