package view;

import javax.swing.*;
import java.util.Scanner;

public class AdminView extends JFrame {
    private boolean visible = false;
    private Scanner sc = new Scanner(System.in);
    public AdminView() {
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void print_menu() {
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
        return sc.nextInt();
    }

    public String getUsername() {
        System.out.print("\nusername: ");
        return sc.nextLine();
    }

    public String getPassword() {
        System.out.print("\npassword: ");
        return sc.nextLine();
    }

}
