import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sun.awt.windows.ThemeReader;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class Player extends Application implements EventHandler<ActionEvent> {
    // Connection properties
    private Player.ClientSideConnection csc;
    private int playerID;
    private boolean isTurn;
    volatile ArrayList<String> classes;
    volatile boolean gameStarted;
    private int playerCount = 0;
    volatile boolean done = false;
    private Game currentGameState;

    // ana pencere
    Stage window;

    // farklı sayfalar için farklı sceneler
    Scene mainMenu, hostScreen, joinGame, classSelect, howToPlay, soundSettings, credits, inGame;

    // main menü bileşenleri
    Label gameName;
    Button hostButton;
    Button joinGameButton;
    Button tutorialButton;
    Button settingsButton;
    Button exitButton;
    Button creditsButton;

    // hostScreen components
    Label enterPlayerCount;
    Button hostGameButton;
    Button hostBackButton;
    ComboBox dropdown;

    // join game bileşenleri
    Label enterGameId;
    TextField gameIDTxtField;
    Button joinButton;
    Button joinBack;

    // classSelect components
    ListView playerList;
    Button leaveLobby;
    Label gameID;
    Label selectClass;
    Button startGame;
    Button traveler1;
    Button traveler2;
    Button noble;
    Button knight;
    Button treasureHunter;
    Button wizard;
    Button fortuneTeller;
    Button thief;
    Button builder;
    Button cardinal;

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


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        // pencerenin adı
        primaryStage.setTitle("Scrolls of Estatia");

        // buton oluşturma ve isimlendirme
        // main menü
        gameName = new Label();
        gameName.setText("Scrolls of Estatia");

        hostButton = new Button();
        hostButton.setText("Host Game");
        hostButton.setOnAction(e -> {
            window.setScene(hostScreen);
        });


        joinGameButton = new Button();
        joinGameButton.setText("Join Game");
        joinGameButton.setOnAction(e -> {
            window.setScene(joinGame);
        });

        tutorialButton = new Button();
        tutorialButton.setText("Tutorial");
        tutorialButton.setOnAction(this);

        settingsButton = new Button();
        settingsButton.setText("Settings");
        settingsButton.setOnAction(this);

        exitButton = new Button();
        exitButton.setText("Exit");
        exitButton.setOnAction(e -> Platform.exit());

        creditsButton = new Button();
        creditsButton.setText("Credits");
        creditsButton.setOnAction(this);

        // Main menu layout
        VBox mainMenuLayout = new VBox(10);
        mainMenuLayout.getChildren().addAll(gameName, hostButton, joinGameButton, tutorialButton, settingsButton, creditsButton, exitButton);
        mainMenuLayout.setAlignment(Pos.CENTER);
        mainMenu = new Scene(mainMenuLayout, 500, 500);

        // ilk ekran main menü olsun diye
        window.setScene(mainMenu);
        window.show();

        // hostScreen
        enterPlayerCount = new Label();
        enterPlayerCount.setText("Please select the number of players:");

        hostGameButton = new Button();
        hostGameButton.setText("Host Game");
        hostGameButton.setOnAction(e -> {
            if( 2 <= playerCount && playerCount <=8) {
                connectToServer(true, "");
                boolean response = false;
                try {
                    response = csc.dataIn.readBoolean();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                if (response) {
                    try {
                        csc.dataOut.writeInt(playerCount);
                        csc.dataOut.flush();
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                    csc.continueConnection();
                    csc.isHost = true;
                    try {
                        csc.gameID = csc.dataIn.readInt();
                        gameID.setText("Game ID: " + csc.gameID);
                        classes = (ArrayList<String>) (csc.dataIn.readObject());
                        playerList.getItems().setAll(classes);
                        playerList.getItems().set(playerID - 1, playerList.getItems().get(playerID - 1) + " [YOU]");
                    } catch (IOException | ClassNotFoundException ex) {
                        ex.printStackTrace();
                    }
                    startGame.setVisible(true);
                    Thread t2 = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            // HERE
                            while(!gameStarted) {
                                try {
                                    try {
                                        classes = (ArrayList<String>) (csc.dataIn.readObject());
                                        Platform.runLater(new Runnable() {
                                            @Override
                                            public void run() {
                                                playerList.getItems().setAll(classes);
                                                playerList.getItems().set(playerID - 1, playerList.getItems().get(playerID - 1) + " [YOU]");
                                                return;
                                            }
                                        });
                                    } catch (OptionalDataException opEx) {
                                        System.out.println(opEx.length);
                                    }
                                } catch (IOException | ClassNotFoundException exception) {
                                    exception.printStackTrace();
                                }
                            }
                            return;
                        }
                    });
                    t2.start();
                    window.setScene(classSelect);
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Unable to host game!");
                    alert.setHeaderText(null);
                    alert.setContentText("You can't host a game!");
                    try {
                        csc.dataOut.flush();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Unable to host game!");
                alert.setHeaderText(null);
                alert.setContentText("Please enter a valid player count!");
                alert.showAndWait();
            }
        });

        hostBackButton = new Button();
        hostBackButton.setText("Back");
        hostBackButton.setOnAction(e -> {
            window.setScene(mainMenu);
        });

        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "2 Players",
                        "3 Players",
                        "4 Players",
                        "5 Players",
                        "6 Players",
                        "7 Players",
                        "8 Players"
                );
        dropdown = new ComboBox(options);
        dropdown.setOnAction(e -> {
            playerCount = Character.getNumericValue(dropdown.getValue().toString().charAt(0));
        });

        // hostScreen layout
        VBox hostScreenLayout = new VBox(20);
        hostScreenLayout.getChildren().addAll(enterPlayerCount, dropdown, hostGameButton, hostBackButton);
        hostScreenLayout.setAlignment(Pos.CENTER);
        hostScreen = new Scene(hostScreenLayout, 500, 500);

        // join game ekranı
        enterGameId = new Label();
        enterGameId.setText("Please enter the Game ID:");

        gameIDTxtField = new TextField();

        joinButton = new Button();
        joinButton.setText("Join");
        AtomicBoolean joinSuccessful = new AtomicBoolean(false);
        joinButton.setOnAction(e -> {
            try {
                connectToServer(false, gameIDTxtField.getText());
                int checkResponse = csc.dataIn.readInt();
                switch(checkResponse) {
                    case 0:
                        joinSuccessful.set(false);
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Unable to join game!");
                        alert.setHeaderText(null);
                        alert.setContentText("Game with such ID does not exist!");
                        try {
                            csc.dataOut.flush();
                        } catch(IOException ex) {
                            ex.printStackTrace();
                        }
                        alert.showAndWait();
                        break;
                    case 1:
                        joinSuccessful.set(false);
                        Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                        alert2.setTitle("Unable to join game!");
                        alert2.setHeaderText(null);
                        alert2.setContentText("Game with such ID does not exist!");
                        try {
                            csc.dataOut.flush();
                        } catch(IOException ex) {
                            ex.printStackTrace();
                        }
                        alert2.showAndWait();
                        break;
                    case 2:
                        joinSuccessful.set(true);
                        csc.continueConnection();
                        window.setScene(classSelect);
                        break;
                    case 3:
                        joinSuccessful.set(false);
                        Alert alert3 = new Alert(Alert.AlertType.INFORMATION);
                        alert3.setTitle("Unable to join game!");
                        alert3.setHeaderText(null);
                        alert3.setContentText("Game is full!");
                        try {
                            csc.dataOut.flush();
                        } catch(IOException ex) {
                            ex.printStackTrace();
                        }
                        alert3.showAndWait();
                        break;
                    case 4:
                        joinSuccessful.set(false);
                        Alert alert4 = new Alert(Alert.AlertType.INFORMATION);
                        alert4.setTitle("Unable to join game!");
                        alert4.setHeaderText(null);
                        alert4.setContentText("Game is already in progress!");
                        try {
                            csc.dataOut.flush();
                        } catch(IOException ex) {
                            ex.printStackTrace();
                        }
                        alert4.showAndWait();
                        break;
                }
            } catch(IOException ex) {
                ex.printStackTrace();
            }
            if(joinSuccessful.get()) {
                startGame.setVisible(false);
                gameID.setText("Game ID: " + String.valueOf(gameIDTxtField.getText()));
                try {
                    try {
                        int update = csc.dataIn.readInt();
                        classes = (ArrayList<String>) (csc.dataIn.readObject());
                        playerList.getItems().setAll(classes);
                        playerID = csc.dataIn.readInt();
                        playerList.getItems().set(playerID - 1, playerList.getItems().get(playerID - 1) + " [YOU]");
                    } catch( OptionalDataException ex) {
                        System.out.println(ex.length);
                    }
                } catch (IOException | ClassNotFoundException exception) {
                    exception.printStackTrace();
                }
                window.setScene(classSelect);
                Thread t = new Thread(new Runnable() {
                    public void run() {
                        try {
                            // HERE
                            while(!gameStarted) {
                                int hostCommand = csc.dataIn.readInt();
                                System.out.println("HOST COMMAND IS " + hostCommand);
                                if(hostCommand == 0) {
                                    csc.dataOut.writeInt(0);
                                    currentGameState = (Game) (csc.dataIn.readObject());
                                    startReceivingTurns();
                                    Platform.runLater(new Runnable() {
                                        @Override public void run() {
                                            window.setScene(inGame);
                                            gameStarted = true;
                                            return;
                                        }
                                    });
                                    return;
                                } else if(hostCommand == 1){
                                    return;
                                } else if(hostCommand == 2) {
                                    Platform.runLater(new Runnable() {
                                        @Override public void run() {
                                            window.setScene(mainMenu);
                                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                            alert.setTitle("Lobby disbanded!");
                                            alert.setHeaderText(null);
                                            alert.setContentText("Host left the lobby!");
                                            try {
                                                csc.dataOut.flush();
                                            } catch(IOException ex) {
                                                ex.printStackTrace();
                                            }
                                            alert.showAndWait();
                                            return;
                                        }
                                    });
                                    return;
                                } else if(hostCommand == 3) {
                                    try {
                                        classes = (ArrayList<String>) (csc.dataIn.readObject());
                                        playerID = csc.dataIn.readInt();
                                        Platform.runLater(new Runnable() {
                                            @Override public void run() {
                                                playerList.getItems().setAll(classes);
                                                playerList.getItems().set(playerID - 1, playerList.getItems().get(playerID - 1) + " [YOU]");
                                                return;
                                            }
                                        });
                                    } catch (IOException | ClassNotFoundException exception) {
                                        exception.printStackTrace();
                                    }
                                }
                            }
                            return;
                        } catch(IOException | ClassNotFoundException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
                t.start();
            }
        });

        joinBack = new Button();
        joinBack.setText("Back");
        joinBack.setOnAction(e -> {
            window.setScene(mainMenu);
        });

        //join game layout ayarları
        VBox joinGameLayout = new VBox(20);
        joinGameLayout.getChildren().addAll(enterGameId, gameIDTxtField, joinButton, joinBack);
        joinGameLayout.setAlignment(Pos.CENTER);
        joinGame = new Scene(joinGameLayout, 500, 500);

        // classSelect screen
        playerList = new ListView();

        leaveLobby = new Button();
        leaveLobby.setText("Leave Lobby");
        leaveLobby.setOnAction(e -> {
            try
            {
                csc.dataOut.writeInt(1);
                csc.dataOut.flush();
            } catch(IOException ex) {
                ex.printStackTrace();
            }
            window.setScene(mainMenu);
        });

        selectClass = new Label();
        selectClass.setText("Please select a class");

        traveler1 = new Button();
        traveler1.setText("Traveler (One-in-Two)");
        traveler1.setOnAction(e -> {
            try
            {
                csc.dataOut.writeInt(2);
                csc.dataOut.flush();
            } catch(IOException ex) {
                ex.printStackTrace();
            }
        });

        traveler2 = new Button();
        traveler2.setText("Traveler (Three-in-Five)");
        traveler2.setOnAction(e -> {
            try
            {
                csc.dataOut.writeInt(3);
                csc.dataOut.flush();
            } catch(IOException ex) {
                ex.printStackTrace();
            }
        });

        noble = new Button();
        noble.setText("Noble");
        noble.setOnAction(e -> {
            try
            {
                csc.dataOut.writeInt(4);
                csc.dataOut.flush();
            } catch(IOException ex) {
                ex.printStackTrace();
            }
        });

        knight = new Button();
        knight.setText("Knight");
        knight.setOnAction(e -> {
            try
            {
                csc.dataOut.writeInt(5);
                csc.dataOut.flush();
            } catch(IOException ex) {
                ex.printStackTrace();
            }
        });

        treasureHunter = new Button();
        treasureHunter.setText("Treasure Hunter");
        treasureHunter.setOnAction(e -> {
            try
            {
                csc.dataOut.writeInt(6);
                csc.dataOut.flush();
            } catch(IOException ex) {
                ex.printStackTrace();
            }
        });

        wizard = new Button();
        wizard.setText("Wizard");
        wizard.setOnAction(e -> {
            try
            {
                csc.dataOut.writeInt(7);
                csc.dataOut.flush();
            } catch(IOException ex) {
                ex.printStackTrace();
            }
        });

        fortuneTeller = new Button();
        fortuneTeller.setText("Fortune Teller");
        fortuneTeller.setOnAction(e -> {
            try
            {
                csc.dataOut.writeInt(8);
                csc.dataOut.flush();
            } catch(IOException ex) {
                ex.printStackTrace();
            }
        });

        thief = new Button();
        thief.setText("Thief");
        thief.setOnAction(e -> {
            try
            {
                csc.dataOut.writeInt(9);
                csc.dataOut.flush();
            } catch(IOException ex) {
                ex.printStackTrace();
            }
        });

        builder = new Button();
        builder.setText("Builder");
        builder.setOnAction(e -> {
            try
            {
                csc.dataOut.writeInt(10);
                csc.dataOut.flush();
            } catch(IOException ex) {
                ex.printStackTrace();
            }
        });

        cardinal = new Button();
        cardinal.setText("Cardinal");
        cardinal.setOnAction(e -> {
            try
            {
                csc.dataOut.writeInt(11);
                csc.dataOut.flush();
            } catch(IOException ex) {
                ex.printStackTrace();
            }
        });

        startGame = new Button();
        startGame.setText("Start Game");
        startGame.setOnAction(e -> {
            try {
                csc.dataOut.writeInt(0);
                csc.dataOut.flush();
            } catch(IOException ex) {
                ex.printStackTrace();
            }
            Thread t = new Thread(new Runnable() {
                public void run() {
                    try {
                        while(true) {
                            int startConfirmed = csc.dataIn.readInt();
                            if (startConfirmed == 3) {
                                gameStarted = true;
                                currentGameState = (Game) (csc.dataIn.readObject());
                                startReceivingTurns();
                                Platform.runLater(new Runnable() {
                                    @Override public void run() {
                                        window.setScene(inGame);
                                        return;
                                    }
                                });
                                return;
                            } else {
                                Platform.runLater(new Runnable() {
                                    @Override public void run() {
                                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                        alert.setTitle("Can't start game!");
                                        alert.setHeaderText(null);
                                        alert.setContentText("Not enough players!");
                                        alert.showAndWait();
                                        return;
                                    }
                                });
                                return;
                            }
                        }
                    } catch(IOException | ClassNotFoundException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            t.start();
        });

        gameID = new Label();

        // classSelect Layout
        VBox classSelectLayout = new VBox(20);
        classSelectLayout.getChildren().addAll(playerList, leaveLobby, gameID, startGame, selectClass, traveler1, traveler2, noble, knight, treasureHunter, wizard, fortuneTeller, thief, builder, cardinal);
        classSelect = new Scene(classSelectLayout, 500, 500);


        // inGame Ekranı
        rollDice = new Button();
        rollDice.setText("Roll Dice");
        rollDice.setOnAction(this);

        build = new Button();
        build.setText("Build");
        build.setOnAction(this);

        useScroll = new Button();
        useScroll.setText("Use Scroll");
        useScroll.setOnAction(this);

        buyProperty = new Button();
        buyProperty.setText("Buy Property");
        buyProperty.setOnAction(this);

        sendTrade = new Button();
        sendTrade.setText("Send Trade Request"); // bu listed olacak
        sendTrade.setOnAction(this);

        acceptTrade = new Button();
        acceptTrade.setText("Accept Trade Request");
        acceptTrade.setOnAction(this);

        declineTrade = new Button();
        declineTrade.setText("Decline Trade Request");
        declineTrade.setOnAction(this);

        endTurn = new Button();
        endTurn.setText("End Turn");
        endTurn.setOnAction(this);


        // layout ayarları
        VBox inGameLayout = new VBox(20);
        inGameLayout.getChildren().addAll(rollDice, build, useScroll, buyProperty, sendTrade, acceptTrade, declineTrade, endTurn);
        inGame = new Scene(inGameLayout, 500, 500);
    }

    // kullanıcı butona tıklayınca bu çağırılıyor
    @Override
    public void handle(ActionEvent event) {

        // hangi buton ne yapıyor kontrol etmek için
        if (event.getSource() == rollDice) {
            if(isTurn) {
                rollDice.setDisable(true);
                build.setDisable(false);
                useScroll.setDisable(false);
                buyProperty.setDisable(false);
                sendTrade.setDisable(false);
                acceptTrade.setDisable(false);
                declineTrade.setDisable(false);
                endTurn.setDisable(false);
                try {
                    System.out.println("Player " + playerID + " rolled dice");
                    csc.dataOut.writeInt(0);
                    csc.dataOut.flush();
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (event.getSource() == build) {
            if(isTurn) {
                build.setDisable(true);
                try {
                    System.out.println("Player " + playerID + " built");
                    csc.dataOut.writeInt(1);
                    csc.dataOut.flush();
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (event.getSource() == useScroll) {
            if(isTurn) {
                useScroll.setDisable(true);
                try {
                    System.out.println("Player " + playerID + " used scroll");
                    csc.dataOut.writeInt(2);
                    csc.dataOut.flush();

                    // GET PLAYER TO CHOOSE A SCROLL AND VICTIM
                    int scrollIndex = 0;
                    int victimID = 0;

                    csc.dataOut.writeInt(scrollIndex);
                    csc.dataOut.flush();
                    csc.dataOut.writeInt(victimID);
                    csc.dataOut.flush();

                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (event.getSource() == buyProperty) {
            if(isTurn) {
                buyProperty.setDisable(true);
                try {
                    System.out.println("Player " + playerID + " bought property");
                    csc.dataOut.writeInt(3);
                    csc.dataOut.flush();
                    boolean purchaseSuccessful = csc.dataIn.readBoolean();
                    if (!purchaseSuccessful) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Unable to purchase land!");
                        alert.setHeaderText(null);
                        alert.setContentText("You cannot purchase this land!");
                    }
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (event.getSource() == sendTrade) {
            if(isTurn) {
                sendTrade.setDisable(true);
                try {
                    System.out.println("Player " + playerID + " sent trade");

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
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (event.getSource() == acceptTrade) {
            if(isTurn) {
                acceptTrade.setDisable(true);
                declineTrade.setDisable(true);
                try {
                    System.out.println("Player " + playerID + " accepted trade");
                    csc.dataOut.writeInt(5);
                    csc.dataOut.flush();
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (event.getSource() == declineTrade) {
            if(isTurn) {
                declineTrade.setDisable(true);
                acceptTrade.setDisable(true);
                try {
                    System.out.println("Player " + playerID + " declined trade");
                    csc.dataOut.writeInt(6);
                    csc.dataOut.flush();
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (event.getSource() == endTurn) {
            if(isTurn) {
                rollDice.setDisable(true);
                build.setDisable(true);
                useScroll.setDisable(true);
                buyProperty.setDisable(true);
                sendTrade.setDisable(true);
                acceptTrade.setDisable(true);
                declineTrade.setDisable(true);
                endTurn.setDisable(true);
                try {
                    System.out.println("Player " + playerID + " ended Turn");
                    csc.dataOut.writeInt(7);
                    csc.dataOut.flush();
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void connectToServer(boolean isHost, String enteredGameID) {
        csc = new Player.ClientSideConnection(isHost, enteredGameID);
        rollDice.setDisable(!isHost);
        build.setDisable(true);
        useScroll.setDisable(true);
        buyProperty.setDisable(true);
        sendTrade.setDisable(true);
        acceptTrade.setDisable(true);
        declineTrade.setDisable(true);
        endTurn.setDisable(true);
    }

    public void startReceivingTurns() {
        Thread t = new Thread(new Runnable() {
            public void run() {
                while (true) {
                    try {
                        isTurn = csc.dataIn.readBoolean();
                        System.out.println("Player " + playerID + " started Turn");
                        rollDice.setDisable(!isTurn);
                        build.setDisable(true);
                        useScroll.setDisable(true);
                        buyProperty.setDisable(true);
                        sendTrade.setDisable(true);
                        acceptTrade.setDisable(true);
                        declineTrade.setDisable(true);
                        endTurn.setDisable(true);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        t.start();
    }

    //Client connection
    private class ClientSideConnection {
        private Socket socket;
        private ObjectInputStream dataIn;
        private ObjectOutputStream dataOut;
        private boolean isHost;
        private int gameID;

        private ClientSideConnection(boolean isHost, String enteredGameID) {
            try {
                socket = new Socket("localhost", 12345);
                dataOut = new ObjectOutputStream(socket.getOutputStream());
                dataOut.flush();
                dataIn = new ObjectInputStream(socket.getInputStream());
                if(!isHost) {
                    dataOut.writeBoolean(false);
                    dataOut.writeUTF(enteredGameID);
                    dataOut.flush();
                } else {
                    dataOut.writeBoolean(true);
                    dataOut.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void continueConnection() {
            try {
                playerID = dataIn.readInt();
                isTurn = dataIn.readBoolean();
                System.out.println("Connected to server as player " + playerID);
            } catch(IOException e) {
                e.printStackTrace();
            }

        }
    }
}