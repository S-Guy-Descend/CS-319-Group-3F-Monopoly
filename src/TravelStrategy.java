import java.io.Serializable;

public interface TravelStrategy
{
    void move( Traveler traveler );
}

class OneInTwo implements TravelStrategy, Serializable
{
    public void move( Traveler traveler )
    {
        if ( Game.instance.board.map[traveler.currentLocation].tokensOnTop.contains( traveler.ID ) )
        {
            Game.instance.board.map[traveler.currentLocation].removeTokenFromSquare(traveler.ID);
        }

        if ( traveler.forcedToMove )
        {
            traveler.forcedToMove = false;
            traveler.currentLocation = (traveler.currentLocation + traveler.diceRollOutcome) % 40;
            traveler.activateSquare();
            Game.instance.board.map[traveler.currentLocation].addTokenOnSquare(traveler.ID);
            return;
        }

        traveler.readyToTravel ++;

        if(traveler.currentLocation + traveler.diceRollOutcome >= 40)
        {
            traveler.money += 20000;
        }

        traveler.currentLocation = (traveler.currentLocation + traveler.diceRollOutcome) % 40;

        if ( traveler.feastCounter != 0 )
        {
            traveler.currentLocation = (traveler.currentLocation + 1) % 40;
            traveler.feastCounter = traveler.feastCounter - 1;
        }

        if ( traveler.readyToTravel == 2 )
        {
            traveler.currentLocation = (traveler.currentLocation + 1) % 40;
            traveler.readyToTravel = 0;
        }

        Game.instance.board.map[traveler.currentLocation].addTokenOnSquare(traveler.ID);
        traveler.activateSquare();
    }
}

class ThreeInFive implements TravelStrategy, Serializable
{
    public void move( Traveler traveler )
    {
        if ( Game.instance.board.map[traveler.currentLocation].tokensOnTop.contains( traveler.ID ) )
        {
            Game.instance.board.map[traveler.currentLocation].removeTokenFromSquare(traveler.ID);
        }

        if ( traveler.forcedToMove )
        {
            traveler.forcedToMove = false;
            traveler.currentLocation = (traveler.currentLocation + traveler.diceRollOutcome) % 40;
            traveler.activateSquare();
            Game.instance.board.map[traveler.currentLocation].addTokenOnSquare(traveler.ID);
            return;
        }

        traveler.readyToTravel ++;

        if(traveler.currentLocation + traveler.diceRollOutcome >= 40)
        {
            traveler.money += 20000;
        }

        traveler.currentLocation = (traveler.currentLocation + traveler.diceRollOutcome) % 40;

        if ( traveler.feastCounter != 0 )
        {
            traveler.currentLocation = (traveler.currentLocation + 1) % 40;
            traveler.feastCounter = traveler.feastCounter - 1;
        }

        if ( traveler.readyToTravel == 5 )
        {
            traveler.currentLocation = (traveler.currentLocation + 3) % 40;
            traveler.readyToTravel = 0;
        }

        Game.instance.board.map[traveler.currentLocation].addTokenOnSquare(traveler.ID);
        traveler.activateSquare();
    }
}