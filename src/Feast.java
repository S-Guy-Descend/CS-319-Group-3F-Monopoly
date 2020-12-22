import java.util.ArrayList;

public class Feast extends Square{
    public Feast(String name) {
        super(name);
    }

    void buffTokenClass(Token player){
        if (player instanceof Traveler) {
            ((Traveler) player).feastCounter = 4;
        }
        else if (player instanceof Noble) {
            player.receiveMoney( 30000 );
        }
        else if (player instanceof Knight) {
            ((Knight) player).feastEvasion = true;
        }
        else if (player instanceof TreasureHunter) {
            player.drawFortuneCard();
            player.drawScroll();
        }
        else if (player instanceof Wizard) {
            ((Wizard) player).manaGainMultiplier *= 1.15;
        }
        else if (player instanceof FortuneTeller) {
            ArrayList<Token> scrollOwners = new ArrayList<Token>();
            for (int i = 0; i < Game.instance.tokens.size(); i++) {
                if ( Game.instance.tokens.get(i) == player || Game.instance.tokens.get(i).isBankrupt )
                {
                    continue;
                }
                if (!Game.instance.tokens.get(i).scrollCards.isEmpty()) {
                    scrollOwners.add( Game.instance.tokens.get(i) );
                }
            }
            if (scrollOwners.size() == 0) {
                // Draw a scroll card if there is nobody to steal from.
                player.drawScroll();
                return;
            }
            else{
                int randomPlayerIndex;
                int randomCardIndex;
                // Choosing the index of the victim
                randomPlayerIndex = (int)( Math.random() * scrollOwners.size() );
                // Choosing the card the victim is going to lose
                randomCardIndex = (int)( Math.random() * scrollOwners.get(randomPlayerIndex).scrollCards.size());
                // Stealing the card
                if ( player.scrollCards.size() < 5 )
                {
                    player.scrollCards.add(scrollOwners.get(randomPlayerIndex).scrollCards.get(randomCardIndex));
                }
                scrollOwners.get(randomPlayerIndex).scrollCards.remove(randomCardIndex);
            }
        }
        else if (player instanceof Thief) {
            for (int i = 0; i < Game.instance.tokens.size(); i++)
            {
                if (Game.instance.tokens.get(i) == player || Game.instance.tokens.get(i).isBankrupt)
                {
                    continue;
                }
                Token victim = Game.instance.tokens.get(i);
                victim.payMoney(player, (victim.money / 100) * 5 );
            }
        }
        else if (player instanceof Builder) {
            int totalInns = 0;
            for ( int i = 0; i < player.residenceIDs.size(); i ++ )
            {
                totalInns += ((Town)Game.instance.board.map[player.residenceIDs.get(i)]).numberOfInns;
            }
            player.receiveMoney( totalInns * ((Builder) player).BUILDER_FEAST_MULTIPLIER );
        }
        else if (player instanceof Cardinal) {
            int homeToGo = (int)( Math.random() * player.residenceIDs.size() );
            player.forceMove( homeToGo, true );
        }
    }
}
