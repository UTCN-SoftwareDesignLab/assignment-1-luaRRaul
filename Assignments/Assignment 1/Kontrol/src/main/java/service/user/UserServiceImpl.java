package service.user;

import dto.UserDTO;
import dto.builder.UserDTOBuilder;
import model.User;
import model.builder.UserBuilder;
import model.validation.Notification;
import model.validation.Validator;
import repository.user.UserRepository;

import java.util.ArrayList;
import java.util.List;


public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository repository){
        this.userRepository=repository;
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
    public Notification<Boolean> changeUserUsername(UserDTO userDTO, String newUsername) {
        Notification<Boolean> changeUsernameNotification = new Notification<>();
        User user = new UserBuilder().setUsername(userDTO.getUsername()).build();
        Validator v = new Validator();
        if(v.usernameIsValid(newUsername)){
            return userRepository.changeUsername(user, newUsername);
        }
        changeUsernameNotification.setResult(false);
        changeUsernameNotification.addError("Invalid username");
        return changeUsernameNotification;
    }

    @Override
    public Notification<Boolean> delete(UserDTO user) {
        return userRepository.deleteByUsername(user.getUsername());
    }

}
