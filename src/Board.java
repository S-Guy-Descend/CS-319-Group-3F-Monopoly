public class Board {
    public Square[] map;
    public ScrollCard[] scrollDeck;
    public FortuneCard[] fortuneDeck;

    public Board() {
        map = new Square[40];
        scrollDeck = new ScrollCard[5];
        fotuneDeck = new FortuneCard[5];
    }
}
