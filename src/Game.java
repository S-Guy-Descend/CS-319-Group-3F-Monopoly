import java.util.ArrayList;

public class Game {
    // instance is a Singleton of the game class
    public static Game instance = new Game();
    public ArrayList<Token> tokens;
    public Board board;

    private Game() {
        tokens = new ArrayList<Token>();
        board = new Board();
    }

    public void advanceTurn() {

    }

    public void initializeGame() {

    }

    public void endGame() {

    }
}
