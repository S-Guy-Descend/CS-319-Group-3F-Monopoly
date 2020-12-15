import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    private ServerSocket ss;
    private int numPlayers;
    private int maxPlayers;
    private ArrayList<ServerSideConnection> connections = new ArrayList<ServerSideConnection>();

    public Server(int maxPlayers) {
        this.maxPlayers = maxPlayers;
        System.out.println("Server");
        numPlayers = 0;
        try {
            ss = new ServerSocket(12345);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void acceptConnections() {
        try {
            System.out.println("Waiting for connections");
            while( numPlayers < maxPlayers) {
                Socket s = ss.accept();
                numPlayers++;
                System.out.println("Player # " + numPlayers + " has connected");
                ServerSideConnection ssc = new ServerSideConnection(s, numPlayers);
                connections.add(ssc);

                Thread t = new Thread(ssc);
                t.start();
            }
            System.out.println("We now have " + maxPlayers + " players");
        } catch( IOException e) {
            e.printStackTrace();
        }
    }

    private class ServerSideConnection implements Runnable {
        private Socket socket;
        private DataInputStream dataIn;
        private DataOutputStream dataOut;
        private int playerID;

        public ServerSideConnection(Socket s, int id) {
            socket = s;
            playerID = id;
            try {
                dataIn = new DataInputStream(socket.getInputStream());
                dataOut = new DataOutputStream(socket.getOutputStream());
            } catch(IOException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void run() {
            try {
                dataOut.writeInt(playerID);
                dataOut.flush();


                while(true) {

                }
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Server server = new Server(4);
        server.acceptConnections();
    }
}
