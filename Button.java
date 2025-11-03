package Spel15;

import javax.swing.*;
import java.awt.*;

public class Button {
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
}
