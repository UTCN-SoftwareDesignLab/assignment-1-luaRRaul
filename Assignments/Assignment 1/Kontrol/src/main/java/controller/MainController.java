package controller;

import model.Account;
import model.SessionManager;
import model.User;
import model.validation.Notification;
import service.account.AccountService;
import service.user.AuthenticationService;
import service.user.UserService;
import view.AdminView;
import view.EmployeeView;
import view.MainView;
import launcher.ContainerFactory;

import javax.swing.*;
import javax.swing.text.View;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Scanner;

import static database.Constants.Roles.*;

public class MainController {
    private final MainView mainView;
    private final AdminView adminView;
    private final EmployeeView employeeView;
    private final SessionManager sessionManager;

    private Scanner sc = new Scanner(System.in);
    private boolean show = true;

    private int option;
    private int display_menu = 0;

    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final AccountService accountService;

    public MainController(MainView mainView, AdminView adminView, EmployeeView employeeView, AuthenticationService authenticationService, UserService userService,AccountService accountService, SessionManager sessionManager) {
        this.sessionManager = sessionManager;
        this.mainView = mainView;
        this.adminView = adminView;
        this.employeeView = employeeView;
        this.authenticationService = authenticationService;
        this.userService = userService;
        this.accountService = accountService;
        while (this.show) {
            if (this.display_menu == 0) {
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
                        this.show = false;
                        break;
                }
            } else {
                if (this.display_menu == 1) {
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
                } else {
                    if (this.display_menu == 2) {
                        employeeView.print_menu();
                        this.option = sc.nextInt();
                        User client = new User();
                        switch (this.option) {
                            case 0:
                                Logout();
                                break;
                            case 1:
                                employeeView.print_client_ops_menu();
                                this.option = sc.nextInt();
                                switch (this.option) {
                                    case 0:
                                        break;
                                    case 1:
                                        addClient();
                                        break;
                                    case 2:
                                        client = selectUser();
                                        viewClientInfo(client);
                                        break;
                                    case 3:
                                        client = selectUser();
                                        updateClientInfo(client);
                                        break;
                                }
                                break;
                            case 2:
                                employeeView.print_client_account_ops_menu();
                                this.option = sc.nextInt();
                                switch (this.option){
                                    case 0:
                                        break;
                                    case 1:
                                        client = selectUser();
                                        createClientAccount(client);
                                        break;
                                    case 2:
                                        client = selectUser();
                                        viewClientAccounts(client);
                                        break;
                                    case 3:
                                        client = selectUser();
                                        Account account = selectClientAccount(client);
                                        System.out.print("New balance: ");
                                        int newBalance = sc.nextInt();
                                        updateClientAccountBalance(account, String.valueOf(newBalance));
                                        break;
                                    case 4:
                                        System.out.println("Please select the sender account:");
                                        client = selectUser();
                                        Account senderAcc = selectClientAccount(client);
                                        System.out.println("Please select the receiver account:");
                                        User receiverClient = selectUser();
                                        Account receiverAcc = selectClientAccount(client);
                                        System.out.print("Select the ammount:");
                                        int ammount = sc.nextInt();
                                        sendMoney(senderAcc,receiverAcc,String.valueOf(ammount));
                                }
                                break;
                        }
                    }
                }
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
                if (user.getRoles().size() == 1) {
                    if (user.getRoles().get(0).getRole().equals(ADMINISTRATOR)) {
                        System.out.println(user.getRoles().get(0).getRole());
                        this.display_menu = 1;
                    } else {
                        this.display_menu = 2;
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(mainView.getContentPane(), "I don't think this can happen but ok!");
        }
    }

    private void Logout() {
        this.display_menu = 0;
        this.sessionManager.setUser(null);
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

    //METHODS USED FOR ADMIN (MAKE ADMIN CONTROLLER)
    private void RegisterEmployee() {
        if (this.sessionManager.getUser().getRoles().get(0).getRole().equals(ADMINISTRATOR)) {
            String username = adminView.getUsername();
            String password = adminView.getPassword();
            Notification<Boolean> registerNotification;
            registerNotification = authenticationService.register(username, password, EMPLOYEE);

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

    private void ViewEmployeeInfo(User employee) {
        //System.out.println(this.sessionManager.getUser().getRoles().get(0).getRole());
        if (this.sessionManager.getUser().getRoles().get(0).getRole().equals(ADMINISTRATOR)) {
            System.out.println(employee.toString());
        }
    }

    private void UpdateEmployeeInfo(User employee){
        //System.out.println(this.sessionManager.getUser().getRoles().get(0).getRole());
        if (this.sessionManager.getUser().getRoles().get(0).getRole().equals(ADMINISTRATOR)) {
        }

    }

    //METHODS FOR EMPLOYEE
    private void addClient() {
        if (this.sessionManager.getUser().getRoles().get(0).getRole().equals(EMPLOYEE)) {
            String username = employeeView.getUsername();
            String password = employeeView.getPassword();
            Notification<Boolean> registerNotification;
            registerNotification = authenticationService.register(username, password, CUSTOMER);

            if (registerNotification.hasErrors()) {
                JOptionPane.showMessageDialog(mainView.getContentPane(), registerNotification.getFormattedErrors());
            } else {
                if (!registerNotification.getResult()) {
                    JOptionPane.showMessageDialog(mainView.getContentPane(), "Something went wrong, please try again later.");
                } else {
                    JOptionPane.showMessageDialog(mainView.getContentPane(), "You have successfully created a new customer account");
                }
            }

        }

    }

    private void viewClientInfo(User client) {
        //System.out.println(this.sessionManager.getUser().getRoles().get(0).getRole());
        if (this.sessionManager.getUser().getRoles().get(0).getRole().equals(EMPLOYEE)) {
            System.out.println(client.toString());
        }

    }

    private void updateClientInfo(User client) {

    }

    private void createClientAccount(User client) {
        if (this.sessionManager.getUser().getRoles().get(0).getRole().equals(EMPLOYEE)) {
            String iban = employeeView.getIban();
            String currency = employeeView.getCurrency();
            Notification<Boolean> registerNotification;
            registerNotification = accountService.createAccount(iban, currency, client);

            if (registerNotification.hasErrors()) {
                JOptionPane.showMessageDialog(mainView.getContentPane(), registerNotification.getFormattedErrors());
            } else {
                if (!registerNotification.getResult()) {
                    JOptionPane.showMessageDialog(mainView.getContentPane(), "Something went wrong, please try again later.");
                } else {
                    JOptionPane.showMessageDialog(mainView.getContentPane(), "You have successfully created the account");
                }
            }

        }

    }

    private void viewClientAccounts(User client) {
        if (this.sessionManager.getUser().getRoles().get(0).getRole().equals(EMPLOYEE)) {
           List<Account> accounts = accountService.findAccountsForUser(client);
           for (Account account : accounts){
               System.out.print("(" + accounts.indexOf(account) + ") ");
               System.out.println(account.toString());
           }
        }

    }

    private Account selectClientAccount(User client){
        if (this.sessionManager.getUser().getRoles().get(0).getRole().equals(EMPLOYEE)) {
            int selection=-1;
            List<Account> accounts = accountService.findAccountsForUser(client);
            for (Account account : accounts){
                System.out.print("(" + accounts.indexOf(account) + ") ");
                System.out.println(account.toString());
            }
            System.out.print("Select account:");
            selection=sc.nextInt();
            if (selection>=0 & selection<accounts.size())
                return accounts.get(selection);
        }
       return null;
    }

    private void updateClientAccountBalance(Account account, String balance) {
        if (this.sessionManager.getUser().getRoles().get(0).getRole().equals(EMPLOYEE)) {
            accountService.updateBalance(account, balance);
        }

    }

    private void sendMoney(Account sender, Account receiver, String ammount){
        if (this.sessionManager.getUser().getRoles().get(0).getRole().equals(EMPLOYEE)) {
            accountService.sendMoney(sender, receiver, ammount);
        }

    }

    private void deleteClientAccount() {

    }

    private void transferMoney() {
        if (this.sessionManager.getUser().getRoles().get(0).getRole().equals(EMPLOYEE)) {

        }

    }

    private User selectUser() {
        int selection=-1;
        //System.out.println(this.sessionManager.getUser().getRoles().get(0).getRole());
        if (this.sessionManager.getUser().getRoles().get(0).getRole().equals(EMPLOYEE)) {
            List<User> customers = userService.findAllCustomers();
            System.out.println("Customer list:");
            for (User u : customers) {
                System.out.print("(" + customers.indexOf(u) + ") ");
                System.out.println(u.getUsername());
            }
            System.out.print("Select client:");
            selection=sc.nextInt();
            if (selection>=0 & selection<customers.size())
                return customers.get(selection);
        } else {
            if (this.sessionManager.getUser().getRoles().get(0).getRole().equals(ADMINISTRATOR)) {
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
        }
        return null;
    }

}
