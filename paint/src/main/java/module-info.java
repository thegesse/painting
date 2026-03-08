module com.geese.paint {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.desktop;
    requires java.compiler;
    requires javafx.swing;

    opens com.geese.paint to javafx.fxml;
    opens com.geese.paint.Controller to javafx.fxml;
    exports com.geese.paint;
}