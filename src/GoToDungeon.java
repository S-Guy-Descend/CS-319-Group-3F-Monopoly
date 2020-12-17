public class GoToDungeon extends Square{
    public GoToDungeon(String name) {
        super(name);
    }

    void sendTokenToDungeon(Token player){
        player.dungeonCountdown = 3;
        player.forceMove(10, false);
        System.out.println("Player"+ player.ID + " is sent to dungeon");
    }
}
