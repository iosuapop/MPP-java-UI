package donations.controllers;

import donations.service.Service;
import javafx.stage.Stage;

public class Controller {
    protected Service service;
    protected Stage stage;

    public void set(Service service, Stage stage){
        this.service = service;
        this.stage = stage;
    }
}
