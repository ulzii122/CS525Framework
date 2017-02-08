package framework.controller;

import java.util.List;

import org.mongodb.morphia.Datastore;

import framework.model.IAccount;
import framework.model.ICustomer;
import framework.model.impl.Account;
import framework.model.impl.Customer;

public interface IFinco {
	public void addAccount(ICustomer cust, IAccount acc);

	public void addInterest(Double value);

	public void addInterest(Double value, List<Account> accList);

	public Double deposit(Double amount, IAccount acc);

	public Double withdraw(Double amount, IAccount acc);

	public List<Customer> getCustomerList();

	public Datastore getDataStore();
}
