import java.util.Arrays;

public class FortuneTeller extends Token
{

    int fortuneCardPriority[];

    public FortuneTeller( String name )
    {
        super( name );
        fortuneCardPriority = new int[]{4, 2, 3, 0, 1};
    }

    public void drawFortuneCard()
    {
        int firstOption = (int) (Math.random() * Game.instance.board.fortuneDeck.length);
        int secondOption = (int) (Math.random() * Game.instance.board.fortuneDeck.length);
        while ( firstOption == secondOption )
        {
            secondOption = (int) (Math.random() * Game.instance.board.fortuneDeck.length);
        }
        int effectID;
        if ( fortuneCardPriority[firstOption] > fortuneCardPriority[secondOption] )
        {
            effectID = firstOption;
        }
        else
        {
            effectID = secondOption;
        }
        System.out.println("FortuneCard " + Game.instance.board.fortuneDeck[effectID].cardName + " is drawn");
        Game.instance.board.fortuneDeck[effectID].performEffect( this );
    }
}
