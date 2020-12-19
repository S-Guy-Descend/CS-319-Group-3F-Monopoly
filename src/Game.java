import java.io.Serializable;
import java.util.ArrayList;

public class Game implements Serializable {
    // instance is a Singleton of the game class
    public static Game instance = new Game();
    public ArrayList<Token> tokens;
    public Board board;
    int playerCount = 0;
    int turnCounter = 0;

    private Game() {
        tokens = new ArrayList<Token>();
        board = new Board();
    }

    public void addPlayer(int playerID, String playerClass) {
        switch (playerClass) {
            case "Traveler (One-in-Two)":
                tokens.add(new Traveler("Player" + playerID, new OneInTwo()));
                tokens.get(tokens.size() - 1).ID = playerID;
                playerCount++;
                break;
            case "Traveler (Three-in-Five)":
                tokens.add(new Traveler("Player" + playerID, new ThreeInFive()));
                tokens.get(tokens.size() - 1).ID = playerID;
                playerCount++;
                break;
            case "Noble":
                tokens.add(new Noble("Player" + playerID) );
                tokens.get(tokens.size() - 1).ID = playerID;
                playerCount++;
                break;
            case "Knight":
                tokens.add(new Knight("Player" + playerID) );
                tokens.get(tokens.size() - 1).ID = playerID;
                playerCount++;
                break;
            case "Treasure Hunter":
                tokens.add(new TreasureHunter("Player" + playerID) );
                tokens.get(tokens.size() - 1).ID = playerID;
                playerCount++;
                break;
            case "Wizard":
                tokens.add(new Wizard("Player" + playerID) );
                tokens.get(tokens.size() - 1).ID = playerID;
                playerCount++;
                break;
            case "Fortune Teller":
                tokens.add(new FortuneTeller("Player" + playerID) );
                tokens.get(tokens.size() - 1).ID = playerID;
                playerCount++;
                break;
            case "Thief":
                tokens.add(new Thief("Player" + playerID) );
                tokens.get(tokens.size() - 1).ID = playerID;
                playerCount++;
                break;
            case "Builder":
                tokens.add(new Builder("Player" + playerID) );
                tokens.get(tokens.size() - 1).ID = playerID;
                playerCount++;
                break;
            case "Cardinal":
                tokens.add(new Cardinal("Player" + playerID) );
                tokens.get(tokens.size() - 1).ID = playerID;
                playerCount++;
                break;
        }
        /*
        for(int i = 0; i < playerCount; i++) {
            Wizard player = new Wizard("Player"+i);
            player.ID = i;
            tokens.add(player);
        }*/
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
