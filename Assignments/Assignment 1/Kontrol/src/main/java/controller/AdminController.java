package controller;

import model.SessionManager;
import model.User;
import model.validation.Notification;
import service.user.AuthenticationService;
import service.user.UserService;
import view.AdminView;
import view.MainView;

import javax.swing.*;
import java.util.List;
import java.util.Scanner;

import static database.Constants.Roles.EMPLOYEE;

public class AdminController extends Thread{
    private final SessionManager sessionManager;
    private final MainView mainView;
    private final AdminView adminView;
    private final AuthenticationService authenticationService;
    private final UserService userService;

    private Scanner sc = new Scanner(System.in);

    private int option;

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
                CommandListener();
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void CommandListener(){
        if(this.adminView.isVisible()) {
            System.out.println("HELOO ADMIN");
            adminView.print_menu();
            User employee = new User();
            this.option = sc.nextInt();
            switch (this.option) {
                case 0:
                    Logout();
                    break;
                case 1:
                    RegisterEmployee();
                    break;
                case 2:
                    employee = selectUser();
                    ViewEmployeeInfo(employee);
                    break;
                case 3:
                    employee = selectUser();
                    UpdateEmployeeInfo(employee);
                    break;
            }
        }
    }

    private void RegisterEmployee() {
        if (this.sessionManager.isAdmin()) {
            //System.out.println(this.sessionManager.getUser().getRoles().get(0).getRole());
            String username = adminView.getUsername();
            String password = adminView.getPassword();
            Notification<Boolean> registerNotification;
            registerNotification = authenticationService.register(username, password, EMPLOYEE);

            if (registerNotification.hasErrors()) {
                JOptionPane.showMessageDialog(this.adminView.getContentPane(), registerNotification.getFormattedErrors());
            } else {
                if (!registerNotification.getResult()) {
                    JOptionPane.showMessageDialog(this.adminView.getContentPane(), "Registration not successful, please try again later.");
                } else {
                    JOptionPane.showMessageDialog(this.adminView.getContentPane(), "Registration successful!");
                }
            }

        }
    }

    private void ViewEmployeeInfo(User employee) {
        //System.out.println(this.sessionManager.getUser().getRoles().get(0).getRole());
        if (this.sessionManager.isAdmin()) {
            System.out.println(employee.toString());
        }
    }

    private void UpdateEmployeeInfo(User employee){
        //System.out.println(this.sessionManager.getUser().getRoles().get(0).getRole());
        if (this.sessionManager.isAdmin()) {
        }

    }

    private void Logout() {
        this.sessionManager.setUser(null);
        this.adminView.setVisible(false);
        this.mainView.setVisible(true);
    }

    private User selectUser() {
        int selection=-1;
        //System.out.println(this.sessionManager.getUser().getRoles().get(0).getRole());
        if(this.sessionManager.isAdmin()) {
                List<User> employees = userService.findAllEmployees();
                System.out.println("Employee list:");
                for (User u : employees) {
                    System.out.print("(" + employees.indexOf(u) + ") ");
                    System.out.println(u.getUsername());
                }
                System.out.print("Select employee:");
                selection = sc.nextInt();
                if (selection>=0 & selection<employees.size())
                    return employees.get(selection);
        }
        return null;
    }

}
