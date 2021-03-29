package service.user;

import dto.UserDTO;
import dto.builder.UserDTOBuilder;
import model.User;
import model.validation.Notification;
import repository.security.RightsRolesRepository;
import repository.user.UserRepository;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
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
    public List<UserDTO> findAllEmployees() {
        List<UserDTO> users = new ArrayList<>();
        for(User u : userRepository.findAllEmployees()){
            users.add(new UserDTOBuilder().setUsername(u.getUsername()).setRoles(u.getRoles()).setId(u.getId()).build());
        }
        return users;
    }

    @Override
    public List<UserDTO> findAllCustomers() {
        List<UserDTO> users = new ArrayList<>();
        for(User u : userRepository.findAllCustomers()){
            users.add(new UserDTOBuilder().setUsername(u.getUsername()).setRoles(u.getRoles()).setId(u.getId()).build());
        }
        return users;
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
