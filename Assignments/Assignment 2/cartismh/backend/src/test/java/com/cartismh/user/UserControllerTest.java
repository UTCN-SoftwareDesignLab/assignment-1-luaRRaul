package com.cartismh.user;

import com.cartismh.BaseControllerTest;
import com.cartismh.TestCreationFactory;
import com.cartismh.user.dto.UserDTO;
import com.cartismh.user.dto.UserListDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.cartismh.TestCreationFactory.*;
import static com.cartismh.UrlMapping.ENTITY;
import static com.cartismh.UrlMapping.USERS;
import static com.cartismh.user.model.ERole.EMPLOYEE;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest extends BaseControllerTest {

    @InjectMocks
    private UserController controller;

    @Mock
    private UserService userService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        MockitoAnnotations.openMocks(this);
        controller = new UserController(userService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void allUsers() throws Exception {
        List<UserListDTO> userListDTOs = TestCreationFactory.listOf(UserListDTO.class);
        when(userService.allUsersForList()).thenReturn(userListDTOs);

        ResultActions result = mockMvc.perform(get(USERS));
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(userListDTOs));
    }

    @Test
    void edit() throws Exception {
        long id = randomLong();
        Set<String> roles = new HashSet<>();
        roles.add(String.valueOf(EMPLOYEE));
        UserDTO reqUser = UserDTO.builder()
                .id(id)
                .name(randomString())
                .email(randomEmail())
                .roles(roles)
                .build();

        when(userService.edit(id, reqUser)).thenReturn(reqUser);

        ResultActions result = performPutWithRequestBodyAndPathVariable(USERS+ENTITY, reqUser, id);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqUser));
    }

    @Test
    void getUser() throws Exception {
        long id  = randomLong();
        Set<String> roles = new HashSet<>();
        roles.add(String.valueOf(EMPLOYEE));
        UserDTO reqUser = UserDTO.builder()
                .id(id)
                .name(randomString())
                .email(randomEmail())
                .roles(roles)
                .build();
        when(userService.get(id)).thenReturn(reqUser);

        ResultActions result = performGetWithPathVariable(USERS + ENTITY, id);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqUser));
    }

    @Test
    void delete() throws Exception {
        long id  = randomLong();
        doNothing().when(userService).delete(id);

        ResultActions result = performDeleteWithPathVariable(USERS + ENTITY, id);
        result.andExpect(status().isOk());
    }
}