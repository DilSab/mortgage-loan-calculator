package controllers;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ResourceBundle;

import application.RepaymentSchemes;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Controller implements Initializable {
	@FXML
	private MenuItem close;

	@FXML
	private MenuItem about;

	@FXML
	private Slider slider1;
	@FXML
	private Slider slider2;
	@FXML
	private Slider slider3;

	@FXML
	private TextField field1;
	@FXML
	private TextField field2;
	@FXML
	private TextField field3;

	@FXML
	private ToggleGroup scheduleTypes;

	@FXML
	private RadioButton radio1;
	@FXML
	private RadioButton radio2;

	@FXML
	private Button button1;

	@FXML
	private Text errorText1;
	@FXML
	private Text errorText2;
	@FXML
	private Text errorText3;

	private Stage calcStage = new Stage();
	private Stage aboutStage = new Stage();
	
	private double amount;
	private double interestRate;
	private int year;
	private DecimalFormat df = new DecimalFormat("#.##");
	private RepaymentSchemes repaymentSchemes = new RepaymentSchemes();

	@FXML
	private void pressedCalc(ActionEvent Event) {
		amount = slider1.getValue();
		interestRate = slider2.getValue();
		year = (int) slider3.getValue();
		try {
			if (radio1.isSelected()) {
				repaymentSchemes.populateListAnnuity(amount, interestRate, year);
			} else {
				repaymentSchemes.populateListLinear(amount, interestRate, year);
			}
			new CalcController();
			calcStage.close();
			Parent root = FXMLLoader.load(getClass().getResource("fxml/UiCalc.fxml"));
			calcStage.setTitle("Payments");
			Scene scene = new Scene(root);
			scene.getStylesheets().add("/css/DigitsOnly.css");
			calcStage.setScene(scene);
			calcStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
    void deleteInput1(MouseEvent event) {
		field1.setText("");
    }
	
	@FXML
    void deleteInput2(MouseEvent event) {
		field2.setText("");
    }
	
	@FXML
    void deleteInput3(MouseEvent event) {
		field3.setText("");
    }

	@FXML
	void closeProgram(ActionEvent event) {
		Platform.exit();
	}

	@FXML
	void showAbout(ActionEvent event) {
		try {
			aboutStage.close();
			Parent root = FXMLLoader.load(getClass().getResource("fxml/UiAbout.fxml"));
			aboutStage.setTitle("About");
			Scene scene = new Scene(root);
			aboutStage.setScene(scene);
			aboutStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		field1.textProperty().bindBidirectional(slider1.valueProperty(), NumberFormat.getNumberInstance());
		field2.textProperty().bindBidirectional(slider2.valueProperty(), NumberFormat.getNumberInstance());
		field3.textProperty().bindBidirectional(slider3.valueProperty(), NumberFormat.getNumberInstance());

		field1.lengthProperty().addListener((obs, oldVal, newVal) -> {
			if (newVal.intValue() > oldVal.intValue()) {
				char charTyped = field1.getText().charAt(oldVal.intValue());
				field1.getStyleClass().remove("error");
				errorText1.setVisible(false);
				errorText2.setVisible(false);
				errorText3.setVisible(false);
				if (!(charTyped >= '0' && charTyped <= '9') && (charTyped != '.')) {
					field1.setText(field1.getText().substring(0, field1.getText().length() - 1));
					field1.getStyleClass().add("error");
					errorText1.setVisible(true);
				}
			}
			if (newVal.intValue() == 0) {
				field1.setText("1000");
			}
		});
		field2.lengthProperty().addListener((obs, oldVal, newVal) -> {
			if (newVal.intValue() > oldVal.intValue()) {
				char charTyped = field2.getText().charAt(oldVal.intValue());
				field2.getStyleClass().remove("error");
				errorText1.setVisible(false);
				errorText2.setVisible(false);
				errorText3.setVisible(false);
				if (!(charTyped >= '0' && charTyped <= '9') && (charTyped != '.')) {
					field2.setText(field2.getText().substring(0, field2.getText().length() - 1));
					field2.getStyleClass().add("error");
					errorText2.setVisible(true);
				}
			}
			if (newVal.intValue() == 0) {
				field2.setText("1");
			}
		});
		field3.lengthProperty().addListener((obs, oldVal, newVal) -> {
			if (newVal.intValue() > oldVal.intValue()) {
				char charTyped = field3.getText().charAt(oldVal.intValue());
				field3.getStyleClass().remove("error");
				errorText1.setVisible(false);
				errorText2.setVisible(false);
				errorText3.setVisible(false);
				if (!(charTyped >= '0' && charTyped <= '9') && (charTyped != '.')) {
					field3.setText(field3.getText().substring(0, field3.getText().length() - 1));
					field3.getStyleClass().add("error");
					errorText3.setVisible(true);
				} else if (charTyped == '.') {
					field3.setText(field3.getText().substring(0, field3.getText().length() - 1));
				}
			}
			if (newVal.intValue() == 0) {
				field3.setText("1");
			}
		});

		slider2.valueProperty().addListener((obs, oldVal, newVal) -> field2.setText(df.format(newVal.doubleValue())));
		slider1.valueProperty().addListener((obs, oldVal, newVal) -> field1.setText(df.format(newVal.doubleValue())));
		slider3.valueProperty().addListener((obs, oldVal, newVal) -> slider3.setValue(newVal.intValue()));
	}
}