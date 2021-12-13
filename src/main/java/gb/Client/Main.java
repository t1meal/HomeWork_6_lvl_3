package gb.Client;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/client.fxml"));
        stage.setTitle("Chat_chat");
        stage.setScene(new Scene(root, 400, 600));
        stage.show();
        stage.setOnCloseRequest(event -> {
            System.out.println("Stage is closing");
            stage.close();
            System.exit(1);

        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
