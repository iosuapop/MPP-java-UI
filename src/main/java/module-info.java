module donation.donations {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.logging.log4j;
    requires java.sql;


    opens donations.controllers to javafx.fxml;
    exports donations.controllers;

    opens donations.repository.implementations to javafx.fxml;
    exports donations.repository.implementations;

    opens donations.domain to javafx.fxml;
    exports donations.domain;

    opens donations.service to javafx.fxml;
    exports donations.service;

    opens donations.utils to javafx.fxml;
    exports donations.utils;

    opens donations to javafx.fxml;
    exports donations;
}