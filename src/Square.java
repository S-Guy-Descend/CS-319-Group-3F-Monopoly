import java.io.Serializable;
import java.util.ArrayList;

public class Square implements Serializable {
    //variables
    protected String name;
    ArrayList<Integer> tokensOnTop;

    //default constructor (Necessary for TradeRequest Serialization)
    public Square() {

    }

    //constructor
    public Square(String name) {
        this.name = name;
        this.tokensOnTop = new ArrayList<Integer>();
    }

    void addTokenOnSquare(int residentId) {
        tokensOnTop.add(residentId);
    }

    void removeTokenFromSquare(int residentId) {
        tokensOnTop.remove(Integer.valueOf(residentId));
    }
}