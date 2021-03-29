package controller;

import dto.UserDTO;
import model.Role;
import model.SessionManager;
import model.User;
import model.validation.Notification;
import service.rightsRoles.RightsRolesService;
import service.user.AuthenticationService;
import view.AdminView;
import view.EmployeeView;
import view.MainView;

import java.util.List;

import static database.Constants.Roles.ADMINISTRATOR;
import static database.Constants.Roles.EMPLOYEE;

public class MainController extends Thread{
    private final SessionManager sessionManager;
    private final MainView mainView;
    private final AdminView adminView;
    private final EmployeeView employeeView;
    private final AuthenticationService authenticationService;
    private final RightsRolesService rightsRolesService;

    private boolean running = true;

    public MainController(SessionManager sessionManager, MainView mainView, AdminView adminView, EmployeeView employeeView, AuthenticationService authenticationService, RightsRolesService rightsRolesService) {
        this.sessionManager = sessionManager;
        this.mainView = mainView;
        this.adminView = adminView;
        this.employeeView = employeeView;
        this.authenticationService = authenticationService;
        this.rightsRolesService = rightsRolesService;
        mainView.setVisible(true);
    }

    @Override
    public void run() {
        while (this.running) {
            try {
                if(mainView.isVisible()){
                    mainView.printMenu();
                    commandListener(mainView.getOption());
                }
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void commandListener(int option){
            switch (option) {
                case 1:
                    loginButtonListener(mainView.getUserDTO());
                    break;
                case 2:
                    registerButtonListener(mainView.getUserDTO(), true);
                    break;
                case 3:
                    registerButtonListener(mainView.getUserDTO(), false);
                    break;
                case 0:
                    this.running = false;
                    break;
        }
    }

    private void loginButtonListener(UserDTO userDTO) {
        Notification<User> loginNotification = authenticationService.login(userDTO);
        if (this.sessionManager.getUser() == null) {
            if (loginNotification.hasErrors()) {
                mainView.printMessage(loginNotification.getFormattedErrors());
            } else {
               mainView.printMessage("Login successful!");
                User user = loginNotification.getResult();
                this.sessionManager.setUser(user);
                mainView.setVisible(false);
                if(this.sessionManager.isAdmin()){
                    this.adminView.setVisible(true);
                }else{
                    this.employeeView.setVisible(true);
                }
            }
        } else {
            mainView.printMessage("I don't think this can happen but ok!");
        }
    }

    private void registerButtonListener(UserDTO userDTO, boolean registerAdmin) {
        Notification<Boolean> registerNotification;
        if (registerAdmin) {
            userDTO.setRoles((List<Role>) rightsRolesService.getRoleByTitle(ADMINISTRATOR));
        } else {
            userDTO.setRoles((List<Role>) rightsRolesService.getRoleByTitle(EMPLOYEE));
        }
        registerNotification = authenticationService.register(userDTO);
        if (registerNotification.hasErrors()) {
            mainView.printMessage(registerNotification.getFormattedErrors());
        } else {
            if (!registerNotification.getResult()) {
                mainView.printMessage("Registration not successful, please try again later.");
            } else {
                mainView.printMessage("Registration successful!");
            }
        }
    }

}
