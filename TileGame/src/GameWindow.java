import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class GameWindow extends JFrame {

    private class Canvas extends JPanel{

        @Override
        public void paint(Graphics g) {
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(0,0,width,height);//34x11 50x40 1400x720 II 1500x800
            int spaceX;
            int spaceY;
            spaceY= 0;
            g.setColor(Color.BLACK);
            for(int i=0;i<vertAm;i++){
                spaceX = 0;
                for(int j=0;j<horizAm;j++){
                    if(gameBoard.getValue(j,i) == 0) {
                        g.setColor(new Color(110,175,250));
                        g.fillRect(offsetX + j * tileWidth + spaceX, offsetY + i * tileHeight + spaceY, tileWidth, tileHeight);
                    }
                    if(gameBoard.getValue(j,i) == 1) {
                        g.setColor(new Color(100,240,80));
                        g.fillRect(offsetX + j * tileWidth + spaceX, offsetY + i * tileHeight + spaceY, tileWidth, tileHeight);
                    }
                    if(gameBoard.getValue(j,i) == 2) {
                        g.setColor(new Color(240,150,235));
                        g.fillRect(offsetX + j * tileWidth + spaceX, offsetY + i * tileHeight + spaceY, tileWidth, tileHeight);
                    }
                    if(gameBoard.getValue(j,i) == 3) {
                        g.setColor(new Color(225,20,90));
                        g.fillRect(offsetX + j * tileWidth + spaceX, offsetY + i * tileHeight + spaceY, tileWidth, tileHeight);
                    }
                    if(gameBoard.getValue(j,i) == 4) {
                        g.setColor(new Color(245,155,105));
                        g.fillRect(offsetX + j * tileWidth + spaceX, offsetY + i * tileHeight + spaceY, tileWidth, tileHeight);
                    }
                    spaceX+=spacing;
                }
                spaceY+=spacing;
            }
            spaceY= 0;
            g.setColor(Color.BLACK);
            for(int i=0;i<vertAm;i++){
                spaceX = 0;
                for(int j=0;j<horizAm;j++){
                    g.drawRect(offsetX+j*tileWidth+spaceX,offsetY+i*tileHeight+spaceY,tileWidth,tileHeight);
                    spaceX+=spacing;
                }
                spaceY+=spacing;
            }
        }
    }

    private int width, height;
    private int tileWidth, tileHeight;
    private int horizAm, vertAm;
    private int offsetX, offsetY;
    private int spacing;
    private int score;
    private double bombs;
    private Board gameBoard;
    private boolean bombMode;

    public GameWindow(){
        setDefaultValues();
        setDefaultOptions();
        resetGame();
        Canvas mainPanel = new Canvas();
        mainPanel.setBounds(0,0,width,height);
        mainPanel.setLayout(null);
        mainPanel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX()-offsetX;
                int y = e.getY()-offsetY;
                int modX = x%(tileWidth+spacing);
                int modY = y%(tileHeight+spacing);
                x = (x-modX)/(tileWidth+spacing);
                y = (y-modY)/(tileHeight+spacing);
                if(modX<=tileWidth && modY<=tileHeight) {
                    if(!bombMode){
                        gameBoard.popValue(x,y);
                    }
                    if(bombMode){
                        gameBoard.bombValue(x,y);
                    }
                    bombs += gameBoard.getBombs();
                    score += gameBoard.getScore();
                    bombMode = !gameBoard.checkForSets();
                }
                mainPanel.repaint();
                System.out.println(bombs + " * " + score);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        add(mainPanel);


        setVisible(true);
    }

    private void setDefaultValues(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        width = (int)screenSize.getWidth()-100;
        height = (int)screenSize.getHeight()-100;
        horizAm = 26;
        vertAm = 10;
        tileHeight = 80;
        tileWidth = 60;
        spacing = 5;
        offsetX = (width - horizAm*tileWidth - (horizAm-1)*spacing)/2;
        offsetY = (height - vertAm*tileHeight - (vertAm-1)*spacing)/2 -5;
    }

    private void setDefaultOptions(){
        setSize(width,height);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);
        setResizable(false);
    }

    public void resetGame(){
        gameBoard = new Board(horizAm, vertAm, 3, 3);
        bombMode = !gameBoard.checkForSets();
        score = 0;
        bombs = 0;
    }
}
