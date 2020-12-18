import java.util.ArrayList;

public class Cardinal extends Token
{
    ArrayList<Integer> residenceIDs = new ArrayList<Integer>();

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

    public boolean build()
    {
        boolean isSuccessful = super.build();

        if ( isSuccessful )
        {
            residenceIDs.add(this.currentLocation);
        }

        return isSuccessful;
    }

    public boolean mortgageLand( int locationToMortgage )
    {
        boolean isSuccessful = super.mortgageLand( locationToMortgage );
        residenceIDs.remove( locationToMortgage );

        return isSuccessful;
    }

    public boolean redeemMortgage( int locationToMortgage )
    {
        boolean isSuccessful = super.redeemMortgage( locationToMortgage );
        residenceIDs.add( locationToMortgage );

        return isSuccessful;
    }
}