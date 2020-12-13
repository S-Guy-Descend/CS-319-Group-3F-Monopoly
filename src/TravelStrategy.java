public interface TravelStrategy
{
    void move( int currentLocation, int diceRollOutcome, int readyToTravel, int feastCounter );
}

class OneInTwo implements TravelStrategy
{
    public void move( int currentLocation, int diceRollOutcome, int readyToTravel, int feastCounter )
    {
        readyToTravel ++;

        if ( feastCounter != 0 )
        {
            currentLocation = currentLocation + diceRollOutcome + 1;
            feastCounter = feastCounter - 1;
        }

        if ( readyToTravel == 2 )
        {
            currentLocation = currentLocation + diceRollOutcome + 1;
            readyToTravel = 0;
        }
        else
        {
            currentLocation = currentLocation + diceRollOutcome;
        }
    }
}

class ThreeInFive implements TravelStrategy
{
    public void move( int currentLocation, int diceRollOutcome, int readyToTravel, int feastCounter )
    {
        readyToTravel ++;

        if ( feastCounter != 0 )
        {
            currentLocation = currentLocation + diceRollOutcome + 1;
            feastCounter = feastCounter - 1;
        }

        if ( readyToTravel == 5 )
        {
            currentLocation = currentLocation + diceRollOutcome + 3;
            readyToTravel = 0;
        }
        else
        {
            currentLocation = currentLocation + diceRollOutcome;
        }
    }
}