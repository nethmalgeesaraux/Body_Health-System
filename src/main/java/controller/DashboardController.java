package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DashboardController {
    @FXML private Label lblWelcome;
    private int currentUserId;

    public void setCurrentUserId(int id) {
        this.currentUserId = id;
        loadUser();
    }

    private void loadUser() {
        // simple show id or load username from DB if you prefer
        lblWelcome.setText("Welcome, user #" + currentUserId);
    }

    @FXML
    void openHealth() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/health.fxml"));
            Parent root = loader.load();
            HealthController hc = loader.getController();
            hc.setUserId(currentUserId);
            Stage stage = new Stage();
            stage.setTitle("Record Health Metric");
            stage.setScene(new Scene(root));
            stage.initOwner(lblWelcome.getScene().getWindow());
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
