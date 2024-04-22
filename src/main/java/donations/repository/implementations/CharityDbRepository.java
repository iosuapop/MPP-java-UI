package donations.repository.implementations;

import donations.domain.Charity;
import donations.repository.ICharityRepository;
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

public class CharityDbRepository implements ICharityRepository {
    private final JdbcUtils dbUtils;
    private static final Logger logger= LogManager.getLogger();

    public CharityDbRepository(Properties props) {
        logger.info("Initializing CharityDbRepository with properties: {} ", props);
        this.dbUtils = new JdbcUtils(props);
    }

    @Override
    public Charity findOne(Integer integer) {
        return null;
    }

    @Override
    public ArrayList<Charity> getAll() {
        return null;
    }

    @Override
    public Integer add(Charity entity) {
        logger.traceEntry("add: entity={}", entity);
        Connection con = dbUtils.getConnection();
        try(PreparedStatement prepStmt =
                    con.prepareStatement("insert into Charity (description) values (?)",
                            PreparedStatement.RETURN_GENERATED_KEYS)){
            prepStmt.setString(1, entity.getDescription());
            int result = prepStmt.executeUpdate();
            if (result == 0) {
                logger.error("Chaarity not added!");
                throw new SQLException("Charity not added!");
            }
            int id = -1;
            try (ResultSet generatedKeys = prepStmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    id = generatedKeys.getInt(1);
                    entity.setId(id);
                }
                else {
                    logger.error("Charity not added!");
                    throw new SQLException("Charity not added!");
                }
            }
            logger.traceExit("Added Charity={}", entity);
            return id;
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB " + e);
        }
        logger.traceExit(-1);
        return -1;
    }

    @Override
    public void update(Integer integer, Charity entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(Charity entity) {
        logger.traceEntry("delete: entity={}", entity);
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preparedStatement=con.prepareStatement("DELETE FROM Charity " +
                "where id=? and description=?")){
            preparedStatement.setInt(1, entity.getId());
            preparedStatement.setString(2, entity.getDescription());
            int result = preparedStatement.executeUpdate();
            if (result == 0) {
                throw new SQLException("Charity not deleted!");
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB " + e);
        }
        logger.traceExit();
    }

    @Override
    public Charity getIDByCharity(Charity charity) {
        logger.traceEntry("searching id for: chaarity={}", charity);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement prepStmt =
                     con.prepareStatement("SELECT id FROM Charity WHERE description = ?")) {
            prepStmt.setString(1, charity.getDescription());
            ResultSet resultSet = prepStmt.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                charity.setId(id);
                logger.traceExit("Found Chaarity with id={}", id);
                return charity;
            } else {
                logger.traceExit("Id not found for charity{}", charity);
                return null;
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB " + e);
            return null;
        }
    }

    @Override
    public List<String> getCases() {
        logger.traceEntry("Getting all charity descriptions");
        Connection con = dbUtils.getConnection();
        List<String> descriptions = new ArrayList<>();
        try (PreparedStatement prepStmt =
                     con.prepareStatement("SELECT DISTINCT description FROM Charity")) {
            ResultSet resultSet = prepStmt.executeQuery();
            while (resultSet.next()) {
                String description = resultSet.getString("description");
                descriptions.add(description);
            }
            logger.traceExit("Fetched {} descriptions", descriptions.size());
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB " + e);
        }
        return descriptions;
    }
}
