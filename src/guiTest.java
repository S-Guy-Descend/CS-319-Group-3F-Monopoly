import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
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
    /*
    Label creditsLabel;
    Label atakuB;
    Label keremKaramel;
    Label memati06;
    Label samarKing;
     */



    public static void main( String[] args)
    {
        launch( args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        window = primaryStage;
        // pencerenin adı
        primaryStage.setTitle( "Uzak Doğuya Has Macera, Karakterini Seç ve Savaşa Katıl");

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
        creditsButton.setOnAction( this);

        // Main menu layout
        VBox mainMenuLayout = new VBox( 10);
        mainMenuLayout.getChildren().addAll( gameName, hostGameButton, joinGameButton, tutorialButton, settingsButton, exitButton, creditsButton);
        mainMenuLayout.setAlignment(Pos.CENTER);
        mainMenu = new Scene( mainMenuLayout, 500, 500);

        // ilk ekran main menü olsun diye
        window.setScene( mainMenu);
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
        joinGame = new Scene( joinGameLayout, 500, 500);


        // inGame Ekranı
        rollDice = new Button();
        rollDice.setText( "Roll the Dice");
        rollDice.setOnAction( this);

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


        // layout ayarları
        VBox inGameLayout = new VBox( 20);
        inGameLayout.getChildren().addAll( rollDice, build, useScroll, buyProperty, sendTrade, acceptTrade, declineTrade, endTurn);
        // içerik scenenin içine koyuluyor, constructor dimensionları alıyor
        inGame = new Scene( inGameLayout, 500, 500);
        /*
        StackPane layout = new StackPane();
        layout.getChildren().add( rollDice);
        layout.getChildren().add( build);
        layout.getChildren().add( useScroll);
        layout.getChildren().add( buyProperty);
        layout.getChildren().add( sendTrade);
        layout.getChildren().add( acceptTrade);
        layout.getChildren().add( declineTrade);
        layout.getChildren().add( endTurn);

         */

        // Main Menü




        //primaryStage.setScene( inGame);
        // kullanıcıya göstermek için
        //primaryStage.show();
    }
    // kullanıcı butona tıklayınca bu çağırılıyor
    @Override
    public void handle(ActionEvent event) {

        // hangi buton ne yapıyor kontrol etmek için
        if( event.getSource() == rollDice)
        {
            //oyuncuya zar attır laaa
            System.out.println( "2 zara 100.000 verdik Ekrem Abi Ayıp Oluyor");
        }
        if( event.getSource() == build)
        {
            System.out.println( "Malzeme Kaçırarak İşlem Tamamlandı");
        }
        if( event.getSource() == useScroll )
        {
            System.out.println( "Tf W attı ! (scroll)");
        }
        if( event.getSource() == buyProperty)
        {
            System.out.println( "Bu ev Artık BENİM !!!!");
        }
        if( event.getSource() == sendTrade)
        {
            System.out.println( "Ticaret Atsana lan");
        }
        if( event.getSource() == acceptTrade)
        {
            System.out.println( "Ticaretini görüyorum ve kabul ediyorum");
        }
        if( event.getSource() == declineTrade)
        {
            System.out.println( "Siktir lan");
        }
        if( event.getSource() == endTurn)
        {
            System.out.println( "Benim Kumarım Burda Biter");
        }

    }
}
