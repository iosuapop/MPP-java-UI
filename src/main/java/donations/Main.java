package donations;

import donations.controllers.Controller;
import donations.service.Service;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class Main extends Application {

    public Service service;
    public Stage stage;

    public static void main(String [] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        service = new Service();
        this.stage = primaryStage;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/LoginView.fxml"));
        primaryStage.setScene(new Scene(loader.load()));

        primaryStage.setTitle("Login");
        ((Controller)loader.getController()).set(service, stage);
        primaryStage.show();

        System.out.println("caca");
    }
}
