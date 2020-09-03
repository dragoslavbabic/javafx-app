module evidencija {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.prefs;
    requires com.fasterxml.jackson.annotation;

    opens evidencija to javafx.fxml;
    opens evidencija.model to javafx.base;
    opens evidencija.controller to javafx.fxml;


    exports evidencija;
}


