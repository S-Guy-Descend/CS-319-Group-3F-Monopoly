public class Transport extends Square{
    final int RENT_FOR_ONE_TRANSPORT_OWNED = 250;
    final int RENT_FOR_TWO_TRANSPORT_OWNED = 500;
    final int RENT_FOR_THREE_TRANSPORT_OWNED = 1000;
    final int RENT_FOR_FOUR_TRANSPORT_OWNED = 2500;
    final int PRICE = 20000;
    final int MORTGAGE_PRICE = 10000;

    boolean isPurchased;
    int ownerId;

    public Transport(String name) {
        super(name);
        isPurchased = false;
        ownerId = -1;
    }
}
