import java.util.ArrayList;

public class Game {
    // instance is a Singleton of the game class
    public static Game instance = new Game();
    public ArrayList<Token> tokens;
    public Board board;
    int playerCount = 0;
    int turnCounter = 0;

    Game() {
        tokens = new ArrayList<Token>();
        board = new Board();
        addPlayers(4);
    }

    public void addPlayers(int playerCount) {
        this.playerCount += playerCount;
        for(int i = 0; i < playerCount; i++) {
            Token player = new Token("Player"+i);
            player.ID = i;
            tokens.add(player);
        }
    }

    public int advanceTurn() {
        int playerIdToPlay = turnCounter % playerCount;
        turnCounter++;
        return playerIdToPlay;
    }

    public void initializeGame() {

    }

    public void endGame() {

    }
}
