package controller;

import model.SessionManager;
import model.User;
import model.validation.Notification;
import service.user.AuthenticationService;
import view.AdminView;
import view.EmployeeView;
import view.MainView;

import static database.Constants.Roles.ADMINISTRATOR;
import static database.Constants.Roles.EMPLOYEE;

public class MainController extends Thread{
    private final SessionManager sessionManager;
    private final MainView mainView;
    private final AdminView adminView;
    private final EmployeeView employeeView;
    private final AuthenticationService authenticationService;

    private boolean running = true;

    public MainController(SessionManager sessionManager, MainView mainView, AdminView adminView, EmployeeView employeeView, AuthenticationService authenticationService) {
        this.sessionManager = sessionManager;
        this.mainView = mainView;
        this.adminView = adminView;
        this.employeeView = employeeView;
        this.authenticationService = authenticationService;
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
                    loginButtonListener();
                    break;
                case 2:
                    registerButtonListener(true);
                    break;
                case 3:
                    registerButtonListener(false);
                    break;
                case 0:
                    this.running = false;
                    break;
        }
    }

    private void loginButtonListener() {
        String username = mainView.getUsername();
        String password = mainView.getPassword();
        Notification<User> loginNotification = authenticationService.login(username, password);
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

    private void registerButtonListener(boolean registerAdmin) {
        String username = mainView.getUsername();
        String password = mainView.getPassword();
        Notification<Boolean> registerNotification;
        if (registerAdmin) {
            registerNotification = authenticationService.register(username, password, ADMINISTRATOR);
        } else {
            registerNotification = authenticationService.register(username, password, EMPLOYEE);
        }
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
