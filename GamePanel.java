package Spel15;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JFrame {

    public GamePanel (Board board){
        JPanel gameComponents = new JPanel(new GridLayout(3, 1));
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setBackground(Color.lightGray);

        JPanel row1 = new JPanel(new FlowLayout());
        JLabel gameName = new JLabel("15 Puzzle");
        row1.add(gameName);

        JPanel row2 = new JPanel(new FlowLayout());
        row2.add(board);

        JPanel row3 = new JPanel(new FlowLayout());
        JButton buttonNewGame = new JButton();
        buttonNewGame.setText("New game");
        row3.add(buttonNewGame);

        gameComponents.add(row1);
        gameComponents.add(row2);
        gameComponents.add(row3);
        pack();

    }
}
