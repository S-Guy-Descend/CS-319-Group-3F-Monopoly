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
                if(numPlayers == 1) {
                    ssc.isTurn = true;
                } else {
                    ssc.isTurn = false;
                }
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
        volatile boolean isTurn;

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
                dataOut.writeBoolean(isTurn);
                dataOut.flush();

                while(true) {
                    if(isTurn) {
                        int operation = dataIn.readInt();
                        switch(operation) {
                            case 0:
                                break;
                            case 1:
                                break;
                            case 2:
                                break;
                            case 3:
                                break;
                            case 4:
                                break;
                            case 5:
                                break;
                            case 6:
                                break;
                            case 7:
                                System.out.println("Player " + playerID + " ended Turn");
                                isTurn = false;
                                int nextPlayer;
                                if(playerID < maxPlayers) {
                                    nextPlayer = playerID + 1;
                                } else {
                                    nextPlayer = 1;
                                }
                                connections.get(nextPlayer - 1).isTurn = true;
                                connections.get(nextPlayer - 1).dataOut.writeBoolean(true);
                                break;
                        }
                    }
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
