package Spel15;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GamePanel extends JFrame{
    private Board board;
    private boolean solved = false;
    private Scores scores;
    JLabel displayMoves;
    JPanel centerPanel;
    JPanel bottomPanel;
    String userName;

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
        revalidate();
        repaint();
        pack();
    }
    private JPanel whenSolved () {
        JPanel endBoard = new JPanel(new BorderLayout());
        JLabel endMessage = new JLabel("Grattis! Du vann!");
        endBoard.add(endMessage, BorderLayout.CENTER);
        JPanel name = new JPanel(new GridLayout(1, 3));
        JLabel askForName = new JLabel("Skriv ditt namn:");
        JTextField inputName = new JTextField(15);
        JButton saveName = new JButton("Spara namn");
        saveName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userName = inputName.getText().trim();
                if (userName.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Skriv ett namn först!");
                } else {
                    scores.saveScore(userName);
                    showHighScore();
                }
            }
        });
        userName = inputName.getText();
        name.add(askForName);
        name.add(inputName);
        name.add(saveName);
        endBoard.add(endMessage, BorderLayout.NORTH);
        endBoard.add(name, BorderLayout.CENTER);
        endBoard.setBackground(Color.green);
        pack();
        return endBoard;
    }
    private void newGame() {
        showGameBoard();
        board.addTiles();
        displayMoves.setText("0");
        scores.resetScore();
    }
    public void showGameBoard (){
        centerPanel.removeAll();
        centerPanel.add(board);
        revalidate();
        repaint();
        pack();
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
    public String getUserName (){
        return userName;
    }
}
