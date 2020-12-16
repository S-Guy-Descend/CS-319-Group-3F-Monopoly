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
        map[1] = new Town("Pyungmo","brown",3000,6000,600);
        map[2] = new FortuneSquare("Fortune Square");
        map[3] = new Town("Pyungmo2","brown",3000,6000,600);
        //map[4]=vergi levhası
        map[5] = new Transport("King's Cross");
        map[6] = new Town("Joan", "blue", 5000, 10000, 800);
        map[7] = new Town("Joan2", "blue", 5000, 10000, 800);
        map[8] = new ScrollSquare("Scroll Square");
        map[9] = new Town("Joan3", "blue", 6000, 12000, 900);
        map[10] = new Dungeon("Dungeon");
        map[11] = new Town("Bakara1", "pink", 7000, 14000, 1200);
        map[12] = new Smith("Armor Smith");
        map[13] = new Town("Bakara2", "pink", 7000, 14000, 1200);
        map[14] = new Town("Bakara3", "pink", 8000, 16000, 1400);
        map[15] = new Transport("Sarp's kahvehane");
        map[16] = new Town("Seuryong Vadisi", "orange", 9000, 18000, 1800);
        map[17] = new Town("Seuryong Vadisi2", "orange", 9000, 18000, 1800);
        map[18] = new FortuneSquare("Fortune Square");
        map[19] = new Town("Seuryong Vadisi3", "orange", 10000, 20000, 2000);
        map[20] = new Feast("Sarp Ağanın Sofrası");
        map[21] = new Town("Yongbi Çölü", "red", 11000, 22000, 2200);
        map[22] = new ScrollSquare("Scroll Square");
        map[23] = new Town("Yongbi Çölü2", "red", 11000, 22000, 2200);
        map[24] = new Town("Yongbi Çölü3", "red", 12000, 24000, 2400);
        map[25] = new Transport("Oğulcan's Sürgün makrosu");
        map[26] = new Town("Sürgün Mağarası1", "yellow", 13000, 26000, 2600);
        map[27] = new Town("Sürgün Mağarası2", "yellow", 13000, 26000, 2600);
        map[28] = new Smith("Wepon Smith");
        map[29] = new Town("Sürgün Mağarası3", "yellow", 14000, 28000, 2800);
        map[30] = new GoToDungeon("Banged King's wife. Go to Dungeon");
        map[31] = new Town("Büyülü Orman1", "green", 15000, 30000, 3000);
        map[32] = new Town("Büyülü Orman2", "green", 15000, 30000, 3000);
        map[33] = new FortuneSquare("Fortune Square");
        map[34] = new Town("Büyülü Orman3", "green", 16000, 32000, 3200);
        map[35] = new Transport("Atakan's anime convention");
        map[36] = new ScrollSquare("Scroll Square");
        map[37] = new Town("Uyanış Zindanı1", "purple", 17500, 35000, 3500);
        //map[38]=vergi levhası
        map[39] = new Town("Uyanış Zindanı2", "purple", 20000, 40000, 4000);
    }
}
