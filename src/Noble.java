public class Noble extends Token
{
    public Noble( String name )
    {
        super( name );
        money = 200000;
    }

    public void payTax( int amount )
    {
        int newAmount = (int)( amount * 0.5 );
        money = money - newAmount;
    }
}
