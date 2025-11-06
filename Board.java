package Spel15;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Board extends JPanel {
    public List<Tile> tiles = new ArrayList<>();
    private List<Integer>values = new ArrayList<>();
    Scanner scan = new Scanner(System.in);


    public Board(boolean randomizeTiles) {
        setLayout(new GridLayout(4, 4));
        if (randomizeTiles){
            addTiles();
        }
        else {
            addTilesInOrder();
        }
    }

    public void addTiles() {
        values = getValuesForSolvablePuzzle();

        if (tiles.size() == 16) {
            for (int i = 0; i < tiles.size(); i++) {
                Tile tile = tiles.get(i);
                int value = values.get(i);
                tile.setValue(value);
                tile.adjustTile(value);
            }
        } else {
            tiles.clear();
            removeAll();
            for (int i = 0; i < values.size(); i++) {
                int row = (i / 4);
                int col = i % 4;
                Tile tile = new Tile(row, col, values.get(i));
                tiles.add(tile);
                add(tile);
            }
        }
        revalidate();
        repaint();
    }
    public void addTilesInOrder() {
        List<Integer> values = getOrderedValues();
        if (tiles.size() == 16) {
            for (int i = 0; i < tiles.size(); i++) {
                Tile tile = tiles.get(i);
                int value = values.get(i);
                tile.setValue(value);
                tile.adjustTile(value);
            }
        } else {
            tiles.clear();
            removeAll();
            for (int i = 0; i < values.size(); i++) {
                int row = (i / 4);
                int col = i % 4;
                Tile tile = new Tile(row, col, values.get(i));
                tiles.add(tile);
                add(tile);
            }
        }
        revalidate();
        repaint();
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
    private List<Integer> getValuesForSolvablePuzzle() {
        boolean solvable = false;
        while (!solvable) {
            List<Integer> tempValues = getRandomValues();
            values = tempValues;
            if(checkIfSolvable()) {
                solvable = true;
            }
        }
        return values;
    }
    public List<Integer> getOrderedValues() {
        List<Integer> valuesInOrder = new ArrayList<>();{
            for (int i = 1; i < 16; i++) {
                valuesInOrder.add(i);
            }
        }
        valuesInOrder.add(0);
        return valuesInOrder;
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
            List<Tile> row = createTileRow(r);
            for (int i = 0; i < row.size(); i++) {
                int index = r * 4 + i;
                int expected;
                if (index == 15) {
                    expected = 0;
                }
                else  {
                    expected = index+1;
                }
                if (row.get(i).getValue() != expected) {
                    return false;
                }
            }
        }
        System.out.println("checkIfSolved is working");
        return true;
    }
    public Tile getEmptyTile(){
        for (Tile tile : tiles) {
            if (tile.getValue() == 0) {
                return tile;
            }
        }
        return null;
    }

    public void switchTiles(Tile tile1, Tile tile2) {
        int tempValue = tile1.getValue();
        tile1.setValue(tile2.getValue());
        tile2.setValue(tempValue);
        tile1.adjustTile(tile1.getValue());
        tile2.adjustTile(tile2.getValue());
    }
    /*If N(width is odd,
     *   then puzzle instance is solvable if number of inversions is even in the input state.
     * If N is even, puzzle instance is solvable if:
     *   the blank is on an even row counting from the bottom
     *   (second-last, fourth-last, etc.)
     *   AND number of inversions is odd.
     *  OR the blank is on an odd row counting from the bottom
     *   (last, third-last, fifth-last, etc.)
     *   AND number of inversions is even.
     *  For all other cases, the puzzle instance is not solvable.*/
    public boolean checkIfSolvable() {
        int numberOfTiles = values.size();
        int gridWidth = (int) Math.sqrt(numberOfTiles);

        int emptyIndex = values.indexOf(0);
        int rowOfEmptyTileFromBottom = gridWidth - (emptyIndex/gridWidth);

        if (gridWidth%2 !=0){
            if (checkIfEvenInversions()){
                return true;
            }
        }
        else {
            if (rowOfEmptyTileFromBottom % 2 != 0) {
                if (checkIfEvenInversions()) {
                    return true;
                }
            } else {
                if (!checkIfEvenInversions()) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkIfEvenInversions () {
        int count = 0;
        for (int i = 0; i < values.size(); i++) {
            int value1 = values.get(i);
            if (value1 != 0) {
                for (int j = i + 1; j < values.size(); j++) {
                    int value2 = values.get(j);
                    if (value2 != 0 && value1 > value2) {
                        count++;
                    }
                }
            }
        }
        if (count % 2 == 0) {
            return true;
        }
        return false;
    }
}

