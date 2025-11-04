package Spel15;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GamePanel extends JFrame {
    private Board board;
    private boolean solved = false;
    JPanel centerPanel;

    public GamePanel (Board board){
        this.board = board;
        setLayout(new BorderLayout());
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setBackground(Color.lightGray);

        JLabel gameName = new JLabel("15 Puzzle");

        JButton buttonNewGame = new JButton();
        buttonNewGame.setText("New game");
        buttonNewGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent e) {
                newGame();
            }
        });
        JButton buttonScores = new JButton();
        buttonScores.setText("Highscores");
        buttonScores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showScores();
            }
        });
        JPanel options = new JPanel(new BorderLayout());
        options.add(buttonNewGame, BorderLayout.WEST);
        options.add(buttonScores, BorderLayout.EAST);

        add(gameName, BorderLayout.NORTH);
        centerPanel = new JPanel();
        centerPanel.add(board);
        add(centerPanel, BorderLayout.CENTER);
        add(options, BorderLayout.SOUTH);
        pack();
    }
    private JPanel boardOptions (boolean solved) {
        JPanel thisBoardOption;
        if (!solved) {
            thisBoardOption = board;
        }
        else {
            thisBoardOption = whenSolved();
        }
        return thisBoardOption;
    }
    public void showEndMessage() {
        solved = true;
        centerPanel.removeAll();
        centerPanel.add(whenSolved());
        centerPanel.setBackground(Color.green);
        add(centerPanel);
        revalidate();
        repaint();
        pack();
    }
    private JPanel whenSolved () {
        JPanel endBoard = new JPanel();
        JLabel endMessage = new JLabel("Grattis! Du vann!");
        endBoard.setBackground(Color.green);
        endBoard.add(endMessage);
        pack();
        return endBoard;
    }
    private void newGame() {
        board.addTilesInOrder();
    }
    public void showScores(){

    }
}
