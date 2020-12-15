import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Player {
    private ClientSideConnection csc;
    private int playerID;
    private int otherPlayer;

    public Player() {
    }

    public void connectToServer() {
        csc = new ClientSideConnection();

    }

    //Client connection
    private class ClientSideConnection {
        private Socket socket;
        private DataInputStream dataIn;
        private DataOutputStream dataOut;

        private ClientSideConnection() {
            System.out.println("ClientSideConnection");
            try {
                socket = new Socket("localhost", 12345);
                dataIn = new DataInputStream(socket.getInputStream());
                dataOut = new DataOutputStream(socket.getOutputStream());
                playerID = dataIn.readInt();
                System.out.println("Connected to server as player #" + playerID);
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Player p = new Player();
        p.connectToServer();
    }
}
