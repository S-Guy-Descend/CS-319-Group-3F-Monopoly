import java.io.Serializable;
import java.util.ArrayList;

public class ColorGroup implements Serializable {

    String name;
    ArrayList<Town> towns = new ArrayList<Town>();

    ColorGroup (String name) {
        this.name = name;
    }

    public void addTownToColorGroup (Town town) {
        towns.add(town);
    }

    public ArrayList<Town> getAllTownsOfColorGroup () {
        return towns;
    }
}
