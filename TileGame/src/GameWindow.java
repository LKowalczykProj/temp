import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public class GameWindow extends JFrame {

    private class Canvas extends JPanel{

        @Override
        public void paint(Graphics g) {
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(0,0,width,height);//34x11 50x40 1400x720 II 1500x800
            g.drawImage(background,offsetX,offsetY,null);
            int spaceX;
            int spaceY;
            spaceY= 0;
            ColorModifier mod = new ColorModifier();
            g.setColor(Color.BLACK);
            for(int i=0;i<vertAm;i++){
                spaceX = 0;
                for(int j=0;j<horizAm;j++){
                    if(gameBoard.getValue(j,i) == 0) {
                        g.setColor(mod.darken(new Color(110,175,250),gameBoard.getLevel(j,i)));
                        g.fillRect(offsetX + j * tileWidth + spaceX, offsetY + i * tileHeight + spaceY, tileWidth, tileHeight);
                    }
                    if(gameBoard.getValue(j,i) == 1) {
                        g.setColor(mod.darken(new Color(100,240,80),gameBoard.getLevel(j,i)));
                        g.fillRect(offsetX + j * tileWidth + spaceX, offsetY + i * tileHeight + spaceY, tileWidth, tileHeight);
                    }
                    if(gameBoard.getValue(j,i) == 2) {
                        g.setColor(mod.darken(new Color(240,150,235),gameBoard.getLevel(j,i)));
                        g.fillRect(offsetX + j * tileWidth + spaceX, offsetY + i * tileHeight + spaceY, tileWidth, tileHeight);
                    }
                    if(gameBoard.getValue(j,i) == 3) {
                        g.setColor(mod.darken(new Color(238, 239, 29),gameBoard.getLevel(j,i)));
                        g.fillRect(offsetX + j * tileWidth + spaceX, offsetY + i * tileHeight + spaceY, tileWidth, tileHeight);
                    }
                    if(gameBoard.getValue(j,i) == 4) {
                        g.setColor(mod.darken(new Color(245,155,105),gameBoard.getLevel(j,i)));
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
                    if(gameBoard.getLevel(j,i)!=0) {
                        g.drawRect(offsetX + j * tileWidth + spaceX, offsetY + i * tileHeight + spaceY, tileWidth, tileHeight);
                    }
                    spaceX+=spacing;
                }
                spaceY+=spacing;
            }

            g.setColor(Color.WHITE);
            g.fillOval(100, 20, 30, 30);
            g.setFont(new Font("Arial",Font.BOLD,25));
            g.drawString(Integer.toString((int)bombs),140,45);
            g.drawString("Score: "+score,190,45);
            g.setColor(Color.BLACK);
            g.fillOval(102, 22, 26, 26);

            if(bombMode) {
                g.setColor(Color.WHITE);
                g.fillOval(mouseX - 15, mouseY - 15, 30, 30);
                g.setColor(Color.BLACK);
                g.fillOval(mouseX - 13, mouseY - 13, 26, 26);
            }
        }
    }

    private int width, height;
    private int tileWidth, tileHeight;
    private int horizAm, vertAm;
    private int offsetX, offsetY;
    private int spacing;
    private int score;
    private int layers;
    private double bombs;
    private Board gameBoard;
    private boolean bombMode;
    private BufferedImage background;
    private int mouseX, mouseY;

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
                        gameBoard.pop(x,y);
                        bombs += gameBoard.getBombs();
                        score += gameBoard.getScore();
                    }
                    if(bombMode){
                        gameBoard.bombValue(x,y);
                        bombs -=1;
                        score +=1;
                    }
                    bombMode = !gameBoard.checkForSets();
                }
                mainPanel.repaint();
                System.out.println(bombs + " * " + score);
                if(gameBoard.checkGameOver(bombs)){
                    System.out.println("GAME OVER!!!");
                }
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

        mainPanel.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent mouseEvent) {

            }

            @Override
            public void mouseMoved(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
                mainPanel.repaint();
            }
        });
        add(mainPanel);


        setVisible(false);
    }

    private void setDefaultValues(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        width = (int)screenSize.getWidth()-100;
        height = (int)screenSize.getHeight()-100;
        horizAm = 26;//26
        vertAm = 10;//10
        tileHeight = 80;
        tileWidth = 60;
        spacing = 5;
        layers = 1;
        offsetX = (width - horizAm*tileWidth - (horizAm-1)*spacing)/2;
        offsetY = (height - vertAm*tileHeight - (vertAm-1)*spacing)/2 -5;
    }

    private void setDefaultOptions(){
        setSize(width,height);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);
        setResizable(false);
        setTitle("TileGame - Level 1");
    }

    public void resetGame(){
        gameBoard = new Board(horizAm, vertAm, layers, 3);
        bombMode = !gameBoard.checkForSets();
        score = 0;
        bombs = 0;
        try{
            background = ImageIO.read(new File("imgs/bg/bgIm5.jpg"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
