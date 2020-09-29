# CS-319-Group-3F-Monopoly
PROJECT SUMMARY
Planned Features:

-Class system with different benefits and drawbacks (if necessary, for balancing purposes) for each class. Each class will have a focus on a different aspect of a game.

-Combined Community Chest and Chance cards, a new deck of drawable, consumable cards with new effects that the player can use whenever they want during their turn. The goal of this feature is to add a bit more strategy to the game.

-Saveable game state to easily continue where the game was left off.

-Teams mode where multiple players share the same wealth and properties.

-Online play mode

-Chat box

Currently, we are planning to have a player class, which is going to have the player information, the instances of which are going to be in the game class. There will be a parent space class which the neighbourhoods, chances, community chests, services, the jail and other spaces are going to inherit from. In the game class, there is going to be an array of spaces, which can also be modified at the start of the game to implement different versions of the game. The functions that make the game work can be in the game class. Since the game is turn based, and only one player can play during a turn, a main loop can control the turns. 
