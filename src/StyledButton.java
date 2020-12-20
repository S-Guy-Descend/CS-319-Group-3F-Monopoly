import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;


public class StyledButton extends Button {
    final String BUTTON_STYLE_PRESSED = "-fx-background-color: transparent; -fx-background-image: url('/model/yellow_button.png'); -fx-opacity: 0.5";
    final String BUTTON_STYLE_RELEASED = "-fx-background-color: transparent; -fx-background-image: url('/model/yellow_button.png');";

    public StyledButton(String text) {
        setText(text);
        setButtonFont();
        setPrefHeight(49);
        setPrefWidth(190);
        setStyle(BUTTON_STYLE_RELEASED);
        addButtonListener();
    }

    public void setButtonFont() {
        setFont(Font.font("Verdana",23));
    }

    public void setButtonStylePressed() {
        setStyle(BUTTON_STYLE_PRESSED);
        setPrefHeight(45);
        setLayoutY(getLayoutY() + 4);
    }

    public void setButtonStyleReleased() {
        setStyle(BUTTON_STYLE_RELEASED);
        setPrefHeight(49);
        setLayoutY(getLayoutY() - 4);
    }

    public void addButtonListener(){
        setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getButton().equals(MouseButton.PRIMARY)){
                    setButtonStylePressed();
                }

            }
        });
        setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(event.getButton().equals(MouseButton.PRIMARY)){
                    setButtonStyleReleased();
                }

            }
        });
        setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setEffect(new DropShadow());
            }
        });
        setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setEffect(null);
            }
        });
    }
}
