package controller;

import model.Account;
import model.SessionManager;
import model.User;
import model.validation.Notification;
import service.account.AccountService;
import service.user.AuthenticationService;
import service.user.UserService;
import view.EmployeeView;
import view.MainView;

import javax.swing.*;
import java.util.List;
import java.util.Scanner;

import static database.Constants.Roles.CUSTOMER;
import static database.Constants.Roles.EMPLOYEE;

public class EmployeeController extends Thread{
    private final SessionManager sessionManager;
    private final MainView mainView;
    private final EmployeeView employeeView;
    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final AccountService accountService;

    private Scanner sc = new Scanner(System.in);

    private int option;

    public EmployeeController(SessionManager sessionManager, MainView mainView, EmployeeView employeeView, AuthenticationService authenticationService, UserService userService, AccountService accountService) {
        this.sessionManager = sessionManager;
        this.mainView = mainView;
        this.employeeView = employeeView;
        this.userService = userService;
        this.authenticationService = authenticationService;
        this.accountService = accountService;
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
        if(employeeView.isVisible()) {
            employeeView.print_menu();
            this.option = sc.nextInt();
            switch (this.option) {
                case 0:
                    Logout();
                    break;
                case 1:
                    ClientOperationMenu();
                    break;
                case 2:
                    ClientAccountMenu();
                    break;
            }
        }
    }

    private void ClientOperationMenu(){
        User client = new User();
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
    }

    private void ClientAccountMenu(){
        User client = new User();
        employeeView.print_client_account_ops_menu();
        this.option = sc.nextInt();
        switch (this.option) {
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
                Account receiverAcc = selectClientAccount(receiverClient);
                System.out.print("Select the ammount:");
                int ammount = sc.nextInt();
                sendMoney(senderAcc, receiverAcc, String.valueOf(ammount));
        }
    }

    private void addClient() {
        if (this.sessionManager.getUser().getRoles().get(0).getRole().equals(EMPLOYEE)) {
            String username = employeeView.getUsername();
            String password = employeeView.getPassword();
            Notification<Boolean> registerNotification;
            registerNotification = authenticationService.register(username, password, CUSTOMER);

            if (registerNotification.hasErrors()) {
                JOptionPane.showMessageDialog(this.employeeView.getContentPane(), registerNotification.getFormattedErrors());
            } else {
                if (!registerNotification.getResult()) {
                    JOptionPane.showMessageDialog(this.employeeView.getContentPane(), "Something went wrong, please try again later.");
                } else {
                    JOptionPane.showMessageDialog(this.employeeView.getContentPane(), "You have successfully created a new customer account");
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
        if (this.sessionManager.isEmployee()) {
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
        }
        return null;
    }

    private void Logout() {
        this.sessionManager.setUser(null);
        this.employeeView.setVisible(false);
        this.mainView.setVisible(true);
    }
}
