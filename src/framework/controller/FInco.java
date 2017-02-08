package framework.controller;

import java.util.ArrayList;
import java.util.List;

import org.mongodb.morphia.Datastore;

import framework.DAOFacade;
import framework.model.IAccount;
import framework.model.ICustomer;
import framework.model.impl.Account;
import framework.model.impl.Company;
import framework.model.impl.Customer;
import framework.model.impl.Person;

public class Finco implements IFinco {

	private Datastore dataStore = null;

	public Finco() {
		dataStore = DAOFacade.getInstance().getDatastore();
	}

	@Override
	public Datastore getDataStore() {
		return dataStore;
	}

	@Override
	public void addAccount(ICustomer cust, IAccount acc) {
		dataStore.save(acc);
		dataStore.save(cust);
	}

	@Override
	public void addInterest(Double value) {
		List<Account> accList = dataStore.find(Account.class).asList();
		for (Account acc : accList)
			acc.addInterest(value);

		dataStore.save(accList);
	}

	@Override
	public Double deposit(Double amount, IAccount acc) {
		// =========Adding responsibility in Runtime through Proxy Pattern:
		acc = new AccountProxy(acc);
		Double currentBal = acc.deposit(amount);

		return currentBal;
	}

	@Override
	public Double withdraw(Double amount, IAccount acc) {
		// =========Adding responsibility in Runtime through Proxy Pattern:
		acc = new AccountProxy(acc);
		Double currentBal = acc.withdraw(amount);

		return currentBal;
	}

	@Override
	public List<Customer> getCustomerList() {
		List<Customer> custList = new ArrayList<>();
		List<Person> pList = dataStore.find(Person.class).asList();
		List<Company> cList = dataStore.find(Company.class).asList();
		custList.addAll(pList);
		custList.addAll(cList);

		return custList;
	}

	public static void main(String[] args) {

	}

	@Override
	public void addInterest(Double value, List<Account> accList) {
		for (Account acc : accList)
			acc.addInterest(value);

		dataStore.save(accList);
	}
}
