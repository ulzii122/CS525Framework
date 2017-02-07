package framework.controller;

import framework.model.IAccount;
import framework.model.ICustomer;

public interface IFinco {
	public void addPersonalAccount(ICustomer cust);

	public void addCompanyAccount(ICustomer cust);

	public void addInterest(Double value);

	public Double deposit(Double amount, IAccount acc);

	public Double withdraw(Double amount, IAccount acc);
}
