public class Transport extends Square{
    final int RENT_FOR_ONE_TRANSPORT_OWNED = 500;
    final int RENT_FOR_TWO_TRANSPORT_OWNED = 1000;
    final int RENT_FOR_THREE_TRANSPORT_OWNED = 1500;
    final int RENT_FOR_FOUR_TRANSPORT_OWNED = 2000;
    final double MORTGAGE_REDEMPTION_MULTIPLIER = 1.5;

    final int price = 20000;
    final int mortgagePrice = 10000;

    boolean isMortgaged;
    boolean isPurchased;
    int ownerId;
    int rent;

    public Transport(String name) {
        super(name);
        isPurchased = false;
        ownerId = -1;
        isMortgaged = false;
    }

    public void calculateRent() {
        if(Game.instance.tokens.get(ownerId).ownedTransportCount == 1) {
            rent = RENT_FOR_ONE_TRANSPORT_OWNED;
        }
        else if(Game.instance.tokens.get(ownerId).ownedTransportCount == 2) {
            rent = RENT_FOR_TWO_TRANSPORT_OWNED;
        }
        else if(Game.instance.tokens.get(ownerId).ownedTransportCount == 3) {
            rent = RENT_FOR_THREE_TRANSPORT_OWNED;
        }
        else if(Game.instance.tokens.get(ownerId).ownedTransportCount == 4) {
            rent = RENT_FOR_FOUR_TRANSPORT_OWNED;
        }
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
