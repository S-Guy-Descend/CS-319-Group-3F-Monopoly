public class Traveler extends Token
{
    TravelStrategy strategy;
    int feastCounter;
    int readyToTravel;
    boolean forcedToMove;

    public Traveler( String name, TravelStrategy strategy )
    {
        super( name );
        this.strategy = strategy;
        feastCounter = 0;
        readyToTravel = 0;
        forcedToMove = false;
    }

    public void move()
    {
        strategy.move( this );
    }
}