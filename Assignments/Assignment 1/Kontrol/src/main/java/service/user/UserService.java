package service.user;

import dto.UserDTO;
import model.User;
import model.validation.Notification;
import repository.user.UserRepository;

import java.util.List;

public interface UserService {
    List<UserDTO> findAllEmployees();
    List<UserDTO> findAllCustomers();
    Notification<Boolean> changeUserUsername(UserDTO user, String newUsername);//shocking name I know - The floor is made of floor
    Notification<Boolean> delete(UserDTO user);
}
