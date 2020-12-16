public class GoToDungeon extends Square{
    public GoToDungeon(String name) {
        super(name);
    }

    void sendTokenToDungeon(Token player){
        player.isInDungeon = true;
        player.dungeonCountdown = 3;
        player.forceMove(10);
    }
}
