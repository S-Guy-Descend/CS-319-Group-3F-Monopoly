import java.io.Serializable;
import java.util.ArrayList;

public class Token implements Serializable
{
    String name;
    int ID;
    int money;
    int color;
    int diceRollOutcome;
    int dungeonCountdown;
    int turnsPlayed;
    int currentLocation;
    int ownedSmithCount;
    int ownedTransportCount;
    ArrayList<ScrollCard> scrollCards;

    TradeRequest currentPendingTradeRequest;

    ArrayList<Integer> activeLands;
    ArrayList<Integer> residenceIDs;


    public Token( String name )
    {
        this.name = name;
        scrollCards = new ArrayList<ScrollCard>();
        activeLands = new ArrayList<Integer>();
        residenceIDs = new ArrayList<Integer>();
        money = 150000;
        turnsPlayed = 0;
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
            if(currentSquare.isPurchased && currentSquare.ownerId != ID && !currentSquare.isMortgaged()) {
                payMoney(Game.instance.tokens.get(Integer.valueOf(currentSquare.ownerId)), currentSquare.rent);
            }
        }
        else if(Game.instance.board.map[this.currentLocation] instanceof Smith) {
            Smith currentSquare = (Smith) Game.instance.board.map[this.currentLocation];
            if(currentSquare.isPurchased && currentSquare.ownerId != ID && !currentSquare.isMortgaged()) {
                payMoney(Game.instance.tokens.get(currentSquare.ownerId), currentSquare.rent);
            }
        }
        else if(Game.instance.board.map[this.currentLocation] instanceof Transport) {
            Transport currentSquare = (Transport) Game.instance.board.map[this.currentLocation];
            if(currentSquare.isPurchased && currentSquare.ownerId != ID && !currentSquare.isMortgaged()) {
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

    public void forceMove(int newPlace, boolean passGO)
    {
        if(newPlace < currentLocation && passGO) {
            ((StartingSquare) Game.instance.board.map[0]).giveLeapMoney(this);
        }

        currentLocation = newPlace;
        diceRollOutcome = 0;
        this.move();
    }

    public boolean isLandPurchasable()
    {
        if(Game.instance.board.map[this.currentLocation] instanceof Town) {
            Town currentTown = (Town) Game.instance.board.map[this.currentLocation];
            if(currentTown.isPurchased) {
                return false;
            }
            if(money < currentTown.price) {
                return false;
            }
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
            return true;
        }
        return false;
    }

    public boolean purchaseLand()
    {
        if ( isLandPurchasable() )
        {
            if(Game.instance.board.map[this.currentLocation] instanceof Town) {
                Town currentTown = (Town) Game.instance.board.map[this.currentLocation];
                money -= currentTown.price;
                currentTown.changeOwner(ID);
                activeLands.add( currentLocation );
                return true;
            }
            else if(Game.instance.board.map[this.currentLocation] instanceof Smith) {
                Smith currentSmith = (Smith) Game.instance.board.map[this.currentLocation];
                money -= currentSmith.price;
                currentSmith.changeOwner(ID);
                ownedSmithCount++;
                return true;
            }
            else if(Game.instance.board.map[this.currentLocation] instanceof Transport) {
                Transport currentTransport = (Transport) Game.instance.board.map[this.currentLocation];
                money -= currentTransport.price;
                currentTransport.changeOwner(ID);
                this.ownedTransportCount += 1;
                ((Transport)Game.instance.board.map[5]).calculateRent();
                ((Transport)Game.instance.board.map[15]).calculateRent();
                ((Transport)Game.instance.board.map[25]).calculateRent();
                ((Transport)Game.instance.board.map[35]).calculateRent();
                activeLands.add( currentLocation );
                return true;
            }
            return false;
        }
        return false;
    }

    public boolean isBuildAvailable()
    {
        if(Game.instance.board.map[this.currentLocation] instanceof Town) {
            Town currentTown = (Town) Game.instance.board.map[this.currentLocation];
            if( currentTown.numberOfInns >= currentTown.MAX_NUMBER_OF_HOUSES )
            {
                System.out.println( "There are already max number of buildings" );
                return false;
            }
            if(money < currentTown.innPrice) {
                System.out.println("You don't have enough money to build");
                return false;
            }
            if(!this.isColorGroupOwner(currentTown)){
                System.out.println("You don't own all of the color group. Can't Build");
                return false;
            }
            return true;
        }
        return false;
    }

    public boolean build()
    {
        if ( isBuildAvailable() )
        {
            Town currentTownToBuild = (Town) Game.instance.board.map[this.currentLocation];
            money -= currentTownToBuild.innPrice;
            currentTownToBuild.numberOfInns += 1;
            currentTownToBuild.calculateRent();
            if ( !residenceIDs.contains(this.currentLocation) )
            {
                residenceIDs.add(this.currentLocation);
            }
            System.out.println( "Build successful" );
            return true;
        }
        return false;
    }

    public boolean isMortgageable( int locationToMortgage )
    {
        if(Game.instance.board.map[locationToMortgage] instanceof Town) {
            Town currentTown = (Town) Game.instance.board.map[locationToMortgage];
            if(currentTown.ownerId != this.ID || currentTown.isMortgaged()) {
                System.out.println("You cant mortgage this land");
                return false;
            }
            return true;
        }
        else if(Game.instance.board.map[locationToMortgage] instanceof Smith) {
            Smith currentSmith = (Smith) Game.instance.board.map[locationToMortgage];
            if(currentSmith.ownerId != this.ID || currentSmith.isMortgaged()) {
                System.out.println("You cant mortgage this land");
                return false;
            }
            return true;
        }
        else if(Game.instance.board.map[locationToMortgage] instanceof Transport) {
            Transport currentTransport = (Transport) Game.instance.board.map[locationToMortgage];
            if(currentTransport.ownerId != this.ID || currentTransport.isMortgaged()) {
                System.out.println("You cant mortgage this land");
                return false;
            }
            return true;
        }
        return false;
    }

    public boolean mortgageLand( int locationToMortgage ) {
        if ( isMortgageable( locationToMortgage ) )
        {
            if(Game.instance.board.map[locationToMortgage] instanceof Town) {
                Town currentTown = (Town) Game.instance.board.map[locationToMortgage];
                money += currentTown.mortgagePrice;
                currentTown.setAsMortgaged();
                System.out.println("Land is mortgaged");
                activeLands.remove( new Integer(locationToMortgage) );
                if ( residenceIDs.contains( locationToMortgage ) )
                {
                    residenceIDs.remove( new Integer(locationToMortgage) );
                }
                return true;
            }
            else if(Game.instance.board.map[locationToMortgage] instanceof Smith) {
                Smith currentSmith = (Smith) Game.instance.board.map[locationToMortgage];
                money += currentSmith.mortgagePrice;
                currentSmith.setAsMortgaged();
                System.out.println("Land is mortgaged");
                return true;
            }
            else if(Game.instance.board.map[locationToMortgage] instanceof Transport) {
                Transport currentTransport = (Transport) Game.instance.board.map[locationToMortgage];
                money += currentTransport.mortgagePrice;
                currentTransport.setAsMortgaged();
                ownedTransportCount --;
                ((Transport)Game.instance.board.map[5]).calculateRent();
                ((Transport)Game.instance.board.map[15]).calculateRent();
                ((Transport)Game.instance.board.map[25]).calculateRent();
                ((Transport)Game.instance.board.map[35]).calculateRent();
                System.out.println("Land is mortgaged");
                activeLands.remove( new Integer(locationToMortgage) );
                return true;
            }
            return false;
        }
        return false;
    }

    public boolean isUnmortgageable( int locationToUnmortgage )
    {
        if(Game.instance.board.map[locationToUnmortgage] instanceof Town) {
            Town currentTown = (Town) Game.instance.board.map[locationToUnmortgage];
            if(currentTown.ownerId != this.ID || !currentTown.isMortgaged()) {
                System.out.println("You cant UNMORTGAGE this land");
                return false;
            }
            return true;
        }
        else if(Game.instance.board.map[locationToUnmortgage] instanceof Smith) {
            Smith currentSmith = (Smith) Game.instance.board.map[locationToUnmortgage];
            if(currentSmith.ownerId != this.ID || !currentSmith.isMortgaged()) {
                System.out.println("You cant UNMORTGAGE this land");
                return false;
            }
            return true;
        }
        else if(Game.instance.board.map[locationToUnmortgage] instanceof Transport) {
            Transport currentTransport = (Transport) Game.instance.board.map[locationToUnmortgage];
            if(currentTransport.ownerId != this.ID || !currentTransport.isMortgaged()) {
                System.out.println("You cant UNMORTGAGE this land");
                return false;
            }
            return true;
        }
        return false;
    }


    public boolean redeemMortgage( int locationToUnmortgage ) {
        if ( isUnmortgageable( locationToUnmortgage ) )
        {
            if(Game.instance.board.map[locationToUnmortgage] instanceof Town) {
                Town currentTown = (Town) Game.instance.board.map[locationToUnmortgage];
                money -= (int) (currentTown.mortgagePrice * currentTown.MORTGAGE_REDEMPTION_MULTIPLIER);
                currentTown.removeMortgage();
                System.out.println("Land is UNMORTGAGED");
                activeLands.add(locationToUnmortgage);
                if ( ((Town) Game.instance.board.map[locationToUnmortgage]).numberOfInns > 0 )
                {
                    residenceIDs.add(locationToUnmortgage);
                }
                return true;
            }
            else if(Game.instance.board.map[locationToUnmortgage] instanceof Smith) {
                Smith currentSmith = (Smith) Game.instance.board.map[locationToUnmortgage];
                money -= (int) (currentSmith.mortgagePrice * currentSmith.MORTGAGE_REDEMPTION_MULTIPLIER);
                currentSmith.removeMortgage();
                System.out.println("Land is UNMORTGAGED");
                return true;
            }

            else if(Game.instance.board.map[locationToUnmortgage] instanceof Transport) {
                Transport currentTransport = (Transport) Game.instance.board.map[locationToUnmortgage];
                money -= (int) (currentTransport.mortgagePrice * currentTransport.MORTGAGE_REDEMPTION_MULTIPLIER);
                currentTransport.removeMortgage();
                ownedTransportCount ++;
                ((Transport)Game.instance.board.map[5]).calculateRent();
                ((Transport)Game.instance.board.map[15]).calculateRent();
                ((Transport)Game.instance.board.map[25]).calculateRent();
                ((Transport)Game.instance.board.map[35]).calculateRent();
                System.out.println("Land is UNMORTGAGED");
                activeLands.add( locationToUnmortgage );
                return true;
            }
            return false;
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

    public boolean isScrollAvailable()
    {
        return ( scrollCards.size() > 0 );
    }

    public void useScroll( int scrollIndex, Token effectVictim )
    {
        if ( scrollIndex >= scrollCards.size() )
        {
            System.out.println( "Scroll card index invalid" );
            return;
        }
        if ( isScrollAvailable() )
        {
            scrollCards.get( scrollIndex ).performEffect( this, effectVictim );
            scrollCards.remove( scrollIndex );
        }
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

    public void receiveMoney( int amount )
    {
        money = money + amount;
    }

    // TRADE FEATURE ON HOLD
    /*
    public void respondToTradeOffer( boolean response )
    {

    }

    public void prepareTradeOffer( Token receiver )
    {
        //Square[] placesToGive;
        //Square[] placesToTake;
        int moneyToGive;
        int moneyToTake;
        // trade kodlarını halledince ownedAreas ve ownerID güncellenecek.
        //makeTradeOffer( placesToGive, placesToTake, moneyToGive, moneyToTake );
    }


    public void makeTradeOffer( Square[] placesToGive, Square[] placesToTake, int moneyToGive, int moneyToTake)
    {

    } */

}
