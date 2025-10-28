import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Start extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        //DBConnection.init();

        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/login.fxml"))));
        stage.show();
    }
}
