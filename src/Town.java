import java.math.*;

public class Town extends Square {
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
    int innPrice;

    //constructor
    public Town(String name, String color, int mortgagePrice, int price, int rent, int innPrice) {
        super(name);
        this.color = color;
        this.mortgagePrice = mortgagePrice;
        this.ownerId = ownerId;
        this.price = price;
        this.rent = rent;
        this.ownerId = -1;
        this.innPrice = innPrice;
    }

    //methods
    public void calculateRent() {
        rent = (int) ((Math.pow(RENT_MULTIPLIER_PER_INN, numberOfInns)) * rent);
        if(belongsToCardinal)
            rent *= RENT_MULTIPLIER_BY_CARDINAL;
    }

    public void changeOwner(int newOwnerId) {
        if(isPurchased == false) {
            isPurchased = true;
        }
        ownerId = newOwnerId;
    }
}
