import java.io.IOException;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.UnknownHostException;

public class Server {
    private ServerSocket ss;
    volatile int numPlayers;
    volatile int maxPlayers;
    volatile ArrayList<ServerSideConnection> connections = new ArrayList<ServerSideConnection>();
    volatile boolean gameExists;
    volatile int serverGameID;
    volatile boolean gameStarted;
    volatile ArrayList<String> classes = new ArrayList<String>();

    public Server(int maxPlayers) {
        this.maxPlayers = maxPlayers;
        numPlayers = 0;
        gameExists = false;
        try {
            ss = new ServerSocket(12345);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void acceptConnections() {
        try {
            System.out.println("Waiting for connections");
            while (true) {
                Socket s = ss.accept();
                ServerSideConnection ssc = new ServerSideConnection(s, numPlayers);
                boolean wantsToBeHost = ssc.dataIn.readBoolean();
                try {
                    if (numPlayers != 0) {
                        int clientGameID = 0;
                        if (!wantsToBeHost) {
                            String clientGameIDString = ssc.dataIn.readUTF();
                            clientGameID = Integer.parseInt(clientGameIDString);
                        }
                        if (gameExists) {
                            if (clientGameID == serverGameID) {
                                if (!wantsToBeHost) {
                                    if (gameStarted) {
                                        ssc.dataOut.writeInt(4);
                                        ssc.dataOut.flush();
                                        s.close();
                                    } else {
                                        if (numPlayers < maxPlayers) {
                                            ssc.dataOut.writeInt(2);
                                            ssc.dataOut.flush();
                                            numPlayers++;
                                            System.out.println("Player # " + numPlayers + " has connected");
                                            ssc.playerID = numPlayers;
                                            ssc.dataOut.writeInt(ssc.playerID);
                                            ssc.dataOut.flush();
                                            ssc.isTurn = false;
                                            ssc.dataOut.writeBoolean(ssc.isTurn);
                                            ssc.dataOut.flush();
                                            connections.add(ssc);
                                            classes.add("Player " + ssc.playerID + " - Traveler (One-in-Two)");
                                            System.out.println(classes.size());
                                            for(int i = 0; i < connections.size(); i++) {
                                                if(i != 0) {
                                                    connections.get(i).dataOut.writeInt(3);
                                                    connections.get(i).dataOut.flush();
                                                }
                                                connections.get(i).dataOut.writeObject(classes);
                                                connections.get(i).dataOut.flush();
                                                connections.get(i).dataOut.reset();
                                                if(i != 0) {
                                                    connections.get(i).dataOut.writeInt(connections.get(i).playerID);
                                                }
                                                connections.get(i).dataOut.flush();
                                            }
                                            Thread t = new Thread(ssc);
                                            t.start();
                                        } else {
                                            ssc.dataOut.writeInt(3);
                                            ssc.dataOut.flush();
                                            s.close();
                                        }
                                    }
                                } else {
                                    ssc.dataOut.writeBoolean(true);
                                    ssc.dataOut.flush();
                                }
                            } else {
                                ssc.dataOut.writeInt(1);
                                ssc.dataOut.flush();
                                s.close();
                            }
                        } else {
                            ssc.dataOut.writeInt(0);
                            ssc.dataOut.flush();
                            s.close();
                        }
                    } else {
                        if (wantsToBeHost) {
                            ssc.dataOut.writeBoolean(true);
                            ssc.dataOut.flush();
                            maxPlayers = ssc.dataIn.readInt();
                            numPlayers++;
                            System.out.println("Player # " + numPlayers + " has connected");
                            ssc.playerID = numPlayers;
                            ssc.dataOut.writeInt(ssc.playerID);
                            ssc.dataOut.flush();
                            ssc.isTurn = true;
                            ssc.dataOut.writeBoolean(ssc.isTurn);
                            ssc.dataOut.flush();
                            ssc.isHost = true;
                            int gameID = (int) (Math.random() * 900000 - 1);
                            gameID = gameID + 100000;
                            ssc.dataOut.writeInt(gameID);
                            ssc.dataOut.flush();
                            gameExists = true;
                            serverGameID = gameID;
                            connections.add(ssc);
                            classes.add("Player " + ssc.playerID + " - Traveler (One-in-Two)");
                            for(int i = 0; i < connections.size(); i++) {
                                connections.get(i).dataOut.writeObject(classes);
                                connections.get(i).dataOut.flush();
                                connections.get(i).dataOut.reset();
                            }
                            Thread t = new Thread(ssc);
                            t.start();
                        } else {
                            ssc.dataOut.writeInt(0);
                            ssc.dataOut.flush();
                            s.close();
                        }
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class ServerSideConnection implements Runnable, Serializable {
        private Socket socket;
        private ObjectInputStream dataIn;
        private ObjectOutputStream dataOut;
        private int playerID;
        volatile boolean isTurn;
        volatile boolean isHost;

        public ServerSideConnection(Socket s, int id) {
            socket = s;
            playerID = id;
            try {
                dataIn = new ObjectInputStream(socket.getInputStream());
                dataOut = new ObjectOutputStream(socket.getOutputStream());
                dataOut.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void run() {
            try {
                if (isHost) {
                    while (true) {
                        int hostCommand = dataIn.readInt();
                        if (hostCommand == 0) {
                            if (numPlayers == maxPlayers) {
                                gameStarted = true;
                                dataOut.writeInt(3);
                                dataOut.flush();
                                dataOut.writeBoolean(true);
                                dataOut.flush();
                                for (int i = 1; i < maxPlayers; i++) {
                                    connections.get(i).dataOut.writeInt(0);
                                    connections.get(i).dataOut.flush();
                                }
                                break;
                            } else {
                                dataOut.writeInt(2);
                                dataOut.flush();
                                System.out.println("NOT ENOUGH PLAYERS!");
                            }
                        } else if (hostCommand == 1) {
                            System.out.println("HOST LEFT!");
                            System.out.println(connections.size() + " " + numPlayers);
                            for(int i = 1; i < connections.size(); i++) {
                                connections.get(i).dataOut.writeInt(2);
                                connections.get(i).dataOut.flush();
                                connections.get(i).socket.close();
                            }
                            connections.clear();
                            classes.clear();
                            gameExists = false;
                            numPlayers = 0;
                        } else {
                            //HOST CHANGED CLASS
                            switch(hostCommand) {
                                case 2:
                                    classes.set(playerID - 1, "Player " + playerID + " - Traveler (One-in-Two)");
                                    break;
                                case 3:
                                    classes.set(playerID - 1, "Player " + playerID + " - Traveler (Three-in-Five)");
                                    break;
                                case 4:
                                    classes.set(playerID - 1, "Player " + playerID + " - Noble");
                                    break;
                                case 5:
                                    classes.set(playerID - 1, "Player " + playerID + " - Knight");
                                    break;
                                case 6:
                                    classes.set(playerID - 1, "Player " + playerID + " - Treasure Hunter");
                                    break;
                                case 7:
                                    classes.set(playerID - 1, "Player " + playerID + " - Wizard");
                                    break;
                                case 8:
                                    classes.set(playerID - 1, "Player " + playerID + " - Fortune Teller");
                                    break;
                                case 9:
                                    classes.set(playerID - 1, "Player " + playerID + " - Thief");
                                    break;
                                case 10:
                                    classes.set(playerID - 1, "Player " + playerID + " - Builder");
                                    break;
                                case 11:
                                    classes.set(playerID - 1, "Player " + playerID + " - Cardinal");

                                    break;
                            }
                            for(int i = 0; i < connections.size(); i++) {
                                if(i != 0) {
                                    connections.get(i).dataOut.writeInt(3);
                                    connections.get(i).dataOut.flush();
                                }
                                connections.get(i).dataOut.writeObject(classes);
                                connections.get(i).dataOut.flush();
                                connections.get(i).dataOut.reset();
                                if(i != 0) {
                                    connections.get(i).dataOut.writeInt(connections.get(i).playerID);
                                }
                                connections.get(i).dataOut.flush();
                            }
                        }
                    }
                } else {
                    while (true) {
                        int command = dataIn.readInt();
                        if(command == 0) {
                            break;
                        }
                        if(command == 1) {
                            dataOut.writeInt(1);
                            dataOut.flush();
                            socket.close();
                            connections.remove(playerID - 1);
                            classes.remove(playerID - 1);
                            for (int i = 0; i < connections.size(); i++) {
                                connections.get(i).playerID = i + 1;
                                classes.set(i, classes.get(i).substring(0, 7) + connections.get(i).playerID + classes.get(i).substring(8));
                            }
                            numPlayers--;
                            for (int i = 0; i < connections.size(); i++) {
                                if (i != 0) {
                                    connections.get(i).dataOut.writeInt(3);
                                    connections.get(i).dataOut.flush();
                                }
                                connections.get(i).dataOut.writeObject(classes);
                                connections.get(i).dataOut.flush();
                                connections.get(i).dataOut.reset();
                                if (i != 0) {
                                    connections.get(i).dataOut.writeInt(connections.get(i).playerID);
                                }
                                connections.get(i).dataOut.flush();
                            }
                            break;
                        } else {
                            // NON-HOST CHANGED CLASS
                            switch (command) {
                                case 2:
                                    classes.set(playerID - 1, "Player " + playerID + " - Traveler (One-in-Two)");
                                    break;
                                case 3:
                                    classes.set(playerID - 1, "Player " + playerID + " - Traveler (Three-in-Five)");
                                    break;
                                case 4:
                                    classes.set(playerID - 1, "Player " + playerID + " - Noble");
                                    break;
                                case 5:
                                    classes.set(playerID - 1, "Player " + playerID + " - Knight");
                                    break;
                                case 6:
                                    classes.set(playerID - 1, "Player " + playerID + " - Treasure Hunter");
                                    break;
                                case 7:
                                    classes.set(playerID - 1, "Player " + playerID + " - Wizard");
                                    break;
                                case 8:
                                    classes.set(playerID - 1, "Player " + playerID + " - Fortune Teller");
                                    break;
                                case 9:
                                    classes.set(playerID - 1, "Player " + playerID + " - Thief");
                                    break;
                                case 10:
                                    classes.set(playerID - 1, "Player " + playerID + " - Builder");
                                    break;
                                case 11:
                                    classes.set(playerID - 1, "Player " + playerID + " - Cardinal");
                                    break;
                            }
                            for (int i = 0; i < connections.size(); i++) {
                                if (i != 0) {
                                    connections.get(i).dataOut.writeInt(3);
                                    connections.get(i).dataOut.flush();
                                }
                                connections.get(i).dataOut.writeObject(classes);
                                connections.get(i).dataOut.flush();
                                connections.get(i).dataOut.reset();
                                if (i != 0) {
                                    connections.get(i).dataOut.writeInt(connections.get(i).playerID);
                                }
                                connections.get(i).dataOut.flush();
                            }
                        }
                    }
                }

                while (true) {
                    if (isTurn) {
                        int operation = dataIn.readInt();
                        switch (operation) {
                            case 0:
                                System.out.println("Player " + playerID + " rolled dice");
                                break;
                            case 1:
                                System.out.println("Player " + playerID + " built");
                                break;
                            case 2:
                                System.out.println("Player " + playerID + " used scroll");
                                break;
                            case 3:
                                System.out.println("Player " + playerID + " bought property");
                                break;
                            case 4:
                                System.out.println("Player " + playerID + " sent trade request");
                                try {
                                    try {
                                        TradeRequest tradeRequest = (TradeRequest) dataIn.readObject();
                                    } catch (IOException e2) {
                                        e2.printStackTrace();
                                    }
                                } catch (ClassNotFoundException e1) {
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
                                if (playerID < maxPlayers) {
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
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Server server = new Server(2);
        server.acceptConnections();
    }
}
