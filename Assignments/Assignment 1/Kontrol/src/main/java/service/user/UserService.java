package service.user;

import model.User;
import repository.user.UserRepository;

import java.util.List;

public interface UserService {
    List<User> findAllEmployees();
    List<User> findAllCustomers();
    User findById(long id);
    boolean changeEmployeePassword(String password);
}
