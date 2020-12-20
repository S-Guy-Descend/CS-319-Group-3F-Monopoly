import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientSideConnection {
    public Socket socket;
    public ObjectInputStream dataIn;
    public ObjectOutputStream dataOut;
    public boolean isHost;
    public int gameID;
    public int playerID;
    public boolean isTurn;

    public ClientSideConnection(boolean isHost, String enteredGameID) {
        try {
            socket = new Socket("localhost", 12345);
            dataOut = new ObjectOutputStream(socket.getOutputStream());
            dataOut.flush();
            dataIn = new ObjectInputStream(socket.getInputStream());
            if (!isHost) {
                dataOut.writeBoolean(false);
                dataOut.writeUTF(enteredGameID);
                dataOut.flush();
            } else {
                dataOut.writeBoolean(true);
                dataOut.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void continueConnection() {
        try {
            playerID = dataIn.readInt();
            isTurn = dataIn.readBoolean();
            System.out.println("Connected to server as player " + playerID);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}