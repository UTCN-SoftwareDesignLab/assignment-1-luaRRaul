package view;

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
        sc.nextLine();
        System.out.print("\nusername: ");
        return sc.nextLine();
    }

    public String getPassword() {
        System.out.print("\npassword: ");
        return sc.nextLine();
    }

    public int getOption(){
        return sc.nextInt();
    }

    public void printMessage(String message){
        System.out.println("\n"+message+"\n");
    }

}
