package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class AboutController implements Initializable {
	@FXML
	private Label label;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		String about = "Calculates how much you will " + "need to repay in total based "
				+ "on selected repayment scheme. " + "Shows table populated with "
				+ "information about monthly payments. " + "You can save this information to file "
				+ "and/or see yearly payments graph. \n\n" + "Program made by:\n"
				+ "@Dilanas Sabaliauskas \n\n" + "Now you can calculate your " + "next loan's interest!\n"
				+ "Enjoy. ";

		label.setText(about);
	}
}