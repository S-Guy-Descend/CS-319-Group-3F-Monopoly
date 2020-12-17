public class TaxSquare extends Square{

    int taxAmount;

    public TaxSquare(String name, int taxAmount) {
        super(name);
        this.taxAmount = taxAmount;
    }

    void getTax(Token player, int taxAmount) {
        player.money -= taxAmount;
    }
}
