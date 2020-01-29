import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonControler implements ActionListener {

    private GameWindow GW;
    private MenuWindow MW;

    public ButtonControler(GameWindow GW, MenuWindow MW){
        this.GW = GW;
        this.MW = MW;
        MW.bSettings.addActionListener(this);
        MW.bStart.addActionListener(this);
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        Object src = actionEvent.getSource();
        if(src == MW.bStart){
            MW.setVisible(false);
            GW.setVisible(true);
        }
    }
}
