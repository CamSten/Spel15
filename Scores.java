package Spel15;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Scores {
    private int moves = 0;
    private GamePanel panel;

    public Scores () {
    }
    public int getMovesInt() {
        return moves;
    }
    public String getMovesString (){
        return Integer.toString(moves);
    }

    public void addMove () {
        moves+=1;
    }
    public void resetScore() {
        moves = 0;
    }
    private Path getPath () {
        Path path = Paths.get("src/Spel15/Scores.txt");
        if (!Files.exists(path)) {
            try {
                Files.createFile(path);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return path;
    }
    public void saveScore (String userName) {
        Path saveTo = getPath();
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy HH:mm");
        String formatted = now.format(formatter);
            try (BufferedWriter saving = Files.newBufferedWriter(saveTo, StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
                saving.write( userName + ";" +moves + ";" + formatted + "\n");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
    }
    private List<String> readHighScores (){
        String savedScore;
        List<String> scoreList = new ArrayList<>();
        try (BufferedReader bf = new BufferedReader(new FileReader("src/Spel15/Scores.txt"))) {
            while ((savedScore = bf.readLine()) != null) {
                scoreList.add(savedScore);
            }
        } catch (Exception e) {
            System.out.println("Något gick fel vid filinläsningen, försök igen.");
        }
        return scoreList;
    }
    public List<String[]> splitScoreString (){
        List<String> scoreList = readHighScores();
        List<String[]> savedMoves = new ArrayList<>();
        for (String savedScore : scoreList) {
            String[] movesAndDate = savedScore.split(";");
            savedMoves.add(movesAndDate);
        }
        return savedMoves;
    }
    public JTextArea scorePrintout (String type){
        List<String[]> movesAndDate = splitScoreString();
        String whatToPrint;
        String printout = "";
        if (!movesAndDate.isEmpty()) {
            for (int i = 0; i < movesAndDate.size(); i++) {
                if (type.equals("name")) {
                    whatToPrint = movesAndDate.get(i)[0];
                }
                else if (type.equals("moveCount")) {
                    whatToPrint = movesAndDate.get(i)[1];
                }
                else {
                    whatToPrint = movesAndDate.get(i)[2];
                }
                printout = printout + whatToPrint + "\n";
            }
        }
        return new JTextArea(printout);
    }
    public JPanel scorePanel () {
        JPanel scorePanel = new JPanel(new BorderLayout());

        JLabel header = new JLabel("Highscores:");
        header.setPreferredSize(new Dimension(500, 20));

        JPanel centerPanel = new JPanel(new BorderLayout());

        JPanel scoreHeader = new JPanel(new GridLayout(1,3));
        JLabel showName = new JLabel("Namn:");
        JLabel showMoves = new JLabel("Antal drag:");
        JLabel showDate = new JLabel("Datum:");
        scoreHeader.add(showName);
        scoreHeader.add(showMoves);
        scoreHeader.add(showDate);
        centerPanel.add(scoreHeader, BorderLayout.NORTH);

        JPanel showScores = new JPanel(new GridLayout(1, 3));
        showScores.add(scorePrintout((name)));
        showScores.add(scorePrintout(moveCount));
        showScores.add(scorePrintout(date));
        centerPanel.add(showScores, BorderLayout.CENTER);

        scorePanel.add(header, BorderLayout.NORTH);
        scorePanel.add(centerPanel, BorderLayout.CENTER);
        scorePanel.setBackground(Color.pink);
        scorePanel.setVisible(true);

        return scorePanel;
    }
    private String name = "name";
    private String moveCount = "moveCount";
    private String date = "date";
}

