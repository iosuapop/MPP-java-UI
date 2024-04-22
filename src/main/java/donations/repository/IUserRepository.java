package donations.repository;
import donations.domain.User;

public interface IUserRepository extends IRepository<Integer, User>{
    boolean checkCredentials(String username, String password);
    User getUser(String username, String password);
}
