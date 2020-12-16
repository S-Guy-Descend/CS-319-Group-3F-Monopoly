import com.sun.tools.javadoc.Start;

import java.util.ArrayList;

public class Token
{
    String name;
    int ID;
    int money;
    int color;
    int diceRollOutcome;
    int dungeonCountdown;
    int[] ownedLands;
    int turnsPlayed;
    boolean isInDungeon;
    boolean isBankrupt;
    int currentLocation;
    int ownedSmithCount;
    int ownedTransportCount;
    ArrayList<ScrollCard> scrollCards;

    public Token( String name )
    {
        this.name = name;
        scrollCards = new ArrayList<ScrollCard>();
        money = 1500;
        turnsPlayed = 0;
        isBankrupt = false;
        isInDungeon = false;
        ownedSmithCount = 0;
        ownedTransportCount = 0;
        currentLocation = 0;
        dungeonCountdown = 0;
    }

    public void rollDice()
    {
        int diceTotal = 0;
        int die1 = 0;
        int die2 = 0;

        die1 = (int)(Math.random()*6 + 1);
        die2 = (int)(Math.random()*6 + 1);
        diceTotal = die1 + die2;

        diceRollOutcome = diceTotal;
    }

    public void move()
    {
        currentLocation = currentLocation + diceRollOutcome;
        if(Game.instance.board.map[this.currentLocation] instanceof ScrollSquare) {
            drawScroll();
            return;
        }
        else if(Game.instance.board.map[this.currentLocation] instanceof FortuneSquare) {
            drawFortuneCard();
            return;
        }
        else if(Game.instance.board.map[this.currentLocation] instanceof Town) {
            Town currentSquare = (Town) Game.instance.board.map[this.currentLocation];
            if(currentSquare.isPurchased && currentSquare.ownerId != ID) {
                payMoney(Game.instance.tokens.get(Integer.valueOf(currentSquare.ownerId)), currentSquare.rent);
            }
        }
        else if(Game.instance.board.map[this.currentLocation] instanceof Smith) {
            Smith currentSquare = (Smith) Game.instance.board.map[this.currentLocation];
            if(currentSquare.isPurchased && currentSquare.ownerId != ID) {
                payMoney(Game.instance.tokens.get(currentSquare.ownerId), currentSquare.rent);
            }
        }
        else if(Game.instance.board.map[this.currentLocation] instanceof Transport) {
            System.out.println("Entered move function Transport tab");
            Transport currentSquare = (Transport) Game.instance.board.map[this.currentLocation];
            if(currentSquare.isPurchased && currentSquare.ownerId != ID) {
                System.out.println("Entered2");
                System.out.println(currentSquare.ownerId);
                Token owner = Game.instance.tokens.get(currentSquare.ownerId);
                System.out.println("Owner: " + owner.name);
                payMoney(owner, currentSquare.rent);
            }
        }
        else if(Game.instance.board.map[this.currentLocation] instanceof taxSquare) {
            taxSquare currentSquare = (taxSquare) Game.instance.board.map[this.currentLocation];
            payTax(currentSquare.taxAmount);
        }
        else if(Game.instance.board.map[this.currentLocation] instanceof GoToDungeon){
            GoToDungeon currentSquare = (GoToDungeon) Game.instance.board.map[this.currentLocation];
            currentSquare.sendTokenToDungeon(this);
        }
        else if(Game.instance.board.map[this.currentLocation] instanceof Feast){
            Feast currentSquare = (Feast) Game.instance.board.map[this.currentLocation];
            currentSquare.buffTokenClass(this);
        }
        else if(Game.instance.board.map[this.currentLocation] instanceof StartingSquare){
            StartingSquare currentSquare = (StartingSquare) Game.instance.board.map[this.currentLocation];
            currentSquare.giveLeapMoney(this);
        }
    }
    // maybe we can combine this method's logic with move method so that we wouldn't have to duplicate code from move to here
    public void forceMove( int newPlace )
    {
        currentLocation = newPlace;
        //why do we need following two lines?
        diceRollOutcome = 0;
        this.move();
    }

    public boolean purchaseLand()
    {
        if(Game.instance.board.map[this.currentLocation] instanceof Town) {
            Town currentTown = (Town) Game.instance.board.map[this.currentLocation];
            if(currentTown.isPurchased) {
                return false;
            }
            money -= currentTown.price;
            currentTown.changeOwner(ID);
            return true;
        }
        else if(Game.instance.board.map[this.currentLocation] instanceof Smith) {
            Smith currentSmith = (Smith) Game.instance.board.map[this.currentLocation];
            if(currentSmith.isPurchased) {
                return false;
            }
            money -= currentSmith.price;
            currentSmith.changeOwner(ID);
            ownedSmithCount++;
            return true;
        }
        else if(Game.instance.board.map[this.currentLocation] instanceof Transport) {
            System.out.println("Entered purchaseLand Transport tab");
            Transport currentTransport = (Transport) Game.instance.board.map[this.currentLocation];
            if(currentTransport.isPurchased) {
                return false;
            }
            money -= currentTransport.price;
            currentTransport.changeOwner(ID);
            System.out.println("Update transport owner, new owner: " + currentTransport.ownerId);
            System.out.println(this.name);
            this.ownedTransportCount += 1;
            System.out.println("Owned transport count: " + this.ownedTransportCount);
            currentTransport.calculateRent();
            return true;
        }
        return false;
    }

    public boolean build()
    {
        if(Game.instance.board.map[this.currentLocation] instanceof Town) {
            Town currentTown = (Town) Game.instance.board.map[this.currentLocation];
            if(currentTown.ownerId != ID){
                return false;
            }
            money -= currentTown.innPrice;
            currentTown.numberOfInns += 1;
            currentTown.calculateRent();
            return true;
        }
        return false;
    }

    public void endTurn()
    {
        turnsPlayed++;
        Game.instance.advanceTurn();
    }

    public void drawScroll()
    {
        int effectID = (int)Math.random() * Game.instance.board.scrollDeck.length;
        scrollCards.add( Game.instance.board.scrollDeck[effectID] );
        System.out.println("Scroll " + Game.instance.board.scrollDeck[effectID].cardName + " is drawn");
    }

    public void useScroll( int scrollIndex, Token effectVictim )
    {
        scrollCards.get( scrollIndex ).performEffect( this, effectVictim );
        scrollCards.remove( scrollIndex );
    }

    public void drawFortuneCard()
    {
        int effectID = (int)Math.random() * Game.instance.board.fortuneDeck.length;
        Game.instance.board.fortuneDeck[effectID].performEffect( this );
        System.out.println("FortuneCard " + Game.instance.board.fortuneDeck[effectID].cardName + " is drawn");
    }

    public void payMoney( Token receiver, int amount )
    {
        System.out.println("Pay :" + amount );
        money = money - amount;
        receiver.receiveMoney( amount );
    }

    public void payTax( int amount )
    {
        money = money - amount;
    }

    public void respondToTradeOffer( boolean response )
    {

    }

    public void receiveMoney( int amount )
    {
        money = money + amount;
    }

    public void prepareTradeOffer( Token receiver )
    {
        //Square[] placesToGive;
        //Square[] placesToTake;
        int moneyToGive;
        int moneyToTake;

        //makeTradeOffer( placesToGive, placesToTake, moneyToGive, moneyToTake );
    }

    /*
    public void makeTradeOffer( Square[] placesToGive, Square[] placesToTake, int moneyToGive, int moneyToTake)
    {

    }*/



}
