import com.sun.security.ntlm.Client;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class GameViewManager {
    private final static int GAME_WIDTH = 1024;
    private final static int GAME_HEIGHT = 1024;
    private final String PLAYER_INFO_BACKGROUND = "-fx-background-color: #B2B2B2;";

    private AnchorPane gamePane;
    private Scene gameScene;
    private Stage gameStage;

    private StyledButton rollDice;
    private StyledButton build;
    private StyledButton useScroll;
    private StyledButton purchaseLand;
    private StyledButton sendTrade;
    private StyledButton acceptTrade;
    private StyledButton declineTrade;
    private StyledButton endTurn;

    private GridPane playerInfo;

    private ArrayList<Label> playerNames;
    private ArrayList<Label> playerMoneys;

    private ClientSideConnection csc;
    private ArrayList<String> classes;
    private Game currentGameState;

    private int lastOp = -1;

    private SquareVisual[] rectArr;

    public GameViewManager(ClientSideConnection csc, ArrayList<String> classes) {
        this.csc = csc;
        this.classes = classes;
        initializeGame();
    }

    private void initializeGame() {



        rollDice = new StyledButton("Roll Dice");
        rollDice.setOnAction( e -> {
            if (csc.isTurn) {
                rollDice.disable(true);
                // CHECK EACH BUTTON HERE


                try {
                    System.out.println("Player " + csc.playerID + " rolled dice");
                    csc.dataOut.writeInt(0);
                    lastOp = 0;
                    csc.dataOut.flush();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        build = new StyledButton("Build");
        build.setOnAction( e -> {
            if (csc.isTurn) {
                build.disable(true);
                try {
                    System.out.println("Player " + csc.playerID + " built");
                    csc.dataOut.writeInt(1);
                    lastOp = 1;
                    csc.dataOut.flush();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        useScroll = new StyledButton("Use Scroll");
        useScroll.setOnAction( e -> {
            if (csc.isTurn) {
                useScroll.disable(true);
                try {
                    System.out.println("Player " + csc.playerID + " used scroll");
                    csc.dataOut.writeInt(2);
                    lastOp = 2;
                    csc.dataOut.flush();

                    // GET PLAYER TO CHOOSE A SCROLL AND VICTIM
                    int scrollIndex = 0;
                    int victimID = 0;

                    csc.dataOut.writeInt(scrollIndex);
                    csc.dataOut.flush();
                    csc.dataOut.writeInt(victimID);
                    csc.dataOut.flush();

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        purchaseLand = new StyledButton("Purchase Land");
        purchaseLand.setOnAction( e -> {
            if (csc.isTurn) {
                purchaseLand.disable(true);
                try {
                    System.out.println("Player " + csc.playerID + " bought property");
                    csc.dataOut.writeInt(3);
                    lastOp = 3;
                    csc.dataOut.flush();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        sendTrade = new StyledButton("Send Trade Offer");
        sendTrade.setOnAction( e -> {
            if (csc.isTurn) {
                sendTrade.disable(true);
                try {
                    System.out.println("Player " + csc.playerID + " sent trade");

                    // TO-DO CONSTRUCT THE TRADE REQUEST PROPERLY
                    csc.dataOut.writeInt(4);
                    lastOp = 4;
                    csc.dataOut.flush();

                    //Construct the trade request
                    ArrayList<Square> placesToGive = new ArrayList<Square>();
                    placesToGive.add(Game.instance.board.map[1]);
                    ArrayList<Square> placesToTake = new ArrayList<Square>();
                    placesToTake.add(Game.instance.board.map[3]);
                    int moneyToGive = 100;
                    int moneyToTake = 300;
                    int tradeReceiver = 0;
                    TradeRequest tradeRequest = new TradeRequest(placesToGive, placesToTake, moneyToGive, moneyToTake, tradeReceiver);

                    csc.dataOut.writeObject(tradeRequest);
                    csc.dataOut.flush();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        acceptTrade = new StyledButton("Accept Trade Offer");
        acceptTrade.setOnAction( e -> {
            if (csc.isTurn) {
                acceptTrade.disable(true);
                declineTrade.disable(true);
                try {
                    System.out.println("Player " + csc.playerID + " accepted trade");
                    csc.dataOut.writeInt(5);
                    lastOp = 5;
                    csc.dataOut.flush();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        declineTrade = new StyledButton("Decline Trade Offer");
        declineTrade.setOnAction( e -> {
            if (csc.isTurn) {
                declineTrade.disable(true);
                acceptTrade.disable(true);
                try {
                    System.out.println("Player " + csc.playerID + " declined trade");
                    csc.dataOut.writeInt(6);
                    lastOp = 6;
                    csc.dataOut.flush();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        endTurn = new StyledButton("End Turn");
        endTurn.setOnAction( e -> {
            if (csc.isTurn) {
                rollDice.disable(true);
                build.disable(true);
                useScroll.disable(true);
                purchaseLand.disable(true);
                sendTrade.disable(true);
                acceptTrade.disable(true);
                declineTrade.disable(true);
                endTurn.disable(true);
                try {
                    System.out.println("Player " + csc.playerID + " ended Turn");
                    csc.dataOut.writeInt(7);
                    lastOp = 7;
                    csc.dataOut.flush();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        playerInfo = new GridPane();
        playerInfo.setGridLinesVisible(true);
        playerInfo.setStyle(PLAYER_INFO_BACKGROUND);
        playerNames = new ArrayList<Label>();
        for ( int i = 0; i < classes.size(); i++) {
            playerNames.add( new Label(classes.get(i)));
        }

        playerMoneys = new ArrayList<Label>();
        for ( int i = 0; i < classes.size(); i++) {
            playerMoneys.add( new Label(""));
        }

        for ( int i = 0; i < classes.size(); i++) {
            playerNames.get(i).setFont( new Font(24));
            playerMoneys.get(i).setFont( new Font(24));
        }

        for ( int i = 0; i < classes.size(); i++) {
            switch (i) {
                case 0:
                    playerNames.get(i).setTextFill(Color.RED);
                    playerMoneys.get(i).setTextFill(Color.RED);
                    break;
                case 1:
                    playerNames.get(i).setTextFill(Color.GREEN);
                    playerMoneys.get(i).setTextFill(Color.GREEN);
                    break;
                case 2:
                    playerNames.get(i).setTextFill(Color.BLUE);
                    playerMoneys.get(i).setTextFill(Color.BLUE);
                    break;
                case 3:
                    playerNames.get(i).setTextFill(Color.YELLOW);
                    playerMoneys.get(i).setTextFill(Color.YELLOW);
                    break;
                case 4:
                    playerNames.get(i).setTextFill(Color.CYAN);
                    playerMoneys.get(i).setTextFill(Color.CYAN);
                    break;
                case 5:
                    playerNames.get(i).setTextFill(Color.HOTPINK);
                    playerMoneys.get(i).setTextFill(Color.HOTPINK);
                    break;
                case 6:
                    playerNames.get(i).setTextFill(Color.ORANGE);
                    playerMoneys.get(i).setTextFill(Color.ORANGE);
                    break;
                case 7:
                    playerNames.get(i).setTextFill(Color.PURPLE);
                    playerMoneys.get(i).setTextFill(Color.PURPLE);
                    break;
            }
        }

        for (int i = 0; i < classes.size(); i++) {
            playerInfo.add( playerNames.get(i), 0, i);
            playerInfo.add( playerMoneys.get(i), 1, i);
        }


        gamePane = new AnchorPane();
        createBackground();

        rollDice.setLayoutX(1050);
        rollDice.setLayoutY(50);

        build.setLayoutX(1050);
        build.setLayoutY(100);

        purchaseLand.setLayoutX(1050);
        purchaseLand.setLayoutY(150);

        useScroll.setLayoutX(1050);
        useScroll.setLayoutY(200);

        sendTrade.setLayoutX(1050);
        sendTrade.setLayoutY(350);

        acceptTrade.setLayoutX(1050);
        acceptTrade.setLayoutY(400);

        declineTrade.setLayoutX(1050);
        declineTrade.setLayoutY(450);

        endTurn.setLayoutX(1050);
        endTurn.setLayoutY(250);

        playerInfo.setLayoutX(1300);
        playerInfo.setLayoutY(50);

        rollDice.disable(!csc.isHost);
        build.disable(true);
        useScroll.disable(true);
        purchaseLand.disable(true);
        sendTrade.disable(true);
        acceptTrade.disable(true);
        declineTrade.disable(true);
        endTurn.disable(true);

        gamePane.getChildren().addAll(rollDice, build, purchaseLand, useScroll, endTurn, playerInfo);
        gameScene = new Scene(gamePane, GAME_WIDTH, GAME_HEIGHT);
        gameStage = new Stage();
        gameStage.setScene(gameScene);
        gameStage.setMaximized(true);

        initializeBoard();
    }

    public void enterGame(){
        gameStage.show();
    }


    public void createBackground() {
        Image image = new Image("/model/wooden_background.png", 256, 256, false, true);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
        gamePane.setBackground(new Background(backgroundImage));
    }

    public void startReceivingTurns() {
        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    currentGameState = (Game) (csc.dataIn.readObject());
                    redrawBoard();
                    Platform.runLater(new Runnable() {
                        @Override public void run() {
                            updateMoneys();
                        }
                    });
                    for(int i = 0; i < currentGameState.tokens.size(); i++) {
                        System.out.println("Player: " + (i + 1) + " Location: " +  currentGameState.tokens.get(i).currentLocation + " Money: " + currentGameState.tokens.get(i).money);
                    }
                } catch (IOException exception) {
                    exception.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                while (true) {
                    try {
                        csc.isTurn = csc.dataIn.readBoolean();

                        if (!csc.isTurn) {
                            boolean isMyTurn = false;
                            while (!isMyTurn) {
                                // Getting game data during someone else's turn
                                currentGameState = (Game) (csc.dataIn.readObject());

                                for(int i = 0; i < currentGameState.tokens.size(); i++) {
                                    System.out.println("Player: " + (i + 1) + " Location: " +  currentGameState.tokens.get(i).currentLocation + " Money: " + currentGameState.tokens.get(i).money + "ScrollCards: " + currentGameState.tokens.get(i).scrollCards.size() );
                                }

                                //UPDATE BOARD HERE
                                redrawBoard();
                                Platform.runLater(new Runnable() {
                                    @Override public void run() {
                                        updateMoneys();
                                    }
                                });

                                isMyTurn = csc.dataIn.readBoolean();
                            }
                        }
                        System.out.println("Player " + csc.playerID + " started Turn");

                        rollDice.disable(false);

                        build.disable(true);
                        useScroll.disable(true);
                        purchaseLand.disable(true);
                        sendTrade.disable(true);
                        acceptTrade.disable(true);
                        declineTrade.disable(true);
                        endTurn.disable(true);
                        if (csc.isTurn) {
                            boolean endedTurn = false;
                            while (!endedTurn) {
                                // Getting game data during your turn
                                currentGameState = (Game) (csc.dataIn.readObject());
                                if (lastOp == 0) {
                                    build.disable(!currentGameState.tokens.get(csc.playerID - 1).isBuildAvailable());
                                    useScroll.disable(!currentGameState.tokens.get(csc.playerID - 1).isScrollAvailable());
                                    purchaseLand.disable(!currentGameState.tokens.get(csc.playerID - 1).isLandPurchasable());
                                    sendTrade.disable(true);
                                    acceptTrade.disable(true);
                                    declineTrade.disable(true);
                                    endTurn.disable(false);
                                }
                                for(int i = 0; i < currentGameState.tokens.size(); i++) {
                                    System.out.println("Player: " + (i + 1) + "Location: " +  currentGameState.tokens.get(i).currentLocation + "Money: " + currentGameState.tokens.get(i).money);
                                }

                                // UPDATE BOARD HERE
                                redrawBoard();
                                Platform.runLater(new Runnable() {
                                    @Override public void run() {
                                        updateMoneys();
                                    }
                                });


                                endedTurn = csc.dataIn.readBoolean();
                            }
                        }
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        t.start();
    }

    public void initializeBoard() {
        // board
        // board arrayi
        rectArr = new SquareVisual[40];

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

        gridPane.setLayoutX(20);

        gamePane.getChildren().add(gridPane);
    }

    public void redrawBoard() {
        for( int i = 0; i < rectArr.length; i++)
        {
            rectArr[i].reDrawSquare(currentGameState);
        }
    }

    public void updateMoneys() {
        for ( int i = 0; i < classes.size(); i++) {
            playerMoneys.get(i).setText(String.valueOf(currentGameState.tokens.get(i).money));
        }
    }
}
