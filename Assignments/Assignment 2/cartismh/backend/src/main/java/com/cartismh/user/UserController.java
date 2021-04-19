package com.cartismh.user;

import com.cartismh.user.dto.UserDTO;
import com.cartismh.user.dto.UserListDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.cartismh.UrlMapping.ENTITY;
import static com.cartismh.UrlMapping.USERS;

@RestController
@RequestMapping(USERS)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserListDTO> allUsers() {
        return userService.allUsersForList();
    }

    @PutMapping(ENTITY)//full edit
    public UserDTO edit(@PathVariable Long id, @RequestBody UserDTO user){
        return userService.edit(id, user);
    }

    @PatchMapping(ENTITY)//partial edit
    public UserListDTO changePassword(@PathVariable Long id, @RequestBody String newPassword){
        return userService.changePassword(id, newPassword);
    }

    @GetMapping(ENTITY)
    public UserDTO getUser(@PathVariable Long id){
        return userService.get(id);
    }

    @DeleteMapping(ENTITY)
    public void delete(@PathVariable Long id)
    {
        userService.delete(id);
    }

}
