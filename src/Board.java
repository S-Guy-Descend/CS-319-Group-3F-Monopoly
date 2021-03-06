import java.io.Serializable;

public class Board implements Serializable {
    public Square[] map;
    public ScrollCard[] scrollDeck;
    public FortuneCard[] fortuneDeck;

    public Board() {
        initializeBoard();
        initializeScrollDeck();
        initializeFortuneDeck();
    }

    void initializeFortuneDeck() {
        fortuneDeck = new FortuneCard[5];
        fortuneDeck[0] = new FortuneCard(0);
        fortuneDeck[1] = new FortuneCard(1);
        fortuneDeck[2] = new FortuneCard(2);
        fortuneDeck[3] = new FortuneCard(3);
        fortuneDeck[4] = new FortuneCard(4);

    }

    void initializeScrollDeck() {
        scrollDeck = new ScrollCard[5];
        scrollDeck[0] = new ScrollCard(0);
        scrollDeck[1] = new ScrollCard(1);
        scrollDeck[2] = new ScrollCard(2);
        scrollDeck[3] = new ScrollCard(3);
        scrollDeck[4] = new ScrollCard(4);
    }

    void initializeBoard() {
        //Create color groups
        ColorGroup brown = new ColorGroup("brown");
        ColorGroup blue = new ColorGroup("blue");
        ColorGroup pink = new ColorGroup("pink");
        ColorGroup orange = new ColorGroup("orange");
        ColorGroup red = new ColorGroup("red");
        ColorGroup yellow = new ColorGroup("yellow");
        ColorGroup green = new ColorGroup("green");
        ColorGroup purple = new ColorGroup("purple");

        //Create board squares
        map = new Square[40];
        map[0] = new StartingSquare("Go");
        map[1] = new Town("Crudeberg",brown,3000,6000,600, 600, false);
        map[2] = new FortuneSquare("Fortune");
        map[3] = new Town("Belchwood",brown,3000,6000,600, 600, false);
        map[4] = new TaxSquare("King's Tax", 20000);
        map[5] = new Transport("Southern Stables");
        map[6] = new Town("Rustmound", blue, 5000, 10000, 800, 1000, false);
        map[7] = new ScrollSquare("Scroll");
        map[8] = new Town("Dullgrove", blue, 5000, 10000, 800, 1000, false);
        map[9] = new Town("Levnor", blue, 6000, 12000, 900, 1000, false);
        map[10] = new Dungeon("Dungeon");
        map[11] = new Town("Pippinclaw", pink, 7000, 14000, 1200, 1200, false);
        map[12] = new Smith("Armorsmith");
        map[13] = new Town("Lochnar", pink, 7000, 14000, 1200, 1200, false);
        map[14] = new Town("Coppertooth", pink, 8000, 16000, 1400, 1200, false);
        map[15] = new Transport("Western Docks");
        map[16] = new Town("Buckthorn", orange, 9000, 18000, 1800, 1800, false);
        map[17] = new FortuneSquare("Fortune");
        map[18] = new Town("Anchorbeak", orange, 9000, 18000, 1800, 1800, false);
        map[19] = new Town("Couragestone", orange, 10000, 20000, 2000, 1800, false);
        map[20] = new Feast("Feast");
        map[21] = new Town("Pilgrimwit", red, 11000, 22000, 2200, 2200, false);
        map[22] = new ScrollSquare("Scroll");
        map[23] = new Town("Ironchest", red, 11000, 22000, 2200, 2200, false);
        map[24] = new Town("Vermion", red, 12000, 24000, 2400, 2200, false);
        map[25] = new Transport("Northern Stables");
        map[26] = new Town("Emberswan", yellow, 13000, 26000, 2600, 2600, false);
        map[27] = new Town("Dovehaven", yellow, 13000, 26000, 2600, 2600, false);
        map[28] = new Smith("Weaponsmith");
        map[29] = new Town("Ivnir", yellow, 14000, 28000, 2800, 2600, false);
        map[30] = new GoToDungeon("Go to Dungeon");
        map[31] = new Town("Rehilos", green, 15000, 30000, 3000, 3000, false);
        map[32] = new Town("Edenbright", green, 15000, 30000, 3000, 3000, false);
        map[33] = new FortuneSquare("Fortune");
        map[34] = new Town("Stormbrace", green, 16000, 32000, 3200, 3000, false);
        map[35] = new Transport("Eastern Docks");
        map[36] = new ScrollSquare("Scroll");
        map[37] = new Town("Grizaloth", purple, 17500, 35000, 3500, 4000, false);
        map[38] = new TaxSquare("Queen's Tax", 10000);
        map[39] = new Town("Zephros", purple, 20000, 40000, 4000, 4000, false);

        //Add squares to corresponding color groups
        brown.addTownToColorGroup((Town)map[1]);
        brown.addTownToColorGroup((Town)map[3]);
        blue.addTownToColorGroup((Town)map[6]);
        blue.addTownToColorGroup((Town)map[8]);
        blue.addTownToColorGroup((Town)map[9]);
        pink.addTownToColorGroup((Town)map[11]);
        pink.addTownToColorGroup((Town)map[13]);
        pink.addTownToColorGroup((Town)map[14]);
        orange.addTownToColorGroup((Town)map[16]);
        orange.addTownToColorGroup((Town)map[18]);
        orange.addTownToColorGroup((Town)map[19]);
        red.addTownToColorGroup((Town)map[21]);
        red.addTownToColorGroup((Town)map[23]);
        red.addTownToColorGroup((Town)map[24]);
        yellow.addTownToColorGroup((Town)map[26]);
        yellow.addTownToColorGroup((Town)map[27]);
        yellow.addTownToColorGroup((Town)map[29]);
        green.addTownToColorGroup((Town)map[31]);
        green.addTownToColorGroup((Town)map[32]);
        green.addTownToColorGroup((Town)map[34]);
        purple.addTownToColorGroup((Town)map[37]);
        purple.addTownToColorGroup((Town)map[39]);
    }
}
