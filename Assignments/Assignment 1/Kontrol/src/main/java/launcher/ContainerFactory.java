package launcher;

import controller.MainController;
import database.DBConnectionFactory;
import model.SessionManager;
import repository.account.AccountRepository;
import repository.account.AccountRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.account.AccountService;
import service.account.AccountServiceImpl;
import service.user.AuthenticationService;
import service.user.AuthenticationServiceMySQL;
import service.user.UserService;
import service.user.UserServiceImpl;
import view.AdminView;
import view.EmployeeView;
import view.MainView;

import java.sql.Connection;

public class ContainerFactory {

    private final MainView mainView;
    private final AdminView adminView;
    private final EmployeeView employeeView;

    private final MainController mainController;

    private final UserRepository userRepository;

    private final RightsRolesRepository rightsRolesRepository;
    private final AccountRepository accountRepository;

    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final AccountService accountService;

    private static ContainerFactory instance;
    private final SessionManager sessionManager;

    public static ContainerFactory instance(boolean componentsForTest) {
        if(instance == null) {
            instance = new ContainerFactory(componentsForTest);
        }
       return instance; 
    }

    public ContainerFactory(Boolean componentsForTest) {
        Connection connection = new DBConnectionFactory().getConnectionWrapper(componentsForTest).getConnection();
        this.rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        this.userRepository= new UserRepositoryMySQL(connection, this.rightsRolesRepository);
        this.accountRepository = new AccountRepositoryMySQL(connection);
        this.authenticationService = new AuthenticationServiceMySQL(this.userRepository, this.rightsRolesRepository);
        this.userService = new UserServiceImpl(this.userRepository, this.rightsRolesRepository);
        this.mainView = new MainView();
        this.adminView = new AdminView();
        this.employeeView = new EmployeeView();
        this.sessionManager =  new SessionManager();
        this.accountService = new AccountServiceImpl(this.accountRepository, this.userRepository);
        this.mainController = new MainController(this.mainView, this.adminView, this.employeeView, this.authenticationService, this.userService, this.accountService, this.sessionManager);

    }

    public MainView getMainView() {
        return this.mainView;
    }

    public MainController getMainController() {
        return this.mainController;
    }

    public AdminView getAdminView() {
        return adminView;
    }

    public EmployeeView getEmployeeView() {
        return employeeView;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public RightsRolesRepository getRightsRolesRepository() {
        return rightsRolesRepository;
    }

    public AccountRepository getAccountRepository() {
        return accountRepository;
    }

    public AuthenticationService getAuthenticationService() {
        return authenticationService;
    }

    public UserService getUserService() {
        return userService;
    }

    public AccountService getAccountService() {
        return accountService;
    }

    public static ContainerFactory getInstance() {
        return instance;
    }

    public SessionManager getSessionManager() {
        return sessionManager;
    }

    public UserRepository getUserRepositoryMySQL() {
        return this.userRepository;
    }
}
