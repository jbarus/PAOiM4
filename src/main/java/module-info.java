module com.example.paoim4 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.naming;
    requires java.sql;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires com.opencsv;


    opens com.example.paoim4 to javafx.fxml;
    opens com.example.paoim4.back;
    opens com.example.paoim4.front;
    opens com.example.paoim4.utils;

    exports com.example.paoim4;
    exports com.example.paoim4.front;
    exports com.example.paoim4.back;
    exports com.example.paoim4.utils;
}