import javafx.animation.TranslateTransition;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.util.Duration;

public class Subscene extends SubScene{

    private boolean isHidden;

    public Subscene(int height, int width, int startingXCoordinate, int startingYCoordinate) {
        super(new AnchorPane(), width,height);
        prefWidth(height);
        prefHeight(width);

        isHidden = true;

        Image image = new Image("/model/yellow_panel.png",width,height,false, true);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,null);

        AnchorPane root = (AnchorPane) this.getRoot();
        root.setBackground(new Background(backgroundImage));

        setLayoutX(startingXCoordinate);
        setLayoutY(startingYCoordinate);
    }

    public void leftFloatSubScene() {
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(0.3));
        transition.setNode(this);

        if(isHidden) {
            transition.setToX(570);
            isHidden = false;
        }
        else{
            transition.setToX(0);
            isHidden = true;
        }

        transition.play();
    }

    public void rightFloatSubScene() {
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(0.3));
        transition.setNode(this);

        if(isHidden) {
            transition.setToX(-(this.getWidth() + 20));
            isHidden = false;
        }
        else{
            transition.setToX(0);
            isHidden = true;
        }

        transition.play();
    }

    public AnchorPane getPane() {
        return (AnchorPane) this.getRoot();
    }
}
