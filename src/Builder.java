import java.util.ArrayList;

public class Builder extends Token
{
    final double BUILDER_CLASS_DISCOUNT = 0.8;
    final int BUILDER_FEAST_MULTIPLIER = 5000;
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

            String buildingType = currentTown.numberOfInns <= 4 ? "Inn" : "Mansion";
            Game.instance.infoPanel += "\nPlayer " + (ID + 1) + " build " + buildingType + " on " + Game.instance.board.map[currentLocation].name;

            return true;
        }
        return false;
    }
}
