package Spel15;
public class Main {
    public static void main (String [] args) {
        Board board = new Board(false);
        Scores scores = new Scores();
        GamePanel panel = new GamePanel(board, scores);
        new GameLogics(board, panel);
    }
}
