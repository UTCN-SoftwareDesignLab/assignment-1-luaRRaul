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

import static database.Constants.Roles.CUSTOMER;

public class EmployeeController extends Thread{
    private final SessionManager sessionManager;
    private final MainView mainView;
    private final EmployeeView employeeView;
    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final AccountService accountService;

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
                if(employeeView.isVisible()) {
                    employeeView.printMenu();
                    commandListener(employeeView.getOption());
                }
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void commandListener(int option){
            switch (option) {
                case 0:
                    Logout();
                    break;
                case 1:
                    clientOperationMenu(employeeView.getOption());
                    break;
                case 2:
                    clientAccountMenu(employeeView.getOption());
                    break;
        }
    }

    private void clientOperationMenu(int option){
        employeeView.printClientOpsMenu();
        switch (option) {
            case 0:
                break;
            case 1:
                addClient();
                break;
            case 2:
                viewClientInfo(selectUser());
                break;
            case 3:
                updateClientInfo(selectUser());
                break;
        }
    }

    private void clientAccountMenu(int option){
        employeeView.printClientAccountOpsMenu();
        switch (option) {
            case 0:
                break;
            case 1:
                createClientAccount(selectUser());
                break;
            case 2:
                viewClientAccounts(selectUser());
                break;
            case 3:
                updateClientAccountBalance(selectClientAccount(selectUser()), employeeView.getNewBalance());
                break;
            case 4:
                sendMoney(selectClientAccount(selectUser()), selectClientAccount(selectUser()), employeeView.getAmmount());
        }
    }

    private void viewClientInfo(User client) {
        if (this.sessionManager.isEmployee()) {
            employeeView.printClient(client);
        }
    }

    private void addClient() {
        if (this.sessionManager.isEmployee()) {
            String username = employeeView.getUsername();
            String password = employeeView.getPassword();
            Notification<Boolean> registerNotification;
            registerNotification = authenticationService.register(username, password, CUSTOMER);
            if (registerNotification.hasErrors()) {
                employeeView.printMessage(registerNotification.getFormattedErrors());
            } else {
                if (!registerNotification.getResult()) {
                    employeeView.printMessage("Something went wrong, please try again later.");
                } else {
                    employeeView.printMessage("You have successfully created a new customer account");
                }
            }
        }
    }

    private void updateClientInfo(User client) {

    }

    private void createClientAccount(User client) {
        if (this.sessionManager.isEmployee()) {
            String iban = employeeView.getIban();
            String currency = employeeView.getCurrency();
            Notification<Boolean> registerNotification;
            registerNotification = accountService.createAccount(iban, currency, client);
            if (registerNotification.hasErrors()) {
                employeeView.printMessage(registerNotification.getFormattedErrors());
            } else {
                if (!registerNotification.getResult()) {
                    employeeView.printMessage("Something went wrong, please try again later.");
                } else {
                    employeeView.printMessage("You have successfully created the account");
                }
            }
        }
    }

    private void viewClientAccounts(User client) {
        if (this.sessionManager.isEmployee()) {
            employeeView.printClientAccounts(accountService.findAccountsForUser(client));
        }
    }

    private Account selectClientAccount(User client){
        if (this.sessionManager.isEmployee()) {
            return employeeView.getSelectedAccount(accountService.findAccountsForUser(client));
        }
        return null;
    }

    private void updateClientAccountBalance(Account account, String balance) {
        if (sessionManager.isEmployee()) {
            accountService.updateBalance(account, balance);
        }
    }

    private void sendMoney(Account sender, Account receiver, String ammount){
        if (sessionManager.isEmployee()) {
            accountService.sendMoney(sender, receiver, ammount);
        }
    }

    private void deleteClientAccount() {

    }

    private void transferMoney() {
        if (sessionManager.isEmployee()) {

        }
    }

    private User selectUser() {
        if (this.sessionManager.isEmployee()) {
            return employeeView.getSelectedUser(userService.findAllEmployees());
        }
        return null;
    }

    private void Logout() {
        this.sessionManager.setUser(null);
        this.employeeView.setVisible(false);
        this.mainView.setVisible(true);
    }
}
