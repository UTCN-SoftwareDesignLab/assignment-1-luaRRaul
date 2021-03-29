package service.user;

import dto.UserDTO;
import model.User;
import repository.user.UserRepository;

import java.util.List;

public interface UserService {
    List<UserDTO> findAllEmployees();
    List<UserDTO> findAllCustomers();
    User findById(long id);
    boolean changeEmployeePassword(String password);
}
