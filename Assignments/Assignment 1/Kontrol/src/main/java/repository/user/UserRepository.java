package repository.user;

import dto.ActivityDTO;
import dto.UserDTO;
import model.User;
import model.validation.Notification;

import java.util.List;

/**
 * Created by Alex on 11/03/2017.
 */
public interface UserRepository {
    List<User> findAllEmployees();
    List<User> findAllCustomers();

    Notification<User> findByUsernameAndPassword(String username, String password);
    boolean save(User user);

    void removeAll();

    Notification<Boolean> changeUsername(User user, String newUsername);

    Notification<Boolean> deleteByUsername(String username);

    Notification<User> findUserById(long user_id);
}
