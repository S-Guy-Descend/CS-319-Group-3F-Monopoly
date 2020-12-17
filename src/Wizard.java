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
    // asdfsadfsdafsadfsadf
    public void move()
    {

        System.out.println("Wizard's move method entered");
        System.out.println("DiceRollOutcome in Wizard move method: " + diceRollOutcome);

        // Restore mana
        currentMana = currentMana + ( diceRollOutcome * manaGainMultiplier );
        System.out.println("currentMana in Wizard move method: " + currentMana);

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
        super.move();
    }
}
