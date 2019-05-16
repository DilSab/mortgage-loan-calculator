package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import application.Payment;
import application.RepaymentSchemes;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.ScatterChart;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;

public class GraphController implements Initializable {
	@FXML
	private LineChart<String, Number> graph1;
	@FXML
	private ScatterChart<String, Number> graph2;
	@FXML
	private BarChart<String, Number> graph3;
	
	@FXML
    private ToggleGroup selectGraph;
	
	@FXML
    private RadioButton radio1;

    @FXML
    private RadioButton radio2;

    @FXML
    private RadioButton radio3;

	@FXML
    void showDifferentGraph(ActionEvent event) {
		if (radio1.isSelected()) {
			graph1.setVisible(true);
			graph2.setVisible(false);
			graph3.setVisible(false);
		} else if (radio2.isSelected()) {
			graph1.setVisible(false);
			graph2.setVisible(true);
			graph3.setVisible(false);
		} else {
			graph1.setVisible(false);
			graph2.setVisible(false);
			graph3.setVisible(true);
		}
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		LineChart.Series<String, Number> series1 = new LineChart.Series<String, Number>();
		LineChart.Series<String, Number> series2 = new LineChart.Series<String, Number>();
		LineChart.Series<String, Number> series3 = new LineChart.Series<String, Number>();
		LineChart.Series<String, Number> series4 = new LineChart.Series<String, Number>();
		ScatterChart.Series<String, Number> series5 = new ScatterChart.Series<String, Number>();
		ScatterChart.Series<String, Number> series6 = new ScatterChart.Series<String, Number>();
		ScatterChart.Series<String, Number> series7 = new ScatterChart.Series<String, Number>();
		ScatterChart.Series<String, Number> series8 = new ScatterChart.Series<String, Number>();
		BarChart.Series<String, Number> series9 = new BarChart.Series<String, Number>();
		BarChart.Series<String, Number> series10 = new BarChart.Series<String, Number>();
		BarChart.Series<String, Number> series11 = new BarChart.Series<String, Number>();
		BarChart.Series<String, Number> series12 = new BarChart.Series<String, Number>();
		
		series1.setName("Yearly payment");
		series2.setName("Left to pay");
		series3.setName("Yearly redeeming");
		series4.setName("Yearly interest");
		series5.setName("Yearly payment");
		series6.setName("Left to pay");
		series7.setName("Yearly redeeming");
		series8.setName("Yearly interest");
		series9.setName("Yearly payment");
		series10.setName("Left to pay");
		series11.setName("Yearly redeeming");
		series12.setName("Yearly interest");

		int yearCount = 0;
		int monthCount = -1;
		double yearlyPayment = 0;
		double yearlyRedeeming = 0;
		double yearlyInterest = 0;
		double yearlyLeft = 0;

		for (Payment payment : RepaymentSchemes.list) {
			monthCount++;
			yearlyPayment += payment.getColMonthly();
			yearlyRedeeming += payment.getColRedeeming();
			yearlyInterest += payment.getColInterest();
			if (monthCount == 12) {
				yearCount++;
				yearlyLeft += yearlyPayment;
				series1.getData().add(new LineChart.Data<String, Number>(String.valueOf(yearCount), yearlyPayment));
				series3.getData().add(new LineChart.Data<String, Number>(String.valueOf(yearCount), yearlyRedeeming));
				series4.getData().add(new LineChart.Data<String, Number>(String.valueOf(yearCount), yearlyInterest));
				series5.getData().add(new ScatterChart.Data<String, Number>(String.valueOf(yearCount), yearlyPayment));
				series7.getData().add(new ScatterChart.Data<String, Number>(String.valueOf(yearCount), yearlyRedeeming));
				series8.getData().add(new ScatterChart.Data<String, Number>(String.valueOf(yearCount), yearlyInterest));
				series9.getData().add(new BarChart.Data<String, Number>(String.valueOf(yearCount), yearlyPayment));
				series11.getData().add(new BarChart.Data<String, Number>(String.valueOf(yearCount), yearlyRedeeming));
				series12.getData().add(new BarChart.Data<String, Number>(String.valueOf(yearCount), yearlyInterest));
				monthCount = 0;
				yearlyPayment = 0;
				yearlyRedeeming = 0;
				yearlyInterest = 0;
			}
		}
		yearCount = 0;
		monthCount = -1;

		for (Payment payment : RepaymentSchemes.list) {
			monthCount++;
			yearlyPayment += payment.getColMonthly();
			if (monthCount == 12) {
				series2.getData().add(new LineChart.Data<String, Number>(String.valueOf(yearCount), yearlyLeft));
				series6.getData().add(new ScatterChart.Data<String, Number>(String.valueOf(yearCount), yearlyLeft));
				series10.getData().add(new BarChart.Data<String, Number>(String.valueOf(yearCount), yearlyLeft));
				yearCount++;
				yearlyLeft -= yearlyPayment;
				monthCount = 0;
				yearlyPayment = 0;
			}
		}
		series2.getData().add(new LineChart.Data<String, Number>(String.valueOf(yearCount), yearlyLeft));
		series6.getData().add(new ScatterChart.Data<String, Number>(String.valueOf(yearCount), yearlyLeft));
		series10.getData().add(new BarChart.Data<String, Number>(String.valueOf(yearCount), yearlyLeft));
		
		graph1.getData().add(series2);
		graph1.getData().add(series3);
		graph1.getData().add(series4);
		graph1.getData().add(series1);
		graph2.getData().add(series6);
		graph2.getData().add(series7);
		graph2.getData().add(series8);
		graph2.getData().add(series5);
		graph3.getData().add(series10);
		graph3.getData().add(series11);
		graph3.getData().add(series12);
		graph3.getData().add(series9);
	}
}
