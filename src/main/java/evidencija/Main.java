package evidencija;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class  Main extends Application {

    @Override
    public void start(Stage window) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
        window.setTitle("Evidencija vožnje");


        window.setScene(new Scene(root, 500, 475));

        window.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
