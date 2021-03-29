package controller;

import dto.UserDTO;
import dto.builder.UserDTOBuilder;
import model.Account;
import model.SessionManager;
import model.User;
import model.validation.Notification;
import service.account.AccountService;
import service.rightsRoles.RightsRolesService;
import service.user.AuthenticationService;
import service.user.UserService;
import view.EmployeeView;
import view.MainView;

import java.util.Collections;

import static database.Constants.Roles.CUSTOMER;

public class EmployeeController extends Thread{
    private final SessionManager sessionManager;
    private final MainView mainView;
    private final EmployeeView employeeView;
    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final AccountService accountService;
    private final RightsRolesService rightsRolesService;

    public EmployeeController(SessionManager sessionManager, MainView mainView, EmployeeView employeeView, AuthenticationService authenticationService, UserService userService, AccountService accountService, RightsRolesService rightsRolesService) {
        this.sessionManager = sessionManager;
        this.mainView = mainView;
        this.employeeView = employeeView;
        this.userService = userService;
        this.authenticationService = authenticationService;
        this.accountService = accountService;
        this.rightsRolesService = rightsRolesService;
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
                addClient(employeeView.getUserDTO());
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

    private void viewClientInfo(UserDTO client) {
        if (this.sessionManager.isEmployee()) {
            employeeView.printClient(new UserDTOBuilder().setUsername(client.getUsername()).setRoles(client.getRoles()).build());
        }
    }

    private void addClient(UserDTO userDTO) {
        if (this.sessionManager.isEmployee()) {
            userDTO.setRoles(Collections.singletonList(rightsRolesService.getRoleByTitle(CUSTOMER)));
            Notification<Boolean> registerNotification;
            registerNotification = authenticationService.register(userDTO);
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

    private void updateClientInfo(UserDTO client) {

    }

    private void createClientAccount(UserDTO client) {
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

    private void viewClientAccounts(UserDTO client) {
        if (this.sessionManager.isEmployee()) {
            employeeView.printClientAccounts(accountService.findAccountsForUser(client));
        }
    }

    private Account selectClientAccount(UserDTO client){
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

    private UserDTO selectUser() {
        if (this.sessionManager.isEmployee()) {
            return employeeView.getSelectedUser(userService.findAllCustomers());
        }
        return null;
    }

    private void Logout() {
        this.sessionManager.setUser(null);
        this.employeeView.setVisible(false);
        this.mainView.setVisible(true);
    }
}
