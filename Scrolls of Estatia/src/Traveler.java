public class Traveler extends Token
{
    int feastCounter;
    boolean extraSquare;

    public Traveler(String name)
    {
        super( name );
        feastCounter = 0;
        extraSquare = false;
    }

    public void move()
    {
        if ( feastCounter != 0 )
        {
            currentLocation = currentLocation + diceRollOutcome + 1;
            feastCounter = feastCounter - 1;
            if ( feastCounter == 0 )
            {
                extraSquare = false;
            }
        }

        if ( extraSquare )
        {
            currentLocation = currentLocation + diceRollOutcome + 1;
        }
        else
        {
            currentLocation = currentLocation + diceRollOutcome;
        }
        extraSquare = !extraSquare;
    }
}