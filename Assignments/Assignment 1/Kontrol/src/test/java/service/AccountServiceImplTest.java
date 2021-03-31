package service;

import dto.UserDTO;
import dto.builder.AccountDTOBuilder;
import dto.builder.UserDTOBuilder;
import launcher.ContainerFactory;
import model.User;
import model.builder.UserBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.account.AccountRepository;
import repository.security.RightsRolesRepository;
import repository.user.UserRepository;
import service.account.AccountService;
import service.rightsRoles.RightsRolesService;
import service.user.AuthenticationService;

import java.util.Collections;

import static database.Constants.Roles.*;

public class AccountServiceImplTest {
    private static AccountService accountService;
    private static AccountRepository accountRepository;
    private static UserRepository userRepository;
    private static RightsRolesService rightsRolesService;

    private final String USERNAME = "aaa@aaa.com";
    private final String PASS = "aaaaaa@6";

    @BeforeClass
    public static void setUp() {
        ContainerFactory containerFactory = ContainerFactory.instance(true);
        accountService = containerFactory.getAccountService();
        accountRepository = containerFactory.getAccountRepository();
        userRepository = containerFactory.getUserRepository();
        rightsRolesService = containerFactory.getRightsRolesService();
    }

    @Before
    public void cleanUp() {
        accountRepository.removeAll();
        userRepository.removeAll();
        userRepository.save(new UserBuilder().setUsername(USERNAME).setPassword(PASS).setRoles(Collections.singletonList(rightsRolesService.getRoleByTitle(CUSTOMER))).build());
    }

    @Test
    public void createAccount(){
        User user = userRepository.findByUsernameAndPassword(USERNAME,PASS).getResult();
        Assert.assertTrue(accountService.createAccount(new AccountDTOBuilder().setCurrency("RON").setIban("RO001111222233334444").setUserId(user.getId()).build()).getResult());
    }

    @Test
    public void findAccountByUser(){
        User user = userRepository.findByUsernameAndPassword(USERNAME,PASS).getResult();
        accountService.createAccount(new AccountDTOBuilder().setCurrency("RON").setIban("RO001111222233334444").setUserId(user.getId()).build());
        Assert.assertEquals(accountService.findAccountsForUser(new UserDTOBuilder().setUsername(USERNAME).setId(user.getId()).setPassword(PASS).setRoles(Collections.singletonList(rightsRolesService.getRoleByTitle(CUSTOMER))).build()).size(), 1);
    }

    @Test
    public void updateBalance(){
    }

    @Test
    public void deleteAccount(){
    }

}
