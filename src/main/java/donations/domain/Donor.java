package donations.domain;
import java.util.Objects;

public class Donor extends Entity<Integer> {
    /*
    Donor{
int id (pk aauto increment),
varchar(50) name (not null),
varchar(15) telephone (not null),
varchar(100) adress (not null);
}
     */
    String name;
    String telephone;
    String adress;

    public Donor(Integer id, String name, String telephone, String adress) {
        super(id);
        this.name = name;
        this.telephone = telephone;
        this.adress = adress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if ( o == null || getClass() != o.getClass()) return false;
        if ( !super.equals(0)) return false;
        Donor donor = (Donor) o;
        return name.equals(donor.name) && telephone.equals(donor.telephone) && adress.equals(donor.adress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, telephone, adress);
    }

    @Override
    public String toString() {
        return "Donor{" +
                "id=" + getId() +
                ", name='" + name + '\'' +
                ", telephone='" + telephone + '\'' +
                ", adress='" + adress + '\'' +
                '}';
    }
}


