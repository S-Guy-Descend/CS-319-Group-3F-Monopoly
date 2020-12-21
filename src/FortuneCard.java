import java.io.Serializable;

public class FortuneCard implements Serializable
{
    int effectID;
    String cardName;
    String cardText;

    public FortuneCard( int effectID )
    {
        this.effectID = effectID;
        generateName( this.effectID );
        generateText( this.effectID );
    }

    public void generateName( int effectID )
    {
        switch (effectID)
        {
            case 0:
                cardName = "Advance to Go";
                break;
            case 1:
                cardName = "Go Back 3 Squares";
                break;
            case 2:
                cardName = "Painting Sold";
                break;
            case 3:
                cardName = "Go to Dungeon";
                break;
            case 4:
                cardName = "Robbery";
                break;
            default:
        }
    }

    public void generateText( int effectID )
    {
        switch (effectID)
        {
            case 0:
                cardText = "Advance to Go and Collect 200$.";
                break;
            case 1:
                cardText = "Go back 3 Squares.";
                break;
            case 2:
                cardText = "Your paintings were bought by the Royal Family! You receive 300 Gold!";
                break;
            case 3:
                cardText = "Go to Dungeon. Do not pass go. Do not collect 200$.";
                break;
            case 4:
                cardText = "A robber stole your money. You lost 100$.";
                break;
            default:
        }
    }

    public void performEffect( Token effectOwner )
    {
        switch (effectID)
        {
            case 0:
                effectOwner.forceMove( 0 , true);
                break;
            case 1:
                effectOwner.forceMove( (effectOwner.currentLocation + 37) % 40, false);
                break;
            case 2:
                effectOwner.money = effectOwner.money + 300;
                break;
            case 3:
                effectOwner.forceMove( 30, false);
                break;
            case 4:
                effectOwner.money = effectOwner.money - 100;
                break;
            default:
        }
    }
}
