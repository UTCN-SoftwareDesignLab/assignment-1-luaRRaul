package controller;

import dto.AccountDTO;
import dto.ActivityDTO;
import dto.UserDTO;
import dto.builder.ActivityDTOBuilder;
import dto.builder.UserDTOBuilder;
import model.SessionManager;
import model.validation.Notification;
import service.account.AccountService;
import service.activity.ActivityService;
import service.rightsRoles.RightsRolesService;
import service.user.AuthenticationService;
import service.user.UserService;
import view.EmployeeView;
import view.MainView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;

import static database.Constants.Rights.VIEW_EMPLOYEE;
import static database.Constants.Roles.CUSTOMER;

public class EmployeeController extends Thread{
    private final SessionManager sessionManager;
    private final MainView mainView;
    private final EmployeeView employeeView;
    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final AccountService accountService;
    private final RightsRolesService rightsRolesService;
    private final ActivityService activityService;

    public EmployeeController(SessionManager sessionManager, MainView mainView, EmployeeView employeeView, AuthenticationService authenticationService, UserService userService, AccountService accountService, RightsRolesService rightsRolesService, ActivityService activityService) {
        this.sessionManager = sessionManager;
        this.mainView = mainView;
        this.employeeView = employeeView;
        this.userService = userService;
        this.authenticationService = authenticationService;
        this.accountService = accountService;
        this.rightsRolesService = rightsRolesService;
        this.activityService = activityService;
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
                    employeeView.printClientOpsMenu();
                    clientOperationMenu(employeeView.getOption());
                    break;
                case 2:
                    employeeView.printClientAccountOpsMenu();
                    clientAccountMenu(employeeView.getOption());
                    break;
        }
    }

    private void clientOperationMenu(int option){
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
                updateClientUsername(selectUser(),employeeView.getUsername());
                break;
            case 4:
                deleteClient(selectUser());
                break;
        }
    }

    private void clientAccountMenu(int option){
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
                deleteClientAccount(selectClientAccount(selectUser()));
                break;
            case 5:
                transferMoney(selectClientAccount(selectUser()), selectClientAccount(selectUser()), employeeView.getAmount());
                break;
        }
    }

    private void viewClientInfo(UserDTO client) {
        if (this.sessionManager.isEmployee()) {
            Date utilDate = new Date(System.currentTimeMillis());
            activityService.save(new ActivityDTOBuilder().setUserId(this.sessionManager.getUser().getId()).setClientId(client.getId()).setDate(new java.sql.Date(utilDate.getTime())).setRightId(rightsRolesService.getRightByTitle(VIEW_EMPLOYEE).getId()).build());
            employeeView.printClient(new UserDTOBuilder().setUsername(client.getUsername()).setRoles(client.getRoles()).build());
        }
    }

    private void addClient(UserDTO client) {
        if (this.sessionManager.isEmployee()) {
            client.setRoles(Collections.singletonList(rightsRolesService.getRoleByTitle(CUSTOMER)));
            Notification<Boolean> registerNotification;
            registerNotification = authenticationService.register(client);
            if (registerNotification.hasErrors()) {
                employeeView.printMessage(registerNotification.getFormattedErrors());
            } else {
                if (!registerNotification.getResult()) {
                    employeeView.printMessage("Something went wrong, please try again later.");
                } else {
                    employeeView.printMessage("You have successfully created a new customer account.");
                }
            }
        }
    }

    private void updateClientUsername(UserDTO client, String newUsername) {
        if(sessionManager.isEmployee()){
            Notification<Boolean> updateClientUsernameNotif;
            updateClientUsernameNotif = userService.changeUserUsername(client, newUsername);
            if(updateClientUsernameNotif.hasErrors()){
                employeeView.printMessage(updateClientUsernameNotif.getFormattedErrors());
            }else{
                if(!updateClientUsernameNotif.getResult()){
                    employeeView.printMessage("Something went wrong, please try again later.");
                }else{
                    employeeView.printMessage("Client username was updated successfully.");
                }
            }
        }

    }

    private void createClientAccount(UserDTO client) {
        if (this.sessionManager.isEmployee()) {
            Notification<Boolean> registerNotification;
            AccountDTO accountDTO = employeeView.getAccountDTO();
            accountDTO.setUser_id(client.getId());
            registerNotification = accountService.createAccount(accountDTO);
            if (registerNotification.hasErrors()) {
                employeeView.printMessage(registerNotification.getFormattedErrors());
            } else {
                if (!registerNotification.getResult()) {
                    employeeView.printMessage("Something went wrong, please try again later.");
                } else {
                    employeeView.printMessage("You have successfully created the account.");
                }
            }
        }
    }

    private void deleteClient(UserDTO client) {
        if(this.sessionManager.isEmployee()) {
            Notification<Boolean> deleteClientNotification = userService.delete(client);
            if (deleteClientNotification.hasErrors()) {
                employeeView.printMessage(deleteClientNotification.getFormattedErrors());
            } else {
                if (!deleteClientNotification.getResult()) {
                    employeeView.printMessage("Something went wrong, please try again later.");
                } else {
                    employeeView.printMessage("Client deleted successfully!");
                }
            }
        }
    }

    private void viewClientAccounts(UserDTO client) {
        if (this.sessionManager.isEmployee()) {
            employeeView.printClientAccounts(accountService.findAccountsForUser(client));
        }
    }

    private AccountDTO selectClientAccount(UserDTO client){
        if (this.sessionManager.isEmployee()) {
            return employeeView.getSelectedAccount(accountService.findAccountsForUser(client));
        }
        return null;
    }

    private void updateClientAccountBalance(AccountDTO account, String balance) {
        if (sessionManager.isEmployee()) {
            Notification<Boolean> updateBalanceNotification = accountService.updateBalance(account, balance);
            if (updateBalanceNotification.hasErrors()) {
                employeeView.printMessage(updateBalanceNotification.getFormattedErrors());
            } else {
                if (!updateBalanceNotification.getResult()) {
                    employeeView.printMessage("Something went wrong, please try again later.");
                } else {
                    employeeView.printMessage("Balance updated successfully!");
                }
            }
        }
    }

    private void transferMoney(AccountDTO sender, AccountDTO receiver, String amount){
        if (sessionManager.isEmployee()) {
            Notification<Boolean> transferMoneyNotification = accountService.transferMoney(sender, receiver, amount);
            if (transferMoneyNotification.hasErrors()) {
                employeeView.printMessage(transferMoneyNotification.getFormattedErrors());
            } else {
                if (!transferMoneyNotification.getResult()) {
                    employeeView.printMessage("Something went wrong, please try again later.");
                } else {
                    employeeView.printMessage("Money transferred successfully!");
                }
            }
        }
    }

    private void deleteClientAccount(AccountDTO account) {
        if(sessionManager.isEmployee()){
            Notification<Boolean> deleteAccountNotification = accountService.deleteAccount(account);
            if (deleteAccountNotification.hasErrors()) {
                employeeView.printMessage(deleteAccountNotification.getFormattedErrors());
            } else {
                if (!deleteAccountNotification.getResult()) {
                    employeeView.printMessage("Something went wrong, please try again later.");
                } else {
                    employeeView.printMessage("Account deleted successfully!");
                }
            }

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
