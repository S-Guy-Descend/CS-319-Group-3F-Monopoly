import com.sun.security.ntlm.Client;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class GameViewManager {
    private final static int GAME_WIDTH = 1024;
    private final static int GAME_HEIGHT = 1024;

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

    private ClientSideConnection csc;
    private Game currentGameState;

    public GameViewManager(ClientSideConnection csc) {
        this.csc = csc;
        initializeGame();
    }

    private void initializeGame() {

        rollDice = new StyledButton("Roll Dice");
        rollDice.setOnAction( e -> {
            if (csc.isTurn) {
                rollDice.disable(true);
                build.disable(false);
                useScroll.disable(false);
                purchaseLand.disable(false);
                sendTrade.disable(false);
                acceptTrade.disable(false);
                declineTrade.disable(false);
                endTurn.disable(false);
                try {
                    System.out.println("Player " + csc.playerID + " rolled dice");
                    csc.dataOut.writeInt(0);
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
                    csc.dataOut.flush();
                    boolean purchaseSuccessful = csc.dataIn.readBoolean();
                    if (!purchaseSuccessful) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Unable to purchase land!");
                        alert.setHeaderText(null);
                        alert.setContentText("You cannot purchase this land!");
                    }
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
                    csc.dataOut.flush();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });



        gamePane = new AnchorPane();

        rollDice.setLayoutX(1050);
        rollDice.setLayoutY(50);

        build.setLayoutX(1050);
        build.setLayoutY(100);

        purchaseLand.setLayoutX(1050);
        purchaseLand.setLayoutY(150);

        useScroll.setLayoutX(1050);
        useScroll.setLayoutY(200);

        sendTrade.setLayoutX(1050);
        sendTrade.setLayoutY(250);

        acceptTrade.setLayoutX(1050);
        acceptTrade.setLayoutY(300);

        declineTrade.setLayoutX(1050);
        declineTrade.setLayoutY(350);

        endTurn.setLayoutX(1050);
        endTurn.setLayoutY(400);

        rollDice.disable(!csc.isHost);
        build.disable(true);
        useScroll.disable(true);
        purchaseLand.disable(true);
        sendTrade.disable(true);
        acceptTrade.disable(true);
        declineTrade.disable(true);
        endTurn.disable(true);

        gamePane.getChildren().addAll(rollDice, build, purchaseLand, useScroll, sendTrade, acceptTrade, declineTrade, endTurn);
        gameScene = new Scene(gamePane, GAME_WIDTH, GAME_HEIGHT);
        gameStage = new Stage();
        gameStage.setScene(gameScene);
        gameStage.setMaximized(true);

        createBackground();
    }

    public void enterGame(){
        gameStage.show();
    }

    public void createBackground() {
        Image image = new Image("/model/board.png", 1024, 1024, false, true);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
        gamePane.setBackground(new Background(backgroundImage));
    }

    public void startReceivingTurns() {
        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    System.out.println("WAITING FOR GAME, " + csc.dataIn.available());
                    currentGameState = (Game) (csc.dataIn.readObject());
                    System.out.println("GOT GAME, " + csc.dataIn.available());
                } catch (IOException exception) {
                    exception.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                while (true) {
                    try {
                        System.out.println("WAITING FOR A BOOLEAN");
                        csc.isTurn = csc.dataIn.readBoolean();
                        System.out.println("GOT A BOOLEAN");
                        System.out.println("Player " + csc.playerID + " started Turn");
                        if (!csc.isTurn) {
                            boolean isMyTurn = false;
                            while (!isMyTurn) {
                                // Getting game data during someone else's turn
                                System.out.println("WAITING FOR GAME IN WAIT LOOP, " + csc.dataIn.available());
                                currentGameState = (Game) (csc.dataIn.readObject());
                                System.out.println("GOT GAME IN WAIT LOOP, " + csc.dataIn.available());
                                System.out.println("WAITING FOR A BOOLEAN IN WAIT LOOP, " + csc.dataIn.available());
                                isMyTurn = csc.dataIn.readBoolean();
                                System.out.println("GOT A BOOLEAN IN WAIT LOOP");
                            }
                        }

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
                                System.out.println("WAITING FOR GAME IN TURN LOOP, " + csc.dataIn.available());
                                currentGameState = (Game) (csc.dataIn.readObject());
                                System.out.println("GOT GAME IN TURN LOOP, " + csc.dataIn.available());
                                System.out.println("WAITING FOR A BOOLEAN IN TURN LOOP");
                                endedTurn = csc.dataIn.readBoolean();
                                System.out.println("GOT A BOOLEAN IN TURN LOOP");
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
}
