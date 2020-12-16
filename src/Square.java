import java.util.ArrayList;

public class Square {
    //variables
    protected String name;
    ArrayList<Integer> tokensOnTop;

    //constructor
    public Square(String name, ArrayList<Integer> tokensOnTop) {
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
