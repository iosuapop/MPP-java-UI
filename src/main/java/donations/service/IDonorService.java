package donations.service;

import donations.domain.Donor;

import java.util.List;

public interface IDonorService {
    Donor add_Donor(Donor donor);
    List<Donor> getAllDonorsName(String name);
}