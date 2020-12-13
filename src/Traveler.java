public class Traveler extends Token
{
    TravelStrategy strategy;
    int feastCounter;
    int readyToTravel;

    public Traveler( String name, TravelStrategy strategy )
    {
        super( name );
        this.strategy = strategy;
        feastCounter = 0;
        readyToTravel = 0;
    }

    public void move()
    {
        strategy.move( currentLocation, diceRollOutcome, readyToTravel, feastCounter );
    }
}