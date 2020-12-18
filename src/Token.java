import java.util.ArrayList;

public class Token
{
    String name;
    int ID;
    int money;
    int color;
    int diceRollOutcome;
    int dungeonCountdown;
    int turnsPlayed;
    boolean isBankrupt;
    int currentLocation;
    int ownedSmithCount;
    int ownedTransportCount;
    ArrayList<ScrollCard> scrollCards;

    public Token( String name )
    {
        this.name = name;
        scrollCards = new ArrayList<ScrollCard>();
        money = 150000;
        turnsPlayed = 0;
        isBankrupt = false;
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
        if(currentLocation + diceRollOutcome >= 40) {
            money += 20000;
        }

        currentLocation = (currentLocation + diceRollOutcome) % 40;

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
            Transport currentSquare = (Transport) Game.instance.board.map[this.currentLocation];
            if(currentSquare.isPurchased && currentSquare.ownerId != ID) {
                payMoney(Game.instance.tokens.get(currentSquare.ownerId), currentSquare.rent);
            }
        }
        else if(Game.instance.board.map[this.currentLocation] instanceof TaxSquare) {
            payTax(((TaxSquare) Game.instance.board.map[this.currentLocation]).taxAmount);
        }
        else if(Game.instance.board.map[this.currentLocation] instanceof GoToDungeon){
            ((GoToDungeon) Game.instance.board.map[this.currentLocation]).sendTokenToDungeon(this);
        }
        else if(Game.instance.board.map[this.currentLocation] instanceof Feast){
            ((Feast) Game.instance.board.map[this.currentLocation]).buffTokenClass(this);
        }
    }
    // If you face a problem in the future its probably because of this method. For emergencies pls call BKB.
    public void forceMove(int newPlace, boolean passGO)
    {
        if(newPlace < currentLocation && passGO) {
            ((StartingSquare) Game.instance.board.map[0]).giveLeapMoney(this);
        }

        currentLocation = newPlace;
        diceRollOutcome = 0;
        this.move();
        /*
        newPlace = (newPlace+40) % 40;

        if(dontPassGO) {
            diceRollOutcome = newPlace - currentLocation;
        }
        else {
            diceRollOutcome = 40 - currentLocation + newPlace;
        }
        this.move();
         */
    }

    public boolean purchaseLand()
    {
        if(Game.instance.board.map[this.currentLocation] instanceof Town) {
            Town currentTown = (Town) Game.instance.board.map[this.currentLocation];
            if(currentTown.isPurchased) {
                return false;
            }
            if(money < currentTown.price) {
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
            if(money < currentSmith.price) {
                return false;
            }
            money -= currentSmith.price;
            currentSmith.changeOwner(ID);
            ownedSmithCount++;
            return true;
        }
        else if(Game.instance.board.map[this.currentLocation] instanceof Transport) {
            Transport currentTransport = (Transport) Game.instance.board.map[this.currentLocation];
            if(currentTransport.isPurchased) {
                return false;
            }
            if(money < currentTransport.price) {
                return false;
            }
            money -= currentTransport.price;
            currentTransport.changeOwner(ID);
            this.ownedTransportCount += 1;
            currentTransport.calculateRent();
            return true;
        }
        return false;
    }

    public boolean build()
    {
        if(Game.instance.board.map[this.currentLocation] instanceof Town) {
            Town currentTown = (Town) Game.instance.board.map[this.currentLocation];
            if(money < currentTown.innPrice) {
                System.out.println("You don't have enough money to build");
                return false;
            }
            if(!this.isColorGroupOwner(currentTown)){
                System.out.println("You don't own all of the color group. Can't Build");
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

    public boolean isColorGroupOwner (Town town) {
        ColorGroup colorGroup = town.colorGroup;
        ArrayList<Town> townsOfColorGroup = colorGroup.getAllTownsOfColorGroup();

        for (int i = 0; i < townsOfColorGroup.size(); i++) {
            if (townsOfColorGroup.get(i).ownerId != this.ID) {
                return false;
            }
        }
        return true;
    }

    public void drawScroll()
    {
        int effectID = (int) (Math.random() * Game.instance.board.scrollDeck.length);
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
        int effectID = (int) (Math.random() * Game.instance.board.fortuneDeck.length);
        Game.instance.board.fortuneDeck[effectID].performEffect( this );
        System.out.println("FortuneCard " + Game.instance.board.fortuneDeck[effectID].cardName + " is drawn");
    }

    public void payMoney( Token receiver, int amount )
    {
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
