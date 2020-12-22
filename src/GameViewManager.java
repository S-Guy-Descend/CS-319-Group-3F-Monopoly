import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import sun.security.x509.SubjectAlternativeNameExtension;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class GameViewManager {
    private final static int GAME_WIDTH = 1024;
    private final static int GAME_HEIGHT = 1024;
    private final Color CUSTOM_GREY = new Color(0.7, 0.7, 0.7, 1);
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
    private Label currentPlayer;
    private Label winner;

    private GridPane playerInfo;
    private GridPane wizardInfo;

    private Subscene sub0;
    private Subscene sub3;
    private Subscene sub4;

    private ListView scrollsList;
    private Label scrollName;
    private HBox scrollCell;
    private ArrayList<Button> scrollButtons;

    private ListView activeLandsList;
    private Label activeLandText;
    private HBox activeLandsCell;
    private ArrayList<Button> mortgageButtons;
    private ArrayList<Integer> mortgagePrices;

    private Label infoPanel;

    private ArrayList<Label> playerNames;
    private ArrayList<Label> playerMoneys;

    private ArrayList<Label> wizardInfoTexts;
    private ArrayList<Label> wizardInfoNums;

    private ClientSideConnection csc;
    private ArrayList<String> classes;
    private Game currentGameState;

    private int lastOp = -1;

    private SquareVisual[] rectArr;

    private boolean isWizard;

    public GameViewManager(ClientSideConnection csc, ArrayList<String> classes, boolean isWizard) {
        this.isWizard = isWizard;
        this.csc = csc;
        this.classes = classes;
        infoPanel = new Label("");
        infoPanel.setFont( new Font(21));
        infoPanel.setStyle(PLAYER_INFO_BACKGROUND);
        // infopanel yerini ayarla
        sub0 = new Subscene(0,0,0,0);
        sub3 = new Subscene(0,0,0,0);
        sub4 = new Subscene(0,0,0,0);
        scrollButtons = new ArrayList<>();
        mortgageButtons = new ArrayList<>();
        mortgagePrices = new ArrayList<>();
        initializeGame();
    }

    private void initializeGame() {

        rollDice = new StyledButton("Roll Dice");
        rollDice.setOnAction(e -> {
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
        build.setOnAction(e -> {
            if (csc.isTurn) {
                build.disable(true);
                try {
                    System.out.println("Player " + csc.playerID + " built");
                    sub0.setVisible(false);
                    sub3.setVisible(false);
                    sub4.setVisible(false);
                    csc.dataOut.writeInt(1);
                    lastOp = 1;
                    csc.dataOut.flush();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        useScroll = new StyledButton("Use Scroll");
        useScroll.setOnAction(e -> {
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

        purchaseLand = new StyledButton("Buy Land");
        purchaseLand.setOnAction(e -> {
            if (csc.isTurn) {
                purchaseLand.disable(true);
                sub0.setVisible(false);
                sub3.setVisible(false);
                sub4.setVisible(false);
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
        sendTrade.setOnAction(e -> {
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
        acceptTrade.setOnAction(e -> {
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
        declineTrade.setOnAction(e -> {
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
        endTurn.setOnAction(e -> {
            if (csc.isTurn) {
                rollDice.disable(true);
                build.disable(true);
                useScroll.disable(true);
                purchaseLand.disable(true);
                sendTrade.disable(true);
                acceptTrade.disable(true);
                declineTrade.disable(true);
                endTurn.disable(true);
                sub0.setVisible(false);
                sub3.setVisible(false);
                sub4.setVisible(false);
                for (int i = 0; i < mortgageButtons.size(); i++) {
                    mortgageButtons.get(i).setDisable(true);
                }
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

        activeLandsList = new ListView();
        activeLandsList.setPrefWidth(400);
        activeLandsList.setLayoutX(1498);
        activeLandsList.setLayoutY(600);

        scrollsList = new ListView();
        scrollsList.setPrefWidth(400);
        scrollsList.setLayoutX(1049);
        scrollsList.setLayoutY(600);

        playerInfo = new GridPane();
        playerInfo.setGridLinesVisible(true);
        playerInfo.setStyle(PLAYER_INFO_BACKGROUND);
        playerNames = new ArrayList<Label>();
        for (int i = 0; i < classes.size(); i++) {
            playerNames.add(new Label(classes.get(i)));
        }

        playerMoneys = new ArrayList<Label>();
        for (int i = 0; i < classes.size(); i++) {
            playerMoneys.add(new Label(""));
        }

        for (int i = 0; i < classes.size(); i++) {
            playerNames.get(i).setFont(new Font(24));
            playerMoneys.get(i).setFont(new Font(24));
        }

        wizardInfo = new GridPane();
        wizardInfo.setGridLinesVisible(true);
        wizardInfo.setStyle(PLAYER_INFO_BACKGROUND);
        wizardInfoTexts = new ArrayList<Label>();
        wizardInfoTexts.add(new Label("Max Mana: "));
        wizardInfoTexts.add(new Label("Current Mana: "));
        wizardInfoTexts.add(new Label("Mana Multiplier: "));

        wizardInfoNums = new ArrayList<Label>();
        for (int i = 0; i < 3; i++) {
            wizardInfoNums.add(new Label(""));
        }

        for (int i = 0; i < 3; i++) {
            wizardInfoTexts.get(i).setFont(new Font(24));
            wizardInfoNums.get(i).setFont(new Font(24));
        }


        for (int i = 0; i < classes.size(); i++) {
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
                    playerNames.get(i).setTextFill(Color.DARKORANGE);
                    playerMoneys.get(i).setTextFill(Color.DARKORANGE);
                    break;
                case 7:
                    playerNames.get(i).setTextFill(Color.PURPLE);
                    playerMoneys.get(i).setTextFill(Color.PURPLE);
                    break;
            }
        }

        winner = new Label("");
        winner.setStyle(PLAYER_INFO_BACKGROUND);
        winner.setFont(new Font(64));
        winner.setVisible(false);
        winner.setLayoutX(189);
        winner.setLayoutY(200);

        currentPlayer = new Label("You are " + playerNames.get(csc.playerID - 1).getText());
        currentPlayer.setFont(new Font(24));

        switch (csc.playerID - 1) {
            case 0:
                currentPlayer.setTextFill(Color.RED);
                break;
            case 1:
                currentPlayer.setTextFill(Color.GREEN);
                break;
            case 2:
                currentPlayer.setTextFill(Color.BLUE);
                break;
            case 3:
                currentPlayer.setTextFill(Color.YELLOW);
                break;
            case 4:
                currentPlayer.setTextFill(Color.CYAN);
                break;
            case 5:
                currentPlayer.setTextFill(Color.HOTPINK);
                break;
            case 6:
                currentPlayer.setTextFill(Color.DARKORANGE);
                break;
            case 7:
                currentPlayer.setTextFill(Color.PURPLE);
                break;
        }

        currentPlayer.setStyle(PLAYER_INFO_BACKGROUND);

        for (int i = 0; i < classes.size(); i++) {
            playerInfo.add(playerNames.get(i), 0, i);
            playerInfo.add(playerMoneys.get(i), 1, i);
        }

        for (int i = 0; i < 3; i++) {
            wizardInfo.add(wizardInfoTexts.get(i), 0, i);
            wizardInfo.add(wizardInfoNums.get(i), 1, i);
        }

        gamePane = new AnchorPane();
        createBackground();

        rollDice.setLayoutX(1050);
        rollDice.setLayoutY(100);

        build.setLayoutX(1050);
        build.setLayoutY(150);

        purchaseLand.setLayoutX(1050);
        purchaseLand.setLayoutY(200);

        useScroll.setLayoutX(1050);
        useScroll.setLayoutY(250);

        sendTrade.setLayoutX(1050);
        sendTrade.setLayoutY(400);

        acceptTrade.setLayoutX(1050);
        acceptTrade.setLayoutY(450);

        declineTrade.setLayoutX(1050);
        declineTrade.setLayoutY(500);

        endTurn.setLayoutX(1050);
        endTurn.setLayoutY(250);

        playerInfo.setLayoutX(1300);
        playerInfo.setLayoutY(100);

        currentPlayer.setLayoutX(1150);
        currentPlayer.setLayoutY(40);

        wizardInfo.setLayoutX(1622);
        wizardInfo.setLayoutY(442);

        rollDice.disable(!csc.isHost);
        build.disable(true);
        useScroll.disable(true);
        purchaseLand.disable(true);
        sendTrade.disable(true);
        acceptTrade.disable(true);
        declineTrade.disable(true);
        endTurn.disable(true);

        if (isWizard) {
            gamePane.getChildren().addAll(currentPlayer, rollDice, build, purchaseLand, endTurn, playerInfo, wizardInfo, activeLandsList, winner, scrollsList);
        } else {
            gamePane.getChildren().addAll(currentPlayer, rollDice, build, purchaseLand, endTurn, playerInfo, activeLandsList, winner, scrollsList);
        }

        gameScene = new Scene(gamePane, GAME_WIDTH, GAME_HEIGHT);
        gameStage = new Stage();
        gameStage.setTitle("Scrolls of Estatia");
        gameStage.setScene(gameScene);
        gameStage.setMaximized(true);

        initializeBoard();
    }

    public void enterGame() {
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
                        @Override
                        public void run() {
                            updateMoneys();
                            if (isWizard) {
                                updateWizardInfo();
                            }
                            updateActiveLandsDisplay();
                            updateScrollsList();
                            updateInfoPanel();
                        }
                    });
                    for (int i = 0; i < currentGameState.tokens.size(); i++) {
                        System.out.println("Player: " + (i + 1) + " Location: " + currentGameState.tokens.get(i).currentLocation + " Money: " + currentGameState.tokens.get(i).money);
                    }
                } catch (IOException exception) {
                    exception.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                while (true) {
                    try {
                        //CHECK GAME END
                        if (currentGameState.gameWinnerID != -1) {
                            rollDice.disable(true);
                            build.disable(true);
                            useScroll.disable(true);
                            purchaseLand.disable(true);
                            sendTrade.disable(true);
                            acceptTrade.disable(true);
                            declineTrade.disable(true);
                            endTurn.disable(true);
                            for (int i = 0; i < mortgageButtons.size(); i++) {
                                mortgageButtons.get(i).setDisable(true);
                            }

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {

                                    winner.setText("Player " + (currentGameState.gameWinnerID + 1) + " won the game!");
                                    switch (currentGameState.gameWinnerID) {
                                        case 0:
                                            winner.setTextFill(Color.RED);
                                            break;
                                        case 1:
                                            winner.setTextFill(Color.GREEN);
                                            break;
                                        case 2:
                                            winner.setTextFill(Color.BLUE);
                                            break;
                                        case 3:
                                            winner.setTextFill(Color.YELLOW);
                                            break;
                                        case 4:
                                            winner.setTextFill(Color.CYAN);
                                            break;
                                        case 5:
                                            winner.setTextFill(Color.HOTPINK);
                                            break;
                                        case 6:
                                            winner.setTextFill(Color.DARKORANGE);
                                            break;
                                        case 7:
                                            winner.setTextFill(Color.PURPLE);
                                            break;
                                    }
                                    System.out.println("GAME ENDED");
                                    winner.setVisible(true);
                                }
                            });
                            break;
                        }
                        csc.isTurn = csc.dataIn.readBoolean();
                        //CHECK GAME END
                        if (currentGameState.gameWinnerID != -1) {
                            rollDice.disable(true);
                            build.disable(true);
                            useScroll.disable(true);
                            purchaseLand.disable(true);
                            sendTrade.disable(true);
                            acceptTrade.disable(true);
                            declineTrade.disable(true);
                            endTurn.disable(true);
                            for (int i = 0; i < mortgageButtons.size(); i++) {
                                mortgageButtons.get(i).setDisable(true);
                            }

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    winner.setText("Player " + (currentGameState.gameWinnerID + 1) + " won the game!");
                                    switch (currentGameState.gameWinnerID) {
                                        case 0:
                                            winner.setTextFill(Color.RED);
                                            break;
                                        case 1:
                                            winner.setTextFill(Color.GREEN);
                                            break;
                                        case 2:
                                            winner.setTextFill(Color.BLUE);
                                            break;
                                        case 3:
                                            winner.setTextFill(Color.YELLOW);
                                            break;
                                        case 4:
                                            winner.setTextFill(Color.CYAN);
                                            break;
                                        case 5:
                                            winner.setTextFill(Color.HOTPINK);
                                            break;
                                        case 6:
                                            winner.setTextFill(Color.DARKORANGE);
                                            break;
                                        case 7:
                                            winner.setTextFill(Color.PURPLE);
                                            break;
                                    }
                                    System.out.println("GAME ENDED");
                                    winner.setVisible(true);
                                }
                            });
                            break;
                        }

                        if (!csc.isTurn) {
                            boolean isMyTurn = false;
                            while (!isMyTurn) {
                                // Getting game data during someone else's turn
                                currentGameState = (Game) (csc.dataIn.readObject());

                                //UPDATE BOARD HERE
                                redrawBoard();
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        updateMoneys();
                                        if (isWizard) {
                                            updateWizardInfo();
                                        }
                                        resetActiveLandsDisplay();
                                        updateActiveLandsDisplay();
                                        resetScrollsList();
                                        updateScrollsList();
                                        updateInfoPanel();
                                    }
                                });

                                isMyTurn = csc.dataIn.readBoolean();

                                for (int i = 0; i < currentGameState.tokens.size(); i++) {
                                    System.out.println("Player: " + (i + 1) + " Location: " + currentGameState.tokens.get(i).currentLocation + " Money: " + currentGameState.tokens.get(i).money + "ScrollCards: " + currentGameState.tokens.get(i).scrollCards.size());
                                }
                                //CHECK GAME END
                                if (currentGameState.gameWinnerID != -1) {
                                    rollDice.disable(true);
                                    build.disable(true);
                                    useScroll.disable(true);
                                    purchaseLand.disable(true);
                                    sendTrade.disable(true);
                                    acceptTrade.disable(true);
                                    declineTrade.disable(true);
                                    endTurn.disable(true);
                                    for (int i = 0; i < mortgageButtons.size(); i++) {
                                        mortgageButtons.get(i).setDisable(true);
                                    }

                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            winner.setText("Player " + (currentGameState.gameWinnerID + 1) + " won the game!");
                                            switch (currentGameState.gameWinnerID) {
                                                case 0:
                                                    winner.setTextFill(Color.RED);
                                                    break;
                                                case 1:
                                                    winner.setTextFill(Color.GREEN);
                                                    break;
                                                case 2:
                                                    winner.setTextFill(Color.BLUE);
                                                    break;
                                                case 3:
                                                    winner.setTextFill(Color.YELLOW);
                                                    break;
                                                case 4:
                                                    winner.setTextFill(Color.CYAN);
                                                    break;
                                                case 5:
                                                    winner.setTextFill(Color.HOTPINK);
                                                    break;
                                                case 6:
                                                    winner.setTextFill(Color.DARKORANGE);
                                                    break;
                                                case 7:
                                                    winner.setTextFill(Color.PURPLE);
                                                    break;
                                            }
                                            System.out.println("GAME ENDED");
                                            winner.setVisible(true);
                                        }
                                    });
                                    break;
                                }


                            }
                        }
                        //CHECK GAME END
                        if (currentGameState.gameWinnerID != -1) {
                            rollDice.disable(true);
                            build.disable(true);
                            useScroll.disable(true);
                            purchaseLand.disable(true);
                            sendTrade.disable(true);
                            acceptTrade.disable(true);
                            declineTrade.disable(true);
                            endTurn.disable(true);
                            for (int i = 0; i < mortgageButtons.size(); i++) {
                                mortgageButtons.get(i).setDisable(true);
                            }

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    winner.setText("Player " + (currentGameState.gameWinnerID + 1) + " won the game!");
                                    switch (currentGameState.gameWinnerID) {
                                        case 0:
                                            winner.setTextFill(Color.RED);
                                            break;
                                        case 1:
                                            winner.setTextFill(Color.GREEN);
                                            break;
                                        case 2:
                                            winner.setTextFill(Color.BLUE);
                                            break;
                                        case 3:
                                            winner.setTextFill(Color.YELLOW);
                                            break;
                                        case 4:
                                            winner.setTextFill(Color.CYAN);
                                            break;
                                        case 5:
                                            winner.setTextFill(Color.HOTPINK);
                                            break;
                                        case 6:
                                            winner.setTextFill(Color.DARKORANGE);
                                            break;
                                        case 7:
                                            winner.setTextFill(Color.PURPLE);
                                            break;
                                    }
                                    System.out.println("GAME ENDED");
                                    winner.setVisible(true);
                                }
                            });
                            break;
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
                                // UPDATE BOARD HERE
                                redrawBoard();

                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        updateMoneys();
                                        if (isWizard) {
                                            updateWizardInfo();
                                        }
                                        resetActiveLandsDisplay();
                                        updateActiveLandsDisplay();
                                        resetScrollsList();
                                        updateScrollsList();
                                        updateInfoPanel();
                                    }
                                });
                                if (lastOp == 0) {
                                    build.disable(!isBuildAvailable(csc.playerID - 1));
                                    useScroll.disable(!currentGameState.tokens.get(csc.playerID - 1).isScrollAvailable());
                                    purchaseLand.disable(!currentGameState.tokens.get(csc.playerID - 1).isLandPurchasable());
                                    Square currentSquare = currentGameState.board.map[currentGameState.tokens.get(csc.playerID - 1).currentLocation];
                                    if (currentSquare instanceof Town) {
                                        if (((Town) currentSquare).isPurchased) {
                                            purchaseLand.disable(true);
                                        } else {
                                            if (currentGameState.tokens.get(csc.playerID - 1).money >= ((Town) currentSquare).price) {
                                                purchaseLand.disable(true);
                                            }
                                            purchaseLand.disable(false);
                                        }
                                    } else if (currentSquare instanceof Transport) {
                                        if (((Transport) currentSquare).isPurchased) {
                                            purchaseLand.disable(true);
                                        } else {
                                            if (currentGameState.tokens.get(csc.playerID - 1).money >= ((Transport) currentSquare).price) {
                                                purchaseLand.disable(true);
                                            }
                                            purchaseLand.disable(false);
                                        }
                                    } else if (currentSquare instanceof Smith) {
                                        if (((Smith) currentSquare).isPurchased) {
                                            purchaseLand.disable(true);
                                        } else {
                                            if (currentGameState.tokens.get(csc.playerID - 1).money >= ((Smith) currentSquare).price) {
                                                purchaseLand.disable(true);
                                            }
                                            purchaseLand.disable(false);
                                        }
                                    }
                                    sendTrade.disable(true);
                                    acceptTrade.disable(true);
                                    declineTrade.disable(true);
                                    endTurn.disable(false);
                                    for (int i = 0; i < mortgageButtons.size(); i++) {
                                        if (currentGameState.tokens.get(csc.playerID - 1).money >= mortgagePrices.get(i)) {
                                            mortgageButtons.get(i).setDisable(false);
                                        } else {
                                            mortgageButtons.get(i).setDisable(true);
                                        }
                                    }
                                } else if (lastOp != 7) {
                                    // CHECK MONEY FOR BUILD PURCHASE AND MORTGAGE HERE
                                    for (int i = 0; i < mortgageButtons.size(); i++) {
                                        if (currentGameState.tokens.get(csc.playerID - 1).money >= mortgagePrices.get(i)) {
                                            mortgageButtons.get(i).setDisable(false);
                                        } else {
                                            mortgageButtons.get(i).setDisable(true);
                                        }
                                    }
                                    purchaseLand.disable(!currentGameState.tokens.get(csc.playerID - 1).isLandPurchasable());
                                    Square currentSquare = currentGameState.board.map[currentGameState.tokens.get(csc.playerID - 1).currentLocation];
                                    if (currentSquare instanceof Town) {
                                        if (((Town) currentSquare).isPurchased) {
                                            purchaseLand.disable(true);
                                        } else {
                                            if (currentGameState.tokens.get(csc.playerID - 1).money >= ((Town) currentSquare).price) {
                                                purchaseLand.disable(true);
                                            }
                                            purchaseLand.disable(false);
                                        }
                                    } else if (currentSquare instanceof Transport) {
                                        if (((Transport) currentSquare).isPurchased) {
                                            purchaseLand.disable(true);
                                        } else {
                                            if (currentGameState.tokens.get(csc.playerID - 1).money >= ((Transport) currentSquare).price) {
                                                purchaseLand.disable(true);
                                            }
                                            purchaseLand.disable(false);
                                        }
                                    } else if (currentSquare instanceof Smith) {
                                        if (((Smith) currentSquare).isPurchased) {
                                            purchaseLand.disable(true);
                                        } else {
                                            if (currentGameState.tokens.get(csc.playerID - 1).money >= ((Smith) currentSquare).price) {
                                                purchaseLand.disable(true);
                                            }
                                            purchaseLand.disable(false);
                                        }
                                    }
                                    build.disable(!isBuildAvailable(csc.playerID - 1));

                                }
                                for (int i = 0; i < currentGameState.tokens.size(); i++) {
                                    System.out.println("Player: " + (i + 1) + "Location: " + currentGameState.tokens.get(i).currentLocation + "Money: " + currentGameState.tokens.get(i).money);
                                }


                                endedTurn = csc.dataIn.readBoolean();
                            }
                            //CHECK GAME END
                            if (currentGameState.gameWinnerID != -1) {
                                rollDice.disable(true);
                                build.disable(true);
                                useScroll.disable(true);
                                purchaseLand.disable(true);
                                sendTrade.disable(true);
                                acceptTrade.disable(true);
                                declineTrade.disable(true);
                                endTurn.disable(true);
                                for (int i = 0; i < mortgageButtons.size(); i++) {
                                    mortgageButtons.get(i).setDisable(true);
                                }

                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        winner.setText("Player " + (currentGameState.gameWinnerID + 1) + " won the game!");
                                        switch (currentGameState.gameWinnerID) {
                                            case 0:
                                                winner.setTextFill(Color.RED);
                                                break;
                                            case 1:
                                                winner.setTextFill(Color.GREEN);
                                                break;
                                            case 2:
                                                winner.setTextFill(Color.BLUE);
                                                break;
                                            case 3:
                                                winner.setTextFill(Color.YELLOW);
                                                break;
                                            case 4:
                                                winner.setTextFill(Color.CYAN);
                                                break;
                                            case 5:
                                                winner.setTextFill(Color.HOTPINK);
                                                break;
                                            case 6:
                                                winner.setTextFill(Color.DARKORANGE);
                                                break;
                                            case 7:
                                                winner.setTextFill(Color.PURPLE);
                                                break;
                                        }
                                        System.out.println("GAME ENDED");
                                        winner.setVisible(true);
                                    }
                                });
                                break;
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
        for (int i = 0; i < 40; i++) {
            rectArr[i] = new SquareVisual(Game.instance.board.map[i].name, i);


        }

        GridPane gridPane = new GridPane();

        for (int i = 0; i < 11; i++) {
            gridPane.add(rectArr[20 + i].sp, i, 0, 1, 1);
        }
        // en sol satır
        for (int i = 0; i < 9; i++) {
            gridPane.add(rectArr[19 - i].sp, 0, i + 1, 1, 1);
        }
        //en sağ
        for (int i = 0; i < 9; i++) {
            gridPane.add(rectArr[i + 31].sp, 10, i + 1, 1, 1);
        }

        // en alt satır
        for (int i = 0; i < 11; i++) {
            gridPane.add(rectArr[10 - i].sp, i, 10, 1, 1);
        }

        // teker teker renk gruplarını girdim
        rectArr[1].squareColor.setFill(Color.PURPLE);
        rectArr[1].colorContainer.setVisible(true);

        rectArr[3].squareColor.setFill(Color.PURPLE);
        rectArr[3].colorContainer.setVisible(true);

        rectArr[6].squareColor.setFill(Color.LIGHTBLUE);
        rectArr[6].colorContainer.setVisible(true);

        rectArr[8].squareColor.setFill(Color.LIGHTBLUE);
        rectArr[8].colorContainer.setVisible(true);

        rectArr[9].squareColor.setFill(Color.LIGHTBLUE);
        rectArr[9].colorContainer.setVisible(true);

        //dungeon burda yapılacak

        //
        rectArr[11].squareColor.setFill(Color.DEEPPINK);
        rectArr[11].colorContainer.setVisible(true);


        rectArr[13].squareColor.setFill(Color.DEEPPINK);
        rectArr[13].colorContainer.setVisible(true);

        rectArr[14].squareColor.setFill(Color.DEEPPINK);
        rectArr[14].colorContainer.setVisible(true);

        rectArr[16].squareColor.setFill(Color.ORANGE);
        rectArr[16].colorContainer.setVisible(true);

        rectArr[18].squareColor.setFill(Color.ORANGE);
        rectArr[18].colorContainer.setVisible(true);

        rectArr[19].squareColor.setFill(Color.ORANGE);
        rectArr[19].colorContainer.setVisible(true);

        // feast
        //
        rectArr[21].squareColor.setFill(Color.RED);
        rectArr[21].colorContainer.setVisible(true);

        rectArr[23].squareColor.setFill(Color.RED);
        rectArr[23].colorContainer.setVisible(true);

        rectArr[24].squareColor.setFill(Color.RED);
        rectArr[24].colorContainer.setVisible(true);

        rectArr[26].squareColor.setFill(Color.GOLD);
        rectArr[26].colorContainer.setVisible(true);

        rectArr[27].squareColor.setFill(Color.GOLD);
        rectArr[27].colorContainer.setVisible(true);

        rectArr[29].squareColor.setFill(Color.GOLD);
        rectArr[29].colorContainer.setVisible(true);

        //go to dungeon
        //
        rectArr[31].squareColor.setFill(Color.GREEN);
        rectArr[31].colorContainer.setVisible(true);

        rectArr[32].squareColor.setFill(Color.GREEN);
        rectArr[32].colorContainer.setVisible(true);

        rectArr[34].squareColor.setFill(Color.GREEN);
        rectArr[34].colorContainer.setVisible(true);

        rectArr[37].squareColor.setFill(Color.DARKBLUE);
        rectArr[37].colorContainer.setVisible(true);

        rectArr[39].squareColor.setFill(Color.DARKBLUE);
        rectArr[39].colorContainer.setVisible(true);

        gridPane.setLayoutX(20);

        gamePane.getChildren().add(gridPane);
    }

    public void redrawBoard() {
        for (int i = 0; i < rectArr.length; i++) {
            rectArr[i].reDrawSquare(currentGameState);
        }
    }

    public void updateMoneys() {
        for (int i = 0; i < classes.size(); i++) {
            if (!currentGameState.tokens.get(i).isBankrupt) {
                playerMoneys.get(i).setText(String.valueOf(currentGameState.tokens.get(i).money));
            } else {
                playerMoneys.get(i).setText("BANKRUPT!");
            }
        }
    }


    public void updateWizardInfo() {
        wizardInfoNums.get(0).setText(String.valueOf(((Wizard) currentGameState.tokens.get(csc.playerID - 1)).maxMana));
        double currentMana = ((Wizard) currentGameState.tokens.get(csc.playerID - 1)).currentMana;
        double manaGainMultiplier = ((Wizard) currentGameState.tokens.get(csc.playerID - 1)).manaGainMultiplier;

        BigDecimal roundedMana = new BigDecimal(currentMana);
        roundedMana = roundedMana.round(new MathContext(4));
        double finalMana = roundedMana.doubleValue();

        BigDecimal roundedMult = new BigDecimal(manaGainMultiplier);
        roundedMult = roundedMult.round(new MathContext(4));
        double finalMult = roundedMult.doubleValue();

        wizardInfoNums.get(1).setText(String.valueOf(finalMana));
        wizardInfoNums.get(2).setText(String.valueOf(finalMult));
    }

    public void updateActiveLandsDisplay() {
        for (int i = 0; i < currentGameState.tokens.get(csc.playerID - 1).activeLands.size(); i++) {
            final int squareID = currentGameState.tokens.get(csc.playerID - 1).activeLands.get(i);
            Square squareToMortgage = currentGameState.board.map[squareID];
            if (squareToMortgage instanceof Town) {
                if (!((Town) squareToMortgage).isMortgaged) {
                    activeLandText = new Label(currentGameState.board.map[squareID].name);
                    Button activeLandsMortgageButton;
                    activeLandsMortgageButton = new Button("Mortgage (" + ((Town) (currentGameState.board.map[squareID])).mortgagePrice + ")");
                    mortgagePrices.add(0);
                    mortgageButtons.add(activeLandsMortgageButton);
                    activeLandsMortgageButton.setOnAction(e -> {
                        System.out.println("Square ID to mortgage is : " + squareID);
                        // SEND REQUEST TO MORTGAGE TOWN HERE
                        try {
                            csc.dataOut.writeInt(8);
                            csc.dataOut.flush();
                            csc.dataOut.writeInt(squareID);
                            csc.dataOut.flush();
                        } catch (IOException exception) {
                            exception.printStackTrace();
                        }
                    });
                    activeLandsCell = new HBox();
                    activeLandsCell.getChildren().addAll(activeLandText, activeLandsMortgageButton);
                    activeLandsCell.setSpacing(20);
                    activeLandsList.getItems().add(activeLandsCell);

                } else {
                    activeLandText = new Label(currentGameState.board.map[squareID].name);
                    Button activeLandsMortgageButton;
                    activeLandsMortgageButton = new Button("Unmortgage (" + (int) (((Town) (currentGameState.board.map[squareID])).mortgagePrice * ((Town) (currentGameState.board.map[squareID])).MORTGAGE_REDEMPTION_MULTIPLIER) + ")");
                    mortgagePrices.add(new Integer((int) (((Town) (currentGameState.board.map[squareID])).mortgagePrice * ((Town) (currentGameState.board.map[squareID])).MORTGAGE_REDEMPTION_MULTIPLIER)));
                    mortgageButtons.add(activeLandsMortgageButton);
                    activeLandsMortgageButton.setOnAction(e -> {
                        System.out.println("Square ID to mortgage is : " + squareID);
                        // SEND REQUEST TO UNMORTGAGE TOWN HERE
                        try {
                            csc.dataOut.writeInt(9);
                            csc.dataOut.flush();
                            csc.dataOut.writeInt(squareID);
                            csc.dataOut.flush();
                        } catch (IOException exception) {
                            exception.printStackTrace();
                        }

                    });
                    activeLandsCell = new HBox();
                    activeLandsCell.getChildren().addAll(activeLandText, activeLandsMortgageButton);
                    activeLandsCell.setSpacing(20);
                    activeLandsList.getItems().add(activeLandsCell);
                }
            } else if (squareToMortgage instanceof Transport) {
                if (!((Transport) squareToMortgage).isMortgaged) {
                    activeLandText = new Label(currentGameState.board.map[squareID].name);
                    Button activeLandsMortgageButton;
                    activeLandsMortgageButton = new Button("Mortgage (" + ((Transport) (currentGameState.board.map[squareID])).mortgagePrice + ")");
                    mortgagePrices.add(0);
                    mortgageButtons.add(activeLandsMortgageButton);
                    activeLandsMortgageButton.setOnAction(e -> {
                        System.out.println("Square ID to mortgage is : " + squareID);
                        // SEND REQUEST TO MORTGAGE TRANSPORT HERE
                        try {
                            csc.dataOut.writeInt(10);
                            csc.dataOut.flush();
                            csc.dataOut.writeInt(squareID);
                            csc.dataOut.flush();
                        } catch (IOException exception) {
                            exception.printStackTrace();
                        }
                    });
                    activeLandsCell = new HBox();
                    activeLandsCell.getChildren().addAll(activeLandText, activeLandsMortgageButton);
                    activeLandsCell.setSpacing(20);
                    activeLandsList.getItems().add(activeLandsCell);

                } else {
                    activeLandText = new Label(currentGameState.board.map[squareID].name);
                    Button activeLandsMortgageButton;
                    activeLandsMortgageButton = new Button("Unmortgage (" + (int) (((Transport) (currentGameState.board.map[squareID])).mortgagePrice * ((Transport) (currentGameState.board.map[squareID])).MORTGAGE_REDEMPTION_MULTIPLIER) + ")");
                    mortgagePrices.add(new Integer((int) (((Transport) (currentGameState.board.map[squareID])).mortgagePrice * ((Transport) (currentGameState.board.map[squareID])).MORTGAGE_REDEMPTION_MULTIPLIER)));
                    mortgageButtons.add(activeLandsMortgageButton);
                    activeLandsMortgageButton.setOnAction(e -> {
                        System.out.println("Square ID to mortgage is : " + squareID);
                        // SEND REQUEST TO UNMORTGAGE TRANSPORT HERE
                        try {
                            csc.dataOut.writeInt(11);
                            csc.dataOut.flush();
                            csc.dataOut.writeInt(squareID);
                            csc.dataOut.flush();
                        } catch (IOException exception) {
                            exception.printStackTrace();
                        }
                    });
                    activeLandsCell = new HBox();
                    activeLandsCell.getChildren().addAll(activeLandText, activeLandsMortgageButton);
                    activeLandsCell.setSpacing(20);
                    activeLandsList.getItems().add(activeLandsCell);
                }
            } else if (squareToMortgage instanceof Smith) {
                if (!((Smith) squareToMortgage).isMortgaged) {
                    activeLandText = new Label(currentGameState.board.map[squareID].name);
                    Button activeLandsMortgageButton;
                    activeLandsMortgageButton = new Button("Mortgage (" + ((Smith) (currentGameState.board.map[squareID])).mortgagePrice + ")");
                    mortgagePrices.add(0);
                    mortgageButtons.add(activeLandsMortgageButton);
                    activeLandsMortgageButton.setOnAction(e -> {
                        System.out.println("Square ID to mortgage is : " + squareID);
                        // SEND REQUEST TO MORTGAGE SMITH HERE
                        try {
                            csc.dataOut.writeInt(12);
                            csc.dataOut.flush();
                            csc.dataOut.writeInt(squareID);
                            csc.dataOut.flush();
                        } catch (IOException exception) {
                            exception.printStackTrace();
                        }
                    });
                    activeLandsCell = new HBox();
                    activeLandsCell.getChildren().addAll(activeLandText, activeLandsMortgageButton);
                    activeLandsCell.setSpacing(20);
                    activeLandsList.getItems().add(activeLandsCell);

                } else {
                    activeLandText = new Label(currentGameState.board.map[squareID].name);
                    Button activeLandsMortgageButton;
                    activeLandsMortgageButton = new Button("Unmortgage (" + (int) (((Smith) (currentGameState.board.map[squareID])).mortgagePrice * ((Smith) (currentGameState.board.map[squareID])).MORTGAGE_REDEMPTION_MULTIPLIER) + ")");
                    mortgagePrices.add(new Integer((int) (((Smith) (currentGameState.board.map[squareID])).mortgagePrice * ((Smith) (currentGameState.board.map[squareID])).MORTGAGE_REDEMPTION_MULTIPLIER)));
                    mortgageButtons.add(activeLandsMortgageButton);
                    activeLandsMortgageButton.setOnAction(e -> {
                        System.out.println("Square ID to mortgage is : " + squareID);
                        // SEND REQUEST TO UNMORTGAGE SMITH HERE
                        try {
                            csc.dataOut.writeInt(13);
                            csc.dataOut.flush();
                            csc.dataOut.writeInt(squareID);
                            csc.dataOut.flush();
                        } catch (IOException exception) {
                            exception.printStackTrace();
                        }
                    });
                    activeLandsCell = new HBox();
                    activeLandsCell.getChildren().addAll(activeLandText, activeLandsMortgageButton);
                    activeLandsCell.setSpacing(20);
                    activeLandsList.getItems().add(activeLandsCell);
                }
            }
        }
        for (int i = 0; i < currentGameState.tokens.get(csc.playerID - 1).mortgagedLands.size(); i++) {
            final int squareID = currentGameState.tokens.get(csc.playerID - 1).mortgagedLands.get(i);
            Square squareToMortgage = currentGameState.board.map[squareID];
            if (squareToMortgage instanceof Town) {
                if (!((Town) squareToMortgage).isMortgaged) {
                    activeLandText = new Label(currentGameState.board.map[squareID].name);
                    Button activeLandsMortgageButton;
                    activeLandsMortgageButton = new Button("Mortgage (" + ((Town) (currentGameState.board.map[squareID])).mortgagePrice + ")");
                    mortgagePrices.add(0);
                    mortgageButtons.add(activeLandsMortgageButton);
                    activeLandsMortgageButton.setOnAction(e -> {
                        System.out.println("Square ID to mortgage is : " + squareID);
                        // SEND REQUEST TO MORTGAGE TOWN HERE
                        try {
                            csc.dataOut.writeInt(8);
                            csc.dataOut.flush();
                            csc.dataOut.writeInt(squareID);
                            csc.dataOut.flush();
                        } catch (IOException exception) {
                            exception.printStackTrace();
                        }
                    });
                    activeLandsCell = new HBox();
                    activeLandsCell.getChildren().addAll(activeLandText, activeLandsMortgageButton);
                    activeLandsCell.setSpacing(20);
                    activeLandsList.getItems().add(activeLandsCell);

                } else {
                    activeLandText = new Label(currentGameState.board.map[squareID].name);
                    Button activeLandsMortgageButton;
                    activeLandsMortgageButton = new Button("Unmortgage (" + (int) (((Town) (currentGameState.board.map[squareID])).mortgagePrice * ((Town) (currentGameState.board.map[squareID])).MORTGAGE_REDEMPTION_MULTIPLIER) + ")");
                    mortgagePrices.add(new Integer((int) (((Town) (currentGameState.board.map[squareID])).mortgagePrice * ((Town) (currentGameState.board.map[squareID])).MORTGAGE_REDEMPTION_MULTIPLIER)));
                    mortgageButtons.add(activeLandsMortgageButton);
                    activeLandsMortgageButton.setOnAction(e -> {
                        System.out.println("Square ID to mortgage is : " + squareID);
                        // SEND REQUEST TO UNMORTGAGE TOWN HERE
                        try {
                            csc.dataOut.writeInt(9);
                            csc.dataOut.flush();
                            csc.dataOut.writeInt(squareID);
                            csc.dataOut.flush();
                        } catch (IOException exception) {
                            exception.printStackTrace();
                        }

                    });
                    activeLandsCell = new HBox();
                    activeLandsCell.getChildren().addAll(activeLandText, activeLandsMortgageButton);
                    activeLandsCell.setSpacing(20);
                    activeLandsList.getItems().add(activeLandsCell);
                }
            } else if (squareToMortgage instanceof Transport) {
                if (!((Transport) squareToMortgage).isMortgaged) {
                    activeLandText = new Label(currentGameState.board.map[squareID].name);
                    Button activeLandsMortgageButton;
                    activeLandsMortgageButton = new Button("Mortgage (" + ((Transport) (currentGameState.board.map[squareID])).mortgagePrice + ")");
                    mortgagePrices.add(0);
                    mortgageButtons.add(activeLandsMortgageButton);
                    activeLandsMortgageButton.setOnAction(e -> {
                        System.out.println("Square ID to mortgage is : " + squareID);
                        // SEND REQUEST TO MORTGAGE TRANSPORT HERE
                        try {
                            csc.dataOut.writeInt(10);
                            csc.dataOut.flush();
                            csc.dataOut.writeInt(squareID);
                            csc.dataOut.flush();
                        } catch (IOException exception) {
                            exception.printStackTrace();
                        }
                    });
                    activeLandsCell = new HBox();
                    activeLandsCell.getChildren().addAll(activeLandText, activeLandsMortgageButton);
                    activeLandsCell.setSpacing(20);
                    activeLandsList.getItems().add(activeLandsCell);

                } else {
                    activeLandText = new Label(currentGameState.board.map[squareID].name);
                    Button activeLandsMortgageButton;
                    activeLandsMortgageButton = new Button("Unmortgage (" + (int) (((Transport) (currentGameState.board.map[squareID])).mortgagePrice * ((Transport) (currentGameState.board.map[squareID])).MORTGAGE_REDEMPTION_MULTIPLIER) + ")");
                    mortgagePrices.add(new Integer((int) (((Transport) (currentGameState.board.map[squareID])).mortgagePrice * ((Transport) (currentGameState.board.map[squareID])).MORTGAGE_REDEMPTION_MULTIPLIER)));
                    mortgageButtons.add(activeLandsMortgageButton);
                    activeLandsMortgageButton.setOnAction(e -> {
                        System.out.println("Square ID to mortgage is : " + squareID);
                        // SEND REQUEST TO UNMORTGAGE TRANSPORT HERE
                        try {
                            csc.dataOut.writeInt(11);
                            csc.dataOut.flush();
                            csc.dataOut.writeInt(squareID);
                            csc.dataOut.flush();
                        } catch (IOException exception) {
                            exception.printStackTrace();
                        }
                    });
                    activeLandsCell = new HBox();
                    activeLandsCell.getChildren().addAll(activeLandText, activeLandsMortgageButton);
                    activeLandsCell.setSpacing(20);
                    activeLandsList.getItems().add(activeLandsCell);
                }
            } else if (squareToMortgage instanceof Smith) {
                if (!((Smith) squareToMortgage).isMortgaged) {
                    activeLandText = new Label(currentGameState.board.map[squareID].name);
                    Button activeLandsMortgageButton;
                    activeLandsMortgageButton = new Button("Mortgage (" + ((Smith) (currentGameState.board.map[squareID])).mortgagePrice + ")");
                    mortgagePrices.add(0);
                    mortgageButtons.add(activeLandsMortgageButton);
                    activeLandsMortgageButton.setOnAction(e -> {
                        System.out.println("Square ID to mortgage is : " + squareID);
                        // SEND REQUEST TO MORTGAGE SMITH HERE
                        try {
                            csc.dataOut.writeInt(12);
                            csc.dataOut.flush();
                            csc.dataOut.writeInt(squareID);
                            csc.dataOut.flush();
                        } catch (IOException exception) {
                            exception.printStackTrace();
                        }
                    });
                    activeLandsCell = new HBox();
                    activeLandsCell.getChildren().addAll(activeLandText, activeLandsMortgageButton);
                    activeLandsCell.setSpacing(20);
                    activeLandsList.getItems().add(activeLandsCell);

                } else {
                    activeLandText = new Label(currentGameState.board.map[squareID].name);
                    Button activeLandsMortgageButton;
                    activeLandsMortgageButton = new Button("Unmortgage (" + (int) (((Smith) (currentGameState.board.map[squareID])).mortgagePrice * ((Smith) (currentGameState.board.map[squareID])).MORTGAGE_REDEMPTION_MULTIPLIER) + ")");
                    mortgagePrices.add(new Integer((int) (((Smith) (currentGameState.board.map[squareID])).mortgagePrice * ((Smith) (currentGameState.board.map[squareID])).MORTGAGE_REDEMPTION_MULTIPLIER)));
                    mortgageButtons.add(activeLandsMortgageButton);
                    activeLandsMortgageButton.setOnAction(e -> {
                        System.out.println("Square ID to mortgage is : " + squareID);
                        // SEND REQUEST TO UNMORTGAGE SMITH HERE
                        try {
                            csc.dataOut.writeInt(13);
                            csc.dataOut.flush();
                            csc.dataOut.writeInt(squareID);
                            csc.dataOut.flush();
                        } catch (IOException exception) {
                            exception.printStackTrace();
                        }
                    });
                    activeLandsCell = new HBox();
                    activeLandsCell.getChildren().addAll(activeLandText, activeLandsMortgageButton);
                    activeLandsCell.setSpacing(20);
                    activeLandsList.getItems().add(activeLandsCell);
                }
            }
        }
        if (lastOp == 7) {
            for (int i = 0; i < mortgageButtons.size(); i++) {
                mortgageButtons.get(i).setDisable(true);
            }
        } else {
            for (int i = 0; i < mortgageButtons.size(); i++) {
                if (currentGameState.tokens.get(csc.playerID - 1).money >= mortgagePrices.get(i)) {
                    mortgageButtons.get(i).setDisable(false);
                } else {
                    mortgageButtons.get(i).setDisable(true);
                }
            }
        }
    }

    public void resetActiveLandsDisplay() {
        activeLandsList.getItems().clear();
        mortgagePrices.clear();
        mortgageButtons.clear();
    }

    public void updateScrollsList() {
        for (int i = 0; i < currentGameState.tokens.get(csc.playerID - 1).scrollCards.size(); i++) {
            scrollName = new Label(currentGameState.tokens.get(csc.playerID - 1).scrollCards.get(i).cardName);
            Button useScrollButton;
            useScrollButton = new Button("Use");
            scrollButtons.add(useScrollButton);
            int finalI = i;
            int finalI1 = i;
            useScrollButton.setOnAction(e -> {
                System.out.println("Open " + currentGameState.tokens.get(csc.playerID - 1).scrollCards.get(finalI1).cardName + " menu");
                switch (currentGameState.tokens.get(csc.playerID - 1).scrollCards.get(finalI1).effectID) {
                    case 0:
                        sub0 = new Subscene(423,884,1026,578);
                        sub0.setVisible(true);

                        gamePane.getChildren().add(sub0);
                        Label scrollName0 = new Label("Forceful Accomodation");
                        Label scrollText0 = new Label("Teleport a player to one of your random Town or Transportation Square.");
                        Label selectVictim0 = new Label("Select who you want to use this scroll on: ");
                        ObservableList<String> victims0 = FXCollections.observableArrayList();
                        for ( int j = 0; j < currentGameState.tokens.size(); j++) {
                            if ( !currentGameState.tokens.get(j).isBankrupt && !(currentGameState.tokens.get(j).dungeonCountdown > 0) ) {
                                if ( currentGameState.tokens.get(j).ID != csc.playerID - 1) {
                                    victims0.add(String.valueOf(j + 1));
                                }
                            }
                        }
                        ComboBox victimDropdown0 = new ComboBox(victims0);
                        AtomicInteger selectedVictim0 = new AtomicInteger(-1);
                        victimDropdown0.getSelectionModel().selectFirst();
                        selectedVictim0.set(Integer.parseInt(victimDropdown0.getItems().get(0).toString()) - 1);
                        victimDropdown0.setOnAction( event -> {
                            switch (victimDropdown0.getValue().toString() ) {
                                case "1":
                                    selectedVictim0.set(0);
                                    break;
                                case "2":
                                    selectedVictim0.set(1);
                                    break;
                                case "3":
                                    selectedVictim0.set(2);
                                    break;
                                case "4":
                                    selectedVictim0.set(3);
                                    break;
                                case "5":
                                    selectedVictim0.set(4);
                                    break;
                                case "6":
                                    selectedVictim0.set(5);
                                    break;
                                case "7":
                                    selectedVictim0.set(6);
                                    break;
                                case "8":
                                    selectedVictim0.set(7);
                                    break;
                            }
                        });
                        StyledButton use0 = new StyledButton("Use Scroll");
                        StyledButton cancel0 = new StyledButton("Cancel");
                        use0.setOnAction( event -> {
                            try {
                                csc.dataOut.writeInt(2);
                                csc.dataOut.flush();
                                csc.dataOut.writeInt(finalI);
                                csc.dataOut.flush();
                                csc.dataOut.writeInt(selectedVictim0.get());
                                csc.dataOut.flush();
                                sub0.setVisible(false);
                            } catch (IOException exception) {
                                exception.printStackTrace();
                            }
                        });
                        cancel0.setOnAction( event -> {
                            sub0.setVisible(false);
                        });
                        VBox subBox0 = new VBox(20);
                        subBox0.setAlignment( Pos.CENTER);
                        subBox0.setLayoutX(193);
                        subBox0.setLayoutY(18);
                        subBox0.getChildren().addAll(scrollName0, scrollText0, selectVictim0, victimDropdown0, use0, cancel0);
                        sub0.getPane().getChildren().add(subBox0);
                        break;
                    case 1:
                        try {
                            csc.dataOut.writeInt(2);
                            csc.dataOut.flush();
                            csc.dataOut.writeInt(finalI);
                            csc.dataOut.flush();
                            csc.dataOut.writeInt(csc.playerID - 1);
                            csc.dataOut.flush();
                        } catch (IOException exception) {
                            exception.printStackTrace();
                        }
                        break;
                    case 2:
                        ArrayList<Square> possibleSquares = new ArrayList<Square>();
                        for ( int j = 0; j < 40; j++) {
                            if (currentGameState.board.map[j] instanceof Town) {
                                if ( ((Town) currentGameState.board.map[j]).ownerId != csc.playerID - 1) {
                                    if ( ((Town) currentGameState.board.map[j]).numberOfInns > 0) {
                                        possibleSquares.add(currentGameState.board.map[j]);
                                    }
                                }
                            }
                        }
                        int chosenSquare = (int)(Math.random() * possibleSquares.size());
                        int owner = ((Town) possibleSquares.get(chosenSquare)).ownerId;
                        try {
                            csc.dataOut.writeInt(2);
                            csc.dataOut.flush();
                            csc.dataOut.writeInt(finalI);
                            csc.dataOut.flush();
                            csc.dataOut.writeInt(owner);
                            csc.dataOut.flush();
                        } catch (IOException exception) {
                            exception.printStackTrace();
                        }
                        break;
                    case 3:
                        sub3 = new Subscene(423,884,1026,578);
                        sub3.setVisible(true);
                        gamePane.getChildren().add(sub3);
                        Label scrollName3 = new Label("Dungeon Reverse");
                        Label scrollText3 = new Label("Send a player to dungeon, or if he is already in dungeon, release him and send him to GO.");
                        Label selectVictim3 = new Label("Select who you want to use this scroll on: ");
                        ObservableList<String> victims3 = FXCollections.observableArrayList();
                        for ( int j = 0; j < currentGameState.tokens.size(); j++) {
                            if ( !currentGameState.tokens.get(j).isBankrupt ) {
                                if ( currentGameState.tokens.get(j).ID != csc.playerID - 1) {
                                    victims3.add(String.valueOf(j + 1));
                                }
                            }
                        }
                        ComboBox victimDropdown3 = new ComboBox(victims3);
                        AtomicInteger selectedVictim3 = new AtomicInteger(-1);
                        victimDropdown3.getSelectionModel().selectFirst();
                        selectedVictim3.set(Integer.parseInt(victimDropdown3.getItems().get(0).toString()) - 1);
                        victimDropdown3.setOnAction( event -> {
                            switch (victimDropdown3.getValue().toString() ) {
                                case "1":
                                    selectedVictim3.set(0);
                                    break;
                                case "2":
                                    selectedVictim3.set(1);
                                    break;
                                case "3":
                                    selectedVictim3.set(2);
                                    break;
                                case "4":
                                    selectedVictim3.set(3);
                                    break;
                                case "5":
                                    selectedVictim3.set(4);
                                    break;
                                case "6":
                                    selectedVictim3.set(5);
                                    break;
                                case "7":
                                    selectedVictim3.set(6);
                                    break;
                                case "8":
                                    selectedVictim3.set(7);
                                    break;
                            }
                        });
                        StyledButton use3 = new StyledButton("Use Scroll");
                        StyledButton cancel3 = new StyledButton("Cancel");
                        use3.setOnAction( event -> {
                            try {
                                csc.dataOut.writeInt(2);
                                csc.dataOut.flush();
                                csc.dataOut.writeInt(finalI);
                                csc.dataOut.flush();
                                csc.dataOut.writeInt(selectedVictim3.get());
                                csc.dataOut.flush();
                                sub3.setVisible(false);
                            } catch (IOException exception) {
                                exception.printStackTrace();
                            }
                        });
                        cancel3.setOnAction( event -> {
                            sub3.setVisible(false);
                        });
                        VBox subBox3 = new VBox(20);
                        subBox3.setAlignment( Pos.CENTER);
                        subBox3.setLayoutX(141);
                        subBox3.setLayoutY(18);
                        subBox3.getChildren().addAll(scrollName3, scrollText3, selectVictim3, victimDropdown3, use3, cancel3);
                        sub3.getPane().getChildren().add(subBox3);
                        break;
                    case 4:
                        sub4 = new Subscene(423,884,1026,578);
                        sub4.setVisible(true);
                        gamePane.getChildren().add(sub4);
                        Label scrollName4 = new Label("Counterspell");
                        Label scrollText4 = new Label("Burn a player's randomly chosen scroll.");
                        Label selectVictim4 = new Label("Select who you want to use this scroll on: ");
                        ObservableList<String> victims4 = FXCollections.observableArrayList();
                        for ( int j = 0; j < currentGameState.tokens.size(); j++) {
                            if ( currentGameState.tokens.get(j).ID != csc.playerID - 1) {
                                if ( currentGameState.tokens.get(j).scrollCards.size() > 0) {
                                    victims4.add(String.valueOf(j + 1));
                                }
                            }
                        }
                        ComboBox victimDropdown4 = new ComboBox(victims4);
                        AtomicInteger selectedVictim4 = new AtomicInteger(-1);
                        victimDropdown4.getSelectionModel().selectFirst();
                        selectedVictim4.set(Integer.parseInt(victimDropdown4.getItems().get(0).toString()) - 1);
                        victimDropdown4.setOnAction( event -> {
                            switch (victimDropdown4.getValue().toString() ) {
                                case "1":
                                    selectedVictim4.set(0);
                                    break;
                                case "2":
                                    selectedVictim4.set(1);
                                    break;
                                case "3":
                                    selectedVictim4.set(2);
                                    break;
                                case "4":
                                    selectedVictim4.set(3);
                                    break;
                                case "5":
                                    selectedVictim4.set(4);
                                    break;
                                case "6":
                                    selectedVictim4.set(5);
                                    break;
                                case "7":
                                    selectedVictim4.set(6);
                                    break;
                                case "8":
                                    selectedVictim4.set(7);
                                    break;
                            }
                        });
                        StyledButton use4 = new StyledButton("Use Scroll");
                        StyledButton cancel4 = new StyledButton("Cancel");
                        use4.setOnAction( event -> {
                            try {
                                csc.dataOut.writeInt(2);
                                csc.dataOut.flush();
                                csc.dataOut.writeInt(finalI);
                                csc.dataOut.flush();
                                csc.dataOut.writeInt(selectedVictim4.get());
                                csc.dataOut.flush();
                                sub4.setVisible(false);
                            } catch (IOException exception) {
                                exception.printStackTrace();
                            }
                        });
                        cancel4.setOnAction( event -> {
                            sub4.setVisible(false);
                        });
                        VBox subBox4 = new VBox(20);
                        subBox4.setAlignment( Pos.CENTER);
                        subBox4.setLayoutX(284);
                        subBox4.setLayoutY(18);
                        subBox4.getChildren().addAll(scrollName4, scrollText4, selectVictim4, victimDropdown4, use4, cancel4);
                        sub4.getPane().getChildren().add(subBox4);
                        break;
                }
            });
            scrollCell = new HBox();
            scrollCell.getChildren().addAll(scrollName, useScrollButton);
            scrollCell.setSpacing(20);
            scrollsList.getItems().add(scrollCell);

            switch (currentGameState.tokens.get(csc.playerID - 1).scrollCards.get(i).effectID) {
                case 0:
                    boolean playerHasNonSmith = false;
                    for ( int j = 0; j < currentGameState.tokens.get(csc.playerID - 1).activeLands.size(); j++) {
                        if (currentGameState.board.map[currentGameState.tokens.get(csc.playerID - 1).activeLands.get(j)] instanceof Town || currentGameState.board.map[currentGameState.tokens.get(csc.playerID - 1).activeLands.get(j)] instanceof Transport ) {
                            playerHasNonSmith = true;
                            System.out.println("PLAYER HAS NON SMITH");
                            break;
                        }
                    }
                    boolean victimExists0 = false;
                    for ( int j = 0; j < currentGameState.tokens.size(); j++) {
                        if ( !currentGameState.tokens.get(j).isBankrupt && !(currentGameState.tokens.get(j).dungeonCountdown > 0) ) {
                            if ( currentGameState.tokens.get(j).ID != csc.playerID - 1) {
                                System.out.println("PLAYER HAS VICTIM TO FORCEFULLY ACCOMODATE");
                                victimExists0 = true;
                                break;
                            }
                        }
                    }
                    useScrollButton.setDisable( !victimExists0 || !playerHasNonSmith);
                    break;
                case 1:

                    break;
                case 2:
                    boolean victimExists2 = false;
                    for ( int j = 0; j < 40; j++) {
                        if (currentGameState.board.map[j] instanceof Town) {
                            if ( ((Town) currentGameState.board.map[j]).ownerId != csc.playerID - 1) {
                                if ( ((Town) currentGameState.board.map[j]).numberOfInns > 0) {
                                    victimExists2 = true;
                                    break;
                                }
                            }
                        }
                    }
                    useScrollButton.setDisable(!victimExists2);
                    break;
                case 3:
                    boolean victimExists3 = false;
                    for ( int j = 0; j < currentGameState.tokens.size(); j++) {
                        if ( !currentGameState.tokens.get(j).isBankrupt ) {
                            if ( currentGameState.tokens.get(j).ID != csc.playerID - 1) {
                                victimExists3 = true;
                                break;
                            }
                        }
                    }
                    useScrollButton.setDisable( !victimExists3 );
                    break;
                case 4:
                    boolean victimExists4 = false;
                    for ( int j = 0; j < currentGameState.tokens.size(); j++) {
                        if ( currentGameState.tokens.get(j).ID != csc.playerID - 1) {
                            if ( currentGameState.tokens.get(j).scrollCards.size() > 0) {
                                victimExists4 = true;
                                break;
                            }
                        }
                    }
                    useScrollButton.setDisable( !victimExists4 );
                    break;
            }

        }
        if (lastOp == 7) {
            for (int i = 0; i < scrollButtons.size(); i++) {
                scrollButtons.get(i).setDisable(true);
            }
        }
    }

    public void resetScrollsList() {
        scrollsList.getItems().clear();
        scrollButtons.clear();
    }

    public boolean isBuildAvailable(int playerID)
    {
        if(currentGameState.board.map[currentGameState.tokens.get(playerID).currentLocation] instanceof Town) {
            Town currentTown = (Town) currentGameState.board.map[currentGameState.tokens.get(playerID).currentLocation];
            if( currentTown.numberOfInns >= currentTown.MAX_NUMBER_OF_HOUSES )
            {
                return false;
            }
            if(currentGameState.tokens.get(playerID).money < currentTown.innPrice) {
                return false;
            }
            if(!currentGameState.tokens.get(playerID).isColorGroupOwner(currentTown)){
                return false;
            }
            return true;
        }
        return false;
    }

    public void updateInfoPanel() {
        infoPanel.setText(currentGameState.infoPanel);
    }
}
