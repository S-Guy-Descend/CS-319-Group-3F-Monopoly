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
    private boolean isTurn;

    // placeholder UI
    private Container contentPane;
    private JButton rollDice;
    private JButton endTurn;
    private JButton build;
    private JButton useScroll;
    private JButton buyProperty;
    private JButton sendTrade;
    private JButton acceptTrade;
    private JButton declineTrade;

    public Player() {
        contentPane = this.getContentPane();

        rollDice = new JButton("Roll Dice");
        build = new JButton("Build");
        useScroll = new JButton("Use Scroll");
        buyProperty = new JButton("Buy Property");
        sendTrade = new JButton("Send Trade");
        acceptTrade = new JButton("Accept Trade");
        declineTrade = new JButton("Decline Trade");
        endTurn = new JButton("End Turn");

        this.setSize(1400, 100);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        contentPane.setLayout(new GridLayout(1,7));

        contentPane.add(rollDice);     // 0
        contentPane.add(build);        // 1
        contentPane.add(useScroll);    // 2
        contentPane.add(buyProperty);  // 3
        contentPane.add(sendTrade);    // 4
        contentPane.add(acceptTrade);  // 5
        contentPane.add(declineTrade); // 6
        contentPane.add(endTurn);      // 7

        this.setVisible(true);

        // Roll dice action listener
        ActionListener rollDiceAL = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if(isTurn) {
                    JButton b = (JButton) ae.getSource();
                    b.setEnabled(false);
                    rollDice.setEnabled(false);
                    build.setEnabled(true);
                    useScroll.setEnabled(true);
                    buyProperty.setEnabled(true);
                    sendTrade.setEnabled(true);
                    acceptTrade.setEnabled(true);
                    declineTrade.setEnabled(true);
                    endTurn.setEnabled(true);
                    try {
                        System.out.println("Player " + playerID + " rolled dice");
                        csc.dataOut.writeInt(0);
                    } catch(IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        rollDice.addActionListener(rollDiceAL);

        // Build action listener
        ActionListener buildAL = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if(isTurn) {
                    JButton b = (JButton) ae.getSource();
                    b.setEnabled(false);
                    try {
                        System.out.println("Player " + playerID + " built");
                        csc.dataOut.writeInt(1);
                    } catch(IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        build.addActionListener(buildAL);

        // Use scroll action listener
        ActionListener useScrollAL = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if(isTurn) {
                    JButton b = (JButton) ae.getSource();
                    b.setEnabled(false);
                    try {
                        System.out.println("Player " + playerID + " used scroll");
                        csc.dataOut.writeInt(2);
                    } catch(IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        useScroll.addActionListener(useScrollAL);

        // Buy property action listener
        ActionListener buyPropertyAL = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if(isTurn) {
                    JButton b = (JButton) ae.getSource();
                    b.setEnabled(false);
                    try {
                        System.out.println("Player " + playerID + " bought property");
                        csc.dataOut.writeInt(3);
                    } catch(IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        buyProperty.addActionListener(buyPropertyAL);

        // Send trade action listener
        ActionListener sendTradeAL = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if(isTurn) {
                    JButton b = (JButton) ae.getSource();
                    b.setEnabled(false);
                    try {
                        System.out.println("Player " + playerID + " sent trade");
                        csc.dataOut.writeInt(4);
                    } catch(IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        sendTrade.addActionListener(sendTradeAL);

        // Accept trade action listener
        ActionListener acceptTradeAL = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if(isTurn) {
                    JButton b = (JButton) ae.getSource();
                    b.setEnabled(false);
                    declineTrade.setEnabled(false);
                    try {
                        System.out.println("Player " + playerID + " accepted trade");
                        csc.dataOut.writeInt(5);
                    } catch(IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        acceptTrade.addActionListener(acceptTradeAL);

        // Decline trade action listener
        ActionListener declineTradeAL = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if(isTurn) {
                    JButton b = (JButton) ae.getSource();
                    b.setEnabled(false);
                    acceptTrade.setEnabled(false);
                    try {
                        System.out.println("Player " + playerID + " declined trade");
                        csc.dataOut.writeInt(6);
                    } catch(IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        declineTrade.addActionListener(declineTradeAL);

        // End turn action listener
        ActionListener endTurnAL = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if(isTurn) {
                    JButton b = (JButton) ae.getSource();
                    rollDice.setEnabled(false);
                    build.setEnabled(false);
                    useScroll.setEnabled(false);
                    buyProperty.setEnabled(false);
                    sendTrade.setEnabled(false);
                    acceptTrade.setEnabled(false);
                    declineTrade.setEnabled(false);
                    endTurn.setEnabled(false);
                    try {
                        System.out.println("Player " + playerID + " ended Turn");
                        csc.dataOut.writeInt(7);
                    } catch(IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        endTurn.addActionListener(endTurnAL);
    }

    public void connectToServer() {
        csc = new ClientSideConnection();
        rollDice.setEnabled(isTurn);
        build.setEnabled(false);
        useScroll.setEnabled(false);
        buyProperty.setEnabled(false);
        sendTrade.setEnabled(false);
        acceptTrade.setEnabled(false);
        declineTrade.setEnabled(false);
        endTurn.setEnabled(false);
    }

    public void startReceivingTurns() {
        Thread t = new Thread( new Runnable() {
            public void run() {
                while(true) {
                    try {
                        isTurn = csc.dataIn.readBoolean();
                        System.out.println("Player " + playerID + " started Turn");
                        rollDice.setEnabled(isTurn);
                        build.setEnabled(false);
                        useScroll.setEnabled(false);
                        buyProperty.setEnabled(false);
                        sendTrade.setEnabled(false);
                        acceptTrade.setEnabled(false);
                        declineTrade.setEnabled(false);
                        endTurn.setEnabled(false);
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
                System.out.println("Connected to server as player " + playerID);
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
