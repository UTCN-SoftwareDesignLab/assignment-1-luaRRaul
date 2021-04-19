package com.cartismh.user;

import com.cartismh.user.dto.UserDTO;
import com.cartismh.user.dto.UserListDTO;
import com.cartismh.user.dto.UserMinimalDTO;
import com.cartismh.user.mapper.UserMapper;
import com.cartismh.user.model.ERole;
import com.cartismh.user.model.Role;
import com.cartismh.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    public List<UserMinimalDTO> allUsersMinimal() {
        return userRepository.findAll()
                .stream().map(userMapper::userMinimalFromUser)
                .collect(toList());
    }

    public List<UserListDTO> allUsersForList() {
        return userRepository.findAll()
                .stream().map(userMapper::userListDtoFromUser)
                .collect(toList());
    }

    public UserDTO edit(Long id, UserDTO user) {
        User actUser = findById(id);
        actUser.setEmail(user.getEmail());
        actUser.setPassword(user.getName());
        actUser.setUsername(user.getName());


        if (user.getRoles() == null){
            throw new RuntimeException("Cannot find role for user: " + user.getName());
        }
        else{
            Set<Role> roles = (Set<Role>) user.getRoles().stream().map((String role) -> roleRepository.findByName(ERole.valueOf(role)));
            actUser.setRoles(roles);
        }
        return userMapper.userDtoFromUser(
                userRepository.save(actUser)
        );
    }

    private User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Item not found: " + id));
    }

    public UserListDTO changePassword(Long id, String newPassword) {
        return null;
    }

    public UserDTO get(Long id) {
        return userMapper.userDtoFromUser(userRepository.findById(id).get());
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
