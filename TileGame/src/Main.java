public class Main {

    public static void main(String[] args) {
        GameWindow GW = GameWindow.getInstance();
        MenuWindow MW = new MenuWindow();
        ButtonControler btnController = new ButtonControler(GW,MW);

    }
}
