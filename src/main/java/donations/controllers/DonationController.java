package donations.controllers;

import donations.domain.Charity;
import donations.domain.Donation;
import donations.domain.Donor;
import donations.service.Service;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.List;

public class DonationController extends Controller {

    public Button buttonComplete;
    public Button buttonFind;
    @FXML
    private TableView<Donor> tableViewDonor;

    @FXML
    private TableColumn<Donor, String> colName;

    @FXML
    private TableColumn<Donor, String> colTelephone;

    @FXML
    private TableColumn<Donor, String> colAddress;

    @FXML
    private TextField textFieldPart;
    @FXML
    private TextField textFieldName;

    @FXML
    private TextField textFieldTelephone;

    @FXML
    private TextField textFieldAddress;

    @FXML
    private ComboBox<String> comboBoxCase;

    @FXML
    private TextField textFieldAmount;

    @FXML
    private Text textErrorMessage;

    @Override
    public void set(Service service, Stage stage) {
        super.set(service, stage);
        loadDonors("");
        caseAll();
    }

    @FXML
    private void caseAll(){
        List<String> caseCharity = service.getAllCase();
        ObservableList<String> caseOptions = FXCollections.observableArrayList(caseCharity);
        comboBoxCase.setItems(caseOptions);
    }

    @FXML
    void handleFind(ActionEvent event) {
        String part = textFieldPart.getText();
        loadDonors(part);
        addSelectionListenerToTableView();
    }

    @FXML
    void handleComplete(ActionEvent event) throws IOException {
        String name = textFieldName.getText();
        String telephone = textFieldTelephone.getText();
        String address = textFieldAddress.getText();
        String selectedCase = comboBoxCase.getValue();
        int amount = Integer.parseInt(textFieldAmount.getText());

        Donor donor = new Donor(0,name, telephone, address);
        service.add_Donor(donor);

        Charity charity = new Charity(0,selectedCase);
        charity = service.getIdByCase(charity);
        System.out.println(charity);


        Donation donation = new Donation(0, charity, donor, amount);
        service.add_Donation(donation);

        tableViewDonor.getItems().clear();
        textFieldName.clear();
        textFieldTelephone.clear();
        textFieldAddress.clear();
        comboBoxCase.getSelectionModel().clearSelection();
        textFieldAmount.clear();

        // aici intram inapoi in Statistics

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/StatisticsView.fxml"));
        Parent root = loader.load();
        this.stage.setScene(new Scene(root));
        this.stage.setTitle("Statistics");
        StatisticsController controller = loader.getController();
        controller.set(this.service, stage);
    }

    private void loadDonors(String part) {
        try
        {
            List<Donor> donors = service.getAllDonorsName(part);
            ObservableList<Donor> data = FXCollections.observableArrayList(donors);
            colName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
            colTelephone.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTelephone()));
            colAddress.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAdress()));

            tableViewDonor.setItems(data);
        } catch (Exception e) {
            if (textErrorMessage != null) {
                textErrorMessage.setText("Error loading charity funds: " + e.getMessage());
            }
        }
    }

    private void addSelectionListenerToTableView() {
        tableViewDonor.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Actualizăm valorile câmpurilor name, telephone și address cu valorile entității selectate
                textFieldName.setText(newValue.getName());
                textFieldTelephone.setText(newValue.getTelephone());
                textFieldAddress.setText(newValue.getAdress());
            }
        });
    }

}
