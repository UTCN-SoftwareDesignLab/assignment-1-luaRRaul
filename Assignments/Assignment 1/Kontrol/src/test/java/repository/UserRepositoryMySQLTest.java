package repository;

import launcher.ContainerFactory;
import org.junit.Before;
import org.junit.BeforeClass;
import repository.user.UserRepository;

public class UserRepositoryMySQLTest {
    private static UserRepository userRepository;

    @BeforeClass
    public static void setupClass() {
        ContainerFactory containerFactory = ContainerFactory.instance(true);

        userRepository = containerFactory.getUserRepositoryMySQL();
    }

    @Before
    public void cleanUp() {
        userRepository.removeAll();
    }
}
