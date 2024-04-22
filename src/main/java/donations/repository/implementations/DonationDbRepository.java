package donations.repository.implementations;

import donations.domain.Charity;
import donations.domain.Donation;
import donations.domain.Donor;
import donations.repository.IDonationRepository;
import donations.utils.JdbcUtils;
import javafx.util.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DonationDbRepository implements IDonationRepository {
    private final JdbcUtils dbUtils;
    private static final Logger logger= LogManager.getLogger();

    public DonationDbRepository(Properties props) {
        logger.info("Initializing DonationDbRepository with properties: {} ", props);
        this.dbUtils = new JdbcUtils(props);
    }

    @Override
    public Donation findOne(Integer integer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ArrayList<Donation> getAll() {
        logger.traceEntry("Enter getAll Orders");
        Connection con = dbUtils.getConnection();
        ArrayList<Donation> donations = new ArrayList<>();
        try (PreparedStatement prepStmt = con.prepareStatement(
                "SELECT d.*, c.*, do.* FROM Donation do " +
                        "INNER JOIN Donor d ON do.id_donor = d.id " +
                        "INNER JOIN Charity c ON do.id_charity = c.id")) {
            try (ResultSet rs = prepStmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    int id_donor = rs.getInt("id_donor");
                    int id_charity = rs.getInt("id_charity");
                    float amount = rs.getFloat("amount");
                    String name = rs.getString("name");
                    String telephone = rs.getString("telephone");
                    String adress = rs.getString("adress");
                    String description = rs.getString("description");
                    Donation donation =
                            new Donation(id, new Charity(id_charity, description),
                                    new Donor(id_donor, name, telephone, adress),
                                    amount);
                    donations.add(donation);
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB " + e);
        }
        logger.traceExit("getAll Donations: {}", donations);
        return donations;
    }

    @Override
    public Integer add(Donation entity) {
        logger.traceEntry("add: entity={}", entity);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement prepStmt =
                     con.prepareStatement("insert into Donation (id_donor, id_charity, amount) values (?, ?, ?)",
                             Statement.RETURN_GENERATED_KEYS)) {
            prepStmt.setInt(1, entity.getDonor().getId());
            prepStmt.setInt(2, entity.getCharity().getId());
            prepStmt.setFloat(3, entity.getAmount());
            int result = prepStmt.executeUpdate();
            try(ResultSet rs = prepStmt.getGeneratedKeys()){
                if(rs.next()){
                    int id = rs.getInt(1);
                    entity.setId(id);
                    return id;
                }
            }
            logger.trace("Saved {} instances", result);
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB " + e);
        }
        logger.traceExit(-1);
        return -1;
    }

    @Override
    public void update(Integer integer, Donation entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(Donation entity) {
        logger.traceEntry("delete: entity={}", entity);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement prepStmt = con.prepareStatement("delete from Donation where id=?")) {
            prepStmt.setInt(1, entity.getId());
            int result = prepStmt.executeUpdate();
            logger.trace("Deleted {} instances", result);
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB " + e);
        }
        logger.traceExit();
    }

    @Override
    public List<Pair<Charity, Float>> getCharityAmounts() {
        logger.traceEntry("Enter getCharityAmounts");
        Connection con = dbUtils.getConnection();
        List<Pair<Charity, Float>> charityAmounts = new ArrayList<>();
        try (PreparedStatement prepStmt = con.prepareStatement(
                "SELECT c.*, SUM(do.amount) AS total_amount FROM Charity c " +
                        "LEFT JOIN Donation do ON c.id = do.id_charity " +
                        "GROUP BY c.id")) {
            try (ResultSet rs = prepStmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String description = rs.getString("description");
                    float totalAmount = rs.getFloat("total_amount");
                    Charity charity = new Charity(id, description);
                    charityAmounts.add(new Pair<>(charity, totalAmount));
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB " + e);
        }
        logger.traceExit("getCharityAmounts: {}", charityAmounts);
        return charityAmounts;
    }

}
