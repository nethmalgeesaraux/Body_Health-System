package controller;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import models.HealthMetric;
import util.DBConnection;
import util.ValidationUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

public class HealthController {

    @FXML private DatePicker dpDate;
    @FXML private TextField txtWeight;
    @FXML private TextField txtSys;
    @FXML private TextField txtDia;
    @FXML private TextField txtHR;
    @FXML private TextArea txtNotes;
    @FXML private Label lblMsg;
    @FXML private LineChart<String, Number> chartWeight;

    private int userId;

    public void setUserId(int id) {
        this.userId = id;
        dpDate.setValue(LocalDate.now());
        loadWeightChart();
    }

    @FXML
    void saveMetric() {
        if (!ValidationUtil.isPositiveDouble(txtWeight.getText())) { lblMsg.setText("Enter weight"); return; }
        if (!ValidationUtil.isPositiveInt(txtSys.getText())) { lblMsg.setText("Enter systolic"); return; }
        if (!ValidationUtil.isPositiveInt(txtDia.getText())) { lblMsg.setText("Enter diastolic"); return; }
        if (!ValidationUtil.isPositiveInt(txtHR.getText())) { lblMsg.setText("Enter heart rate"); return; }

        double weight = Double.parseDouble(txtWeight.getText());
        int sys = Integer.parseInt(txtSys.getText());
        int dia = Integer.parseInt(txtDia.getText());
        int hr = Integer.parseInt(txtHR.getText());
        LocalDate date = dpDate.getValue();
        String notes = txtNotes.getText();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "INSERT INTO health_metrics(user_id, metric_date, weight, systolic, diastolic, heart_rate, notes) VALUES (?, ?, ?, ?, ?, ?, ?)")) {
            ps.setInt(1, userId);
            ps.setString(2, date.toString());
            ps.setDouble(3, weight);
            ps.setInt(4, sys);
            ps.setInt(5, dia);
            ps.setInt(6, hr);
            ps.setString(7, notes);
            ps.executeUpdate();

            lblMsg.setText("Saved.");
            txtWeight.clear(); txtSys.clear(); txtDia.clear(); txtHR.clear(); txtNotes.clear();
            dpDate.setValue(LocalDate.now());
            loadWeightChart();
        } catch (Exception e) {
            e.printStackTrace();
            lblMsg.setText("Error saving.");
        }
    }

    private void loadWeightChart() {
        chartWeight.getData().clear();
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Weight");

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT metric_date, weight FROM health_metrics WHERE user_id=? ORDER BY metric_date")) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String d = rs.getString("metric_date");
                    double w = rs.getDouble("weight");
                    series.getData().add(new XYChart.Data<>(d, w));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        chartWeight.getData().add(series);
    }
}
