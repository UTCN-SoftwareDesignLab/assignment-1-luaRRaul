package view;

import javax.swing.*;
import java.util.Scanner;

public class EmployeeView extends JFrame{
    private boolean visible = false;
    private Scanner sc = new Scanner(System.in);
    public EmployeeView() {
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void print_menu() {
        System.out.println("-------------------Employee menu");
        System.out.println("(1) Client operations");
        System.out.println("(2) Client account operations");
        System.out.println("(3) Transfer money between accounts");
        System.out.println("(4) Process utility bill");
        System.out.println("(0) Logout");
        System.out.print("Option:");
    }

    public void print_client_ops_menu(){
        System.out.println("------------Client operations:");
        System.out.println("(1) Add client");
        System.out.println("(2) View client information");
        System.out.println("(3) Update client information");
        System.out.println("(0) Go back");
        System.out.print("Option:");
    }

    public void print_client_account_ops_menu(){
        System.out.println("------------Account operations:");
        System.out.println("(1) Add account");
        System.out.println("(2) View account information");
        System.out.println("(3) Update account information");
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
    public String getIban() {
        System.out.print("\niban: ");
        return sc.nextLine();
    }

    public String getCurrency() {
        System.out.print("\ncurrency: ");
        return sc.nextLine();
    }

}
