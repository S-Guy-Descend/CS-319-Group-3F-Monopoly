import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.image.ImageView;

public class classPickerBox extends VBox {

    private static String filledCircleUrl = "";
    private static String unfilledCircleUrl = "";

    private ImageView circleImage;
    private ImageView classImage;

    private boolean isClassChosen;

    enum gameClasses {
        TRAVELER(""),
        NOBLE(""),
        KNIGHT(""),
        TREASUREHUNTER(""),
        WIZARD(""),
        FORTUNETELLER(""),
        THIEF(""),
        BUILDER(""),
        CARDINAL("");

        private String classImageUrl;

        private gameClasses(String classImageUrl){
            this.classImageUrl = classImageUrl;
        }

        String getUrl(){
            return this.classImageUrl;
        }
    }

    private gameClasses gameClass;

    public classPickerBox(gameClasses gameClass) {
        circleImage = new ImageView(unfilledCircleUrl);
        classImage = new ImageView(gameClass.getUrl());
        this.gameClass = gameClass;
        isClassChosen = false;
        this.setAlignment(Pos.CENTER);
        this.setSpacing(20);
        this.getChildren().add(circleImage);
        this.getChildren().add(classImage);
    }

    public gameClasses getGameClass(){
        return gameClass;
    }

    public boolean getIsClassChosen(){
        return isClassChosen;
    }

    public void setClassChosen(){
        isClassChosen = true;
        circleImage.setImage(new Image(filledCircleUrl));
    }

    public void setClassUnchosen(){
        isClassChosen = false;
        circleImage.setImage(new Image(unfilledCircleUrl));
    }
}
