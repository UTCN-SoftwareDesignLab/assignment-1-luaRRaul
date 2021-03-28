package controller;

import model.SessionManager;
import model.User;
import model.validation.Notification;
import service.user.AuthenticationService;
import service.user.UserService;
import view.AdminView;
import view.MainView;

import static database.Constants.Roles.EMPLOYEE;

public class AdminController extends Thread{
    private final SessionManager sessionManager;
    private final MainView mainView;
    private final AdminView adminView;
    private final AuthenticationService authenticationService;
    private final UserService userService;

    public AdminController(SessionManager sessionManager, MainView mainView, AdminView adminView, AuthenticationService authenticationService, UserService userService) {
        this.sessionManager = sessionManager;
        this.mainView = mainView;
        this.adminView = adminView;
        this.authenticationService = authenticationService;
        this.userService = userService;
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
                    registerEmployee();
                    break;
                case 2:
                    viewEmployeeInfo();
                    break;
                case 3:
                    updateEmployeeInfo(selectUser());
                    break;
        }
    }

    private void registerEmployee() {
        if (this.sessionManager.isAdmin()) {
            String username = adminView.getUsername();
            String password = adminView.getPassword();
            Notification<Boolean> registerNotification;
            registerNotification = authenticationService.register(username, password, EMPLOYEE);
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
            adminView.printEmployee(selectUser());
        }
    }

    private void updateEmployeeInfo(User employee){
        if (this.sessionManager.isAdmin()) {
        }
    }

    private User selectUser() {
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
