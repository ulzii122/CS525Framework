package framework.controller;

import java.util.List;

import framework.model.IAccount;
import framework.model.ICustomer;
import framework.model.impl.Customer;

public interface IFinco {
	public void addAccount(ICustomer cust, IAccount acc);

	public void addInterest(Double value);

	public Double deposit(Double amount, String accNo);

	public Double withdraw(Double amount, String accNo);

	public List<Customer> getCustomerList();
}
