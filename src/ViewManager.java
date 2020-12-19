import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.Scene;
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
        creditsSubScene = new Subscene();
        mainPane.getChildren().add(creditsSubScene);
    }

    public Stage getMainStage() {
        return mainStage;
    }

    public void createHostGameButton(){
        StyledButton hostGameButton = new StyledButton("Host Game");
        hostGameButton.setLayoutX(startingXCoordinate);
        hostGameButton.setLayoutY(startingYCoordinate);

        mainPane.getChildren().add(hostGameButton);
    }

    public void createJoinGameButton(){
        StyledButton joinGameButton = new StyledButton("Join Game");
        joinGameButton.setLayoutX(startingXCoordinate);
        joinGameButton.setLayoutY(startingYCoordinate + marginY);

        mainPane.getChildren().add(joinGameButton);
    }

    public void createTutorialButton(){
        StyledButton tutorialButton = new StyledButton("Tutorial");
        tutorialButton.setLayoutX(startingXCoordinate);
        tutorialButton.setLayoutY(startingYCoordinate + 2*marginY);

        mainPane.getChildren().add(tutorialButton);
    }

    public void createSettingsButton(){
        StyledButton settingsButton = new StyledButton("Settings");
        settingsButton.setLayoutX(startingXCoordinate);
        settingsButton.setLayoutY(startingYCoordinate + 3*marginY);

        mainPane.getChildren().add(settingsButton);
    }

    public void createCreditsButton(){
        StyledButton creditsButton = new StyledButton("Credits");
        creditsButton.setLayoutX(startingXCoordinate);
        creditsButton.setLayoutY(startingYCoordinate + 4*marginY);

        creditsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                creditsSubScene.floatSubScene();
            }
        });

        mainPane.getChildren().add(creditsButton);
    }

    public void createExitButton(){
        StyledButton exitButton = new StyledButton("Exit");
        exitButton.setLayoutX(startingXCoordinate);
        exitButton.setLayoutY(startingYCoordinate + 5*marginY);

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
