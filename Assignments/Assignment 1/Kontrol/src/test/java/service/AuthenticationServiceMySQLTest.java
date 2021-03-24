package service;

import launcher.ContainerFactory;
import model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.user.UserRepository;
import service.user.AuthenticationService;

import static database.Constants.Roles.ADMINISTRATOR;

public class AuthenticationServiceMySQLTest {

        public static final String TEST_USERNAME = "test@username.com";
        public static final String TEST_PASSWORD = "TestPassword1@";
        private static AuthenticationService authenticationService;
        private static UserRepository userRepository;

        @BeforeClass
        public static void setUp() {
            ContainerFactory containerFactory = ContainerFactory.instance(true);
            userRepository = containerFactory.getUserRepository();
            authenticationService = containerFactory.getAuthenticationService();
        }

        @Before
        public void cleanUp() {
            userRepository.removeAll();
        }


        @Test
        public void register() {
            Assert.assertTrue(
                    authenticationService.register(TEST_USERNAME, TEST_PASSWORD, ADMINISTRATOR).getResult()
            );
        }

        @Test
        public void login() throws Exception {
            authenticationService.register(TEST_USERNAME, TEST_PASSWORD, ADMINISTRATOR);
            User user = authenticationService.login(TEST_USERNAME, TEST_PASSWORD).getResult();
            Assert.assertNotNull(user);
        }

        @Test
        public void logout() {

        }

}
