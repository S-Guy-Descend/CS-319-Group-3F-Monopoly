public class StartingSquare extends Square{
    final int MONEY_PER_LEAP = 20000;

    public StartingSquare(String name) {
        super(name);
    }

    void giveLeapMoney(Token player){
        player.money += MONEY_PER_LEAP;
    }
}
