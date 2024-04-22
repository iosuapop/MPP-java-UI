package donations.repository;

import donations.domain.Charity;
import donations.domain.Donation;
import javafx.util.Pair;

import java.util.List;
public interface IDonationRepository extends  IRepository<Integer, Donation> {
    List<Pair<Charity,Float>> getCharityAmounts();
}
