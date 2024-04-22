package donations.domain;
import java.util.Objects;

public class User extends Entity<Integer>{
    /*
    User{
int id (pk auto increment),
varchar(50) username (not null),
varchar(50) password (not null);
}*/
    String username;
    String password;

    public User(Integer integer, String username, String password) {
        super(integer);
        this.username = username;
        this.password = password;
    }

    public User(Integer integer, String username) {
        super(integer);
        this.username = username;
        this.password = null;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return username.equals(user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), username);
    }

    @Override
    public String toString() {
        return "User{id=" + getId() + ", " +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
