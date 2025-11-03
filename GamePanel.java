package Spel15;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JFrame {
    JPanel gameComponents = new JPanel(new GridLayout(3, 1));
    JPanel row1 = new JPanel(new FlowLayout());
    JPanel row2 = new JPanel(new FlowLayout());
    JPanel row3 = new JPanel(new FlowLayout());

    public GamePanel (Board board){

        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setBackground(Color.lightGray);


        JLabel gameName = new JLabel("15 Puzzle");
        row1.add(gameName);


        row2.add(board);


        JButton buttonNewGame = new JButton();
        buttonNewGame.setText("New game");
        row3.add(buttonNewGame);

        gameComponents.add(row1);
        gameComponents.add(row2);
        gameComponents.add(row3);
        pack();

    }
    public void showEndMessage() {
        JLabel endMessage = new JLabel("Grattis! Du vann!");
        endMessage.setSize(400, 400);
        endMessage.setBackground(Color.green);
        row2.removeAll();
        row2.add(endMessage);
        pack();

    }
}
