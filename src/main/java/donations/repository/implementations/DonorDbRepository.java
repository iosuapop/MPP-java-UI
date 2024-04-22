package donations.repository.implementations;

import donations.domain.Donor;
import donations.repository.IDonorRepository;
import donations.utils.JdbcUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DonorDbRepository implements IDonorRepository {
    private final JdbcUtils dbUtils;
    private static final Logger logger= LogManager.getLogger();

    public DonorDbRepository(Properties props) {
        logger.info("Initializing DonorDbRepository with properties: {} ", props);
        this.dbUtils = new JdbcUtils(props);
    }

    @Override
    public Donor findOne(Integer integer) {
        return null;
    }

    @Override
    public ArrayList<Donor> getAll() {
        return null;
    }

    @Override
    public Integer add(Donor entity) {
        logger.traceEntry("add: entity={}", entity);
        Connection con = dbUtils.getConnection();
        try(PreparedStatement prepStmt =
                    con.prepareStatement("insert into Donor (name, telephone, adress) values (?,?,?)",
                            PreparedStatement.RETURN_GENERATED_KEYS)){
            prepStmt.setString(1, entity.getName());
            prepStmt.setString(2, entity.getTelephone());
            prepStmt.setString(3, entity.getAdress());
            int result = prepStmt.executeUpdate();
            if (result == 0) {
                logger.error("Donor not added!");
                throw new SQLException("Donor not added!");
            }
            int id = -1;
            try (ResultSet generatedKeys = prepStmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    id = generatedKeys.getInt(1);
                    entity.setId(id);
                }
                else {
                    logger.error("Donor not added!");
                    throw new SQLException("Donor not added!");
                }
            }
            logger.traceExit("Added Donor={}", entity);
            return id;
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB " + e);
        }
        logger.traceExit(-1);
        return -1;
    }

    @Override
    public void update(Integer integer, Donor entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(Donor entity) {
        logger.traceEntry("delete: entity={}", entity);
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preparedStatement=con.prepareStatement("DELETE FROM Donor " +
                "where id=? and name=? and telephone=? and adress=?")){
            preparedStatement.setInt(1, entity.getId());
            preparedStatement.setString(2, entity.getName());
            preparedStatement.setString(3, entity.getTelephone());
            preparedStatement.setString(4, entity.getAdress());
            int result = preparedStatement.executeUpdate();
            if (result == 0) {
                throw new SQLException("Donor not deleted!");
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB " + e);
        }
        logger.traceExit();
    }

    @Override
    public List<Donor> getDonorName(String name) {
        logger.traceEntry("findDonorsByName: name={}", name);
        List<Donor> donors = new ArrayList<>();
        Connection con = dbUtils.getConnection();
        try (PreparedStatement prepStmt =
                     con.prepareStatement("SELECT * FROM Donor WHERE name LIKE ?")) {
            prepStmt.setString(1, "%" +  name + "%");
            ResultSet resultSet = prepStmt.executeQuery();
            while (resultSet.next()) {
                Donor donor = new Donor(0, "", "", "");
                donor.setId(resultSet.getInt("id"));
                donor.setName(resultSet.getString("name"));
                donor.setTelephone(resultSet.getString("telephone"));
                donor.setAdress(resultSet.getString("adress"));
                donors.add(donor);
            }
            logger.traceExit("Found {} donors with name containing '{}'", donors.size() + name);
            return donors;
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB " + e);
            return null;
        }
    }

    @Override
    public Integer getDonorByID(Donor donor) {
        logger.traceEntry("searching id for: donor={}", donor);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement prepStmt =
                     con.prepareStatement("SELECT id FROM Donor WHERE name = ? AND telephone = ? AND adress = ?")) {
            prepStmt.setString(1, donor.getName());
            prepStmt.setString(2, donor.getTelephone());
            prepStmt.setString(3, donor.getAdress());
            ResultSet resultSet = prepStmt.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                logger.traceExit("Found Donor with id={}", id);
                return id;
            } else {
                logger.traceExit("Donor not found for donor={}", donor);
                return -1;
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB " + e);
            return null;
        }
    }
}
