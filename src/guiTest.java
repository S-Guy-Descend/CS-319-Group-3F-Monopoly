        import javafx.application.Application;
        import javafx.event.ActionEvent;
        import javafx.event.EventHandler;
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
        import javafx.scene.text.Text;
        import javafx.stage.Stage;

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
    int[][] boardShape = new int[BOARD_WIDTH][BOARD_HEIGHT];





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
        //Feast
        SquareVisual[] rectArr = new SquareVisual[40];

        for(int i = 0; i < 40; i++)
        {
            rectArr[i] = new SquareVisual(Game.instance.board.map[i].name);
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




        // layout ayarları
        VBox inGameLayout = new VBox( 20);
        inGameLayout.getChildren().addAll( gridPane);
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
        StackPane sp;
        Text squareName;

        //constructor
        public SquareVisual( String name)
        {
            //Rectangle rect = new Rectangle();
            setHeight( SQUARE_SIZE);
            setWidth( SQUARE_SIZE);
            setStroke( Color.BLACK);
            setFill( Color.WHITE);
            squareName = new Text( name);
            sp = new StackPane();
            sp.getChildren().addAll(this, squareName);
        }
        //methods



    }
    /*
    public Pane constructBoardVisual()
    {


    }

    */
}
