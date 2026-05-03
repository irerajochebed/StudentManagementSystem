module com.studentms {
    requires javafx.controls;
    requires javafx.fxml;

    // backend packages
    exports com.studentms.backend.model;
    exports com.studentms.backend.manager;
    exports com.studentms.backend.data;
    exports com.studentms.backend.auth;
    exports com.studentms.backend.exception;

    // ui package — opened to javafx.fxml for reflection
    opens com.studentms.ui.controller to javafx.fxml;
    exports com.studentms.ui.controller;

    // root
    exports com.studentms;
}
