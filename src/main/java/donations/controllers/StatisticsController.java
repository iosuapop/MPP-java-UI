package donations.controllers;

import donations.domain.Charity;
import donations.service.Service;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;
import java.util.List;

public class StatisticsController extends Controller {

    @FXML
    private TableView<Pair<Charity, Float>> tableView;

    @FXML
    private TableColumn<Pair<Charity, Float>, String> colDescription;

    @FXML
    private TableColumn<Pair<Charity, Float>, Float> colValue;

    @FXML
    private Text textErrorMessage;

    @FXML
    private Button buttonLogout;

    @FXML
    private Button buttonDonation;

    @Override
    public void set(Service service, Stage stage) {
        super.set(service, stage);
        loadCharityFonds();
    }

    @FXML
    void handleLogout(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/LoginView.fxml"));
        Parent root = loader.load();
        LoginController controller = loader.getController();
        controller.set(service, stage);
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    @FXML
    void handleMakeDonation(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/DonationView.fxml"));
        Parent root = loader.load();
        DonationController controller = loader.getController();
        controller.set(service, stage);
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    private void loadCharityFonds() {
        try {
            List<Pair<Charity, Float>> charityFonds = service.getCharityFonds();
            ObservableList<Pair<Charity, Float>> data = FXCollections.observableArrayList(charityFonds);
            colDescription.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getKey().getDescription()));
            colValue.setCellValueFactory(cellData -> {Pair<Charity, Float> pair = cellData.getValue();
                Float amount = pair.getValue();
                return new SimpleObjectProperty<>(amount);
            });
            tableView.setItems(data);
        } catch (Exception e) {
            if (textErrorMessage != null) {
                textErrorMessage.setText("Error loading charity funds: " + e.getMessage());
            }
        }
    }

}
