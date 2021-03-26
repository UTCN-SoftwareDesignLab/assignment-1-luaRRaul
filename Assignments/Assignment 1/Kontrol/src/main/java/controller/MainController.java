package controller;

import model.SessionManager;
import model.User;
import model.validation.Notification;
import service.user.AuthenticationService;
import view.AdminView;
import view.EmployeeView;
import view.MainView;

import javax.swing.*;
import java.util.Scanner;

import static database.Constants.Roles.ADMINISTRATOR;
import static database.Constants.Roles.EMPLOYEE;

public class MainController extends Thread{
    private final SessionManager sessionManager;
    private final MainView mainView;
    private final AdminView adminView;
    private final EmployeeView employeeView;
    private final AuthenticationService authenticationService;

    private Scanner sc = new Scanner(System.in);
    private boolean running = true;

    private int option;


    public MainController(SessionManager sessionManager, MainView mainView, AdminView adminView, EmployeeView employeeView, AuthenticationService authenticationService) {
        this.sessionManager = sessionManager;
        this.mainView = mainView;
        this.adminView = adminView;
        this.employeeView = employeeView;
        this.authenticationService = authenticationService;
    }
    @Override
    public void run() {
        while (this.running) {
            try {
                CommandListener();
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void CommandListener(){
        if(mainView.isVisible()){
            mainView.print_menu();
            this.option = sc.nextInt();
            switch (this.option) {
                case 1:
                    LoginButtonListener();
                    break;
                case 2:
                    RegisterButtonListener();
                    break;
                case 3:
                    RegisterButtonListener();
                    break;
                case 0:
                    this.running = false;
                    break;
            }
        }
    }


    private void LoginButtonListener() {
        String username = mainView.getUsername();
        String password = mainView.getPassword();
        Notification<User> loginNotification = authenticationService.login(username, password);
        if (this.sessionManager.getUser() == null) {
            if (loginNotification.hasErrors()) {
                JOptionPane.showMessageDialog(mainView.getContentPane(), loginNotification.getFormattedErrors());
            } else {
                JOptionPane.showMessageDialog(mainView.getContentPane(), "Login successful!");
                User user = loginNotification.getResult();
                this.sessionManager.setUser(user);
                mainView.setVisible(false);
                if(this.sessionManager.isAdmin()){
                    System.out.println("I AM AN ADMIN");
                    this.adminView.setVisible(true);
                }else{
                    this.employeeView.setVisible(true);
                }
            }
        } else {
            JOptionPane.showMessageDialog(mainView.getContentPane(), "I don't think this can happen but ok!");
        }
    }


    private void RegisterButtonListener() {
        String username = mainView.getUsername();
        String password = mainView.getPassword();
        Notification<Boolean> registerNotification;
        if (this.option == 2) {
            registerNotification = authenticationService.register(username, password, ADMINISTRATOR);
        } else {
            registerNotification = authenticationService.register(username, password, EMPLOYEE);
        }

        if (registerNotification.hasErrors()) {
            JOptionPane.showMessageDialog(mainView.getContentPane(), registerNotification.getFormattedErrors());
        } else {
            if (!registerNotification.getResult()) {
                JOptionPane.showMessageDialog(mainView.getContentPane(), "Registration not successful, please try again later.");
            } else {
                JOptionPane.showMessageDialog(mainView.getContentPane(), "Registration successful!");
            }
        }
    }

}
