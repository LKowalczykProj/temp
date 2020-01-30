import java.util.ArrayList;
import java.util.Random;

public class Board {

    private ArrayList<ArrayList<ArrayList<Tile>>> boardMatrix;
    private ArrayList<Integer> comboCounter;
    private int width, height;
    private static int ERASE_VALUE = -1;
    private boolean bombed;

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

    public void pop(int x, int y){
        popValue(x,y);
        clearEmptyColumns();
        bombed = false;
    }

    private void popValue(int x, int y){
        if(getValue(x,y)!=ERASE_VALUE){
            BoardSnapshot snapshot = new BoardSnapshot(this);
            int result = snapshot.pop(x,y);
            if(result>0){
                comboCounter.add(result);
            }
            snapshot.mergeLayer(this);
            removeHoles();
        }
    }

    public void bombValue(int x, int y){
        if(getValue(x,y)!=ERASE_VALUE){
            eraseValue(x,y);
            removeHoles();
            bombed = true;
        }
        clearEmptyColumns();
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
    public void print(){
        System.out.println("#####################");
        for(int i=0;i<height;i++){
            for(int j=0;j<width;j++){
                System.out.print(getValue(j,i)+" ");
            }
            System.out.println("");
        }
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@");
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
        int holePos = -1;
        while(holeFound){
            holeFound = false;
            for(int i = height-1; i>0; i--){
                if(getValue(columnNumber,i) == ERASE_VALUE && getValue(columnNumber,i-1)!=ERASE_VALUE){
                    ArrayList<Tile> temp;
                    temp = boardMatrix.get(columnNumber).get(i);
                    boardMatrix.get(columnNumber).set(i,boardMatrix.get(columnNumber).get(i-1));
                    boardMatrix.get(columnNumber).set(i-1,temp);
                    holeFound = true;
                    if(holePos==-1) {
                        holePos = i;
                    }
                }
            }
        }
        if(holePos!=-1) {
            popValue(columnNumber, holePos);
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
        if(comboCounter.size()>0 && !bombed) {
            double value = (comboCounter.size()-1) * 0.5;
            if(value>2){
                return 2;
            }
            return value;
        }
        return 0;
    }

    public int getLevel(int x, int y){
        return boardMatrix.get(x).get(y).size();
    }

    private void clearEmptyColumns(){
        boolean emptyColumns = true;
        while(emptyColumns) {
            emptyColumns = false;
            for (int i = 0; i < width - 1; i++) {
                if (getValue(i, height - 1) == ERASE_VALUE && getValue(i + 1, height - 1) != ERASE_VALUE) {
                    removeEmptyColumn(i);
                    emptyColumns = true;
                }
            }
        }
    }

    private void removeEmptyColumn(int column){
        for(int i=0; i<height;i++){
            if(getValue(column+1,i) != ERASE_VALUE){
                ArrayList<Tile> temp = boardMatrix.get(column).get(i);
                boardMatrix.get(column).set(i, boardMatrix.get(column+1).get(i));
                boardMatrix.get(column+1).set(i, temp);
            }
        }
    }

    public void eraseValue(int x, int y){
        if(getValue(x,y) != ERASE_VALUE) {
            boardMatrix.get(x).get(y).remove(0);
        }
    }

    public String checkGameOver(double bombsRemaining){
        boolean gameOver = checkForEmptyBoard();
        if(gameOver){
            return "WIN";
        }
        if (!checkForSets() && bombsRemaining < 1){
            return "LOSE";
        }
        return "CONTINUE";
    }

    public boolean checkForEmptyBoard(){
        boolean empty = true;
        for(int i=0;i<width;i++){
            for(int j=0; j<height;j++){
                if(getValue(i,j)!=ERASE_VALUE){
                    empty = false;
                    break;
                }
            }
            if(!empty){
                break;
            }
        }
        return empty;
    }
}