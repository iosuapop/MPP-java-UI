package donations.domain;

import java.util.Objects;

public class Donation extends Entity<Integer> {
    /*
    Donation{
int id (pk auto increment),
int id_charity (fk -> charity),
int id_donor (fk -> charity),
float amount;
}
     */
    Charity charity;
    Donor donor;
    float amount;

    public Donation(Integer id, Charity charity, Donor donor, float amount) {
        super(id);
        this.charity = charity;
        this.donor = donor;
        this.amount = amount;
    }

    public Charity getCharity() {
        return charity;
    }

    public void setCharity(Charity charity) {
        this.charity = charity;
    }

    public Donor getDonor() {
        return donor;
    }

    public void setDonor(Donor donor) {
        this.donor = donor;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Donation donation = (Donation) o;
        return Objects.equals(donor, donation.donor) && Objects.equals(charity, donation.charity) && Objects.equals(amount, donation.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), charity, donor, amount);
    }

    @Override
    public String toString() {
        return "Donation{" +
                "id=" + getId() +
                ", charity=" + charity +
                ", donor=" + donor +
                ", amount=" + amount +
                '}';
    }
}
