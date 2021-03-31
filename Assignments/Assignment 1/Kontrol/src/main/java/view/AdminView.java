package view;

import dto.UserDTO;
import dto.builder.UserDTOBuilder;
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

    public int getOption() {
        return Integer.parseInt(sc.nextLine());
    }

    public UserDTO getSelectedUser(List<UserDTO> employees) {
        System.out.println("Employee list:");
        for (UserDTO u : employees) {
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

    public void printEmployee(UserDTO employee) {
        System.out.println(employee.toString());
    }

    public void printMessage(String message) {
        System.out.println("\n"+message+"\n");
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
}
