package service.user;

import dto.UserDTO;
import model.Role;
import model.User;
import model.validation.Notification;

/**
 * Created by Alex on 11/03/2017.
 */
public interface AuthenticationService {

    Notification<Boolean> register(UserDTO userDTO);

    Notification<User> login(UserDTO userDTO);

    boolean logout(User user);

}
