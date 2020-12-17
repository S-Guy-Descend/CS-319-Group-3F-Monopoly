public class FortuneTeller extends Token
{
    ScrollCard firstScroll;
    ScrollCard secondScroll;

    FortuneCard firstCard;
    FortuneCard secondCard;

    public FortuneTeller( String name )
    {
        super( name );
        firstScroll = null;
        secondScroll = null;
        firstCard = null;
        secondCard = null;
    }

    public void drawScroll()
    {
        int firstEffectID = (int) (Math.random() * Game.instance.board.fortuneDeck.length);
        int secondEffectID = (int) (Math.random() * Game.instance.board.fortuneDeck.length);

        firstScroll = Game.instance.board.scrollDeck[firstEffectID];
        secondScroll = Game.instance.board.scrollDeck[secondEffectID];
        //pickScroll(JButton firstScroll);
    }

    public void pickScroll(boolean selectFirstCard){
        if(selectFirstCard) {
            scrollCards.add( firstScroll );
        }
        else {
            scrollCards.add( secondScroll );
        }
    }

    public void drawFortuneCard(boolean selectFirstCard)
    {
        int firstEffectID = (int) (Math.random() * Game.instance.board.fortuneDeck.length);
        int secondEffectID = (int) (Math.random() * Game.instance.board.fortuneDeck.length);

        firstCard = Game.instance.board.fortuneDeck[firstEffectID];
        secondCard = Game.instance.board.fortuneDeck[secondEffectID];

        if(selectFirstCard) {
            firstCard.performEffect( this );
            System.out.println("FortuneCard " + firstCard.cardName + " is drawn");
        }
        else {
            secondCard.performEffect( this );
            System.out.println("FortuneCard " + secondCard.cardName + " is drawn");
        }
    }
}
