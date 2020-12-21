import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class SquareVisual extends Rectangle
{
    public static final int SQUARE_SIZE = 91;

    //properties
    //playerlar burda tutulacak
    HBox top;
    VBox left;
    VBox right;
    HBox bottom;


    //player avatarları
    Color player1Color = Color.RED;
    Color player2Color = Color.GREEN;
    Color player3Color = Color.BLUE;
    Color player4Color = Color.YELLOW;
    Color player5Color = Color.CYAN;
    Color player6Color = Color.HOTPINK;
    Color player7Color = Color.ORANGE;
    Color player8Color = Color.PURPLE;

    Rectangle player1Avatar;
    Rectangle player2Avatar;
    Rectangle player3Avatar;
    Rectangle player4Avatar;
    Rectangle player5Avatar;
    Rectangle player6Avatar;
    Rectangle player7Avatar;
    Rectangle player8Avatar;

    //bu centerda olacak, square özellikleri
    StackPane sp;
    Text squareName;
    int squareID;
    Rectangle squareColor;
    VBox colorContainer;

    //constructor
    public SquareVisual( String name, int ID)
    {
        //hazır player tokenleri şimdilik rectangle daha sonra image ile değiştirilecek

        player1Avatar = new Rectangle();
        player1Avatar.setStroke( Color.BLACK);
        player1Avatar.setFill( player1Color);
        player1Avatar.setWidth(25);
        player1Avatar.setHeight(25);
        player1Avatar.setVisible( false);


        player2Avatar = new Rectangle();
        player2Avatar.setStroke( Color.BLACK);
        player2Avatar.setFill( player2Color);
        player2Avatar.setWidth(25);
        player2Avatar.setHeight(25);
        player2Avatar.setVisible( false);

        player3Avatar = new Rectangle();
        player3Avatar.setStroke( Color.BLACK);
        player3Avatar.setFill( player3Color);
        player3Avatar.setWidth(25);
        player3Avatar.setHeight(25);
        player3Avatar.setVisible( false);

        player4Avatar = new Rectangle();
        player4Avatar.setStroke( Color.BLACK);
        player4Avatar.setFill( player4Color);
        player4Avatar.setWidth(25);
        player4Avatar.setHeight(25);
        player4Avatar.setVisible( false);

        player5Avatar = new Rectangle();
        player5Avatar.setStroke( Color.BLACK);
        player5Avatar.setFill( player5Color);
        player5Avatar.setWidth(25);
        player5Avatar.setHeight(25);
        player5Avatar.setVisible( false);

        player6Avatar = new Rectangle();
        player6Avatar.setStroke( Color.BLACK);
        player6Avatar.setFill( player6Color);
        player6Avatar.setWidth(25);
        player6Avatar.setHeight(25);
        player6Avatar.setVisible( false);

        player7Avatar = new Rectangle();
        player7Avatar.setStroke( Color.BLACK);
        player7Avatar.setFill( player7Color);
        player7Avatar.setWidth(25);
        player7Avatar.setHeight(25);
        player7Avatar.setVisible( false);

        player8Avatar = new Rectangle();
        player8Avatar.setStroke( Color.BLACK);
        player8Avatar.setFill( player8Color);
        player8Avatar.setWidth(25);
        player8Avatar.setHeight(25);
        player8Avatar.setVisible( false);

        // player1-2 top hboxunda stackpanein en üst ortasında player 3-4 sağda right içinde, player 5-6 en altta bottom içinde
        // player 7-8 en solda, left içinde
        top = new HBox();
        top.getChildren().addAll( player1Avatar, player2Avatar);
        top.setAlignment( Pos.BASELINE_CENTER);

        right = new VBox();
        right.getChildren().addAll( player3Avatar, player4Avatar);
        right.setAlignment(Pos.CENTER_RIGHT);

        bottom = new HBox();
        bottom.getChildren().addAll( player5Avatar, player6Avatar);
        bottom.setAlignment( Pos.BOTTOM_CENTER);

        left = new VBox();
        left.getChildren().addAll( player7Avatar, player8Avatar);
        left.setAlignment( Pos.CENTER_LEFT);

        // square özellikleri
        squareID = ID;
        setHeight( SQUARE_SIZE);
        setWidth( SQUARE_SIZE);
        setStroke( Color.BLACK);
        setStrokeType(StrokeType.INSIDE);
        setStrokeWidth(3);
        setFill( Color.WHITE);
        setStrokeType( StrokeType.INSIDE);

        squareName = new Text( name);
        squareName.setFont( new Font(12));


        squareColor = new Rectangle();
        squareColor.setFill( Color.WHITE);
        squareColor.setWidth( SQUARE_SIZE - 1 );
        squareColor.setStroke( Color.BLACK);
        squareColor.setHeight( 25);



        colorContainer = new VBox();
        colorContainer.getChildren().add( squareColor);
        colorContainer.setAlignment( Pos.TOP_CENTER);
        colorContainer.setVisible( false);


        // square sp nin içinde tutuluyor
        sp = new StackPane();
        sp.getChildren().addAll(this,colorContainer,  top, right, bottom, left, squareName);
    }
    //methods

    public void checkTokensOnTop(Game currentGame)
    {
        if( !currentGame.board.map[ squareID].tokensOnTop.isEmpty())
        {
            for( int i = 0; i < currentGame.board.map[ squareID].tokensOnTop.size(); i++)
            {
                switch( currentGame.board.map[ squareID].tokensOnTop.get(i))
                {
                    case 0:
                        player1Avatar.setVisible(true);
                        break;
                    case 1:
                        player2Avatar.setVisible(true);
                        break;
                    case 2:
                        player3Avatar.setVisible(true);
                        break;
                    case 3:
                        player4Avatar.setVisible(true);
                        break;
                    case 4:
                        player5Avatar.setVisible(true);
                        break;
                    case 5:
                        player6Avatar.setVisible(true);
                        break;
                    case 6:
                        player7Avatar.setVisible(true);
                        break;
                    case 7:
                        player8Avatar.setVisible(true);
                        break;
                }

            }
        }

    }
    public void checkBuildings(Game currentGame)
    {
        if( currentGame.board.map[ squareID] instanceof Town)
        {
            switch( ((Town)(currentGame.board.map[ squareID])).numberOfInns)
            {
                case 0:
                    squareName.setFill( Color.BLACK); // hiç ev yoksa yazı siyah
                    break;
                case 1:
                    squareName.setFill( Color.BLUE); // 1 ev varsa rengi mavi
                    break;
                case 2:
                    squareName.setFill( Color.PURPLE); // 2 ev varsa rengi mor
                    break;
                case 3:
                    squareName.setFill( Color.ORANGE); // 3 ev varsa rengi turuncu
                    break;
                case 4:
                    squareName.setFill( Color.RED); // 4 ev varsa rengi kırmızı
                    break;
                case 5:
                    squareName.setFill( Color.GREEN); // 5 ev varsa rengi yeşil
                    break;
            }
        }
    }

    public void checkPriceOrRent(Game currentGame)
    {
        if( currentGame.board.map[ squareID] instanceof  Town)
        {
            if ( !((Town)(currentGame.board.map[ squareID])).isMortgaged) {
                if (((Town) (currentGame.board.map[squareID])).ownerId == -1) {
                    squareName.setText( currentGame.board.map[ squareID].name + "\n" + "Price: " + ((Town) (currentGame.board.map[squareID])).price);
                } else {
                    squareName.setText( currentGame.board.map[ squareID].name + "\n" + "Rent: " + ((Town) (currentGame.board.map[squareID])).rent);
                }
            }
            else {
                squareName.setText(currentGame.board.map[ squareID].name + "\n" + "Unmortgage for " + (((Town) (currentGame.board.map[squareID])).mortgagePrice *  (((Town) (currentGame.board.map[squareID])).MORTGAGE_REDEMPTION_MULTIPLIER)));
            }
        }
        else if( currentGame.board.map[ squareID] instanceof  Transport) {
            if ( !((Transport)(currentGame.board.map[ squareID])).isMortgaged) {
                if (((Transport) (currentGame.board.map[squareID])).ownerId == -1) {
                    squareName.setText(currentGame.board.map[ squareID].name + "\n" + "Price: " + ((Transport) (currentGame.board.map[squareID])).price);
                } else {
                    squareName.setText(currentGame.board.map[ squareID].name + "\n" + "Rent: " + ((Transport) (currentGame.board.map[squareID])).rent);
                }
            }
            else {
                squareName.setText(currentGame.board.map[ squareID].name + "\n" + "Unmortgage for " + (((Transport) (currentGame.board.map[squareID])).mortgagePrice *  (((Transport) (currentGame.board.map[squareID])).MORTGAGE_REDEMPTION_MULTIPLIER)));
            }
        }
        else if( currentGame.board.map[ squareID] instanceof  Smith) {
            if ( !((Smith)(currentGame.board.map[ squareID])).isMortgaged) {
                if (((Smith) (currentGame.board.map[squareID])).ownerId == -1) {
                    squareName.setText(currentGame.board.map[ squareID].name + "\n" + "Price: " + ((Smith) (currentGame.board.map[squareID])).price);
                } else {
                    if ( squareID != currentGame.tokens.get( (currentGame.turnCounter - 1) % currentGame.playerCount ).currentLocation ) {
                        if (currentGame.tokens.get(((Smith) (currentGame.board.map[squareID])).ownerId).ownedSmithCount == 1) {
                            squareName.setText(currentGame.board.map[squareID].name + "\n" + "Rent: Dice * " + 400);
                        }
                        else if (currentGame.tokens.get(((Smith) (currentGame.board.map[squareID])).ownerId).ownedSmithCount == 2) {
                            squareName.setText(currentGame.board.map[squareID].name + "\n" + "Rent: Dice * " + 1000);
                        }
                    }
                    else {
                        squareName.setText(currentGame.board.map[squareID].name + "\n" + "Rent: " + ((Smith) (currentGame.board.map[squareID])).rent);
                    }
                }
            }
            else {
                squareName.setText(currentGame.board.map[ squareID].name + "\n" + "Unmortgage for " + (((Smith) (currentGame.board.map[squareID])).mortgagePrice *  (((Smith) (currentGame.board.map[squareID])).MORTGAGE_REDEMPTION_MULTIPLIER)));
            }
        }
    }

    public void checkOwner(Game currentGame)
    {
        if( currentGame.board.map[ squareID] instanceof  Town)
        {
            switch( ((Town)(currentGame.board.map[ squareID])).ownerId)
            {
                case 0:
                    setStroke( player1Color);
                    break;
                case 1:
                    setStroke( player2Color);
                    break;
                case 2:
                    setStroke( player3Color);
                    break;
                case 3:
                    setStroke( player4Color);
                    break;
                case 4:
                    setStroke( player5Color);
                    break;
                case 5:
                    setStroke( player6Color);
                    break;
                case 6:
                    setStroke( player7Color);
                    break;
                case 7:
                    setStroke( player8Color);
                    break;
            }

        }
        else if( currentGame.board.map[ squareID] instanceof  Transport) {
            switch (((Transport) (currentGame.board.map[squareID])).ownerId) {
                case 0:
                    setStroke(player1Color);
                    break;
                case 1:
                    setStroke(player2Color);
                    break;
                case 2:
                    setStroke(player3Color);
                    break;
                case 3:
                    setStroke(player4Color);
                    break;
                case 4:
                    setStroke(player5Color);
                    break;
                case 5:
                    setStroke(player6Color);
                    break;
                case 6:
                    setStroke(player7Color);
                    break;
                case 7:
                    setStroke(player8Color);
                    break;
            }
        }
        else if( currentGame.board.map[ squareID] instanceof  Smith) {
            switch (((Smith) (currentGame.board.map[squareID])).ownerId) {
                case 0:
                    setStroke(player1Color);
                    break;
                case 1:
                    setStroke(player2Color);
                    break;
                case 2:
                    setStroke(player3Color);
                    break;
                case 3:
                    setStroke(player4Color);
                    break;
                case 4:
                    setStroke(player5Color);
                    break;
                case 5:
                    setStroke(player6Color);
                    break;
                case 6:
                    setStroke(player7Color);
                    break;
                case 7:
                    setStroke(player8Color);
                    break;
            }
        }
    }
    public void resetSquare()
    {
        player1Avatar.setVisible( false);
        player2Avatar.setVisible( false);
        player3Avatar.setVisible( false);
        player4Avatar.setVisible( false);
        player5Avatar.setVisible( false);
        player6Avatar.setVisible( false);
        player7Avatar.setVisible( false);
        player8Avatar.setVisible( false);

        squareName.setFill( Color.BLACK);
        setStroke( Color.BLACK);

    }
    public void reDrawSquare(Game currentGame)
    {
        this.resetSquare();
        this.checkBuildings(currentGame);
        this.checkTokensOnTop(currentGame);
        this.checkOwner(currentGame);
        this.checkPriceOrRent(currentGame);
    }
}
