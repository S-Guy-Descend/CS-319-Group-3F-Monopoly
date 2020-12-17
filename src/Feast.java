public class Feast extends Square{
    public Feast(String name) {
        super(name);
    }

    void buffTokenClass(Token player){
        if (player instanceof Traveler) {
            ((Traveler) player).feastCounter = 4;
        }
        else if (player instanceof Noble) {
            player.money += 30000;
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
            boolean scrollToSteal = false;
            for (int i = 0; i < Game.instance.tokens.size(); i++) {
                if (!Game.instance.tokens.get(i).scrollCards.isEmpty()) {
                    scrollToSteal = true; //Çalınacak scroll'u olan biri bulunduğu an çık
                    break;
                }
            }
            if (!scrollToSteal) {
                return;     //Çalınacak scroll yoksa metoddan çık
            }
            else{
                int victimId;
                do {        //bu player olmayan ve scroll'u olan biri bulana kadar random player seç
                    victimId = (int) (Math.random() * Game.instance.tokens.size());
                } while ((victimId == player.ID) || Game.instance.tokens.get(victimId).scrollCards.isEmpty());
                player.scrollCards.add(Game.instance.tokens.get(victimId).scrollCards.get(0));
                Game.instance.tokens.get(victimId).scrollCards.remove(0);
                //Hep ilk scroll'unu çaldırdım isterseniz onu da random yaparız
            }
        }
        else if (player instanceof Thief) {
            for (int i = 0; i < Game.instance.tokens.size(); i++) {
                if (Game.instance.tokens.get(i) == player) {    //Kendinden çalmasın diye
                    continue;
                }
                Token victim = Game.instance.tokens.get(i);
                victim.payMoney(player, (victim.money / 100) * 5 );
            }
        }
        else if (player instanceof Builder) {
            //ownedLands nasıl kullanıcaz bilmediğim için şimdilik boş bıraktım
        }
        else if (player instanceof Cardinal) {
            //ownedLands nasıl kullanıcaz bilmediğim için şimdilik boş bıraktım
        }
    }
}
