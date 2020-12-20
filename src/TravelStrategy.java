import java.io.Serializable;

public interface TravelStrategy
{
    void move( Traveler traveler );
}

class OneInTwo implements TravelStrategy, Serializable
{
    public void move( Traveler traveler )
    {
        if(traveler.currentLocation + traveler.diceRollOutcome >= 40)
        {
            traveler.money += 20000;
        }

        traveler.readyToTravel ++;

        if ( traveler.feastCounter != 0 )
        {
            traveler.currentLocation = traveler.currentLocation + traveler.diceRollOutcome + 1;
            traveler.feastCounter = traveler.feastCounter - 1;
        }

        if ( traveler.readyToTravel == 2 )
        {
            traveler.currentLocation = traveler.currentLocation + traveler.diceRollOutcome + 1;
            traveler.readyToTravel = 0;
        }
        else
        {
            traveler.currentLocation = traveler.currentLocation + traveler.diceRollOutcome;
        }

        traveler.activateSquare();
    }
}

class ThreeInFive implements TravelStrategy, Serializable
{
    public void move( Traveler traveler )
    {
        if(traveler.currentLocation + traveler.diceRollOutcome >= 40)
        {
            traveler.money += 20000;
        }

        traveler.readyToTravel ++;

        if ( traveler.feastCounter != 0 )
        {
            traveler.currentLocation = traveler.currentLocation + traveler.diceRollOutcome + 1;
            traveler.feastCounter = traveler.feastCounter - 1;
        }

        if ( traveler.readyToTravel == 5 )
        {
            traveler.currentLocation = traveler.currentLocation + traveler.diceRollOutcome + 3;
            traveler.readyToTravel = 0;
        }
        else
        {
            traveler.currentLocation = traveler.currentLocation + traveler.diceRollOutcome;
        }

        traveler.activateSquare();
    }
}