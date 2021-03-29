package service.user;

import dto.UserDTO;
import model.Role;
import model.User;
import model.builder.UserBuilder;
import model.validation.Notification;
import model.validation.UserValidator;
import repository.security.RightsRolesRepository;
import repository.user.UserRepository;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Collections;

import static database.Constants.Roles.ADMINISTRATOR;

/**
 * Created by Alex on 11/03/2017.
 */
public class AuthenticationServiceMySQL implements AuthenticationService {

    private final UserRepository userRepository;

    public AuthenticationServiceMySQL(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Notification<Boolean> register(UserDTO userDTO) {
        User user = new UserBuilder()
                .setUsername(userDTO.getUsername())
                .setPassword(userDTO.getPassword())
                .setRoles(userDTO.getRoles())
                .build();

        UserValidator userValidator = new UserValidator(user);
        boolean userValid = userValidator.validate();
        Notification<Boolean> userRegisterNotification = new Notification<>();

        if (!userValid) {
            userValidator.getErrors().forEach(userRegisterNotification::addError);
            userRegisterNotification.setResult(Boolean.FALSE);
        } else {
            user.setPassword(encodePassword(userDTO.getPassword()));
            userRegisterNotification.setResult(userRepository.save(user));
        }
        return userRegisterNotification;
    }

    @Override
    public Notification<User> login(UserDTO userDTO) {
        return userRepository.findByUsernameAndPassword(userDTO.getUsername(), encodePassword(userDTO.getPassword()));
    }

    @Override
    public boolean logout(User user) {
        return false;
    }

    private String encodePassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
