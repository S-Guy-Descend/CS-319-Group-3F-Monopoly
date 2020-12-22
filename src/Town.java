import java.io.Serializable;
import java.math.*;

public class Town extends Square implements Serializable {
    //variables
    final double RENT_MULTIPLIER_PER_INN = 1.7;
    final double RENT_MULTIPLIER_PER_MANSION = 1.7;
    final double RENT_MULTIPLIER_BY_CARDINAL = 1.2;
    final double MORTGAGE_REDEMPTION_MULTIPLIER = 1.5;
    final int MAX_NUMBER_OF_HOUSES = 5;

    boolean belongsToCardinal = false;
    ColorGroup colorGroup;
    boolean isPurchased = false;
    int mortgagePrice;
    boolean isMortgaged;
    int numberOfInns;

    int baseRent;

    int ownerId;
    int price;
    int rent;
    int innPrice;

    //constructor
    public Town(String name, ColorGroup colorGroup, int mortgagePrice, int price, int rent, int innPrice, boolean isMortgaged) {
        super(name);
        this.colorGroup = colorGroup;
        this.mortgagePrice = mortgagePrice;
        this.price = price;
        baseRent  = rent;
        this.rent = rent;
        this.ownerId = -1;
        this.innPrice = innPrice;
        this.isMortgaged = isMortgaged;
        this.numberOfInns = 0;
    }

    //methods
    public void calculateRent() {
        rent = (int) ((Math.pow(RENT_MULTIPLIER_PER_INN, numberOfInns)) * baseRent);
        if(belongsToCardinal)
            rent *= RENT_MULTIPLIER_BY_CARDINAL;
    }

    public void changeOwner(int newOwnerId) {
        if(isPurchased == false) {
            isPurchased = true;
        }
        ownerId = newOwnerId;
    }

    public void setAsMortgaged() {
        isMortgaged = true;
    }

    public void removeMortgage() {
        isMortgaged = false;
    }

    public boolean isMortgaged() {
        return isMortgaged;
    }

}
