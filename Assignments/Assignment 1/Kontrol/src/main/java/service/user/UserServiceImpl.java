package service.user;

import model.User;
import model.validation.Notification;
import repository.security.RightsRolesRepository;
import repository.user.UserRepository;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import static database.Constants.Tables.USER;

public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final RightsRolesRepository rightsRolesRepository;

    public UserServiceImpl(UserRepository repository, RightsRolesRepository rightsRolesRepository){
        this.userRepository=repository;
        this.rightsRolesRepository=rightsRolesRepository;
    }

    @Override
    public List<User> findAllEmployees() {
        return userRepository.findAllEmployees();
    }

    @Override
    public List<User> findAllCustomers() {
        return userRepository.findAllCustomers();
    }

    @Override
    public User findById(long id) {
        return null;
    }

    @Override
    public boolean changeEmployeePassword(String password) {
        return false;
    }
}
