package framework.controller;

import java.util.function.Predicate;

import framework.model.IAccount;
import framework.model.ICustomer;
import framework.model.impl.Company;

public class AccountProxy implements IAccount {
	private IAccount acc = null;

	public AccountProxy(IAccount acc) {
		this.acc = acc;
	}

	@Override
	public Double deposit(Double amount, Predicate<Double> amountCheck) {
		Double bal = acc.deposit(amount, null);
		sendEmailEngine(amountCheck, amount, bal);

		return bal;
	}

	@Override
	public Double withdraw(Double amount, Predicate<Double> amountCheck) {
		Double bal = acc.withdraw(amount, null);
		sendEmailEngine(amountCheck, amount, bal);

		return bal;
	}

	@Override
	public Double getBalance() {
		return acc.getBalance();
	}

	private void sendEmailEngine(Predicate<Double> amountCheck, Double amount, Double bal) {
		if (acc.getCustomer() instanceof Company)
			sendEmail();
		else if (amountCheck.test(amount) || bal < 0)
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