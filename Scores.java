package Spel15;

import javax.swing.*;
import javax.swing.plaf.basic.BasicBorders;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Scores {
    List<String> scoreList = new ArrayList<>();
    private int moves = 0;
    private int maxSavedScores = 25;
    private enum scoreValue {NAME, MOVES, DATE }
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy HH:mm");


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

    private List<String> readSaveFile() {
        scoreList.clear();
        Path saveFile = getPath();
        String score;
        try (BufferedReader reader = new BufferedReader(new FileReader(saveFile.toFile()))) {
            while ((score = reader.readLine()) != null) {
                scoreList.add(score);
            }
        } catch (Exception e) {
            System.out.println(errorFileRead);
        }
        return scoreList;
    }
    public void saveScore (String userName) {
        if (scoreList.size() >= maxSavedScores) {
            removeScore();
        }
        Path saveTo = getPath();
        try (BufferedWriter saving = Files.newBufferedWriter(saveTo, StandardOpenOption.CREATE, StandardOpenOption.APPEND))
        {LocalDateTime now = LocalDateTime.now();
            String formatted = now.format(formatter);
            saving.write(userName.trim() + ";" + moves + ";" + formatted + "\n");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public List<String[]> splitScoreString (){
        List<String[]> savedMoves = new ArrayList<>();
        for (String savedScore : scoreList) {
            String[] scoreValues = savedScore.split(";");
            savedMoves.add(scoreValues);
        }
        return savedMoves;
    }
    public String scorePrintout (scoreValue type){
        List<String[]> scoreValues = splitScoreString();
        String printout = "";
        int start;
        int end;
        int step;
        if (!scoreValues.isEmpty()) {
            if (type == scoreValue.DATE) {
                start = scoreValues.size()-1;
                end = -1;
                step = -1;
            }
            else {
                start = 0;
                end = scoreValues.size();
                step = 1;
            }
            for (int i = start; i != end; i+=step) {
                String whatToPrint = "";
                switch (type) {
                    case NAME: {
                        whatToPrint = scoreValues.get(i)[0];
                        break;
                    }
                    case MOVES: {
                        whatToPrint = scoreValues.get(i)[1];
                        break;
                    }
                    case DATE: {
                        whatToPrint = scoreValues.get(i)[2];
                        break;
                    }
                }
                printout = printout + whatToPrint + "\n";
            }
        }
        return printout;
    }

    private void removeScore (){
        scoreList.remove(scoreList.size() - 1);
        overWriteFile();
    }

    private void overWriteFile(){
        Path saveTo = getPath();
        try (BufferedWriter overWrite = new BufferedWriter(new FileWriter(saveTo.toFile()))){
            for (String score : scoreList){
                overWrite.write(score + "\n");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void sortList (scoreValue type){
        List<String[]> values = splitScoreString();
        boolean changePlace = false;
        if (!values.isEmpty()) {

            for (int pass = 0; pass < values.size() -1; pass++) {
                for (int i = 1; i < values.size() - pass; i++) {
                    switch (type) {
                        case NAME: {
                            String score1 = values.get(i)[0];
                            String score2 = values.get(i - 1)[0];
                            changePlace = score1.compareToIgnoreCase(score2) < 0;
                            break;
                        }
                        case MOVES: {
                            int score1 = Integer.parseInt(values.get(i)[1].trim());
                            int score2 = Integer.parseInt(values.get(i - 1)[1].trim());
                            changePlace =  score2 > score1;
                            break;
                        }
                        case DATE: {
                            LocalDateTime score1 = LocalDateTime.parse(values.get(i)[2], formatter);
                            LocalDateTime score2 = LocalDateTime.parse(values.get(i - 1)[2], formatter);
                            changePlace = score2.isAfter(score1);
                            break;
                        }
                    }
                    if (changePlace) {
                        String[] temp = values.get(i-1);
                        values.set(i-1, values.get(i));
                        values.set(i, temp);
                    }
                }
            }
        }
        scoreList.clear();
        for (String [] score : values) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < score.length; j++) {
                sb.append(score[j]);
                if (j < score.length -1){
                    sb.append(";");
                }
            }
            scoreList.add(sb.toString());
        }
    }
    public void clearSavedScores(){
        scoreList.clear();
        Path saveFile = getPath();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(saveFile.toFile(), false))) {
        } catch (Exception e) {
            System.out.println(errorFileRead);
        }
    }
    public JPanel scorePanel () {
        readSaveFile();
        JPanel scorePanel = new JPanel(new BorderLayout());

        JLabel header = new JLabel("Highscores:");
        header.setFont(GameFont.headerFont());
        header.setPreferredSize(new Dimension(500, 20));

        JPanel centerPanel = new JPanel(new BorderLayout());

        JPanel scoreHeader = new JPanel(new GridLayout(1,3));
        JTextArea namesArea = new JTextArea(scorePrintout(scoreValue.NAME));
        JTextArea movesArea = new JTextArea(scorePrintout(scoreValue.MOVES));
        JTextArea datesArea = new JTextArea(scorePrintout(scoreValue.DATE));
        namesArea.setBorder(new BasicBorders.FieldBorder(Color.lightGray, Color.DARK_GRAY, Color.pink, Color.MAGENTA));
        movesArea.setBorder(new BasicBorders.FieldBorder(Color.lightGray, Color.DARK_GRAY, Color.pink, Color.MAGENTA));;
        datesArea.setBorder(new BasicBorders.FieldBorder(Color.lightGray, Color.DARK_GRAY, Color.pink, Color.MAGENTA));
        JButton showName = new JButton("Namn:");
        showName.setFont(GameFont.defaultFont());
        showName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sortList(scoreValue.NAME);
                namesArea.setText(scorePrintout(scoreValue.NAME));
                movesArea.setText(scorePrintout(scoreValue.MOVES));
                datesArea.setText(scorePrintout(scoreValue.DATE));
            }
        });
        JButton showMoves = new JButton("Antal drag:");
        showMoves.setFont(GameFont.defaultFont());
        showMoves.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sortList(scoreValue.MOVES);
                namesArea.setText(scorePrintout(scoreValue.NAME));
                movesArea.setText(scorePrintout(scoreValue.MOVES));
                datesArea.setText(scorePrintout(scoreValue.DATE));
            }
        });
        JButton showDate = new JButton("Datum:");
        showDate.setFont(GameFont.defaultFont());
        showDate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sortList(scoreValue.DATE);
                namesArea.setText(scorePrintout(scoreValue.NAME));
                movesArea.setText(scorePrintout(scoreValue.MOVES));
                datesArea.setText(scorePrintout(scoreValue.DATE));
            }
        });

        scoreHeader.add(showName);
        scoreHeader.add(showMoves);
        scoreHeader.add(showDate);
        centerPanel.add(scoreHeader, BorderLayout.NORTH);

        JPanel showScores = new JPanel(new GridLayout(1, 3));

        namesArea.setFont(GameFont.defaultFont());
        movesArea.setFont(GameFont.defaultFont());
        datesArea.setFont(GameFont.defaultFont());
        namesArea.setOpaque(false);
        movesArea.setOpaque(false);
        datesArea.setOpaque(false);
        namesArea.setEditable(false);
        movesArea.setEditable(false);
        datesArea.setEditable(false);
        namesArea.setPreferredSize(new Dimension(150, 200));
        movesArea.setPreferredSize(new Dimension(100, 200));
        datesArea.setPreferredSize(new Dimension(200, 200));
        showScores.add(namesArea);
        showScores.add(movesArea);
        showScores.add(datesArea);
        centerPanel.add(showScores, BorderLayout.CENTER);

        scorePanel.add(header, BorderLayout.NORTH);
        scorePanel.add(centerPanel, BorderLayout.CENTER);
        scorePanel.setBackground(Color.pink);
        scorePanel.setVisible(true);

        return scorePanel;
    }

    private static final String errorFileRead = "Något gick fel vid filinläsningen, försök igen.";
}

