import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.UnknownHostException;

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

    private class ServerSideConnection implements Runnable{
        private Socket socket;
        private ObjectInputStream dataIn;
        private ObjectOutputStream dataOut;
        private int playerID;
        volatile boolean isTurn;

        public ServerSideConnection(Socket s, int id) {
            socket = s;
            playerID = id;
            try {
                dataIn = new ObjectInputStream(socket.getInputStream());
                dataOut = new ObjectOutputStream(socket.getOutputStream());
                dataOut.flush();
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
                                System.out.println("Player " + playerID + " rolled dice");
                                break;
                            case 1:
                                System.out.println("Player " + playerID + " built");
                                break;
                            case 2:
                                System.out.println("Player " + playerID + " built");
                                break;
                            case 3:
                                System.out.println("Player " + playerID + " used scroll");
                                break;
                            case 4:
                                System.out.println("Player " + playerID + " sent trade request");
                                try {
                                    try {
                                        TradeRequest tradeRequest = (TradeRequest) dataIn.readObject();
                                    } catch(IOException e2) {
                                        e2.printStackTrace();
                                    }
                                } catch( ClassNotFoundException e1) {
                                    e1.printStackTrace();
                                }
                                break;
                            case 5:
                                System.out.println("Player " + playerID + " accepted trade request");
                                break;
                            case 6:
                                System.out.println("Player " + playerID + " rejected trade request");
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
                                connections.get(nextPlayer - 1).dataOut.flush();
                                break;
                        }
                    }
                }
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws ClassNotFoundException{
        Server server = new Server(4);
        server.acceptConnections();
    }
}
