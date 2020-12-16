import java.util.Scanner;

public class Main {

    public static void main(String[] args){
        //initializes players and board
        Game game = new Game();
        int turnCounter;
        boolean running = true;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the game");

        GAME:
        while(running){
            System.out.println("--------------------------------------------------");
            turnCounter = game.advanceTurn();
            Token player = game.tokens.get(turnCounter);
            player.currentLocation = 15;
            System.out.println("Player" + turnCounter + " rolled: " + player.diceRollOutcome);
            player.move();
            System.out.println("Player" + turnCounter + "'s current location: " + player.currentLocation);
            if(player.purchaseLand()){
                System.out.println("Player" + turnCounter + " purchased: " + game.board.map[player.currentLocation].name);
            }
            else {
                System.out.println("This square is not purchasable");
            }
            //player.build();
            System.out.println(player.money);
            scanner.nextLine();
        }
    }
}
