import java.util.ArrayList;

public class BoardSnapshot {

    private ArrayList<ArrayList<Integer>> layer;
    private int tileValue, width, height;
    private static int ERASE_VALUE = -1;

    public BoardSnapshot(Board board){
        layer = new ArrayList<>();
        width = board.getWidth();
        height = board.getHeight();
        tileValue = ERASE_VALUE;
        for(int i=0;i<width;i++){
            ArrayList<Integer> list = new ArrayList<>();
            for(int j=0;j<height;j++){
                list.add(board.getValue(i,j));
            }
            layer.add(list);
        }
    }

    public int pop(int x, int y){
        int result = 0;
        boolean first = false;
        if(tileValue == ERASE_VALUE) {
            tileValue = layer.get(x).get(y);
            first = true;
        }
        if(tileValue == layer.get(x).get(y)) {
            result += 1;
            layer.get(x).set(y, ERASE_VALUE);
            if (x - 1 >= 0) {
                result += pop(x - 1, y);
            }
            if (x + 1 < width) {
                result += pop(x + 1, y);
            }
            if (y - 1 >= 0) {
                result += pop(x, y - 1);
            }
            if (y + 1 < height) {
                result += pop(x, y + 1);
            }
        }
        if(first && result == 1){
            layer.get(x).set(y, tileValue);
            return 0;
        }
        return result;
    }

    public void mergeLayer(Board board){
        for(int i=0;i<width;i++){
            for(int j=0;j<height;j++){
                if(board.getValue(i,j) != ERASE_VALUE && layer.get(i).get(j) == ERASE_VALUE){
                    board.eraseValue(i,j);
                }
            }
        }
    }

    public void print(){
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$");
        for(int i=0;i<height;i++){
            for(int j=0;j<width;j++){
                System.out.print(layer.get(j).get(i)+" ");
            }
            System.out.println("");
        }
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$");
    }

    public boolean checkForSets(){
        for(int i = 0;i<width;i++){
            for(int j =0; j<height;j++){
                int tileValue = layer.get(i).get(j);
                if(i-1>=0 && tileValue!=ERASE_VALUE){
                    if(layer.get(i-1).get(j) == tileValue){
                        return true;
                    }
                }
                if(i+1<width && tileValue!=ERASE_VALUE){
                    if(layer.get(i+1).get(j) == tileValue){
                        return true;
                    }
                }
                if(j-1>=0 && tileValue!=ERASE_VALUE){
                    if(layer.get(i).get(j-1) == tileValue){
                        return true;
                    }
                }
                if(j+1<height && tileValue!=ERASE_VALUE){
                    if(layer.get(i).get(j+1) == tileValue){
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
