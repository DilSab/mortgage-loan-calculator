package application;

import java.text.DecimalFormat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class RepaymentSchemes {
	public static ObservableList<Payment> list = FXCollections.observableArrayList();
	DecimalFormat df = new DecimalFormat("#.##");

	public ObservableList<Payment> populateListAnnuity(double amount, double interestRate, int year) {
		if (!list.isEmpty()) {
			list.clear();
		}
		int paymentMonth = 1;
		int paymentYear = 1;
		String leftFormated;
		String monthlyFormated;
		double monthlyInterestRate = interestRate / 1200;
		double monthlyPayment = amount * monthlyInterestRate / (1 - (1 / Math.pow(1 + monthlyInterestRate, year * 12)));
		double leftAmount = monthlyPayment * year * 12;
		double interest = leftAmount - amount;
		double monthlyInterest = interest / (year * 12);
		double redeeming = amount / (year * 12);
		String redeemingFormated = df.format(redeeming);
		String monthlyInterestFormated = df.format(monthlyInterest);
		list.add(new Payment(0, 0, 0.0, 0.0, 0.0, Double.parseDouble(df.format(leftAmount))));

		for (int i = 0; i < (year * 12); i++) {
			paymentMonth = i + 1;
			paymentYear = ((i / 12) + 1);
			leftAmount = leftAmount - monthlyPayment;
			leftFormated = df.format(leftAmount);
			monthlyFormated = df.format(monthlyPayment);
			Payment payment = new Payment(paymentYear, paymentMonth, Double.parseDouble(redeemingFormated),
					Double.parseDouble(monthlyInterestFormated), Double.parseDouble(monthlyFormated),
					Double.parseDouble(leftFormated));
			list.add(payment);
		}
		return list;
	}

	public ObservableList<Payment> populateListLinear(double amount, double interestRate, int year) {
		if (!list.isEmpty()) {
			list.clear();
		}
		int paymentMonth = 1;
		int paymentYear = 1;
		double redeeming = amount / (year * 12);
		double monthlyInterest;
		double leftAmount = amount;
		double totalMonthly;
		double totalPayment = 0;

		for (int i = 0; i < (year * 12); i++) {
			monthlyInterest = (interestRate / 100) * leftAmount / 12;
			leftAmount -= redeeming;
			totalPayment = totalPayment + monthlyInterest + redeeming;
		}
		leftAmount = amount;
		String leftFormated;
		String monthlyFormated;
		String redeemingFormated = df.format(redeeming);
		String monthlyInterestFormated;
		list.add(new Payment(0, 0, 0.0, 0.0, 0.0, Double.parseDouble(df.format(totalPayment))));

		for (int i = 0; i < (year * 12); i++) {
			monthlyInterest = (interestRate / 100) * leftAmount / 12;
			paymentMonth = i + 1;
			paymentYear = ((i / 12) + 1);
			leftAmount -= redeeming;
			totalMonthly = redeeming + monthlyInterest;
			totalPayment -= totalMonthly;
			leftFormated = df.format(totalPayment);
			monthlyFormated = df.format(totalMonthly);
			monthlyInterestFormated = df.format(monthlyInterest);
			Payment payment = new Payment(paymentYear, paymentMonth, Double.parseDouble(redeemingFormated),
					Double.parseDouble(monthlyInterestFormated), Double.parseDouble(monthlyFormated),
					Double.parseDouble(leftFormated));
			list.add(payment);
		}
		return list;
	}
}