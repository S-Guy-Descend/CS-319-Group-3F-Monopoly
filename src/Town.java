import java.math.*;

public class Town {
    //variables
    final double RENT_MULTIPLIER_PER_INN = 1.1;
    final double RENT_MULTIPLIER_PER_MANSION = 1.7;
    final double RENT_MULTIPLIER_BY_CARDINAL = 1.2;

    boolean belongsToCardinal = false;
    String color;
    boolean isPurchased = false;
    int mortgagePrice;
    int numberOfInns = 0;
    int numberOfMansions = 0;
    int ownerId;
    int price;
    int rent;

    //constructor
    public Town(String color, int mortgagePrice, int ownerId, int price, int rent) {
        this.color = color;
        this.mortgagePrice = mortgagePrice;
        this.ownerId = ownerId;
        this.price = price;
        this.rent = rent;
    }

    //methods
    public void calculateRent() {
        rent = (int) ((Math.pow(RENT_MULTIPLIER_PER_INN, numberOfInns)) + (Math.pow(RENT_MULTIPLIER_PER_MANSION, numberOfMansions)));
        if(belongsToCardinal)
            rent *= RENT_MULTIPLIER_BY_CARDINAL;
    }

    public void changeOwner(int newOwnerId, boolean isNewOwnerCardinal) {
        if(isPurchased == false) {
            isPurchased = true;
        }
        if(isNewOwnerCardinal) {
            belongsToCardinal = true;
            calculateRent();
        }
        ownerId = newOwnerId;
    }
}
