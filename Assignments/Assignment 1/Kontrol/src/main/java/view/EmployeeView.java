package view;

import dto.UserDTO;
import dto.builder.UserDTOBuilder;
import model.Account;
import model.User;

import javax.swing.*;
import java.util.List;
import java.util.Scanner;

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
        System.out.println("(0) Go back");
        System.out.print("Option:");
    }

    public void printClientAccountOpsMenu(){
        System.out.println("------------Account operations:");
        System.out.println("(1) Add account");
        System.out.println("(2) View account information");
        System.out.println("(3) Update account information");
        System.out.println("(0) Go back");
        System.out.print("Option:");
    }

    public String getUsername() {
        sc.nextLine();
        System.out.print("\nusername: ");
        return sc.nextLine();
    }

    public String getPassword() {
        System.out.print("\npassword: ");
        return sc.nextLine();
    }
    public String getIban() {
        System.out.print("\niban: ");
        return sc.nextLine();
    }

    public String getCurrency() {
        System.out.print("\ncurrency: ");
        return sc.nextLine();
    }

    public int getOption() {
        return sc.nextInt();
    }

    public String getAmmount(){
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

    public Account getSelectedAccount(List<Account> accounts) {
        for (Account account : accounts){
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

    public void printClientAccounts(List<Account> accounts) {
        for (Account account : accounts){
            System.out.println("(" + accounts.indexOf(account) + ") ");
            System.out.println(account.toString());
        }
    }

    public UserDTO getUserDTO() {
        sc.nextLine();
        System.out.print("\nusername: ");
        String username = sc.nextLine();
        System.out.print("\npassword: ");
        String password = sc.nextLine();
        return new UserDTOBuilder()
                .setUsername(username)
                .setPassword(password)
                .build();
    }
}
