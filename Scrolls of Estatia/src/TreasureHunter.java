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
        currentLocation = currentLocation + diceRollOutcome;
        treasureCounter ++;
        if ( treasureCounter == 3 )
        {
            this.drawFortuneCard();
            treasureCounter = 0;
        }
    }
}
