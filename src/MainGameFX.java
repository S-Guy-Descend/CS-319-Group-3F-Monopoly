import javafx.application.Application;
import javafx.stage.Stage;


public class MainGameFX extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            ViewManager viewManager = new ViewManager();
            primaryStage = viewManager.getMainStage();
            primaryStage.show();
        }
        catch(Exception e){

        }
    }
}
