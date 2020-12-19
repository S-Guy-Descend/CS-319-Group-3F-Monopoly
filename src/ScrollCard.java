import java.io.Serializable;

public class ScrollCard implements Serializable
{
    int effectID;
    String cardName;
    String cardText;

    public ScrollCard( int effectID )
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
                cardName = "Forceful Accommodation";
                break;
            case 1:
                cardName = "Time for a Feast";
                break;
            case 2:
                cardName = "Earthquake";
                break;
            case 3:
                cardName = "Dungeon Reverse";
                break;
            case 4:
                cardName = "Counterspell";
                break;
            default:
        }
    }
    
    public void generateText( int effectID )
    {
        switch (effectID)
        {
            case 0:
                cardText = "Teleport a player to one of your random properties.";
                break;
            case 1:
                cardText = "Gain your character's Feast effect!";
                break;
            case 2:
                cardText = "Summon a powerful earthquake to destroy one of the buildings of a player.";
                break;
            case 3:
                cardText = "Send a player to dungeon, or if he is already in dungeon, release him.";
                break;
            case 4:
                cardText = "Burn a player's randomly chosen scroll.";
                break;
            default:
        }
    }

    public void performEffect( Token effectOwner, Token effectVictim )
    {
        switch (effectID)
        {
            case 0:
                // effectVictim.forceMove();
                break;
            case 1:
                // gainFeastEffect() ???
                break;
            case 2:
                //
                break;
            case 3:
                // if (notInJail), forceMove(30)
                break;
            case 4:
                // effectVictim.scrollCards.....
                break;
            default:
        }
    }
}
