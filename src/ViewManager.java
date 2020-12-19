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

    private AnchorPane mainPane;
    private Scene mainScene;
    private Stage mainStage;

    public ViewManager() {
        mainPane = new AnchorPane();
        mainScene = new Scene(mainPane, WIDTH, HEIGHT);
        mainStage = new Stage();
        mainStage.setScene(mainScene);
        createButtons();
        createBackground();
        createLogo();
    }

    public Stage getMainStage() {
        return mainStage;
    }

    public void createButtons() {
        StyledButton join = new StyledButton("Join");

        mainPane.getChildren().add(join);

        join.setLayoutX(555);
        join.setLayoutY(375);
    }
    public void createBackground() {
        /*
        Image image = new Image("/model/troll_logo.png", 650, 375, false, true);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, new BackgroundPosition(Side.LEFT,325, false, Side.TOP, 80,false), null);
         */
        Image image = new Image("/model/wooden_background.png", 256, 256, false, true);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
        mainPane.setBackground(new Background(backgroundImage));
    }

    public void createLogo() {
        Image image = new Image("/model/troll_logo.png", 650, 375, false, true);
        ImageView logo = new ImageView(image);
        logo.setLayoutX(325);
        logo.setLayoutY(80);
        mainPane.getChildren().add(logo);
    }

}
