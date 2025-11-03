package Spel15;

import javax.swing.*;
import java.awt.*;

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
    public JButton tileDesign (Tile tile){
        String value = String.valueOf(tile.getValue());
        JButton button = new JButton();
        JLabel tileValue = new JLabel(value);
        button.setVisible(true);
        button.setBackground(Color.BLUE);
        button.setSize(70, 70);
        return button;
    }
    public JButton tileDesignEmpty (Tile tile) {
        JButton button = new JButton();
        button.setSize(70, 70);
        button.setVisible(false);
        return button;
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
