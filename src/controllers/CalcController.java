package controllers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import application.Payment;
import application.RepaymentSchemes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class CalcController implements Initializable {
	@FXML
	private TableView<Payment> table;

	@FXML
	private TableColumn<Payment, Integer> colYear;
	@FXML
	private TableColumn<Payment, Integer> colNumb;
	@FXML
	private TableColumn<Payment, Double> colRedeeming;
	@FXML
	private TableColumn<Payment, Double> colInterest;
	@FXML
	private TableColumn<Payment, Double> colMonthly;
	@FXML
	private TableColumn<Payment, Double> colLeft;

	@FXML
	private TextField filter1;
	@FXML
	private TextField filter2;

	@FXML
	private Button saveToFileButton;
	@FXML
	private Button showGraphButton;
	@FXML
	private Button filter;
	@FXML
	private Button clearFilters;

	@FXML
	private ToggleGroup whenSavingToFile;

	@FXML
	private RadioButton filterRadio1;
	@FXML
	private RadioButton filterRadio2;

	@FXML
	private Text errorText1;
	@FXML
	private Text errorText2;
	
	private Stage graphStage = new Stage();

	@FXML
	void showGraph(ActionEvent event) {
		try {
			new CalcController();
			graphStage.close();
			Parent root = FXMLLoader.load(getClass().getResource("fxml/UiGraph.fxml"));
			graphStage.setTitle("Payments graph");
			graphStage.setScene(new Scene(root));
			graphStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	void filterByKey(KeyEvent event) {
		ObservableList<Payment> listFiltered = FXCollections.observableArrayList();
		if (filter1.getText().isEmpty() && filter2.getText().isEmpty()) {
			table.setItems(RepaymentSchemes.list);
		} else {
			for (Payment payment : RepaymentSchemes.list) {
				if (!filter1.getText().isEmpty() && filter2.getText().isEmpty()) {
					if (Integer.parseInt(filter1.getText()) <= payment.getColYear()) {
						listFiltered.add(payment);
					}
				} else if (filter1.getText().isEmpty() && !filter2.getText().isEmpty()) {
					if (Integer.parseInt(filter2.getText()) >= payment.getColYear()) {
						listFiltered.add(payment);
					}
				} else if (Integer.parseInt(filter1.getText()) <= payment.getColYear()
						&& Integer.parseInt(filter2.getText()) >= payment.getColYear()) {
					listFiltered.add(payment);
				}
			}
			table.setItems(listFiltered);
		}
	}

	@FXML
	void clearFilters(ActionEvent event) {
		filter1.clear();
		filter2.clear();
		table.setItems(RepaymentSchemes.list);
	}

	@FXML
	void saveToFile(ActionEvent event) {
		Stage stage = new Stage();

		FileChooser fileChooser = new FileChooser();

		FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
		fileChooser.getExtensionFilters().add(extensionFilter);

		File file = fileChooser.showSaveDialog(stage);
		if (file != null) {
			if (filterRadio1.isSelected()) {
				SaveFile(RepaymentSchemes.list, file);
			} else {
				ObservableList<Payment> listFiltered = FXCollections.observableArrayList();

				if (!filter1.getText().isEmpty() && !filter2.getText().isEmpty()) {
					for (Payment payment : RepaymentSchemes.list) {
						if (filter1.getText().isEmpty() && filter2.getText().isEmpty()) {
							listFiltered.add(payment);
						} else if (!filter1.getText().isEmpty() && filter2.getText().isEmpty()) {
							if (Integer.parseInt(filter1.getText()) <= payment.getColYear()) {
								listFiltered.add(payment);
							}
						} else if (filter1.getText().isEmpty() && !filter2.getText().isEmpty()) {
							if (Integer.parseInt(filter2.getText()) >= payment.getColYear()) {
								listFiltered.add(payment);
							}
						} else if (Integer.parseInt(filter1.getText()) <= payment.getColYear()
								&& Integer.parseInt(filter2.getText()) >= payment.getColYear()) {
							listFiltered.add(payment);
						}
					}
					SaveFile(listFiltered, file);
				} else {
					SaveFile(RepaymentSchemes.list, file);
				}
			}
		}
	}

	private void SaveFile(ObservableList<Payment> list, File file) {
		try {
			BufferedWriter buffWriter = new BufferedWriter(new FileWriter(file));

			for (Payment payment : list) {
				if (payment.getColYear() == 0) {
					buffWriter.write(payment.totalStringPayment());
					buffWriter.newLine();
				} else {
				buffWriter.write(payment.toStringPayment());
				}
				buffWriter.newLine();
			}
			buffWriter.close();

		} catch (IOException ex) {
			Logger.getLogger(CalcController.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		filter1.lengthProperty().addListener((obs, oldVal, newVal) -> {
			if (newVal.intValue() > oldVal.intValue()) {
				char charTyped = filter1.getText().charAt(oldVal.intValue());
				filter1.getStyleClass().remove("error");
				errorText1.setVisible(false);
				errorText2.setVisible(false);
				if (!(charTyped >= '0' && charTyped <= '9')) {
					filter1.setText(filter1.getText().substring(0, filter1.getText().length() - 1));
					filter1.getStyleClass().add("error");
					errorText1.setVisible(true);
				}
			}
		});
		filter2.lengthProperty().addListener((obs, oldVal, newVal) -> {
			if (newVal.intValue() > oldVal.intValue()) {
				char charTyped = filter2.getText().charAt(oldVal.intValue());
				filter2.getStyleClass().remove("error");
				errorText1.setVisible(false);
				errorText2.setVisible(false);
				if (!(charTyped >= '0' && charTyped <= '9')) {
					filter2.setText(filter2.getText().substring(0, filter2.getText().length() - 1));
					filter2.getStyleClass().add("error");
					errorText2.setVisible(true);
				}
			}
		});

		colYear.setCellValueFactory(new PropertyValueFactory<Payment, Integer>("colYear"));
		colNumb.setCellValueFactory(new PropertyValueFactory<Payment, Integer>("colNumb"));
		colRedeeming.setCellValueFactory(new PropertyValueFactory<Payment, Double>("colRedeeming"));
		colInterest.setCellValueFactory(new PropertyValueFactory<Payment, Double>("colInterest"));
		colMonthly.setCellValueFactory(new PropertyValueFactory<Payment, Double>("colMonthly"));
		colLeft.setCellValueFactory(new PropertyValueFactory<Payment, Double>("colLeft"));

		colYear.prefWidthProperty().bind(table.widthProperty().divide(12).multiply(1));
		colNumb.prefWidthProperty().bind(table.widthProperty().divide(12).multiply(2));
		colRedeeming.prefWidthProperty().bind(table.widthProperty().divide(12).multiply(2));
		colInterest.prefWidthProperty().bind(table.widthProperty().divide(12).multiply(2));
		colMonthly.prefWidthProperty().bind(table.widthProperty().divide(12).multiply(2));
		colLeft.prefWidthProperty().bind(table.widthProperty().divide(12).multiply(3));

		table.setItems(RepaymentSchemes.list);
	}
}
