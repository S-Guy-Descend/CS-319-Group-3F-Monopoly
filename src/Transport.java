public class Transport extends Square{
    final int RENT_FOR_ONE_TRANSPORT_OWNED = 250;
    final int RENT_FOR_TWO_TRANSPORT_OWNED = 500;
    final int RENT_FOR_THREE_TRANSPORT_OWNED = 1000;
    final int RENT_FOR_FOUR_TRANSPORT_OWNED = 2500;

    final int price = 20000;
    final int mortgagePrice = 10000;

    boolean isPurchased;
    int ownerId;
    int rent;

    public Transport(String name) {
        super(name);
        isPurchased = false;
        ownerId = -1;
    }

    public void calculateRent() {
        System.out.println("Entered calculate rent method of transport: " +name+" with owner id: " + ownerId);
        System.out.println(Game.instance.tokens.get(0).name);
        System.out.println(Game.instance.tokens.get(1).name);
        System.out.println(Game.instance.tokens.get(2).name);
        System.out.println(Game.instance.tokens.get(3).name);
        if(Game.instance.tokens.get(ownerId).ownedTransportCount == 1) {
            System.out.println("Entered for transport rent update");
            rent = RENT_FOR_ONE_TRANSPORT_OWNED;
        }
        else if(Game.instance.tokens.get(ownerId).ownedSmithCount == 2) {
            System.out.println("Entered for transport rent update");
            rent = RENT_FOR_TWO_TRANSPORT_OWNED;
        }
        else if(Game.instance.tokens.get(ownerId).ownedSmithCount == 3) {
            System.out.println("Entered for transport rent update");
            rent = RENT_FOR_THREE_TRANSPORT_OWNED;
        }
        else if(Game.instance.tokens.get(ownerId).ownedSmithCount == 4) {
            System.out.println("Entered for transport rent update");
            rent = RENT_FOR_FOUR_TRANSPORT_OWNED;
        }
    }

    public void changeOwner(int newOwnerId) {
        if(isPurchased == false) {
            isPurchased = true;
        }
        ownerId = newOwnerId;
    }
}
