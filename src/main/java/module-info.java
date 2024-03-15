module com.example.cmpt305project {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires eu.hansolo.tilesfx;

    opens com.example.cmpt305project to javafx.fxml;
    exports com.example.cmpt305project;
    exports com.example.cmpt305project.Controllers;
    opens com.example.cmpt305project.Controllers to javafx.fxml;
}