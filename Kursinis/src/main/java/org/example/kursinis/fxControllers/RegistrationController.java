package org.example.kursinis.fxControllers;

import org.example.kursinis.HelloApplication;
import org.example.kursinis.fxControllers.MainWindow;
import org.example.kursinis.hibernate.ShopHibernate;
import org.example.kursinis.model.Customer;
import org.example.kursinis.model.Manager;
import org.example.kursinis.model.User;
import jakarta.persistence.EntityManagerFactory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class RegistrationController {

    @FXML
    public TextField loginField;
    @FXML
    public PasswordField passwordField;
    @FXML
    public PasswordField repeatPasswordField;
    @FXML
    public TextField nameField;
    @FXML
    public TextField surnameField;
    @FXML
    public RadioButton customerCheckbox;
    @FXML
    public ToggleGroup userType;
    @FXML
    public RadioButton managerCheckbox;
    @FXML
    public TextField addressField;
    @FXML
    public TextField cardNoField;
    @FXML
    public DatePicker birthDateField;
    @FXML
    public TextField employeeIdField;
    @FXML
    public TextField medCertificateField;
    @FXML
    public DatePicker employmentDateField;
    @FXML
    public CheckBox isAdminCheck;
    @FXML
    public TextField billingAddressField;
    @FXML
    public Button rtrn;

    private EntityManagerFactory entityManagerFactory;

    public void setData(EntityManagerFactory entityManagerFactory, boolean showManagerFields) {
        this.entityManagerFactory = entityManagerFactory;
        disableFields(showManagerFields);
    }

    public void setData(EntityManagerFactory entityManagerFactory, Manager manager) {
        this.entityManagerFactory = entityManagerFactory;
        toggleFields(customerCheckbox.isSelected(), manager);
    }

    private void disableFields(boolean showManagerFields) {
        if (!showManagerFields) {
            employeeIdField.setVisible(false);
            medCertificateField.setVisible(false);
            employmentDateField.setVisible(false);
            isAdminCheck.setVisible(false);
            managerCheckbox.setVisible(false);
        }else{
            rtrn.setVisible(false);
        }
    }

    public void disableReg() {
        if (customerCheckbox.isSelected()) {
            employeeIdField.setDisable(true);
            isAdminCheck.setDisable(true);
            medCertificateField.setDisable(true);
            employmentDateField.setDisable(true);
        } else {
            billingAddressField.setDisable(true);
            addressField.setDisable(true);
            cardNoField.setDisable(true);
        }
    }

    private void toggleFields(boolean isManager, Manager manager) {
        if (isManager) {
            addressField.setDisable(true);
            cardNoField.setDisable(true);
            employeeIdField.setDisable(false);
            medCertificateField.setDisable(false);
            employmentDateField.setDisable(false);
            if (manager.isAdmin()) isAdminCheck.setDisable(false);
        } else {
            addressField.setDisable(false);
            cardNoField.setDisable(false);
            employeeIdField.setDisable(true);
            medCertificateField.setDisable(true);
            employmentDateField.setDisable(true);
            isAdminCheck.setDisable(true);
        }
    }


    public void createUser() {
        // All fields are filled, proceed with user creation
        ShopHibernate shopHibernate = new ShopHibernate(entityManagerFactory);
        if (customerCheckbox.isSelected()) {
            if (nameField.getText().isEmpty() || surnameField.getText().isEmpty() || loginField.getText().isEmpty() || passwordField.getText().isEmpty() ||
                    cardNoField.getText().isEmpty() || addressField.getText().isEmpty() || billingAddressField.getText().isEmpty() ||
                    (birthDateField.getValue() == null)) {

                // If any field is empty, generate an alert
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Empty Fields");
                alert.setContentText("Please fill in all fields.");
                alert.showAndWait();
                return;
            }
            User user = new Customer(nameField.getText(), surnameField.getText(), loginField.getText(), passwordField.getText(), cardNoField.getText(), addressField.getText(), billingAddressField.getText(), birthDateField.getValue());
            shopHibernate.create(user);
        } else {
            if (nameField.getText().isEmpty() || surnameField.getText().isEmpty() ||
                    loginField.getText().isEmpty() || passwordField.getText().isEmpty() ||
                    (birthDateField.getValue() == null) ||
                    employeeIdField.getText().isEmpty() ||
                    medCertificateField.getText().isEmpty() ||
                    (employmentDateField.getValue() == null)) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Empty Fields");
                alert.setContentText("Please fill in all fields.");
                alert.showAndWait();
                return;
            }

            boolean isAdmin = isAdminCheck.isSelected(); // Check if manager is admin
            Manager manager = new Manager(nameField.getText(), surnameField.getText(), loginField.getText(), passwordField.getText(), birthDateField.getValue(), isAdmin, employeeIdField.getText(), medCertificateField.getText(), employmentDateField.getValue());
            shopHibernate.create(manager);
        }
    }


    public void returnToLogin() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-form.fxml"));
        Parent parent = fxmlLoader.load();
        Stage stage = (Stage) loginField.getScene().getWindow();
        Scene scene = new Scene(parent);
        FxUtils.setStageParameters(stage, scene, false);
    }

}
