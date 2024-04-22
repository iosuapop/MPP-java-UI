package donations.service;

import donations.domain.Charity;
import donations.domain.Donation;
import donations.domain.Donor;
import donations.domain.User;
import donations.repository.ICharityRepository;
import donations.repository.IDonationRepository;
import donations.repository.IDonorRepository;
import donations.repository.IUserRepository;
import donations.repository.implementations.CharityDbRepository;
import donations.repository.implementations.DonationDbRepository;
import donations.repository.implementations.DonorDbRepository;
import donations.repository.implementations.UserDbRepository;
import javafx.util.Pair;
import java.util.Properties;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;


public class Service implements IService{

    IDonorRepository donorRepository;
    IDonationRepository donationRepository;
    ICharityRepository charityRepository;
    IUserRepository userRepository;

    public Service(){
        Properties props=new Properties();
        try {
            props.load(new FileReader("bd.config"));
        } catch (IOException e) {
            System.out.println("Cannot find bd.config "+e);
            return;
        }
        this.donorRepository = new DonorDbRepository(props);
        this.donationRepository = new DonationDbRepository(props);
        this.charityRepository = new CharityDbRepository(props);
        this.userRepository = new UserDbRepository(props);
    }

    @Override
    public Donation add_Donation(Donation donation) {
        if(donation.getAmount() < 0)
            throw new RuntimeException("Cant donate negative balance!");
        donation.setId(donationRepository.add(donation));
        return donation;
    }

    @Override
    public List<Pair<Charity, Float>> getCharityFonds() {
        return donationRepository.getCharityAmounts();
    }

    @Override
    public Donor add_Donor(Donor donor) {
        if(donorRepository.getDonorByID(donor) > 0) {
            donor.setId(donorRepository.getDonorByID(donor));
            return donor;
        }
        donor.setId(donorRepository.add(donor));
        return donor;
    }

    @Override
    public List<Donor> getAllDonorsName(String name) {
        return donorRepository.getDonorName(name);
    }

    @Override
    public boolean login(String username, String password) {
        return userRepository.checkCredentials(username, password);
    }

    @Override
    public User getUser(String username, String password) {
        return userRepository.getUser(username, password);
    }

    @Override
    public List<String> getAllCase() {
        return charityRepository.getCases();
    }

    @Override
    public Charity getIdByCase(Charity charity) {
        return charityRepository.getIDByCharity(charity);
    }
}
