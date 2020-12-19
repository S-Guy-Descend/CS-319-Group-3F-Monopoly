import javafx.scene.text.Font;
import javafx.scene.control.Label;

public class InfoLabel extends Label{
    int width;
    int height;
    public InfoLabel(String text, int width, int height, int fontSize, String fontFamily) {
        setPrefWidth(width);
        setPrefHeight(height);
        this.width =  width;
        this.height = height;
        setText(text);
        setWrapText(true);
        setFont(Font.font(fontFamily, fontSize));
    }
}