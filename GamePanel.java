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
    JButton bottomRight;
    String userName;

    public GamePanel (Board board, Scores scores){
        getContentPane().setBackground(GameColors.defaultBackground()); getRootPane().setBackground(GameColors.defaultBackground());
        getLayeredPane().setBackground(GameColors.defaultBackground());
        this.board = board;
        setTitle("15 Puzzle");

        this.scores = scores;
        setLayout(new BorderLayout());
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setBackground(GameColors.defaultBackground());

        JButton buttonNewGame = new JButton();
        buttonNewGame.setText("Nytt spel");
        buttonNewGame.setFont(GameFont.defaultFont());
        buttonNewGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent e) {
                newGame();
            }
        });
        JButton buttonClearScores = new JButton();
        buttonClearScores.setText("Radera highscores");
        buttonClearScores.setFont(GameFont.defaultFont());
        buttonClearScores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scores.clearSavedScores();
            }
        });

        bottomRight = new JButton("Highscores");
        bottomRight.setFont(GameFont.defaultFont());
        bottomRight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showHighScore();
            }
        });
        bottomPanel = new JPanel(new BorderLayout());
        displayMoves = new JLabel("0");
        displayMoves.setFont(GameFont.defaultFont());
        bottomPanel.add(buttonNewGame, BorderLayout.WEST);
        bottomPanel.add(displayMoves, BorderLayout.CENTER);
        bottomPanel.add(bottomRight, BorderLayout.EAST);
        bottomPanel.setOpaque(true);
        centerPanel = new JPanel();
        centerPanel.add(board);
        centerPanel.setOpaque(true);
        centerPanel.setBackground(GameColors.defaultBackground());
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
        pack();
    }
    public void showEndMessage() {
        solved = true;
        centerPanel.removeAll();
        centerPanel.add(whenSolved());
        centerPanel.setBackground(Color.pink);
        add(centerPanel);
        revalidate();
        repaint();
        pack();
    }
    private JPanel whenSolved () {
        JPanel endBoard = new JPanel(new BorderLayout());
        endBoard.setBackground(GameColors.defaultBackground());
        endBoard.setOpaque(true);

        JLabel endMessage = new JLabel("Grattis! Du vann!");
        endMessage.setFont(GameFont.topHeaderFont());
        endMessage.setForeground(GameColors.headerText());
        endBoard.add(endMessage, BorderLayout.CENTER);
        JPanel name = new JPanel(new GridLayout(1, 3));
        name.setBackground(GameColors.defaultBackground());
        name.setOpaque(true);
        JLabel askForName = new JLabel("Skriv ditt namn:");
        askForName.setFont(GameFont.defaultFont());
        JTextField inputName = new JTextField(15);
        JButton saveName = new JButton("Spara namn");
        saveName.setFont(GameFont.defaultFont());
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
        endBoard.setBackground(Color.pink);
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
        bottomRight.setText("Radera highscores");
        ActionListener[]listener = bottomRight.getActionListeners();
            bottomRight.removeActionListener(listener[0]);

        bottomRight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scores.clearSavedScores();
                showHighScore();
            }
        });
        revalidate();
        repaint();
        pack();
    }
    public String getUserName (){
        return userName;
    }
}
