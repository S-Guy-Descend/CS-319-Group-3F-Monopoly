public class Wizard extends Token
{
    double currentMana;
    int maxMana;
    double manaGainMultiplier;

    public Wizard( String name )
    {
        super( name );
        currentMana = 0;
        maxMana = 50;
        manaGainMultiplier = 1.0;
    }

    public void move()
    {
        currentLocation = currentLocation + diceRollOutcome;

        // Restore mana
        currentMana = currentMana + ( diceRollOutcome * manaGainMultiplier );

        // Draw card for each fulled mana bar
        if ( currentMana >= maxMana )
        {
            int drawAmount = (int)currentMana / maxMana;
            currentMana = currentMana % maxMana;

            for ( int i = drawAmount; i != 0; i -- )
            {
                this.drawScroll();
            }
        }
    }
}
