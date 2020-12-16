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
    ArrayList<ScrollCard> scrollCards;

    public Token( String name )
    {
        this.name = name;
        scrollCards = new ArrayList<ScrollCard>();
        money = 1500;
        turnsPlayed = 0;
        isBankrupt = false;
        isInDungeon = false;
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
    }

    public void forceMove( int newPlace )
    {
        currentLocation = newPlace;
        diceRollOutcome = 0;
        this.move();
    }

    public void purchaseLand()
    {

    }

    public void build()
    {

    }

    public void endTurn()
    {
        Game.instance.advanceTurn();
    }

    public void drawScroll()
    {
        int effectID = (int)Math.random() * Game.instance.board.scrollDeck.length;
        scrollCards.add( Game.instance.board.scrollDeck[effectID] );
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
