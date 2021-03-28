package view;

import model.User;

import javax.swing.*;
import java.util.List;
import java.util.Scanner;

public class AdminView extends ConsoleView {
    public void printMenu() {
        System.out.println("-------------------Admin menu");
        System.out.println("(1) Create employee");
        System.out.println("(2) View employee information");
        System.out.println("(3) Delete employee");
        System.out.println("(4) Update employee");
        System.out.println("(0) Logout");
        System.out.print("Option:");
    }

    public int getEmployeeNumber() {
        System.out.print("\nemployee number: ");
        return Integer.parseInt(sc.nextLine());
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

    public int getOption() {
        return sc.nextInt();
    }

    public User getSelectedUser(List<User> employees) {
        System.out.println("Employee list:");
        for (User u : employees) {
            System.out.print("(" + employees.indexOf(u) + ") ");
            System.out.println(u.getUsername());
        }
        System.out.print("Select employee:");
        int selection = getOption();
        if (selection>=0 & selection<employees.size())
            return employees.get(selection);
        else
            System.out.println("Employee index not good!");
            return null; //something went wrong
    }

    public void printEmployee(User employee) {
        System.out.println(employee.toString());
    }

    public void printMessage(String message) {
        System.out.println("\n"+message+"\n");
    }
}
