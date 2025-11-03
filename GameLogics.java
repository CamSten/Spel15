package Spel15;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameLogics implements ActionListener {
    private Board board;
    private Tile activeTile = null;

    public GameLogics(Board board) {
        this.board = board;
        for (Tile t : board.tiles) {
            t.addActionListener(this);
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        Tile clicked = (Tile) e.getSource();
        checkTileAction(clicked);

    }
    public void checkTileAction(Tile clicked) {
        if (activeTile == null) {
            activeTile = clicked;
        }
        else if (clicked.getValue() == 0)  {
            if(board.checkIfAdjacent(activeTile, clicked)) {
                board.switchTiles(activeTile, clicked);
                if (board.checkIfSolved()) {
                    endGame();
                }
            }
        }
        else {
            activeTile = clicked;
        }
    }
    public void endGame () {
        board.showEndMessage();
    }
}
