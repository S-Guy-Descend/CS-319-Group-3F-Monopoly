public class Board {
    public Square[] map;
    public ScrollCard[] scrollDeck;
    public FortuneCard[] fortuneDeck;

    public Board() {
        scrollDeck = new ScrollCard[5];
        fortuneDeck = new FortuneCard[5];
        initializeBoard();
    }

    void initializeBoard() {
        map = new Square[40];
        map[0] = new StartingSquare("Go");
        map[1] = new Town("Crudeberg","brown",3000,6000,600);
        map[2] = new FortuneSquare("Fortune");
        map[3] = new Town("Belchwood","brown",3000,6000,600);
        //map[4]=vergi levhası
        map[5] = new Transport("Southern Stables");
        map[6] = new Town("Rustmound", "blue", 5000, 10000, 800);
        map[7] = new ScrollSquare("Scroll");
        map[8] = new Town("Dullgrove", "blue", 5000, 10000, 800);
        map[9] = new Town("Levnor", "blue", 6000, 12000, 900);
        map[10] = new Dungeon("Dungeon");
        map[11] = new Town("Pippinclaw", "pink", 7000, 14000, 1200);
        map[12] = new Smith("Armorsmith");
        map[13] = new Town("Lochnar", "pink", 7000, 14000, 1200);
        map[14] = new Town("Coppertooth", "pink", 8000, 16000, 1400);
        map[15] = new Transport("Western Docks");
        map[16] = new Town("Buckthorn", "orange", 9000, 18000, 1800);
        map[17] = new FortuneSquare("Fortune");
        map[18] = new Town("Anchorbeak", "orange", 9000, 18000, 1800);
        map[19] = new Town("Couragestone", "orange", 10000, 20000, 2000);
        map[20] = new Feast("Feast");
        map[21] = new Town("Pilgrimwit", "red", 11000, 22000, 2200);
        map[22] = new ScrollSquare("Scroll");
        map[23] = new Town("Ironchest", "red", 11000, 22000, 2200);
        map[24] = new Town("Vermion", "red", 12000, 24000, 2400);
        map[25] = new Transport("Northern Stables");
        map[26] = new Town("Emberswan", "yellow", 13000, 26000, 2600);
        map[27] = new Town("Dovehaven", "yellow", 13000, 26000, 2600);
        map[28] = new Smith("Weaponsmith");
        map[29] = new Town("Ivnir", "yellow", 14000, 28000, 2800);
        map[30] = new GoToDungeon("Go to Dungeon");
        map[31] = new Town("Rehilos", "green", 15000, 30000, 3000);
        map[32] = new Town("Edenbright", "green", 15000, 30000, 3000);
        map[33] = new FortuneSquare("Fortune");
        map[34] = new Town("Stormbrace", "green", 16000, 32000, 3200);
        map[35] = new Transport("Eastern Docks");
        map[36] = new ScrollSquare("Scroll");
        map[37] = new Town("Grizaloth", "purple", 17500, 35000, 3500);
        //map[38]=vergi levhası
        map[39] = new Town("Zephros", "purple", 20000, 40000, 4000);
    }
}
