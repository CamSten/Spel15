package Spel15;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board extends JFrame {
    public List<Tile> tiles = new ArrayList<>();


    public Board() {
        addTiles();
        JFrame gameBoardFrame = new JFrame();
        JPanel gameBoard = new JPanel(new GridLayout(4, 4));
        for (Tile t : tiles) {
            gameBoard.add(t);
        }
    }

    public void addTiles() {
        List<Integer> values = getRandomValues();
        for (int i = 0; i < values.size(); i++) {
            int row = (i / 4);
            int col = i % 4;
            Tile tile = new Tile(row, col, values.get(i));
            tiles.add(tile);
        }
    }

    public List<Integer> getRandomValues() {
        List<Integer> values = new ArrayList<>();
        Random randomize = new Random();

        while (values.size() < 16) {
            int randomInt = randomize.nextInt(16);
            boolean used = false;
            for (int v : values) {
                if (v == randomInt) {
                    used = true;
                    break;
                }
            }
            if (!used) {
                values.add(randomInt);
            }
        }
        return values;
    }

    public List<Tile> createTileRow(int rowNumber) {
        int startNumber = rowNumber * 4;
        int endNumber = startNumber + 4;
        return tiles.subList(startNumber, endNumber);
    }
    public boolean checkIfAdjacent(Tile tile1, Tile tile2) {
        int[] tile1Position = tile1.getPosition();
        int[] tile2Position = tile2.getPosition();
        int row1 = tile1Position[0];
        int row2 = tile2Position[0];
        int col1 = tile1Position[1];
        int col2 = tile2Position[1];
        int rowDistance = Math.abs(row1 - row2);
        int colDistance = Math.abs(col1 - col2);

        if ((rowDistance == 1 && colDistance == 0) || (rowDistance == 0 && colDistance == 1)) {
            return true;
        }
        return false;
    }
    public boolean checkIfSolved() {
        for (int r = 0; r < 4; r++) {
            List<Tile> row1 = createTileRow(r);
            for (int i = 0; i < row1.size(); i++) {
                int expectedValue = r * 4 + i;
                if (row1.get(i).getValue() != expectedValue) {
                    return false;
                }
            }
        }
        return true;
    }

    public void switchTiles(Tile tile1, Tile tile2) {
        int tempValue = tile1.getValue();
        int[] tile1Position = tile1.getPosition();
        int[] tile2Position = tile2.getPosition();
        tile1.setPosition(tile2Position);
        tile1.setValue(tile2.getValue());
        tile2.setPosition(tile1Position);
        tile2.setValue(tempValue);
    }

    public void showEndMessage() {

    }
}
/*       List<Tile>tilesForRow = createTileRow(1);
        for (Tile t : tilesForRow) {
            row1.add(t);
        }

        JPanel row2 = new JPanel(new GridLayout(1, 4));
        tilesForRow = createTileRow(2);
        for (Tile t : tilesForRow) {
            row2.add(t);
        }

        JPanel row3 = new JPanel(new GridLayout(1, 4));
        tilesForRow = createTileRow(3);
        for (Tile t : tilesForRow) {
            row3.add(t);
        }
        JPanel row4 = new JPanel(new GridLayout(1, 4));
        tilesForRow = createTileRow(4);
        for (Tile t : tilesForRow) {
            row4.add(t);
        }*/

