public class Smith extends Square {

    final int MORTGAGE_PRICE = 7500;
    final int UN_MORTGAGE_PRICE = 10000;

    final int price = 15000;
    boolean isPurchased;
    int ownerId;
    int rent;

    public Smith(String name) {
        super(name);
        this.ownerId = -1;
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

}
