module fr.pa3al2g3.esgi.jello {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;

    opens fr.pa3al2g3.esgi.jello to javafx.fxml;
    exports fr.pa3al2g3.esgi.jello;
    exports fr.pa3al2g3.esgi.jello.enumerator;
    opens fr.pa3al2g3.esgi.jello.enumerator to javafx.fxml;
    exports fr.pa3al2g3.esgi.jello.model;
    opens fr.pa3al2g3.esgi.jello.model to javafx.fxml;
    exports fr.pa3al2g3.esgi.jello.controller;
    opens fr.pa3al2g3.esgi.jello.controller to javafx.fxml;
}