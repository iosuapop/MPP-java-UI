package donations.repository.implementations;

import donations.domain.User;
import donations.repository.IUserRepository;
import donations.utils.JdbcUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

public class UserDbRepository implements IUserRepository {
    private final JdbcUtils dbUtils;
    private static final Logger logger = LogManager.getLogger();

    public UserDbRepository(Properties props) {
        logger.info("Initializing UserDbRepository with properties: {} ", props);
        this.dbUtils = new JdbcUtils(props);
    }

    private String encryptPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            logger.error("Encryption algorithm not found: {}", e.getMessage());
            return null;
        }
    }

    private String decryptPassword(String encryptedPassword) {
        return encryptedPassword;
    }

    @Override
    public User findOne(Integer integer) {
        return null;
    }

    @Override
    public ArrayList<User> getAll() {
        return null;
    }

    @Override
    public Integer add(User entity) {
        logger.traceEntry("add: entity={}", entity);
        String encryptedPassword = encryptPassword(entity.getPassword());
        if (encryptedPassword == null) {
            logger.error("Failed to encrypt password.");
            return -1;
        }
        Connection con = dbUtils.getConnection();
        try (PreparedStatement prepStmt =
                     con.prepareStatement("insert into User (username, password) values (?,?)",
                             PreparedStatement.RETURN_GENERATED_KEYS)) {
            prepStmt.setString(1, entity.getUsername());
            prepStmt.setString(2, encryptedPassword);
            int result = prepStmt.executeUpdate();
            if (result == 0) {
                logger.error("User not added!");
                throw new SQLException("User not added!");
            }
            int id = -1;
            try (ResultSet generatedKeys = prepStmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    id = generatedKeys.getInt(1);
                    entity.setId(id);
                } else {
                    logger.error("User not added!");
                    throw new SQLException("User not added!");
                }
            }
            logger.traceExit("Added user={}", entity);
            return id;
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB " + e);
        }
        logger.traceExit(-1);
        return -1;
    }

    @Override
    public void update(Integer integer, User entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(User entity) {
        logger.traceEntry("delete: entity={}", entity);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preparedStatement = con.prepareStatement("DELETE FROM User " +
                "where id=? and username=? and password=?")) {
            preparedStatement.setInt(1, entity.getId());
            preparedStatement.setString(2, entity.getUsername());
            preparedStatement.setString(3, entity.getPassword());
            int result = preparedStatement.executeUpdate();
            if (result == 0) {
                throw new SQLException("User not deleted!");
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB " + e);
        }
        logger.traceExit();
    }

    @Override
    public boolean checkCredentials(String username, String password) {
        logger.traceEntry("checkCredentials: username={}, password={}", username, password);
        String encryptedPassword = encryptPassword(password);
        if (encryptedPassword == null) {
            logger.error("Failed to encrypt password for checking credentials.");
            return false;
        }
        Connection con = dbUtils.getConnection();
        try (PreparedStatement prepStmt = con.prepareStatement("select " +
                "count(*) from User where username=? and password=?")) {
            prepStmt.setString(1, username);
            prepStmt.setString(2, encryptedPassword);
            try (ResultSet result = prepStmt.executeQuery()) {
                if (result.next() && result.getInt(1) > 0) {
                    logger.traceExit("checkCredentials: true");
                    return true;
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB " + e);
        }
        logger.traceExit("checkCredentials: false");
        return false;
    }

    @Override
    public User getUser(String username, String password) {
        logger.traceEntry("get User: username={}, password={}", username, password);
        String encryptedPassword = encryptPassword(password);
        if (encryptedPassword == null) {
            logger.error("Failed to encrypt password for getting user.");
            return null;
        }
        Connection con = dbUtils.getConnection();
        try (PreparedStatement prepStmt = con.prepareStatement("select " +
                "* from User where username=? and password=?")) {
            prepStmt.setString(1, username);
            prepStmt.setString(2, encryptedPassword);
            try (ResultSet result = prepStmt.executeQuery()) {
                if (result.next()) {
                    return new User(result.getInt("id"), result.getString("username"),
                            decryptPassword(result.getString("password")));
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB " + e);
        }
        logger.traceExit("checkCredentials: false");
        return null;
    }
}
