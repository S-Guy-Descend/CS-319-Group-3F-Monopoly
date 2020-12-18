import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class Player extends Application implements EventHandler<ActionEvent> {
    // Connection properties
    private Player.ClientSideConnection csc;
    private int playerID;
    private boolean isTurn;

    // ana pencere
    Stage window;

    // farklı sayfalar için farklı sceneler
    Scene mainMenu, hostGame, joinGame, classSelect, howToPlay, soundSettings, credits, inGame;

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
    Button joinButton;
    Button joinBack;

    // classSelect components
    ListView playerList;
    Button leaveLobby;
    Label gameID;
    Button startGame;

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

        hostGameButton = new Button();
        hostGameButton.setText("Host Game");
        hostGameButton.setOnAction(e -> {
            connectToServer(true, "");
            boolean response = false;
            try {
                response = csc.dataIn.readBoolean();
            } catch(IOException ex) {
                ex.printStackTrace();
            }
            if(response) {
                csc.continueConnection();
                csc.isHost = true;
                try {
                    csc.gameID = csc.dataIn.readInt();
                    gameID.setText("Game ID: " + csc.gameID);
                } catch( IOException ex) {
                    ex.printStackTrace();
                }
                startGame.setVisible(true);
                window.setScene(classSelect);
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Unable to host game!");
                alert.setHeaderText(null);
                alert.setContentText("You can't host a game!");
                try {
                    csc.dataOut.flush();
                } catch(IOException ex) {
                    ex.printStackTrace();
                }
                alert.showAndWait();
            }

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
        mainMenuLayout.getChildren().addAll(gameName, hostGameButton, joinGameButton, tutorialButton, settingsButton, creditsButton, exitButton);
        mainMenuLayout.setAlignment(Pos.CENTER);
        mainMenu = new Scene(mainMenuLayout, 500, 500);

        // ilk ekran main menü olsun diye
        window.setScene(mainMenu);
        window.show();

        // join game ekranı
        enterGameId = new Label();
        enterGameId.setText("Enter the Game ID");

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
                        window.setScene(inGame);
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
                window.setScene(classSelect);
                Thread t = new Thread(new Runnable() {
                    public void run() {
                        try {
                            System.out.println("TEST1");
                            int hostCommand = csc.dataIn.readInt();
                            System.out.println("TEST2");
                            System.out.println("HOST COMMAND IS " + hostCommand);
                            if(hostCommand == 0) {
                                startReceivingTurns();
                                Platform.runLater(new Runnable() {
                                    @Override public void run() {
                                        window.setScene(inGame);
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
                            }
                        } catch(IOException ex) {
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
                                    }
                                });
                            }
                        }
                    } catch(IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            t.start();
        });

        gameID = new Label();

        // classSelect Layout
        VBox classSelectLayout = new VBox(20);
        classSelectLayout.getChildren().addAll(playerList, leaveLobby, gameID, startGame);
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
        // içerik scenenin içine koyuluyor, constructor dimensionları alıyor
        inGame = new Scene(inGameLayout, 500, 500);
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
                    csc.dataOut.writeInt(4);
                    csc.dataOut.flush();

                    //Construct the trade request
                    ArrayList<Square> placesToGive = new ArrayList<Square>();
                    placesToGive.add(Game.instance.board.map[1]);
                    ArrayList<Square> placesToTake = new ArrayList<Square>();
                    placesToTake.add(Game.instance.board.map[3]);
                    int moneyToGive = 100;
                    int moneyToTake = 300;
                    TradeRequest tradeRequest = new TradeRequest(placesToGive, placesToTake, moneyToGive, moneyToTake);

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

/*
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class Player extends JFrame{
    private ClientSideConnection csc;
    private int playerID;
    private boolean isTurn;

    // placeholder UI
    private Container contentPane;
    private JButton rollDice;
    private JButton endTurn;
    private JButton build;
    private JButton useScroll;
    private JButton buyProperty;
    private JButton sendTrade;
    private JButton acceptTrade;
    private JButton declineTrade;

    public Player() {
        contentPane = this.getContentPane();

        rollDice = new JButton("Roll Dice");
        build = new JButton("Build");
        useScroll = new JButton("Use Scroll");
        buyProperty = new JButton("Buy Property");
        sendTrade = new JButton("Send Trade");
        acceptTrade = new JButton("Accept Trade");
        declineTrade = new JButton("Decline Trade");
        endTurn = new JButton("End Turn");

        this.setSize(1400, 100);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        contentPane.setLayout(new GridLayout(1,7));

        contentPane.add(rollDice);     // 0
        contentPane.add(build);        // 1
        contentPane.add(useScroll);    // 2
        contentPane.add(buyProperty);  // 3
        contentPane.add(sendTrade);    // 4
        contentPane.add(acceptTrade);  // 5
        contentPane.add(declineTrade); // 6
        contentPane.add(endTurn);      // 7

        this.setVisible(true);

        // Roll dice action listener
        ActionListener rollDiceAL = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if(isTurn) {
                    JButton b = (JButton) ae.getSource();
                    b.setEnabled(false);
                    rollDice.setEnabled(false);
                    build.setEnabled(true);
                    useScroll.setEnabled(true);
                    buyProperty.setEnabled(true);
                    sendTrade.setEnabled(true);
                    acceptTrade.setEnabled(true);
                    declineTrade.setEnabled(true);
                    endTurn.setEnabled(true);
                    try {
                        System.out.println("Player " + playerID + " rolled dice");
                        csc.dataOut.writeInt(0);
                        csc.dataOut.flush();
                    } catch(IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        rollDice.addActionListener(rollDiceAL);

        // Build action listener
        ActionListener buildAL = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if(isTurn) {
                    JButton b = (JButton) ae.getSource();
                    b.setEnabled(false);
                    try {
                        System.out.println("Player " + playerID + " built");
                        csc.dataOut.writeInt(1);
                        csc.dataOut.flush();
                    } catch(IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        build.addActionListener(buildAL);

        // Use scroll action listener
        ActionListener useScrollAL = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if(isTurn) {
                    JButton b = (JButton) ae.getSource();
                    b.setEnabled(false);
                    try {
                        System.out.println("Player " + playerID + " used scroll");
                        csc.dataOut.writeInt(2);
                        csc.dataOut.flush();
                    } catch(IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        useScroll.addActionListener(useScrollAL);

        // Buy property action listener
        ActionListener buyPropertyAL = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if(isTurn) {
                    JButton b = (JButton) ae.getSource();
                    b.setEnabled(false);
                    try {
                        System.out.println("Player " + playerID + " bought property");
                        csc.dataOut.writeInt(3);
                        csc.dataOut.flush();
                    } catch(IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        buyProperty.addActionListener(buyPropertyAL);

        // Send trade action listener
        ActionListener sendTradeAL = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if(isTurn) {
                    JButton b = (JButton) ae.getSource();
                    b.setEnabled(false);
                    try {
                        System.out.println("Player " + playerID + " sent trade");
                        csc.dataOut.writeInt(4);
                        csc.dataOut.flush();

                        //Construct the trade request
                        ArrayList<Square> placesToGive = new ArrayList<Square>();
                        placesToGive.add(Game.instance.board.map[1]);
                        ArrayList<Square> placesToTake = new ArrayList<Square>();
                        placesToTake.add(Game.instance.board.map[3]);
                        int moneyToGive = 100;
                        int moneyToTake = 300;
                        TradeRequest tradeRequest = new TradeRequest(placesToGive, placesToTake, moneyToGive, moneyToTake);

                        csc.dataOut.writeObject(tradeRequest);
                        csc.dataOut.flush();
                    } catch(IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        sendTrade.addActionListener(sendTradeAL);

        // Accept trade action listener
        ActionListener acceptTradeAL = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if(isTurn) {
                    JButton b = (JButton) ae.getSource();
                    b.setEnabled(false);
                    declineTrade.setEnabled(false);
                    try {
                        System.out.println("Player " + playerID + " accepted trade");
                        csc.dataOut.writeInt(5);
                        csc.dataOut.flush();
                    } catch(IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        acceptTrade.addActionListener(acceptTradeAL);

        // Decline trade action listener
        ActionListener declineTradeAL = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if(isTurn) {
                    JButton b = (JButton) ae.getSource();
                    b.setEnabled(false);
                    acceptTrade.setEnabled(false);
                    try {
                        System.out.println("Player " + playerID + " declined trade");
                        csc.dataOut.writeInt(6);
                        csc.dataOut.flush();
                    } catch(IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        declineTrade.addActionListener(declineTradeAL);

        // End turn action listener
        ActionListener endTurnAL = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if(isTurn) {
                    JButton b = (JButton) ae.getSource();
                    rollDice.setEnabled(false);
                    build.setEnabled(false);
                    useScroll.setEnabled(false);
                    buyProperty.setEnabled(false);
                    sendTrade.setEnabled(false);
                    acceptTrade.setEnabled(false);
                    declineTrade.setEnabled(false);
                    endTurn.setEnabled(false);
                    try {
                        System.out.println("Player " + playerID + " ended Turn");
                        csc.dataOut.writeInt(7);
                        csc.dataOut.flush();
                    } catch(IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        endTurn.addActionListener(endTurnAL);
    }

    public void connectToServer() {
        csc = new ClientSideConnection();
        rollDice.setEnabled(isTurn);
        build.setEnabled(false);
        useScroll.setEnabled(false);
        buyProperty.setEnabled(false);
        sendTrade.setEnabled(false);
        acceptTrade.setEnabled(false);
        declineTrade.setEnabled(false);
        endTurn.setEnabled(false);
    }

    public void startReceivingTurns() {
        Thread t = new Thread( new Runnable() {
            public void run() {
                while(true) {
                    try {
                        isTurn = csc.dataIn.readBoolean();
                        System.out.println("Player " + playerID + " started Turn");
                        rollDice.setEnabled(isTurn);
                        build.setEnabled(false);
                        useScroll.setEnabled(false);
                        buyProperty.setEnabled(false);
                        sendTrade.setEnabled(false);
                        acceptTrade.setEnabled(false);
                        declineTrade.setEnabled(false);
                        endTurn.setEnabled(false);
                    } catch(IOException e) {
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

        private ClientSideConnection() {
            try {
                socket = new Socket("localhost", 12345);
                dataOut = new ObjectOutputStream(socket.getOutputStream());
                dataOut.flush();
                dataIn = new ObjectInputStream(socket.getInputStream());
                playerID = dataIn.readInt();
                setTitle("Player " + playerID);
                isTurn = dataIn.readBoolean();
                System.out.println("Connected to server as player " + playerID);
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Player p = new Player();
        p.connectToServer();
        p.startReceivingTurns();
    }
}*/
