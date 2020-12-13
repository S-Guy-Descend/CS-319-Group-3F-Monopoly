public class Token
{
    String name;
    int ID;
    int money;
    int color;
    int diceRollOutcome;
    int[] ownedLands;
    int turnsPlayed;
    boolean isInDungeon;
    boolean isBankrupt;
    int currentLocation;
    ScrollCard[] scrollCards;

    public Token( String name )
    {
        this.name = name;
        money = 1500;
        turnsPlayed = 0;
        isBankrupt = false;
        isInDungeon = false;
        currentLocation = 1;
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

    }

    public void drawFortuneCard()
    {

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
        Square[] placesToGive;
        Square[] placesToTake;
        int moneyToGive;
        int moneyToTake;

        makeTradeOffer( placesToGive, placesToTake, moneyToGive, moneyToTake );
    }

    public void makeTradeOffer( Square[] placesToGive, Square[] placesToTake, int moneyToGive, int moneyToTake)
    {

    }



}
