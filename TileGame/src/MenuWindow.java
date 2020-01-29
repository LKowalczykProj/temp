import javax.swing.*;

public class MenuWindow extends JFrame {

    private int width, height;
    public JPanel mainPanel;
    public JButton bSettings, bStart;

    public MenuWindow(){
        setDefaultOptions();
        setComponents();
        setVisible(true);
    }

    public void setDefaultOptions(){
        width = 480;
        height = 270;
        setSize(width,height);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);
        setResizable(false);
        setTitle("Tile game");
    }

    public void setComponents(){
        mainPanel = new JPanel();
        mainPanel.setBounds(0,0,width,height);
        bStart = new JButton("Start game");
        bStart.setBounds(140,100,200,40);
        bSettings = new JButton("Options");
        bSettings.setBounds(140,150,200,40);
        mainPanel.setLayout(null);
        mainPanel.add(bStart);
        mainPanel.add(bSettings);
        add(mainPanel);

    }
}
