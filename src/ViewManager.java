import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;

import javafx.scene.control.*;

import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.io.IOException;
import java.io.OptionalDataException;
import java.io.UTFDataFormatException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class ViewManager {

    static final int WIDTH = 1300;
    static final int HEIGHT = 750;

    //Button layout coordinates
    final int marginY = 60;
    final int startingXCoordinate = 555;
    //for troll layout
    //final int startingYCoordinate = 320;
    final int startingYCoordinate = 250;

    private AnchorPane mainPane;
    private Scene mainScene;
    private Stage mainStage;

    private Subscene creditsSubScene;
    private Subscene settingsSubScene;
    private Subscene tutorialSubScene;
    private Subscene joinGameSubScene;
    private Subscene hostGameSubScene;
    private Subscene lobbySubScene;

    // Connection properties
    private ClientSideConnection csc;
    volatile ArrayList<String> classes;
    volatile boolean gameStarted;
    private int playerCount = 0;
    volatile boolean done = false;

    // LobbySubScene components
    ListView playerList;
    ComboBox classDropdown;
    StyledButton startGameButton;
    StyledButton leaveLobbyButton;
    InfoLabel gameID;

    public ViewManager() {
        mainPane = new AnchorPane();
        mainScene = new Scene(mainPane, WIDTH, HEIGHT);
        mainStage = new Stage();
        mainStage.setScene(mainScene);
        mainStage.setResizable(false);
        createMainMenuButtons();
        createBackground();
        createLogo();
        createSubScenes();
    }
    public void createSubScenes(){
        settingsSubScene = new Subscene(250,500,-550,230);
        creditsSubScene = new Subscene(250,500,-550,485);
        tutorialSubScene = new Subscene(500,500,-550,230);

        mainPane.getChildren().add(creditsSubScene);
        mainPane.getChildren().add(settingsSubScene);
        mainPane.getChildren().add(tutorialSubScene);

        createJoinGameSubScene();
        createHostGameSubScene();
        createLobbySubScene();

    }

    public void createLobbySubScene() {
        lobbySubScene = new Subscene((int) mainScene.getHeight() - 20, (int) mainScene.getWidth() - 20, (int) mainScene.getWidth(), 20);



        playerList = new ListView();

        ObservableList<String> classList =
                FXCollections.observableArrayList(
                        "Traveler (One-in-Two)",
                        "Traveler (Three-in-Five)",
                        "Noble",
                        "Knight",
                        "Treasure Hunter",
                        "Wizard",
                        "Fortune Teller",
                        "Thief",
                        "Builder",
                        "Cardinal"
                );

        InfoLabel selectClassLabel = new InfoLabel( "Please select your class:", 400, 150, 25, "Verdana" );

        classDropdown =  new ComboBox(classList);
        classDropdown.getSelectionModel().selectFirst();
        classDropdown.setOnAction( e -> {
            switch (classDropdown.getValue().toString() ) {
                case "Traveler (One-in-Two)":
                    try {
                        csc.dataOut.writeInt(2);
                        csc.dataOut.flush();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    break;
                case "Traveler (Three-in-Five)":
                    try {
                        csc.dataOut.writeInt(3);
                        csc.dataOut.flush();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    break;
                case "Noble":
                    try {
                        csc.dataOut.writeInt(4);
                        csc.dataOut.flush();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    break;
                case "Knight":
                    try {
                        csc.dataOut.writeInt(5);
                        csc.dataOut.flush();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    break;
                case "Treasure Hunter":
                    try {
                        csc.dataOut.writeInt(6);
                        csc.dataOut.flush();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    break;
                case "Wizard":
                    try {
                        csc.dataOut.writeInt(7);
                        csc.dataOut.flush();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    break;
                case "Fortune Teller":
                    try {
                        csc.dataOut.writeInt(8);
                        csc.dataOut.flush();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    break;
                case "Thief":
                    try {
                        csc.dataOut.writeInt(9);
                        csc.dataOut.flush();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    break;
                case "Builder":
                    try {
                        csc.dataOut.writeInt(10);
                        csc.dataOut.flush();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    break;
                case "Cardinal":
                    try {
                        csc.dataOut.writeInt(11);
                        csc.dataOut.flush();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    break;
            }
        });

        gameID = new InfoLabel( "Game ID: ", 250, 150, 25, "Verdana" );

        startGameButton = new StyledButton("Start Game");
        startGameButton.setOnAction( e -> {
            try {
                csc.dataOut.writeInt(0);
                csc.dataOut.flush();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
            if (classes.size() == playerCount) {
                gameStarted = true;

                // CREATE GAMEVIEWMANAGER HERE
                GameViewManager game = new GameViewManager(csc);
                game.startReceivingTurns();
                mainStage.hide();
                game.enterGame();

            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Can't start game!");
                alert.setHeaderText(null);
                alert.setContentText("Not enough players!");
                alert.showAndWait();
            }
        });

        leaveLobbyButton = new StyledButton("Leave Lobby");
        leaveLobbyButton.setOnAction( e -> {
            try {
                csc.dataOut.writeInt(1);
                csc.dataOut.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            lobbySubScene.rightFloatSubScene();
        });

        VBox lobbyBox1 = new VBox(0);
        lobbyBox1.setAlignment( Pos.CENTER);
        lobbyBox1.getChildren().addAll(playerList, selectClassLabel, classDropdown);

        VBox lobbyBox2 = new VBox(0);
        lobbyBox2.setAlignment( Pos.CENTER);
        lobbyBox2.getChildren().addAll(gameID, startGameButton, leaveLobbyButton);

        HBox createLobbyLayout = new HBox(250);
        createLobbyLayout.setLayoutX(150);
        createLobbyLayout.setLayoutY(50);
        createLobbyLayout.setAlignment( Pos.CENTER);

        createLobbyLayout.getChildren().addAll(lobbyBox1, lobbyBox2);
        lobbySubScene.getPane().getChildren().add(createLobbyLayout);
        mainPane.getChildren().add(lobbySubScene);
    }

    public void createHostGameSubScene() {
        hostGameSubScene = new Subscene(500,500,1300,230);

        VBox hostGameLayout = new VBox( 20);
        hostGameLayout.setLayoutX(40);
        hostGameLayout.setAlignment( Pos.CENTER );

        InfoLabel numberOfPlayersLabel = new InfoLabel( "Please select the number of players:", 400, 150, 25, "Verdana" );

        ObservableList<Integer> options = FXCollections.observableArrayList(2, 3, 4, 5, 6, 7, 8 );
        final ComboBox playerNumDropDown = new ComboBox(options);
        playerNumDropDown.setPrefWidth(200);
        playerNumDropDown.setPrefHeight(50);
        playerNumDropDown.setOnAction( e -> {
            playerCount = Character.getNumericValue(playerNumDropDown.getValue().toString().charAt(0));
        });

        StyledButton hostGameButton = new StyledButton("Host Game");
        hostGameButton.setOnAction( e -> {
            if (2 <= playerCount && playerCount <= 8) {
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
                        playerList.getItems().set(csc.playerID - 1, playerList.getItems().get(csc.playerID - 1) + " [YOU]");
                    } catch (IOException | ClassNotFoundException ex) {
                        ex.printStackTrace();
                    }
                    startGameButton.setVisible(true);
                    Thread t2 = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            // HERE
                            while (!gameStarted) {
                                try {
                                    try {
                                        try {
                                            classes = (ArrayList<String>) (csc.dataIn.readObject());
                                            Platform.runLater(new Runnable() {
                                                @Override
                                                public void run() {
                                                    playerList.getItems().setAll(classes);
                                                    playerList.getItems().set(csc.playerID - 1, playerList.getItems().get(csc.playerID - 1) + " [YOU]");
                                                    return;
                                                }
                                            });
                                        } catch (UTFDataFormatException UTFex) {
                                            System.out.println("UTF");
                                        }
                                    } catch (OptionalDataException opEx) {
                                        System.out.println(opEx.length);
                                        break;
                                    }
                                } catch (IOException | ClassNotFoundException exception) {
                                    exception.printStackTrace();
                                }
                            }
                            return;
                        }
                    });
                    t2.start();
                    lobbySubScene.rightFloatSubScene();
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

        hostGameLayout.getChildren().addAll( numberOfPlayersLabel, playerNumDropDown, hostGameButton);

        hostGameSubScene.getPane().getChildren().add(hostGameLayout);

        mainPane.getChildren().add(hostGameSubScene);
    }

    public void createJoinGameSubScene() {
        joinGameSubScene = new Subscene(500,500,1300,230);

        VBox joinGameLayout = new VBox( 20);
        joinGameLayout.setLayoutX(40);
        joinGameLayout.setAlignment( Pos.CENTER);

        //Label
        InfoLabel enterGameIdLabel = new InfoLabel("Please Enter the Game ID:",400, 150, 25,"Verdana");

        //TextField
        TextField gameIDTxtField = new TextField();
        gameIDTxtField.setPrefWidth(200);
        gameIDTxtField.setPrefHeight(50);

        StyledButton joinGameButton = new StyledButton("Join");
        AtomicBoolean joinSuccessful = new AtomicBoolean(false);
        joinGameButton.setOnAction( e -> {
            try {
                connectToServer(false, gameIDTxtField.getText());
                int checkResponse = csc.dataIn.readInt();
                switch (checkResponse) {
                    case 0:
                        joinSuccessful.set(false);
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Unable to join game!");
                        alert.setHeaderText(null);
                        alert.setContentText("Game with such ID does not exist!");
                        try {
                            csc.dataOut.flush();
                        } catch (IOException ex) {
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
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        alert2.showAndWait();
                        break;
                    case 2:
                        joinSuccessful.set(true);
                        csc.continueConnection();
                        //window.setScene(classSelect);
                        break;
                    case 3:
                        joinSuccessful.set(false);
                        Alert alert3 = new Alert(Alert.AlertType.INFORMATION);
                        alert3.setTitle("Unable to join game!");
                        alert3.setHeaderText(null);
                        alert3.setContentText("Game is full!");
                        try {
                            csc.dataOut.flush();
                        } catch (IOException ex) {
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
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        alert4.showAndWait();
                        break;
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if (joinSuccessful.get()) {
                startGameButton.setVisible(false);
                gameID.setText("Game ID: " + String.valueOf(gameIDTxtField.getText()));
                try {
                    try {
                        int update = csc.dataIn.readInt();
                        classes = (ArrayList<String>) (csc.dataIn.readObject());
                        playerList.getItems().setAll(classes);
                        csc.playerID = csc.dataIn.readInt();
                        playerList.getItems().set(csc.playerID - 1, playerList.getItems().get(csc.playerID - 1) + " [YOU]");
                    } catch (OptionalDataException ex) {
                        System.out.println(ex.length);
                    }
                } catch (IOException | ClassNotFoundException exception) {
                    exception.printStackTrace();
                }
                lobbySubScene.rightFloatSubScene();
                Thread t = new Thread(new Runnable() {
                    public void run() {
                        try {
                            // HERE
                            while (!gameStarted) {
                                int hostCommand = csc.dataIn.readInt();
                                System.out.println("HOST COMMAND IS " + hostCommand);
                                if (hostCommand == 0) {
                                    csc.dataOut.writeInt(0);
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            gameStarted = true;
                                            // CREATE GAMEVIEWMANAGER HERE
                                            GameViewManager game = new GameViewManager(csc);
                                            game.startReceivingTurns();
                                            mainStage.hide();
                                            game.enterGame();
                                            return;
                                        }
                                    });
                                    return;
                                } else if (hostCommand == 1) {
                                    return;
                                } else if (hostCommand == 2) {
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            lobbySubScene.rightFloatSubScene();
                                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                            alert.setTitle("Lobby disbanded!");
                                            alert.setHeaderText(null);
                                            alert.setContentText("Host left the lobby!");
                                            try {
                                                csc.dataOut.flush();
                                            } catch (IOException ex) {
                                                ex.printStackTrace();
                                            }
                                            alert.showAndWait();
                                            return;
                                        }
                                    });
                                    return;
                                } else if (hostCommand == 3) {
                                    try {
                                        classes = (ArrayList<String>) (csc.dataIn.readObject());
                                        csc.playerID = csc.dataIn.readInt();
                                        Platform.runLater(new Runnable() {
                                            @Override
                                            public void run() {
                                                playerList.getItems().setAll(classes);
                                                playerList.getItems().set(csc.playerID - 1, playerList.getItems().get(csc.playerID - 1) + " [YOU]");
                                                return;
                                            }
                                        });
                                    } catch (IOException | ClassNotFoundException exception) {
                                        exception.printStackTrace();
                                    }
                                }
                            }
                            return;
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                });
                t.start();
            }
        });

        joinGameLayout.getChildren().addAll( enterGameIdLabel, gameIDTxtField, joinGameButton);
        joinGameSubScene.getPane().getChildren().add(joinGameLayout);
        mainPane.getChildren().add(joinGameSubScene);
    }

    public Stage getMainStage() {
        return mainStage;
    }

    public void createHostGameButton(){
        StyledButton hostGameButton = new StyledButton("Host Game");
        hostGameButton.setLayoutX(startingXCoordinate);
        hostGameButton.setLayoutY(startingYCoordinate);

        hostGameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                hostGameSubScene.rightFloatSubScene();
            }
        });

        mainPane.getChildren().add(hostGameButton);
    }

    public void createJoinGameButton(){
        StyledButton joinGameButton = new StyledButton("Join Game");
        joinGameButton.setLayoutX(startingXCoordinate);
        joinGameButton.setLayoutY(startingYCoordinate + marginY);

        joinGameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                joinGameSubScene.rightFloatSubScene();
            }
        });

        mainPane.getChildren().add(joinGameButton);
    }

    public void createTutorialButton(){
        StyledButton tutorialButton = new StyledButton("Tutorial");
        tutorialButton.setLayoutX(startingXCoordinate);
        tutorialButton.setLayoutY(startingYCoordinate + 2*marginY);

        tutorialButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                tutorialSubScene.leftFloatSubScene();
            }
        });

        mainPane.getChildren().add(tutorialButton);
    }

    public void createSettingsButton(){
        StyledButton settingsButton = new StyledButton("Settings");
        settingsButton.setLayoutX(startingXCoordinate);
        settingsButton.setLayoutY(startingYCoordinate + 3*marginY);

        settingsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                settingsSubScene.leftFloatSubScene();
            }
        });

        mainPane.getChildren().add(settingsButton);
    }

    public void createCreditsButton(){
        StyledButton creditsButton = new StyledButton("Credits");
        creditsButton.setLayoutX(startingXCoordinate);
        creditsButton.setLayoutY(startingYCoordinate + 4*marginY);

        creditsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                creditsSubScene.leftFloatSubScene();
            }
        });

        mainPane.getChildren().add(creditsButton);
    }

    public void createExitButton(){
        StyledButton exitButton = new StyledButton("Exit");
        exitButton.setLayoutX(startingXCoordinate);
        exitButton.setLayoutY(startingYCoordinate + 5*marginY);

        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mainStage.close();
            }
        });

        mainPane.getChildren().add(exitButton);
    }
    public void createMainMenuButtons() {
        createHostGameButton();
        createJoinGameButton();
        createTutorialButton();
        createSettingsButton();
        createCreditsButton();
        createExitButton();
    }
    public void createBackground() {
        Image image = new Image("/model/wooden_background.png", 256, 256, false, true);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
        mainPane.setBackground(new Background(backgroundImage));
    }

    public void createLogo() {
        Image image = new Image("/model/serious_logo.png", 538, 125, false, true);
        ImageView logo = new ImageView(image);
        logo.setLayoutX(381);
        logo.setLayoutY(100);
        //for troll layout
        /*
        Image image = new Image("/model/troll_logo.png", 650, 375, false, true);
        ImageView logo = new ImageView(image);
        logo.setLayoutX(325);
        logo.setLayoutY(0);
        */
        mainPane.getChildren().add(logo);
    }

    public void connectToServer(boolean isHost, String enteredGameID) {
        csc = new ClientSideConnection(isHost, enteredGameID);
    }


}
