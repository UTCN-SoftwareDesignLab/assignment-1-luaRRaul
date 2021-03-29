package service;

import dto.UserDTO;
import dto.builder.UserDTOBuilder;
import launcher.ContainerFactory;
import model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.user.UserRepository;
import service.rightsRoles.RightsRolesService;
import service.user.AuthenticationService;

import java.util.Collections;

import static database.Constants.Roles.ADMINISTRATOR;

public class AuthenticationServiceMySQLTest {

        public static final String TEST_USERNAME = "test@username.com";
        public static final String TEST_PASSWORD = "TestPassword1@";
        private static AuthenticationService authenticationService;
        private static RightsRolesService rightsRolesService;
        private static UserRepository userRepository;

        @BeforeClass
        public static void setUp() {
            ContainerFactory containerFactory = ContainerFactory.instance(true);
            userRepository = containerFactory.getUserRepository();
            authenticationService = containerFactory.getAuthenticationService();
            rightsRolesService = containerFactory.getRightsRolesService();
        }

        @Before
        public void cleanUp() {
            userRepository.removeAll();
        }


        @Test
        public void register() {
            Assert.assertTrue(
                    authenticationService.register(new UserDTOBuilder().setUsername(TEST_USERNAME).setPassword(TEST_PASSWORD).setRoles(Collections.singletonList(rightsRolesService.getRoleByTitle(ADMINISTRATOR))).build()).getResult()
            );
        }

        @Test
        public void login() throws Exception {
            authenticationService.register(new UserDTOBuilder().setUsername(TEST_USERNAME).setPassword(TEST_PASSWORD).setRoles(Collections.singletonList(rightsRolesService.getRoleByTitle(ADMINISTRATOR))).build());
            User user = authenticationService.login(new UserDTOBuilder().setUsername(TEST_USERNAME).setPassword(TEST_PASSWORD).build()).getResult();
            Assert.assertNotNull(user);
        }

        @Test
        public void logout() {

        }

}
