        import javafx.application.Application;
        import javafx.event.ActionEvent;
        import javafx.event.EventHandler;
        import javafx.geometry.Insets;
        import javafx.geometry.Pos;
        import javafx.scene.Node;
        import javafx.scene.Scene;
        import javafx.scene.control.Button;
        import javafx.scene.control.Label;
        import javafx.scene.control.TextField;
        import javafx.scene.layout.*;
        import javafx.scene.paint.Color;
        import javafx.scene.shape.Circle;
        import javafx.scene.shape.Rectangle;
        import javafx.scene.shape.StrokeType;
        import javafx.scene.text.Font;
        import javafx.scene.text.Text;
        import javafx.stage.Stage;

        import java.util.ArrayList;

        public class guiTest extends Application implements EventHandler<ActionEvent>{
    // ana pencere
    Stage window;

    // farklı sayfalar için farklı sceneler
    Scene mainMenu, hostGame, joinGame, howToPlay, soundSettings, credits, inGame;

    // main menü bileşenleri
    Label gameName;
    Button hostGameButton;
    Button joinGameButton;
    Button tutorialButton;
    Button settingsButton;
    Button exitButton;
    Button creditsButton;

    // join game bileşenleri
    Label enterGameId;
    TextField gameIDTxtField;
    Button joinGameBack;
    Button joinGameJoin;


    // oyuncu seçenekleri ekranı bileşenleri
    Button rollDice;
    Button build;
    Button useScroll;
    Button buyProperty;
    Button sendTrade;
    Button acceptTrade;
    Button declineTrade;
    Button endTurn;

    // credits ekran bileşenleri

    Label creditsLabel;
    Label teamMembers;
    Button creditsBack;

    //board bileşenleri
    public static final int SQUARE_SIZE = 91;
    public static final int BOARD_WIDTH = 5;
    public static final int BOARD_HEIGHT = 5;






    public static void main( String[] args)
    {
        launch( args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        window = primaryStage;
        // pencerenin adı
        primaryStage.setTitle( "Group 3F Monopoly");

        // buton oluşturma ve isimlendirme
        // main menü
        gameName = new Label();
        gameName.setText( "Scrolls of Estatia Ltd Sti");

        hostGameButton = new Button();
        hostGameButton.setText( "Host Game");
        hostGameButton.setOnAction( this);

        joinGameButton = new Button();
        joinGameButton.setText( "Join Game");
        joinGameButton.setOnAction(e -> { window.setScene( joinGame); });

        tutorialButton = new Button();
        tutorialButton.setText( "Tutorial");
        tutorialButton.setOnAction( this);

        settingsButton = new Button();
        settingsButton.setText( "Settings");
        settingsButton.setOnAction( this);

        exitButton = new Button();
        exitButton.setText( "Exit");
        exitButton.setOnAction( this);

        creditsButton = new Button();
        creditsButton.setText( "Credits");
        creditsButton.setOnAction( e -> { window.setScene( credits);});

        // Main menu layout
        VBox mainMenuLayout = new VBox( 10);
        mainMenuLayout.getChildren().addAll( gameName, hostGameButton, joinGameButton, tutorialButton, settingsButton, exitButton, creditsButton);
        mainMenuLayout.setAlignment(Pos.CENTER);
        mainMenu = new Scene( mainMenuLayout, 500, 500);

        // ilk ekran main menü olsun diye
        window.setScene( mainMenu );
        window.show();

        // join game ekranı
        enterGameId = new Label();
        enterGameId.setText( "Enter the Game ID");

        gameIDTxtField = new TextField();

        joinGameButton = new Button();
        joinGameButton.setText( "Join");
        joinGameButton.setOnAction(e -> { window.setScene( inGame);});

        joinGameBack = new Button();
        joinGameBack.setText( "Back");
        joinGameBack.setOnAction( e -> { window.setScene( mainMenu);});

        //join game layout ayarları
        VBox joinGameLayout = new VBox( 20);
        joinGameLayout.getChildren().addAll( enterGameId, gameIDTxtField, joinGameButton, joinGameBack);
        joinGameLayout.setAlignment( Pos.CENTER);
        joinGame = new Scene( joinGameLayout, 500, 500);


        // inGame Ekranı
        rollDice = new Button();
        rollDice.setText( "Roll the Dice");
        rollDice.setOnAction( this); // lambda expression kullanabiliriz

        build = new Button();
        build.setText( "Build");
        build.setOnAction( this);

        useScroll = new Button();
        useScroll.setText( "Use Scroll");
        useScroll.setOnAction( this);

        buyProperty = new Button();
        buyProperty.setText( "Buy Property");
        buyProperty.setOnAction( this);

        sendTrade = new Button();
        sendTrade.setText( "Send Trade Request"); // bu listed olacak
        sendTrade.setOnAction( this);

        acceptTrade = new Button();
        acceptTrade.setText( "Accept Trade Request");
        acceptTrade.setOnAction( this);

        declineTrade = new Button();
        declineTrade.setText( "Decline Trade Request");
        declineTrade.setOnAction( this);

        endTurn = new Button();
        endTurn.setText( "End Turn");
        endTurn.setOnAction( this);

        // board
        SquareVisual[] rectArr = new SquareVisual[40];

        for(int i = 0; i < 40; i++)
        {
            rectArr[i] = new SquareVisual(Game.instance.board.map[i].name, i);
        }


        GridPane gridPane = new GridPane();


        for( int i = 0; i < 11; i++)
        {
            gridPane.add( rectArr[20 + i].sp, i, 0, 1,1);
        }
        // en sol satır
        for( int i = 0; i < 9; i++)
        {
            gridPane.add( rectArr[19 - i].sp, 0, i + 1 , 1, 1);
        }
        //en sağ
        for( int i = 0; i < 9; i++)
        {
            gridPane.add( rectArr[i + 31].sp,  10, i + 1, 1,1);
        }

         // en alt satır
        for( int i = 0; i < 11; i++)
        {
            gridPane.add( rectArr[10 - i].sp, i,  10, 1,1);
        }

        //rectArr[1].player1Avatar.setVisible( true);
        //rectArr[1].player2Avatar.setVisible( true);

        // test alanı
        Button test1 = new Button( "TEST 1");
        test1.setOnAction( e->{
            Game.instance.board.map[1].tokensOnTop.add( 0);
            Game.instance.board.map[1].tokensOnTop.add( 3);
            Game.instance.board.map[1].tokensOnTop.add( 7);

            ((Town)(Game.instance.board.map[1])).numberOfInns = 3;
            ((Town)(Game.instance.board.map[1])).ownerId = 4;

            for( int i = 0; i < rectArr.length; i++)
            {
                rectArr[i].reDrawSquare();
            }
        });

        Button test2 = new Button( "TEST 2");
        test2.setOnAction( e->
        {

            Game.instance.board.map[1].tokensOnTop.remove( 0);
            Game.instance.board.map[1].tokensOnTop.remove( 0);
            Game.instance.board.map[1].tokensOnTop.remove( 0);

            ((Town)(Game.instance.board.map[1])).numberOfInns = 0;
            ((Town)(Game.instance.board.map[1])).ownerId = 2;

            Game.instance.board.map[29].tokensOnTop.add(2);
            Game.instance.board.map[29].tokensOnTop.add(7);

            ((Town)(Game.instance.board.map[29])).numberOfInns = 4;
            ((Town)(Game.instance.board.map[1])).ownerId = 1;

            for( int i = 0; i < rectArr.length; i++)
            {
                rectArr[i].reDrawSquare();
            }
        });



        /*
        for( int i = 0; i < rectArr.length; i++)
        {
            rectArr[i].reDrawSquare();
        }

        */
        
        // layout ayarları
        HBox inGameLayout = new HBox( 20);
        inGameLayout.getChildren().addAll( gridPane, test1, test2);
        // içerik scenenin içine koyuluyor, constructor dimensionları alıyor
        inGame = new Scene( inGameLayout, 500, 500);

        // credits
        creditsLabel = new Label();
        creditsLabel.setText( "~Credits~");

        teamMembers = new Label();
        teamMembers.setText("Atakan Sağlam \n" +
                            "Sarp Ulaş Kaya \n" +
                            "Furkan Başkaya \n" +
                            "Oğulcan Çetinkaya \n" +
                            "Berk Kerem Berçin");

        creditsBack = new Button();
        creditsBack.setText("Back");
        creditsBack.setOnAction(e -> { window.setScene( mainMenu);});

        // credits layout ayarları
        VBox creditsLayout = new VBox();
        creditsLayout.getChildren().addAll( creditsLabel, teamMembers, creditsBack);
        creditsLayout.setAlignment( Pos.CENTER);

        credits = new Scene( creditsLayout, 500, 500);

    }
    // kullanıcı butona tıklayınca bu çağırılıyor
    @Override
    public void handle(ActionEvent event) {

        // hangi buton ne yapıyor kontrol etmek için
        if( event.getSource() == rollDice)
        {
            //oyuncuya zar attır laaa
            System.out.println( "Roll Dice");
        }
        if( event.getSource() == build)
        {
            System.out.println( "Build");
        }
        if( event.getSource() == useScroll )
        {
            System.out.println( "Used a scroll card");
        }
        if( event.getSource() == buyProperty)
        {
            System.out.println( "Buy Property");
        }
        if( event.getSource() == sendTrade)
        {
            System.out.println( "Send Trade");
        }
        if( event.getSource() == acceptTrade)
        {
            System.out.println( "Accept Trade");
        }
        if( event.getSource() == declineTrade)
        {
            System.out.println( "Decline Trade");
        }
        if( event.getSource() == endTurn)
        {
            System.out.println( "End Turn");
        }

    }

    public class SquareVisual extends Rectangle
    {
        //properties
        //playerlar burda tutulacak
        HBox top;
        VBox left;
        VBox right;
        HBox bottom;

        //player avatarları
        Color player1Color = Color.BLACK;
        Color player2Color = Color.RED;
        Color player3Color = Color.GREEN;
        Color player4Color = Color.YELLOW;
        Color player5Color = Color.BLUE;
        Color player6Color = Color.GRAY;
        Color player7Color = Color.GOLD;
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

        //constructor
        public SquareVisual( String name, int ID)
        {
            //hazır player tokenleri şimdilik rectangle daha sonra image ile değiştirilecek




            player1Avatar = new Rectangle();
            player1Avatar.setStroke( Color.BLACK);
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
            squareName = new Text( name);
            squareName.setFont(new Font(12));

            // square sp nin içinde tutuluyor
            sp = new StackPane();
            sp.getChildren().addAll(this, squareName, top, right, bottom, left);
        }
        //methods

        public void checkTokensOnTop()
        {
            if( !Game.instance.board.map[ squareID].tokensOnTop.isEmpty())
            {
                for( int i = 0; i < Game.instance.board.map[ squareID].tokensOnTop.size(); i++)
                {
                    switch( Game.instance.board.map[ squareID].tokensOnTop.get(i))
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
        public void checkBuildings()
        {
            if( Game.instance.board.map[ squareID] instanceof Town)
            {
                switch( ((Town)(Game.instance.board.map[ squareID])).numberOfInns)
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
        public void checkOwner()
        {
            if( Game.instance.board.map[ squareID] instanceof  Town)
            {
                switch( ((Town)(Game.instance.board.map[ squareID])).ownerId)
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
            else if( Game.instance.board.map[ squareID] instanceof  Transport) {
                switch (((Transport) (Game.instance.board.map[squareID])).ownerId) {
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
            else if( Game.instance.board.map[ squareID] instanceof  Smith) {
                switch (((Smith) (Game.instance.board.map[squareID])).ownerId) {
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
        public void reDrawSquare()
        {
            this.resetSquare();
            this.checkBuildings();
            this.checkTokensOnTop();
            this.checkOwner();
        }
    }
}

