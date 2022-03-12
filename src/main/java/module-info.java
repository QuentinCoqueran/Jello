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

    opens fr.pa3al2g3.esgi.jello to javafx.fxml;
    exports fr.pa3al2g3.esgi.jello;
    exports fr.pa3al2g3.esgi.jello.enu;
    opens fr.pa3al2g3.esgi.jello.enu to javafx.fxml;
}