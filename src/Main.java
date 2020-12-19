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
            Wizard player = (Wizard) Game.instance.tokens.get(turnCounter);
            player.currentLocation = 1;
            System.out.println("Player" + turnCounter + " moved: " + player.diceRollOutcome + " squares.");
            player.move();
            System.out.println("Player" + turnCounter + "'s current location: " + player.currentLocation);
            if(player.purchaseLand()){
                System.out.println("Player" + turnCounter + " purchased: " + Game.instance.board.map[player.currentLocation].name);
            }
            else {
                System.out.println("This square is not purchasable");
            }
            player.build();
            System.out.println(player.money);
            player.mortgageLand( player.currentLocation );
            System.out.println(player.money);
            player.redeemMortgage( player.currentLocation );
            System.out.println(player.money);
            scanner.nextLine();
        }
    }
}
