import java.util.ArrayList;
import java.util.Random;

public class Board {

    private ArrayList<ArrayList<ArrayList<Tile>>> boardMatrix;
    private ArrayList<Integer> comboCounter;
    private int width, height;
    private static int ERASE_VALUE = -1;

    public Board(int width, int height, int layers, int tile_ammount){
        this.width = width;
        this.height = height;
        boardMatrix = new ArrayList<>();
        Random generator = new Random();
        comboCounter = new ArrayList<>();
        for(int i=0; i<width;i++){
            ArrayList<ArrayList<Tile>> column = new ArrayList<>();
            for(int j=0;j<height;j++){
                ArrayList<Tile> tiles = new ArrayList<>();
                for(int k=0; k<layers; k++){
                    tiles.add(new Tile(Math.abs(generator.nextInt(tile_ammount))));
                }
                column.add(tiles);
            }
            boardMatrix.add(column);
        }
    }

    public int getValue(int x, int y){
        if(!boardMatrix.get(x).get(y).isEmpty()){
            return boardMatrix.get(x).get(y).get(0).getValue();
        } else {
            return ERASE_VALUE;
        }
    }

    public void popValue(int x, int y){
        if(getValue(x,y)!=ERASE_VALUE){
            BoardSnapshot snapshot = new BoardSnapshot(this);
            comboCounter.add(snapshot.pop(x,y));
            snapshot.mergeLayer(this);
            removeHoles();
        }
    }

    public void bombValue(int x, int y){
        if(getValue(x,y)!=ERASE_VALUE){
            boardMatrix.get(x).get(y).remove(0);
            removeHoles();
        }
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
    public void print(){
        for(int i=0;i<height;i++){
            for(int j=0;j<width;j++){
                System.out.print(getValue(j,i)+" ");
            }
            System.out.println("");
        }
    }

    public boolean checkForSets(){
        BoardSnapshot snapshot = new BoardSnapshot(this);
        return snapshot.checkForSets();
    }

    private void removeHoles() {
        for (int j = 0; j < width; j++) {
            stabilizeColumn(j);
        }
    }

    private void stabilizeColumn(int columnNumber){
        boolean holeFound = true;
        while(holeFound){
            holeFound = false;
            for(int i = height-1; i>0; i--){
                if(getValue(columnNumber,i) == ERASE_VALUE && getValue(columnNumber,i-1)!=ERASE_VALUE){
                    ArrayList<Tile> temp;
                    temp = boardMatrix.get(columnNumber).get(i);
                    boardMatrix.get(columnNumber).set(i,boardMatrix.get(columnNumber).get(i-1));
                    boardMatrix.get(columnNumber).set(i-1,temp);
                    holeFound = true;
                }
            }
        }
    }

    public int getScore(){
        int score = 0;
        for(int i=0;i<comboCounter.size();i++){
            score += comboCounter.get(i)*comboCounter.get(i)*(i+1);
        }
        comboCounter.clear();
        return score;
    }

    public double getBombs(){
        return comboCounter.size()*0.5;
    }
}