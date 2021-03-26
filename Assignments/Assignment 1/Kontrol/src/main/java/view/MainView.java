package view;

import javax.swing.*;
import java.util.Scanner;

public class MainView extends JFrame{
    private boolean visible = true;
    private Scanner sc = new Scanner(System.in);
    public MainView() {
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void print_menu() {
        System.out.println("-------------------Main menu");
        System.out.println("(1) Login");
        System.out.println("(2) Register as admin");
        System.out.println("(3) Register as employee");
        System.out.println("(0) Quit");
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

}
