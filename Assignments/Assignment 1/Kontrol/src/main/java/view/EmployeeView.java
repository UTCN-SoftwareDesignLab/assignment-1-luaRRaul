package view;

import dto.AccountDTO;
import dto.UserDTO;
import dto.builder.AccountDTOBuilder;
import dto.builder.UserDTOBuilder;
import model.Account;

import java.util.List;

public class EmployeeView extends ConsoleView{
    public void printMenu() {
        System.out.println("-------------------Employee menu");
        System.out.println("(1) Client operations");
        System.out.println("(2) Client account operations");
        System.out.println("(3) Transfer money between accounts");
        System.out.println("(4) Process utility bill");
        System.out.println("(0) Logout");
        System.out.print("Option:");
    }

    public void printClientOpsMenu(){
        System.out.println("------------Client operations:");
        System.out.println("(1) Add client");
        System.out.println("(2) View client information");
        System.out.println("(3) Update client information");
        System.out.println("(4) Delete client (application)account");
        System.out.println("(0) Go back");
        System.out.print("Option:");
    }

    public void printClientAccountOpsMenu(){
        System.out.println("------------Account operations:");
        System.out.println("(1) Add account");
        System.out.println("(2) View account information");
        System.out.println("(3) Update account information");
        System.out.println("(4) Delete client account");
        System.out.println("(5) Transfer money");
        System.out.println("(0) Go back");
        System.out.print("Option:");
    }

    public String getUsername() {
        System.out.print("\nusername: ");
        return sc.nextLine();
    }

    public String getPassword() {
        System.out.print("\npassword: ");
        return sc.nextLine();
    }

    public int getOption() {
        return Integer.parseInt(sc.nextLine());
    }

    public String getAmount(){
        return sc.nextLine();
    }

    public void printMessage(String message){
        System.out.println("\n"+message+"\n");
    }

    public UserDTO getSelectedUser(List<UserDTO> clients) {
        System.out.println("Client list:");
        for (UserDTO u : clients) {
            System.out.print("(" + clients.indexOf(u) + ") ");
            System.out.println(u.getUsername());
        }
        System.out.print("Select client:");
        int selection= getOption();
        if (selection>=0 & selection<clients.size())
            return clients.get(selection);
        else
            System.out.println("Customer index not good!");
        return null; //something went wrong
    }

    public AccountDTO getSelectedAccount(List<AccountDTO> accounts) {
        for (AccountDTO account : accounts){
            System.out.print("(" + accounts.indexOf(account) + ") ");
            System.out.println(account.toString());
        }
        System.out.print("Select account:");
        int selection=getOption();
        if (selection>=0 & selection<accounts.size())
            return accounts.get(selection);
        else
            System.out.println("Account index out of bounds!");
            return null;
    }

    public void printClient(UserDTO client) {
        System.out.println(client.toString());
    }

    public String getNewBalance() {
        System.out.print("New balance: ");
        return sc.nextLine();
    }

    public void printClientAccounts(List<AccountDTO> accounts) {
        for (AccountDTO account : accounts){
            System.out.print("(" + accounts.indexOf(account) + ") ");
            System.out.println(account.toString());
        }
    }

    public UserDTO getUserDTO() {
        System.out.print("\nusername: ");
        String username = sc.nextLine();
        System.out.print("\npassword: ");
        String password = sc.nextLine();
        return new UserDTOBuilder()
                .setUsername(username)
                .setPassword(password)
                .build();
    }

    public AccountDTO getAccountDTO() {
        System.out.print("\niban: ");
        String iban = sc.nextLine();

        System.out.print("\ncurrency: ");
        String currency = sc.nextLine();

        return new AccountDTOBuilder().setCurrency(currency).setIban(iban).build();

    }
}
