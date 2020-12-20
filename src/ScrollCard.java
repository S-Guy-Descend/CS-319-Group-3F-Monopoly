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
                cardText = "Send a player to dungeon, or if he is already in dungeon, release him and send him to GO.";
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
                if ( !(effectVictim.dungeonCountdown > 0) ) {
                    int randomPropertyID = (int) (Math.random() * effectOwner.activeLands.size());
                    effectVictim.forceMove(effectOwner.activeLands.get(randomPropertyID), false);
                }
                else {
                    System.out.println("Can't teleport player; player is in the dungeon.");
                }
                break;
            case 1:
                ((Feast) Game.instance.board.map[20]).buffTokenClass( effectOwner );
                System.out.println( "Gained Feast effect" );
                break;
            case 2:
                if ( effectVictim.residenceIDs.size() > 0 )
                {
                    int squareWithBuildingID = (int)(Math.random() * effectVictim.residenceIDs.size() );
                    ((Town)Game.instance.board.map[squareWithBuildingID]).numberOfInns --;
                    if ( ((Town)Game.instance.board.map[squareWithBuildingID]).numberOfInns == 0 )
                    {
                        effectVictim.residenceIDs.remove( new Integer(squareWithBuildingID) );
                    }
                    System.out.println( "Earthquake on " + Game.instance.board.map[squareWithBuildingID].name + "!" );
                }
                else
                {
                    System.out.println( "No buildings to demolish" );
                }
                break;
            case 3:
                if ( effectVictim.dungeonCountdown == 0 )
                {
                    effectVictim.forceMove( 30, false );
                    System.out.println( "Player " + effectVictim.ID + " sent to dungeon" );
                }
                else
                {
                    effectVictim.dungeonCountdown = 0;
                    effectVictim.forceMove( 0, true );
                    System.out.println( "Player " + effectVictim.ID + " is now free" );
                }
                break;
            case 4:
                if ( effectVictim.scrollCards.size() > 0 )
                {
                    int scrollToBurn = (int)(Math.random() * effectVictim.scrollCards.size() );
                    effectVictim.scrollCards.remove( scrollToBurn );
                }
                else
                {
                    System.out.println( "No scrolls to burn" );
                }
                break;
            default:
        }
    }
}
