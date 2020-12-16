public class Thief extends Token
{
    public Thief( String name )
    {
        super( name );
    }

    public void move()
    {
        super.move();
        for (int i = 0; i < Game.instance.board.map[currentLocation].tokensOnTop.size(); i++ ) {
            Token victim = Game.instance.tokens.get(Game.instance.board.map[currentLocation].tokensOnTop.get(i));
            victim.payMoney(this,(victim.money / 100) * 5);
        }
    }
}
