public class Smith extends Square {

    final double MORTGAGE_REDEMPTION_MULTIPLIER = 1.5;

    final int price = 15000;
    final int mortgagePrice = 7500;

    boolean isPurchased;
    boolean isMortgaged;
    int ownerId;
    int rent;

    public Smith(String name) {
        super(name);
        this.ownerId = -1;
        this.isMortgaged = false;
        this.isPurchased = false;
    }

    public void calculateRent(int rolledDiceAmount) {
        if(isPurchased) {
            if(Game.instance.tokens.get(ownerId).ownedSmithCount == 1) {
                rent = rolledDiceAmount * 400;
            }
            else if(Game.instance.tokens.get(ownerId).ownedSmithCount == 2) {
                rent = rolledDiceAmount * 1000;
            }
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
