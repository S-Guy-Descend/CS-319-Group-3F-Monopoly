import java.util.ArrayList;

public class Game {
    // instance is a Singleton of the game class
    public static Game instance = new Game();
    public ArrayList<Token> tokens;
    public Board board;
    int playerCount = 0;
    int turnCounter = 0;

    private Game() {
        tokens = new ArrayList<Token>();
        board = new Board();
        addPlayers(4);
    }

    public void addPlayers(int playerCount) {
        this.playerCount += playerCount;
        for(int i = 0; i < playerCount; i++) {
            Wizard player = new Wizard("Player"+i);
            player.ID = i;
            tokens.add(player);
        }
    }

    public int advanceTurn() {
        int playerIdToPlay = turnCounter % playerCount;
        turnCounter++;
        if(tokens.get(playerIdToPlay).dungeonCountdown > 0) {
            tokens.get(playerIdToPlay).dungeonCountdown -= 1;
            tokens.get(playerIdToPlay).diceRollOutcome = 0;
            System.out.println("This player is in dungeon, Remaining turns in dungeon: " + tokens.get(playerIdToPlay).dungeonCountdown);
            advanceTurn();
        }
        return playerIdToPlay;
    }

    public void initializeGame() {

    }

    public void endGame() {

    }
}
