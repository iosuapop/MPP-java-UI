package donations.service;

import donations.domain.Charity;
import donations.domain.Donation;
import javafx.util.Pair;

import java.util.List;

public interface IDonationService {
    Donation add_Donation(Donation donation);
    List<Pair<Charity, Float>> getCharityFonds();
}
