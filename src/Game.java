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

    }

    public int advanceTurn() {
        System.out.println("START OF ADVANCE TURN");
        System.out.println("TURNCOUNTER = " + turnCounter);
        System.out.println("PLAYERCOUNT = " + playerCount);
        int playerIdToPlay = turnCounter % playerCount;
        System.out.println("MIDDLE OF ADVANCE TURN");
        System.out.println("PLAYERID TO PLAY = " + playerIdToPlay);
        System.out.println("TURNCOUNTER = " + turnCounter);
        System.out.println("PLAYERCOUNT = " + playerCount);
        turnCounter++;
        System.out.println("END OF ADVANCE TURN");
        System.out.println("PLAYERID TO PLAY = " + playerIdToPlay);
        System.out.println("TURNCOUNTER = " + turnCounter);
        System.out.println("PLAYERCOUNT = " + playerCount);
        if(tokens.get(playerIdToPlay).dungeonCountdown > 0) {
            tokens.get(playerIdToPlay).dungeonCountdown -= 1;
            tokens.get(playerIdToPlay).diceRollOutcome = 0;
            System.out.println("This player is in dungeon, Remaining turns in dungeon: " + tokens.get(playerIdToPlay).dungeonCountdown);
            return advanceTurn();
        }
        // ID SIKINTISI OLABİLİR
        // ID SIKINTISI OLABİLİR
        // ID SIKINTISI OLABİLİR
        // ID SIKINTISI OLABİLİR
        // ID SIKINTISI OLABİLİR
        /*
        if ( tokens.get(playerIdToPlay).money < 0 ) {
            for (int i = 0; i < board.map.length; i++) {
                boolean ownedTown = board.map[i] instanceof Town
                        && ((Town) board.map[i]).ownerId == playerIdToPlay;
                boolean ownedSmith = board.map[i] instanceof Smith
                        && ((Smith) board.map[i]).ownerId == playerIdToPlay;
                boolean ownedTransport = board.map[i] instanceof Transport
                        && ((Transport) board.map[i]).ownerId == playerIdToPlay;
                if ( (ownedSmith || ownedTown || ownedTransport) && !(tokens.get(playerIdToPlay).activeLands.contains(i)) ) {
                    tokens.get(playerIdToPlay).activeLands.add(i);
                }
            }
            for (int i = 0; i < tokens.get(playerIdToPlay).activeLands.size(); i++) {
                if ( board.map[tokens.get(playerIdToPlay).activeLands.get(i)] instanceof Smith ) {
                    ((Smith) (board.map[tokens.get(playerIdToPlay).activeLands.get(i)]) ).ownerId = -1;
                    ((Smith) (board.map[tokens.get(playerIdToPlay).activeLands.get(i)]) ).isMortgaged = false;
                    ((Smith) (board.map[tokens.get(playerIdToPlay).activeLands.get(i)]) ).isPurchased = false;
                }
                else if ( board.map[tokens.get(playerIdToPlay).activeLands.get(i)] instanceof Town ) {
                    ((Town) (board.map[tokens.get(playerIdToPlay).activeLands.get(i)]) ).ownerId = -1;
                    ((Town) (board.map[tokens.get(playerIdToPlay).activeLands.get(i)]) ).isMortgaged = false;
                    ((Town) (board.map[tokens.get(playerIdToPlay).activeLands.get(i)]) ).numberOfInns = 0;
                    ((Town) (board.map[tokens.get(playerIdToPlay).activeLands.get(i)]) ).isPurchased = false;
                }
                else if ( board.map[tokens.get(playerIdToPlay).activeLands.get(i)] instanceof Transport ) {
                    ((Transport) (board.map[tokens.get(playerIdToPlay).activeLands.get(i)]) ).ownerId = -1;
                    ((Transport) (board.map[tokens.get(playerIdToPlay).activeLands.get(i)]) ).isMortgaged = false;
                    ((Transport) (board.map[tokens.get(playerIdToPlay).activeLands.get(i)]) ).isPurchased = false;
                }
            }

            for (int i = playerIdToPlay + 1; i < tokens.size(); i++) {
                for (int j = 0; j < board.map.length; j++) {
                    if ( board.map[i] instanceof Town && ( (Town) board.map[i]).ownerId == i ) {
                        ( (Town) board.map[i]).ownerId--;
                    }
                    else if ( board.map[i] instanceof Smith && ( (Smith) board.map[i]).ownerId == i) {
                        ( (Smith) board.map[i]).ownerId--;
                    }
                    else if ( board.map[i] instanceof Transport && ( (Transport) board.map[i]).ownerId == i ) {
                        ( (Transport) board.map[i]).ownerId--;
                    }
                }
            }
            tokens.remove(playerIdToPlay);
            advanceTurn();
        } */
        return playerIdToPlay;
    }

    public void initializeGame() {

    }

    public void endGame() {

    }
}
