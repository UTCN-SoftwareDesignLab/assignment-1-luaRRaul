package controller;

import dto.UserDTO;
import dto.builder.UserDTOBuilder;
import model.SessionManager;
import model.User;
import model.validation.Notification;
import service.rightsRoles.RightsRolesService;
import service.user.AuthenticationService;
import service.user.UserService;
import view.AdminView;
import view.MainView;

import java.util.Collections;

import static database.Constants.Roles.EMPLOYEE;

public class AdminController extends Thread{
    private final SessionManager sessionManager;
    private final MainView mainView;
    private final AdminView adminView;
    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final RightsRolesService rightsRolesService;

    public AdminController(SessionManager sessionManager, MainView mainView, AdminView adminView, AuthenticationService authenticationService, UserService userService, RightsRolesService rightsRolesService) {
        this.sessionManager = sessionManager;
        this.mainView = mainView;
        this.adminView = adminView;
        this.authenticationService = authenticationService;
        this.userService = userService;
        this.rightsRolesService = rightsRolesService;
    }
    @Override
    public void run() {
        while (true) {
            try {
                if(this.adminView.isVisible()) {
                    adminView.printMenu();
                    CommandListener(adminView.getOption());
                }
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void CommandListener(int option){
            switch (option) {
                case 0:
                    Logout();
                    break;
                case 1:
                    registerEmployee(adminView.getUserDTO());
                    break;
                case 2:
                    viewEmployeeInfo();
                    break;
                case 3:
                    updateEmployeeInfo(selectUser());
                    break;
        }
    }

    private void registerEmployee(UserDTO userDTO) {
        if (this.sessionManager.isAdmin()) {
            userDTO.setRoles(Collections.singletonList(rightsRolesService.getRoleByTitle(EMPLOYEE)));
            Notification<Boolean> registerNotification;
            registerNotification = authenticationService.register(userDTO);
            if (registerNotification.hasErrors()) {
                adminView.printMessage(registerNotification.getFormattedErrors());
            } else {
                if (!registerNotification.getResult()) {
                    adminView.printMessage("Registration not successful, please try again later.");
                } else {
                    adminView.printMessage("Registration successful!");
                }
            }
        }
    }

    private void viewEmployeeInfo() {
        if (this.sessionManager.isAdmin()) {
            adminView.printEmployee(new UserDTOBuilder().setUsername(this.sessionManager.getUser().getUsername()).setRoles(this.sessionManager.getUser().getRoles()).build());
        }
    }

    private void updateEmployeeInfo(UserDTO employee){
        if (this.sessionManager.isAdmin()) {
        }
    }

    private UserDTO selectUser() {
        if(this.sessionManager.isAdmin()) {
            return adminView.getSelectedUser(userService.findAllEmployees());
        }
        return null;
    }

    private void Logout() {
        this.sessionManager.setUser(null);
        this.adminView.setVisible(false);
        this.mainView.setVisible(true);
    }

}
