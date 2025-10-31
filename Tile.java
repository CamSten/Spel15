package Spel15;

public class Tile {
    private int row;
    private int col;
    private int value;
    private boolean isActive = false;
    private boolean isEmpty = false;

    public Tile (int row, int col, int value){
        this.row = row;
        this.col = col;
        this.value = value;
    }
    public int getRow() {
        return row;
    }
    public int getCol(){
        return col;
    }
    public int getValue (){
        return value;
    }
    public int[] getPosition(){
        int[]position = new int[]{row, col};
        return position;
    }
    public void setPosition(int[]position){
        this.row = position[0];
        this.col = position[1];
    }
}
