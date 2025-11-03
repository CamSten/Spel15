package Spel15;

import javax.swing.*;
import java.awt.*;

public class Button extends  JButton{


    public Button (Tile tile) {
        String value = String.valueOf(tile.getValue());
        JButton button = new JButton();
        JLabel tileValue = new JLabel(value);
        button.setSize(70, 70);
        if (tile.getValue() == 0) {
            button.setVisible(false);
        }
        else {
            button.setVisible(true);
            button.setBackground(Color.BLUE);
        }
    }
}
