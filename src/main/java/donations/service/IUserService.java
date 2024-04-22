package donations.service;

import donations.domain.User;

public interface IUserService {
    boolean login(String username, String password);
    User getUser(String username, String password);
}
