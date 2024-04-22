package donations;

import donations.repository.implementations.UserDbRepository;

import donations.domain.User;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class FirstMain {


    public static void main(String[] args) {

        Properties props = new Properties();
        try {
            props.load(new FileReader("bd.config"));

            UserDbRepository userRepo = new UserDbRepository(props);
            User user = new User(0, "", "");
            userRepo.add(user);
            /*DonorDbRepository donorRepo = new DonorDbRepository(props);
            DonationDbRepository donationRepo = new DonationDbRepository(props);
            CharityDbRepository charityRepo = new CharityDbRepository(props);


            User user = new User(1, "andrei", "ierdna");
            userRepo.add(user);

            List<User> users = userRepo.getAll();

            assert (userRepo.checkCredentials(user.getUsername(), user.getPassword()));

            User user1 = new User(2, "iosua", "pop");
            userRepo.add(user1);
            User user2 = new User(3, "iosua", "ierdna");
            assert (!userRepo.checkCredentials(user.getUsername(), user.getPassword()));

            userRepo.delete(user);
            assert (!userRepo.checkCredentials(user.getUsername(), user.getPassword()));

            charityRepo.add(new Charity(1, "Orphan kids."));
            charityRepo.add(new Charity(2, "Ukrain support."));
            charityRepo.add(new Charity(3, "Irak hunger."));


            charityRepo.delete(new Charity(1, "Orphan kids."));

            donorRepo.add(new Donor(1, "alex", "0725229931", "strada Dorobantilor nr 56,Cluj Napoca"));
            donorRepo.add(new Donor(2, "maria", "0765817128", "strada Baisoara nr 5,Cluj Napoca"));

            donationRepo.add(new Donation(1, new Charity(3, "Irak hunger."),
                    new Donor(1, "alex", "0725229931", "strada Dorobantilor nr 56,Cluj Napoca"), 500));
            donationRepo.add(new Donation(2, new Charity(2, "Ukrain support."),
                    new Donor(2, "maria", "0765817128", "strada Baisoara nr 5,Cluj Napoca"), 200));*/


        } catch (IOException e) {
            System.out.println("Cannot find bd.config " + e);
        }
    }
}
