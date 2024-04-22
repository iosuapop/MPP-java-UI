package donations.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;

public class LoginController extends Controller {
    public Text textErrorMessage;
    @FXML
    private TextField textFieldUsername;

    @FXML
    private PasswordField textFieldPassword;

    @FXML
    private Button buttonLogin;


    @FXML
    void initialize(){
    }

    public void OnClickButtonLogin(ActionEvent actionEvent) throws IOException {
        if(this.service.login(textFieldUsername.getText(), textFieldPassword.getText())){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/StatisticsView.fxml"));
            Parent root = loader.load();
            this.stage.setScene(new Scene(root));
            this.stage.setTitle("Statistics");
            StatisticsController controller = loader.getController();
            controller.set(this.service, stage);
        }else{
            textErrorMessage.setText("Login failed");
        }
    }
}
