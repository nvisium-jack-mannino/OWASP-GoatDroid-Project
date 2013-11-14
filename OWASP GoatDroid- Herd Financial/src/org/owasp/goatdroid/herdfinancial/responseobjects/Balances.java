package org.owasp.goatdroid.herdfinancial.responseobjects;

public class Balances extends GenericResponseObject {

	String checkingBalance;
	String savingsBalance;

	public String getCheckingBalance() {
		return checkingBalance;
	}

	public void setCheckingBalance(String checkingBalance) {
		this.checkingBalance = checkingBalance;
	}

	public String getSavingsBalance() {
		return savingsBalance;
	}

	public void setSavingsBalance(String savingsBalance) {
		this.savingsBalance = savingsBalance;
	}
}
