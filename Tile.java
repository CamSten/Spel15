package Spel15;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        setBackground(color(value));
        setVisible(true);
        setForeground(Color.lightGray);
        setFont(new Font("Arial", Font.BOLD, 20));
    }
    private String text (int value) {
        String tileText = "";
        if (value != 0) {
            tileText = String.valueOf(value);
        }
        return tileText;
    }
    private Color color (int value) {
        if (value == 0) {
            return Color.WHITE;
        } else {
            return Color.BLUE;
        }
    }
    public void adjustTile(int value) {
        setBackground(color(value));
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
