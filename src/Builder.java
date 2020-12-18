import java.util.ArrayList;

public class Builder extends Token
{
    final double BUILDER_CLASS_DISCOUNT = 0.8;
    final int BUILDER_FEAST_MULTIPLIER = 5000;
    ArrayList<Integer> residenceIDs = new ArrayList<Integer>();
    public Builder( String name )
    {
        super( name );
    }

    public boolean build()
    {
        if(Game.instance.board.map[this.currentLocation] instanceof Town) {
            Town currentTown = (Town) Game.instance.board.map[this.currentLocation];
            if(currentTown.ownerId != ID){
                return false;
            }
            if(money < (int) (currentTown.innPrice * BUILDER_CLASS_DISCOUNT)) {
                return false;
            }
            money -= (int) (currentTown.innPrice * BUILDER_CLASS_DISCOUNT);
            currentTown.numberOfInns += 1;
            currentTown.calculateRent();
            residenceIDs.add(this.currentLocation);
            return true;
        }
        return false;
    }
}
