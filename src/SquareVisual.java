import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
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

    Circle player1Avatar;
    Circle player2Avatar;
    Circle player3Avatar;
    Circle player4Avatar;
    Circle player5Avatar;
    Circle player6Avatar;
    Circle player7Avatar;
    Circle player8Avatar;

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

        player1Avatar = new Circle();
        player1Avatar.setStroke( Color.BLACK);
        player1Avatar.setStrokeType(StrokeType.INSIDE);
        player1Avatar.setStrokeWidth(2);
        player1Avatar.setFill( player1Color);
        player1Avatar.setRadius(12.5);
        player1Avatar.setVisible( false);


        player2Avatar = new Circle();
        player2Avatar.setStroke( Color.BLACK);
        player2Avatar.setStrokeType(StrokeType.INSIDE);
        player2Avatar.setStrokeWidth(2);
        player2Avatar.setFill( player2Color);
        player2Avatar.setRadius(12.5);
        player2Avatar.setVisible( false);

        player3Avatar = new Circle();
        player3Avatar.setStroke( Color.BLACK);
        player3Avatar.setStrokeType(StrokeType.INSIDE);
        player3Avatar.setStrokeWidth(2);
        player3Avatar.setFill( player3Color);
        player3Avatar.setRadius(12.5);
        player3Avatar.setVisible( false);

        player4Avatar = new Circle();
        player4Avatar.setStroke( Color.BLACK);
        player4Avatar.setStrokeType(StrokeType.INSIDE);
        player4Avatar.setStrokeWidth(2);
        player4Avatar.setFill( player4Color);
        player4Avatar.setRadius(12.5);
        player4Avatar.setVisible( false);

        player5Avatar = new Circle();
        player5Avatar.setStroke( Color.BLACK);
        player5Avatar.setStrokeType(StrokeType.INSIDE);
        player5Avatar.setStrokeWidth(2);
        player5Avatar.setFill( player5Color);
        player5Avatar.setRadius(12.5);
        player5Avatar.setVisible( false);

        player6Avatar = new Circle();
        player6Avatar.setStroke( Color.BLACK);
        player6Avatar.setStrokeType(StrokeType.INSIDE);
        player6Avatar.setStrokeWidth(2);
        player6Avatar.setFill( player6Color);
        player6Avatar.setRadius(12.5);
        player6Avatar.setVisible( false);

        player7Avatar = new Circle();
        player7Avatar.setStroke( Color.BLACK);
        player7Avatar.setStrokeType(StrokeType.INSIDE);
        player7Avatar.setStrokeWidth(2);
        player7Avatar.setFill( player7Color);
        player7Avatar.setRadius(12.5);
        player7Avatar.setVisible( false);

        player8Avatar = new Circle();
        player8Avatar.setStroke( Color.BLACK);
        player8Avatar.setStrokeType(StrokeType.INSIDE);
        player8Avatar.setStrokeWidth(2);
        player8Avatar.setFill( player8Color);
        player8Avatar.setRadius(12.5);
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
