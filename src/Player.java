import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Player extends JFrame{
    private ClientSideConnection csc;
    private int playerID;
    private int otherPlayer;
    private boolean isTurn;

    // placeholder UI
    private Container contentPane;
    private JButton button;

    public Player() {
        contentPane = this.getContentPane();
        button = new JButton("End Turn");
        this.setSize(400, 400);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        contentPane.setLayout(new GridLayout(1,1));
        contentPane.add(button);
        this.setVisible(true);
        ActionListener al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if(isTurn) {
                    JButton b = (JButton) ae.getSource();
                    b.setEnabled(false);
                    try {
                        System.out.println("Player " + playerID + " ended Turn");
                        csc.dataOut.writeBoolean(true);
                    } catch(IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        };
        button.addActionListener(al);
    }

    public void connectToServer() {
        csc = new ClientSideConnection();
        button.setEnabled(isTurn);
    }

    public void startReceivingTurns() {
        Thread t = new Thread( new Runnable() {
            public void run() {
                while(true) {
                    try {
                        isTurn = csc.dataIn.readBoolean();
                        System.out.println("Player " + playerID + " started Turn");
                        button.setEnabled(isTurn);
                    } catch(IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        t.start();
    }

    //Client connection
    private class ClientSideConnection {
        private Socket socket;
        private DataInputStream dataIn;
        private DataOutputStream dataOut;

        private ClientSideConnection() {
            try {
                socket = new Socket("localhost", 12345);
                dataIn = new DataInputStream(socket.getInputStream());
                dataOut = new DataOutputStream(socket.getOutputStream());
                playerID = dataIn.readInt();
                setTitle("Player " + playerID);
                isTurn = dataIn.readBoolean();
                System.out.println("Connected to server as player #" + playerID);
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Player p = new Player();
        p.connectToServer();
        p.startReceivingTurns();
    }
}
