public class Knight extends Token
{
    boolean feastEvasion;

    public Knight( String name )
    {
        super( name );
        feastEvasion = false;
    }

    public void payMoney( Token receiver, int amount )
    {
        int newAmount = (int)(amount * 0.75);
        if ( feastEvasion )
        {
            newAmount = 0;
            feastEvasion = false;
        }
        money = money - newAmount;
        receiver.receiveMoney( newAmount );
    }
}
