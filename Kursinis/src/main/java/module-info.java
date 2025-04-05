module com.javacourse.courseprojectfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires mysql.connector.j;
    requires java.sql;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires java.naming;
    requires org.kordamp.bootstrapfx.core;
    requires jbcrypt;

    opens org.example.kursinis to javafx.fxml;
    exports org.example.kursinis;
    opens org.example.kursinis.fxControllers to javafx.fxml;
    opens org.example.kursinis.fxControllers.tableParameters to javafx.fxml, javafx.base;
    exports org.example.kursinis.fxControllers.tableParameters to javafx.fxml, java.base;
    opens org.example.kursinis.model to javafx.fxml, org.hibernate.orm.core, jakarta.persistence;
    exports org.example.kursinis.model to javafx.fxml, org.hibernate.orm.core, jakarta.persistence;
    exports org.example.kursinis.fxControllers;
}