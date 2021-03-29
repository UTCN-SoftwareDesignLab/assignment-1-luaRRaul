package dto.builder;

import dto.UserDTO;
import model.Role;
import model.User;

import java.util.List;

/**
 * Created by Alex on 11/03/2017.
 */
public class UserDTOBuilder {

    private UserDTO user;

    public UserDTOBuilder() {
        user = new UserDTO();
    }

    public UserDTOBuilder setId(long id){
        user.setId(id);
        return this;
    }

    public UserDTOBuilder setUsername(String username) {
        user.setUsername(username);
        return this;
    }

    public UserDTOBuilder setPassword(String password) {
        user.setPassword(password);
        return this;
    }

    public UserDTOBuilder setRoles(List<Role> roles) {
        user.setRoles(roles);
        return this;
    }

    public UserDTO build() {
        return user;
    }


}
