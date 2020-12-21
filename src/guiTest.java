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
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.ArrayList;

public class guiTest extends Application implements EventHandler<ActionEvent>{

    public static void main( String[] args)
    {
        launch( args);
    }


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
        // board arrayi
        SquareVisual[] rectArr = new SquareVisual[40];

        //square objelerini teker teker arraya ekle. Constructor isim ve squareID alıyor. squareID maptaki sayılarla
        // squareleri bağdaştırmak için
        for(int i = 0; i < 40; i++)
        {
            rectArr[i] = new SquareVisual(Game.instance.board.map[i].name + "\nPrice:", i);


        }

        GridPane gridPane = new GridPane();

        for( int i = 0; i < 11; i++)
        {
            gridPane.add( rectArr[20 + i].sp, i, 0, 1,1);
        }
        // en sol satır
        for( int i = 0; i < 9; i++)
        {
            gridPane.add( rectArr[19 - i].sp, 0, i + 1 , 1,1);
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

        // teker teker renk gruplarını girdim
        rectArr[1].squareColor.setFill( Color.PURPLE);
        rectArr[1].colorContainer.setVisible( true);

        rectArr[3].squareColor.setFill( Color.PURPLE);
        rectArr[3].colorContainer.setVisible( true);

        rectArr[6].squareColor.setFill( Color.LIGHTBLUE);
        rectArr[6].colorContainer.setVisible( true);

        rectArr[8].squareColor.setFill( Color.LIGHTBLUE);
        rectArr[8].colorContainer.setVisible( true);

        rectArr[9].squareColor.setFill( Color.LIGHTBLUE);
        rectArr[9].colorContainer.setVisible( true);

        //dungeon burda yapılacak

        //
        rectArr[11].squareColor.setFill( Color.DEEPPINK);
        rectArr[11].colorContainer.setVisible( true);



        rectArr[13].squareColor.setFill( Color.DEEPPINK);
        rectArr[13].colorContainer.setVisible( true);

        rectArr[14].squareColor.setFill( Color.DEEPPINK);
        rectArr[14].colorContainer.setVisible( true);

        rectArr[16].squareColor.setFill( Color.ORANGE);
        rectArr[16].colorContainer.setVisible( true);

        rectArr[18].squareColor.setFill( Color.ORANGE);
        rectArr[18].colorContainer.setVisible( true);

        rectArr[19].squareColor.setFill( Color.ORANGE);
        rectArr[19].colorContainer.setVisible( true);

        // feast
        //
        rectArr[21].squareColor.setFill( Color.RED);
        rectArr[21].colorContainer.setVisible( true);

        rectArr[23].squareColor.setFill( Color.RED);
        rectArr[23].colorContainer.setVisible( true);

        rectArr[24].squareColor.setFill( Color.RED);
        rectArr[24].colorContainer.setVisible( true);

        rectArr[26].squareColor.setFill( Color.GOLD);
        rectArr[26].colorContainer.setVisible( true);

        rectArr[27].squareColor.setFill( Color.GOLD);
        rectArr[27].colorContainer.setVisible( true);

        rectArr[29].squareColor.setFill( Color.GOLD);
        rectArr[29].colorContainer.setVisible( true);

        //go to dungeon
        //
        rectArr[31].squareColor.setFill( Color.GREEN);
        rectArr[31].colorContainer.setVisible( true);

        rectArr[32].squareColor.setFill( Color.GREEN);
        rectArr[32].colorContainer.setVisible( true);

        rectArr[34].squareColor.setFill( Color.GREEN);
        rectArr[34].colorContainer.setVisible( true);

        rectArr[37].squareColor.setFill( Color.DARKBLUE);
        rectArr[37].colorContainer.setVisible( true);

        rectArr[39].squareColor.setFill( Color.DARKBLUE);
        rectArr[39].colorContainer.setVisible( true);

        Image testAvatar = new Image( "thiefAvatar.png");

        rectArr[1].player1Avatar.setFill( new ImagePattern(testAvatar));






        // test alanı
        Button test1 = new Button( "TEST 1");
        test1.setOnAction( e->{
            Game.instance.board.map[1].tokensOnTop.add( 0);
            Game.instance.board.map[1].tokensOnTop.add( 3);
            Game.instance.board.map[1].tokensOnTop.add( 7);

            ((Town)(Game.instance.board.map[1])).numberOfInns = 3;
            ((Town)(Game.instance.board.map[1])).ownerId = 4;


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
            /*
            for( int i = 0; i < rectArr.length; i++)
            {
                rectArr[i].reDrawSquare();
            } */
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


}
