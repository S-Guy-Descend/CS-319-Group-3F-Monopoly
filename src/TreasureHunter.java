public class TreasureHunter extends Token
{
    int treasureCounter;
    boolean forcedToMove;
    boolean treasureHunter;

    public TreasureHunter( String name )
    {
        super( name );
        treasureCounter = 0;
        forcedToMove = false;
        treasureHunter = true;
    }

    public void move()
    {
        if ( !forcedToMove )
        {
            treasureCounter ++;
            if ( treasureCounter == 3 )
            {
                this.drawFortuneCard( treasureHunter );
                treasureCounter = 0;
            }
        }
        else
        {
            forcedToMove = false;
        }
        super.move();
    }

    public void drawFortuneCard( boolean treasureHunter )
    {
        int effectID = (int) (Math.random() * Game.instance.board.fortuneDeck.length);
        while ( effectID == 3 )
        {
            effectID = (int) (Math.random() * Game.instance.board.fortuneDeck.length);
        }
        System.out.println("FortuneCard " + Game.instance.board.fortuneDeck[effectID].cardName + " is drawn");
        Game.instance.infoPanel += "\nPlayer " + (ID + 1) + " draw " + Game.instance.board.fortuneDeck[effectID].cardName + " Scroll";
        Game.instance.board.fortuneDeck[effectID].performEffect( this );
    }
}
