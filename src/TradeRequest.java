import java.io.Serializable;
import java.util.ArrayList;

public class TradeRequest implements Serializable {
    public ArrayList<Square> placesToGive;
    public ArrayList<Square> placesToTake;
    public int moneyToGive;
    public int moneyToTake;
    public int tradeReceiver;

    public TradeRequest(ArrayList<Square> placesToGive, ArrayList<Square> placesToTake, int moneyToGive, int moneyToTake, int tradeReceiver) {
        this.placesToGive = placesToGive;
        this.placesToTake = placesToTake;
        this.moneyToGive = moneyToGive;
        this.moneyToTake = moneyToTake;
        this.tradeReceiver = tradeReceiver;
    }
}
