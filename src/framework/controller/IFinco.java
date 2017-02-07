package framework.controller;

import framework.model.IAccount;
import framework.model.ICustomer;

public interface IFinco {
	public void addPersonalAccount(ICustomer cust, IAccount acc);

	public void addCompanyAccount(ICustomer cust, IAccount acc);

	public void addInterest(Double value);

	public Double deposit(Double amount, String accNo);

	public Double withdraw(Double amount, String accNo);
}
