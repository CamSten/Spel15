package Spel15;

import javax.swing.*;
import java.awt.*;

public class Tile extends JButton {
    private int row;
    private int col;
    private int value;
    private boolean isActive = false;
    private boolean isEmpty = false;

    public Tile (int row, int col, int value){
        this.row = row;
        this.col = col;
        this.value = value;
        setText(text(value));
        setPreferredSize(new Dimension(70, 70));
        setBackground(backgroundColor(value));
        setVisible(true);
        setForeground(foregroundColor(value));
        setFont(new Font("Arial", Font.BOLD, 20));
    }
    private String text (int value) {
        String tileText = "";
        if (value != 0) {
            tileText = String.valueOf(value);
        }
        return tileText;
    }
    private Color backgroundColor(int value) {
        if (value == 0) {
            return Color.white;
        } else {
            return Color.pink;
        }
    }
    private Color foregroundColor (int value) {
        if (value == 0){
            return Color.white;
        }
        else {
            return Color.magenta;
        }
    }
    public void adjustTile(int value) {
        setBackground(backgroundColor(value));
        setForeground(foregroundColor(value));
        setText(text(value));
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
    public void setValue (int i){
        this.value = i;
    }
    public boolean getStatus(){
        return isActive;
    }
    public int[] getPosition(){
        return new int[]{row, col};
    }
    public void setPosition(int[]position){
        this.row = position[0];
        this.col = position[1];
    }
}
