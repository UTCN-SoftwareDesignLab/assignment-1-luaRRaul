package view;

import dto.UserDTO;
import dto.builder.UserDTOBuilder;

public class MainView extends ConsoleView{
    public void printMenu() {
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

    public UserDTO getUserDTO(){
        System.out.print("\nusername: ");
        String username = sc.nextLine();
        System.out.print("\npassword: ");
        String password = sc.nextLine();
        return new UserDTOBuilder()
                .setUsername(username)
                .setPassword(password)
                .build();
    }

    public int getOption(){
        return Integer.parseInt(sc.nextLine());
    }

    public void printMessage(String message){
        System.out.println("\n"+message+"\n");
    }

}
