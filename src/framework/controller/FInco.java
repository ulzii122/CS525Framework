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
	public void addAccount(ICustomer cust, IAccount acc) {
		dataStore.save(acc);
		dataStore.save(cust);
	}

	@Override
	public void addInterest(Double value) {
		// TODO Auto-generated method stub

	}

	@Override
	public Double deposit(Double amount, String accNo) {
		IAccount acc = dataStore.find(Account.class).field("accountNum").equal(accNo).get();
		// =========Adding responsibility in Runtime through Proxy Pattern:
		IAccount proxy = new AccountProxy(acc);
		Double currentBal = proxy.deposit(amount);
		dataStore.save(acc);

		return currentBal;
	}

	@Override
	public Double withdraw(Double amount, String accNo) {
		// TODO Auto-generated method stub
		return null;
	}

	public static void main(String[] args) {

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
}
