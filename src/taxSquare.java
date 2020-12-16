public class taxSquare extends Square{

    int taxAmount;

    public taxSquare(String name, int taxAmount) {
        super(name);
        this.taxAmount = taxAmount;
    }

    void getTax(Token player, int taxAmount) {
        player.money -= taxAmount;
    }
}
