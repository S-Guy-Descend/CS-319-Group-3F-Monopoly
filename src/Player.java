import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sun.awt.windows.ThemeReader;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

public class Player extends Application {

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {

        try {
            ViewManager viewManager = new ViewManager();
            primaryStage = viewManager.getMainStage();
            primaryStage.setTitle("Scrolls of Estatia");

            primaryStage.show();
        }
        catch(Exception e){

        }


        /*

        // inGame Ekranı
        rollDice = new Button();
        rollDice.setText("Roll Dice");
        rollDice.setOnAction(this);

        build = new Button();
        build.setText("Build");
        build.setOnAction(this);

        useScroll = new Button();
        useScroll.setText("Use Scroll");
        useScroll.setOnAction(this);

        buyProperty = new Button();
        buyProperty.setText("Buy Property");
        buyProperty.setOnAction(this);

        sendTrade = new Button();
        sendTrade.setText("Send Trade Request"); // bu listed olacak
        sendTrade.setOnAction(this);

        acceptTrade = new Button();
        acceptTrade.setText("Accept Trade Request");
        acceptTrade.setOnAction(this);

        declineTrade = new Button();
        declineTrade.setText("Decline Trade Request");
        declineTrade.setOnAction(this);

        endTurn = new Button();
        endTurn.setText("End Turn");
        endTurn.setOnAction(this);


        // layout ayarları
        VBox inGameLayout = new VBox(20);
        inGameLayout.getChildren().addAll(rollDice, build, useScroll, buyProperty, sendTrade, acceptTrade, declineTrade, endTurn);
        inGame = new Scene(inGameLayout, 500, 500);
    }

    // kullanıcı butona tıklayınca bu çağırılıyor
    @Override
    public void handle(ActionEvent event) {

        // hangi buton ne yapıyor kontrol etmek için
        if (event.getSource() == rollDice) {
            if (csc.isTurn) {
                rollDice.setDisable(true);
                build.setDisable(false);
                useScroll.setDisable(false);
                buyProperty.setDisable(false);
                sendTrade.setDisable(false);
                acceptTrade.setDisable(false);
                declineTrade.setDisable(false);
                endTurn.setDisable(false);
                try {
                    System.out.println("Player " + csc.playerID + " rolled dice");
                    csc.dataOut.writeInt(0);
                    csc.dataOut.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (event.getSource() == build) {
            if (csc.isTurn) {
                build.setDisable(true);
                try {
                    System.out.println("Player " + csc.playerID + " built");
                    csc.dataOut.writeInt(1);
                    csc.dataOut.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (event.getSource() == useScroll) {
            if (csc.isTurn) {
                useScroll.setDisable(true);
                try {
                    System.out.println("Player " + csc.playerID + " used scroll");
                    csc.dataOut.writeInt(2);
                    csc.dataOut.flush();

                    // GET PLAYER TO CHOOSE A SCROLL AND VICTIM
                    int scrollIndex = 0;
                    int victimID = 0;

                    csc.dataOut.writeInt(scrollIndex);
                    csc.dataOut.flush();
                    csc.dataOut.writeInt(victimID);
                    csc.dataOut.flush();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (event.getSource() == buyProperty) {
            if (csc.isTurn) {
                buyProperty.setDisable(true);
                try {
                    System.out.println("Player " + csc.playerID + " bought property");
                    csc.dataOut.writeInt(3);
                    csc.dataOut.flush();
                    boolean purchaseSuccessful = csc.dataIn.readBoolean();
                    if (!purchaseSuccessful) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Unable to purchase land!");
                        alert.setHeaderText(null);
                        alert.setContentText("You cannot purchase this land!");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (event.getSource() == sendTrade) {
            if (csc.isTurn) {
                sendTrade.setDisable(true);
                try {
                    System.out.println("Player " + csc.playerID + " sent trade");

                    // TO-DO CONSTRUCT THE TRADE REQUEST PROPERLY
                    csc.dataOut.writeInt(4);
                    csc.dataOut.flush();

                    //Construct the trade request
                    ArrayList<Square> placesToGive = new ArrayList<Square>();
                    placesToGive.add(Game.instance.board.map[1]);
                    ArrayList<Square> placesToTake = new ArrayList<Square>();
                    placesToTake.add(Game.instance.board.map[3]);
                    int moneyToGive = 100;
                    int moneyToTake = 300;
                    int tradeReceiver = 0;
                    TradeRequest tradeRequest = new TradeRequest(placesToGive, placesToTake, moneyToGive, moneyToTake, tradeReceiver);

                    csc.dataOut.writeObject(tradeRequest);
                    csc.dataOut.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (event.getSource() == acceptTrade) {
            if (csc.isTurn) {
                acceptTrade.setDisable(true);
                declineTrade.setDisable(true);
                try {
                    System.out.println("Player " + csc.playerID + " accepted trade");
                    csc.dataOut.writeInt(5);
                    csc.dataOut.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (event.getSource() == declineTrade) {
            if (csc.isTurn) {
                declineTrade.setDisable(true);
                acceptTrade.setDisable(true);
                try {
                    System.out.println("Player " + csc.playerID + " declined trade");
                    csc.dataOut.writeInt(6);
                    csc.dataOut.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (event.getSource() == endTurn) {
            if (csc.isTurn) {
                rollDice.setDisable(true);
                build.setDisable(true);
                useScroll.setDisable(true);
                buyProperty.setDisable(true);
                sendTrade.setDisable(true);
                acceptTrade.setDisable(true);
                declineTrade.setDisable(true);
                endTurn.setDisable(true);
                try {
                    System.out.println("Player " + csc.playerID + " ended Turn");
                    csc.dataOut.writeInt(7);
                    csc.dataOut.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } */
    }


}