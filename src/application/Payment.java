package application;

import java.util.HashMap;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Payment {
	private final SimpleIntegerProperty colYear;
	private final SimpleIntegerProperty colNumb;
	private final SimpleDoubleProperty colRedeeming;
	private final SimpleDoubleProperty colInterest;
	private final SimpleDoubleProperty colMonthly;
	private final SimpleDoubleProperty colLeft;

	public Payment(Integer colYear, Integer colNumb, Double colRedeeming, Double colInterest, Double colMonthly,
			Double colLeft) {
		super();
		this.colYear = new SimpleIntegerProperty(colYear);
		this.colNumb = new SimpleIntegerProperty(colNumb);
		this.colRedeeming = new SimpleDoubleProperty(colRedeeming);
		this.colInterest = new SimpleDoubleProperty(colInterest);
		this.colMonthly = new SimpleDoubleProperty(colMonthly);
		this.colLeft = new SimpleDoubleProperty(colLeft);
	}

	public Integer getColYear() {
		return colYear.get();
	}

	public Integer getColNumb() {
		return colNumb.get();
	}

	public Double getColRedeeming() {
		return colRedeeming.get();
	}

	public Double getColInterest() {
		return colInterest.get();
	}

	public Double getColMonthly() {
		return colMonthly.get();
	}

	public Double getColLeft() {
		return colLeft.get();
	}

	public String toStringPayment() {
		return "Year: " + getColYear() + " | Payment month: " + getColNumb() + " | Redeeming: " + getColRedeeming()
				+ " | Interest: " + getColInterest() + " | Total payment: " + getColMonthly() + " | Left to Pay: "
				+ getColLeft();
	}

	public String totalStringPayment() {
		return "Total amount to repay: " + getColLeft();
	}
}
