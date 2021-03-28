package view;

import java.util.Scanner;

public class ConsoleView {
    protected boolean visible = false;
    protected Scanner sc = new Scanner(System.in);

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

}
