import javafx.animation.TranslateTransition;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.util.Duration;

public class Subscene extends SubScene{

    private boolean isHidden;

    public Subscene() {
        super(new AnchorPane(), 500,500);
        prefWidth(500);
        prefHeight(500);

        isHidden = true;

        Image image = new Image("/model/wooden_panel.png",500,250,false, true);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,null);

        AnchorPane root = (AnchorPane) this.getRoot();
        root.setBackground(new Background(backgroundImage));


        setLayoutX(-550);
        setLayoutY(230);
    }

    public void floatSubScene() {
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
}
