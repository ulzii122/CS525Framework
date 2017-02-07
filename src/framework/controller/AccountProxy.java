package framework.controller;

import framework.model.IAccount;
import framework.model.ICustomer;
import framework.model.impl.Company;

public class AccountProxy implements IAccount {
	private IAccount acc = null;

	public AccountProxy(IAccount acc) {
		this.acc = acc;
	}

	@Override
	public Double deposit(Double amount) {
		Double bal = acc.deposit(amount);
		sendEmailEngine(amount, bal);

		return bal;
	}

	@Override
	public Double withdraw(Double amount) {
		Double bal = acc.withdraw(amount);
		sendEmailEngine(amount, bal);

		return bal;
	}

	@Override
	public void addInterest(Double amount) {
		acc.addInterest(amount);
	}

	@Override
	public Double getBalance() {
		return acc.getBalance();
	}

	private void sendEmailEngine(Double amount, Double bal) {
		if (acc.getCustomer() instanceof Company)
			sendEmail();
		else if (amount >= 500 || bal < 0)
			sendEmail();
	}

	private void sendEmail() {

	}

	@Override
	public ICustomer getCustomer() {
		return null;
	}

	@Override
	public void setCustomer(ICustomer cust) {
	}

	@Override
	public void setAccountNum(String accountNum) {
	}

	@Override
	public String getAccountNum() {
		// TODO Auto-generated method stub
		return null;
	}
}