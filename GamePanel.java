package Spel15;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GamePanel extends JFrame {
    private Board board;
    private boolean solved = false;
    private Scores scores;
    JLabel displayMoves;
    JPanel centerPanel;
    JPanel bottomPanel;

    public GamePanel (Board board, Scores scores){
        this.board = board;
        this.scores = scores;
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
                showHighScore();
            }
        });
        bottomPanel = new JPanel(new BorderLayout());
        displayMoves = new JLabel("0");
        bottomPanel.add(buttonNewGame, BorderLayout.WEST);
        bottomPanel.add(displayMoves, BorderLayout.CENTER);
        bottomPanel.add(buttonScores, BorderLayout.EAST);

        add(gameName, BorderLayout.NORTH);
        centerPanel = new JPanel();
        centerPanel.add(board);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
        pack();
    }
    public void showEndMessage() {
        solved = true;
        centerPanel.removeAll();
        centerPanel.add(whenSolved());
        centerPanel.setBackground(Color.green);
        add(centerPanel);
        scores.saveScore();
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

    public void showMoves(){
        scores.addMove();
        if(scores.getMovesInt() > 0) {
            displayMoves.setText(scores.getMovesString());
            revalidate();
            repaint();
            pack();
        }
    }
    private void showHighScore () {
        centerPanel.removeAll();
        displayMoves.setText("");
        JPanel highscores = new JPanel();
        highscores.add(scores.scorePanel());
        centerPanel.add(highscores);
        add(centerPanel);
        revalidate();
        repaint();
        pack();
    }
}
