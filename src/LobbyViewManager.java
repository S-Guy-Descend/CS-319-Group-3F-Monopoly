import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LobbyViewManager {
    private final static int LOBBY_WIDTH = 1500;
    private final static int LOBBY_HEIGHT = 1500;

    private AnchorPane lobbyPane;
    private Scene lobbyScene;
    private Stage lobbyStage;

    public LobbyViewManager() {
        initializeLobby();
    }

    private void initializeLobby() {
        lobbyPane = new AnchorPane();
        lobbyScene = new Scene(lobbyPane, LOBBY_WIDTH, LOBBY_HEIGHT);
        lobbyStage = new Stage();
        lobbyStage.setScene(lobbyScene);
    }

    public void enterLobby(){
        lobbyStage.show();
    }
}
