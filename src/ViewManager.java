import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.image.Image;

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

    public ViewManager() {
        mainPane = new AnchorPane();
        mainScene = new Scene(mainPane, WIDTH, HEIGHT);
        mainStage = new Stage();
        mainStage.setScene(mainScene);
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
    }

    public void createHostGameSubScene() {
        hostGameSubScene = new Subscene(500,500,1300,230);


        //joinGameButton.setOnAction(e -> { mainStage.setScene( inGame);});

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
        joinGameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                LobbyViewManager lobby = new LobbyViewManager();
                lobby.enterLobby();
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

}
