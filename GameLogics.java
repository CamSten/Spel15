package Spel15;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameLogics implements ActionListener {
    private Board board;
    private GamePanel panel;


    public GameLogics(Board board, GamePanel panel) {
        this.board = board;
        this.panel = panel;
        for (Tile t : board.tiles) {
            t.addActionListener(this);
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        Tile clicked = (Tile) e.getSource();
        if  (clicked != null){
            checkTileAction(clicked);
        }
    }
    public void checkTileAction(Tile clicked) {
        Tile emptyTile = board.getEmptyTile();
        if (clicked.getValue() != 0 && emptyTile != null && board.checkIfAdjacent(emptyTile, clicked)) {
            board.switchTiles(emptyTile, clicked);
            panel.showMoves();
            if (board.checkIfSolved()) {
                endGame();
                System.out.println("checkTileAction is working");
            }
        }
    }
    public void endGame () {
        panel.showEndMessage();

    }
}
