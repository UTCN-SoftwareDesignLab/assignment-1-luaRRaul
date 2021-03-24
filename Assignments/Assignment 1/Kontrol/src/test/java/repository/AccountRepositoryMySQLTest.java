package repository;

import launcher.ContainerFactory;
import org.junit.Before;
import org.junit.BeforeClass;
import repository.account.AccountRepository;
import repository.account.AccountRepositoryMySQL;

public class AccountRepositoryMySQLTest {
    private static AccountRepository accountRepository;

    @BeforeClass
    public static void setupClass() {
        ContainerFactory containerFactory = ContainerFactory.instance(true);

        accountRepository = containerFactory.getAccountRepository();
    }

    @Before
    public void cleanUp() {
        accountRepository.removeAll();
    }

}

