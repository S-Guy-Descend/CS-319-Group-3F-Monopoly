import java.util.Scanner;

public class Main {

    public static void main(String[] args){
        //initializes players and board
        int turnCounter;
        boolean running = true;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the game");

        GAME:
        while(running){
            System.out.println("--------------------------------------------------");
            turnCounter = Game.instance.advanceTurn();
            Token player = Game.instance.tokens.get(turnCounter);
            player.currentLocation = 17;
            System.out.println("Player" + turnCounter + " moved: " + player.diceRollOutcome + " squares.");
            player.move();
            System.out.println("Player" + turnCounter + "'s current location: " + player.currentLocation);
            if(player.purchaseLand()){
                System.out.println("Player" + turnCounter + " purchased: " + Game.instance.board.map[player.currentLocation].name);
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
