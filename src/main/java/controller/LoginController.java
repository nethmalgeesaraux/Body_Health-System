package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import util.DBConnection;
import util.ValidationUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginController {

    @FXML private TextField txtUsername;
    @FXML private PasswordField txtPassword;
    @FXML private Label lblMessage;

    @FXML
    void login(ActionEvent event) {
        String username = txtUsername.getText().trim();
        String password = txtPassword.getText().trim();

        if (!ValidationUtil.isNotEmpty(username) || !ValidationUtil.isNotEmpty(password)) {
            lblMessage.setText("Enter credentials.");
            return;
        }

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT id FROM users WHERE LOWER(username)=LOWER(?) AND password=?")) {
            ps.setString(1, username);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int userId = rs.getInt("id");
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/dashboard.fxml"));
                    Parent root = loader.load();
                    controller.DashboardController dc = loader.getController();
                    dc.setCurrentUserId(userId);
                    Stage stage = (Stage) txtUsername.getScene().getWindow();
                    stage.setScene(new Scene(root));
                } else {
                    lblMessage.setText("Invalid credentials.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            lblMessage.setText("Login error.");
        }
    }
}
