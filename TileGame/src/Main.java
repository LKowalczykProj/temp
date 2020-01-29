import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        GameWindow GW = new GameWindow();
        MenuWindow MW = new MenuWindow();
        ButtonControler btnController = new ButtonControler(GW,MW);

    }
}
