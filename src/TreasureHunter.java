public class TreasureHunter extends Token
{
    int treasureCounter;

    public TreasureHunter( String name )
    {
        super( name );
        treasureCounter = 0;
    }

    public void move()
    {
        if(currentLocation + diceRollOutcome >= 40)
        {
            money += 20000;
        }

        currentLocation = (currentLocation + diceRollOutcome) % 40;

        treasureCounter ++;
        if ( treasureCounter == 3 )
        {
            this.drawFortuneCard();
            treasureCounter = 0;
        }

        activateSquare();
    }
}
