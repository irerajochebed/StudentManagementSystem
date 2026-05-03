package com.university.ui.controller;

import com.university.app.StudentManagementApp;
import com.university.backend.exception.StudentManagementException;
import com.university.backend.manager.StudentManagementManager;
import com.university.backend.model.Instructor;
import com.university.ui.component.StatusLabel;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * InstructorController — UI bridge for instructor operations.
 * UI only: captures input → calls backend → displays output.
 */
public class InstructorController implements Initializable {

    @FXML private TextField instructorIdField;
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField emailField;
    @FXML private Spinner<Integer> ageSpinner;
    @FXML private TextField specializationField;
    @FXML private TextField officeField;
    @FXML private TextField salaryField;

    @FXML private TableView<Instructor>            instructorTableView;
    @FXML private TableColumn<Instructor, String>  idColumn;
    @FXML private TableColumn<Instructor, String>  nameColumn;
    @FXML private TableColumn<Instructor, String>  emailColumn;
    @FXML private TableColumn<Instructor, Integer> ageColumn;
    @FXML private TableColumn<Instructor, String>  specializationColumn;
    @FXML private TableColumn<Instructor, String>  officeColumn;
    @FXML private TableColumn<Instructor, Integer> coursesColumn;

    @FXML private Label statusLabel;

    private StudentManagementManager manager;
    private StatusLabel status;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        manager = StudentManagementApp.getManager();
        status  = new StatusLabel(statusLabel);

        ageSpinner.setValueFactory(
            new SpinnerValueFactory.IntegerSpinnerValueFactory(20, 80, 35));

        idColumn.setCellValueFactory(new PropertyValueFactory<>("personId"));
        nameColumn.setCellValueFactory(d ->
            new SimpleStringProperty(d.getValue().getFullName()));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        specializationColumn.setCellValueFactory(new PropertyValueFactory<>("specialization"));
        officeColumn.setCellValueFactory(new PropertyValueFactory<>("officeLocation"));
        coursesColumn.setCellValueFactory(d ->
            new SimpleObjectProperty<>(d.getValue().getAssignedCourses().size()));

        refreshTable();
    }

    // ── ADD ───────────────────────────────────────────────────────
    @FXML
    private void handleAddInstructor() {
        String id    = instructorIdField.getText().trim();
        String first = firstNameField.getText().trim();
        String last  = lastNameField.getText().trim();
        String email = emailField.getText().trim();

        if (id.isEmpty() || first.isEmpty() || last.isEmpty() || email.isEmpty()) {
            status.error("ID, First Name, Last Name and Email are required.");
            return;
        }
        try {
            double salary = salaryField.getText().trim().isEmpty()
                ? 0.0 : Double.parseDouble(salaryField.getText().trim());

            Instructor ins = new Instructor(id, first, last, email,
                ageSpinner.getValue(),
                specializationField.getText().trim(),
                officeField.getText().trim(),
                salary);

            manager.addInstructor(ins);
            status.success("Instructor " + ins.getFullName() + " added!");
            handleClear();
            refreshTable();

        } catch (NumberFormatException e) {
            status.error("Salary must be a number.");
        } catch (StudentManagementException e) {
            status.error(e.getMessage());
        }
    }

    // ── REMOVE ───────────────────────────────────────────────────
    @FXML
    private void handleRemoveInstructor() {
        Instructor selected = instructorTableView.getSelectionModel().getSelectedItem();
        if (selected == null) { status.error("Select an instructor first."); return; }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
            "Remove " + selected.getFullName() + "?",
            ButtonType.YES, ButtonType.NO);
        confirm.setTitle("Confirm Remove");
        confirm.showAndWait().ifPresent(btn -> {
            if (btn == ButtonType.YES) {
                manager.removeInstructor(selected.getPersonId());
                Platform.runLater(() -> {
                    refreshTable();
                    status.success("Instructor removed.");
                });
            }
        });
    }

    // ── SORT by name ─────────────────────────────────────────────
    @FXML
    private void handleSortByName() {
        List<Instructor> list = new ArrayList<>(manager.getAllInstructors());
        for (int i = 0; i < list.size() - 1; i++)
            for (int j = 0; j < list.size() - 1 - i; j++)
                if (list.get(j).getFullName()
                        .compareTo(list.get(j + 1).getFullName()) > 0) {
                    Instructor t = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, t);
                }
        Platform.runLater(() -> {
            instructorTableView.setItems(FXCollections.observableArrayList(list));
            status.info("Sorted by name.");
        });
    }

    // ── VIEW DETAILS ─────────────────────────────────────────────
    @FXML
    private void handleViewDetails() {
        Instructor ins = instructorTableView.getSelectionModel().getSelectedItem();
        if (ins == null) { status.error("Select an instructor first."); return; }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Instructor Details");
        alert.setHeaderText(ins.getFullName());
        alert.setContentText(
            "ID             : " + ins.getPersonId()       + "\n" +
            "Email          : " + ins.getEmail()           + "\n" +
            "Age            : " + ins.getAge()             + "\n" +
            "Specialization : " + ins.getSpecialization()  + "\n" +
            "Office         : " + ins.getOfficeLocation()  + "\n" +
            "Salary         : $" + String.format("%.2f", ins.getSalary()) + "\n" +
            "Courses Taught : " + ins.getAssignedCourses().size()
        );
        alert.showAndWait();
    }

    @FXML
    private void handleClear() {
        instructorIdField.clear();
        firstNameField.clear();
        lastNameField.clear();
        emailField.clear();
        specializationField.clear();
        officeField.clear();
        salaryField.clear();
        ageSpinner.getValueFactory().setValue(35);
    }

    @FXML
    private void handleRefresh() {
        refreshTable();
        status.info("Instructor list refreshed.");
    }

    private void refreshTable() {
        Platform.runLater(() ->
            instructorTableView.setItems(
                FXCollections.observableArrayList(manager.getAllInstructors())));
    }
}
