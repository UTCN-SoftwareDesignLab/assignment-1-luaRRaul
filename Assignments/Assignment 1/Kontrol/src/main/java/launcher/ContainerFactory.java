package launcher;

import controller.AdminController;
import controller.EmployeeController;
import controller.MainController;
import database.DBConnectionFactory;
import model.SessionManager;
import repository.account.AccountRepository;
import repository.account.AccountRepositoryMySQL;
import repository.activity.ActivityRepository;
import repository.activity.ActivityRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.account.AccountService;
import service.account.AccountServiceImpl;
import service.activity.ActivityService;
import service.activity.ActivityServiceImpl;
import service.rightsRoles.RightsRolesService;
import service.rightsRoles.RightsRolesServiceImpl;
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
    private final AdminController adminController;
    private final EmployeeController employeeController;

    private final UserRepository userRepository;

    private final RightsRolesRepository rightsRolesRepository;
    private final AccountRepository accountRepository;
    private final ActivityRepository activityRepository;

    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final AccountService accountService;
    private final RightsRolesService rightsRolesService;
    private final ActivityService activityService;

    private static ContainerFactory instance;
    private final SessionManager sessionManager;

    public static ContainerFactory instance(boolean componentsForTest) {
        if(instance == null) {
            instance = new ContainerFactory(componentsForTest);
        }
       return instance; 
    }

    public ActivityRepository getActivityRepository() {
        return activityRepository;
    }

    public ActivityService getActivityService() {
        return activityService;
    }

    public ContainerFactory(Boolean componentsForTest) {
        Connection connection = new DBConnectionFactory().getConnectionWrapper(componentsForTest).getConnection();
        this.rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        this.userRepository= new UserRepositoryMySQL(connection, this.rightsRolesRepository);
        this.accountRepository = new AccountRepositoryMySQL(connection);
        this.activityRepository = new ActivityRepositoryMySQL(connection);
        this.authenticationService = new AuthenticationServiceMySQL(this.userRepository);
        this.userService = new UserServiceImpl(this.userRepository);
        this.rightsRolesService = new RightsRolesServiceImpl(this.rightsRolesRepository);
        this.activityService = new ActivityServiceImpl(this.activityRepository, rightsRolesRepository, userRepository);
        this.mainView = new MainView();
        this.adminView = new AdminView();
        this.employeeView = new EmployeeView();
        this.sessionManager =  new SessionManager();
        this.accountService = new AccountServiceImpl(this.accountRepository);
        this.mainController = new MainController(this.sessionManager, this.mainView, this.adminView, this.employeeView, this.authenticationService, rightsRolesService);
        mainController.start();
        this.adminController = new AdminController(this.sessionManager, this.mainView, this.adminView, this.authenticationService, this.userService, rightsRolesService, activityService);
        adminController.start();
        this.employeeController = new EmployeeController(this.sessionManager, this.mainView, this.employeeView, this.authenticationService, this.userService, this.accountService, rightsRolesService, activityService);
        employeeController.start();

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

    public RightsRolesService getRightsRolesService() {
        return rightsRolesService;
    }

    public UserRepository getUserRepositoryMySQL() {
        return this.userRepository;
    }
}
