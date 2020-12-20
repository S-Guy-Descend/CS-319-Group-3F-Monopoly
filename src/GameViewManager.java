import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class GameViewManager {
    private final static int GAME_WIDTH = 1024;
    private final static int GAME_HEIGHT = 1024;

    private AnchorPane gamePane;
    private Scene gameScene;
    private Stage gameStage;

    public GameViewManager() {
        initializeGame();
    }

    private void initializeGame() {
        gamePane = new AnchorPane();
        gameScene = new Scene(gamePane, GAME_WIDTH, GAME_HEIGHT);
        gameStage = new Stage();
        gameStage.setScene(gameScene);

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
}
