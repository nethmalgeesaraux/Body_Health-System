package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class RegisterController {

    @FXML private TextField txtUsername;
    @FXML private PasswordField txtPassword;
    @FXML private TextField txtWeight;
    @FXML private TextField txtHeight;
    @FXML private Label lblMessage;

    @FXML
    void registerUser(ActionEvent event) {
        String username = txtUsername.getText().trim();
        String password = txtPassword.getText().trim();
        String weightStr = txtWeight.getText().trim();
        String heightStr = txtHeight.getText().trim();

        if (username.isEmpty() || password.isEmpty() || weightStr.isEmpty() || heightStr.isEmpty()) {
            lblMessage.setText("All fields are required!");
            return;
        }

        try {
            double weight = Double.parseDouble(weightStr);
            double height = Double.parseDouble(heightStr);

            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement ps = conn.prepareStatement(
                         "INSERT INTO users (username, password, weight, height) VALUES (?, ?, ?, ?)")) {
                ps.setString(1, username);
                ps.setString(2, password);
                ps.setDouble(3, weight);
                ps.setDouble(4, height);

                int rows = ps.executeUpdate();
                if (rows > 0) {
                    lblMessage.setStyle("-fx-text-fill: green;");
                    lblMessage.setText("Registration successful! Go back to login.");
                } else {
                    lblMessage.setText("Registration failed.");
                }
            }
        } catch (NumberFormatException e) {
            lblMessage.setText("Invalid weight or height format.");
        } catch (Exception e) {
            e.printStackTrace();
            lblMessage.setText("Error: " + e.getMessage());
        }
    }

    @FXML
    void backToLogin(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
            Stage stage = (Stage) txtUsername.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
