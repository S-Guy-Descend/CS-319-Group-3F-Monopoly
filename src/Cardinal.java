import java.util.ArrayList;

public class Cardinal extends Token
{
    public Cardinal( String name )
    {
        super( name );
    }

    public boolean purchaseLand()
    {
        super.purchaseLand();
        Square landBought = Game.instance.board.map[currentLocation];
        if ( landBought instanceof Town ) {
            ((Town) landBought).belongsToCardinal = true;
        }
        return true;
    }
}