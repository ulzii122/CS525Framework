package framework.controller;

import org.mongodb.morphia.Datastore;

import framework.DAOFacade;
import framework.model.IAccount;
import framework.model.ICustomer;
import framework.model.impl.Account;

public class Finco implements IFinco {

	private Datastore dataStore = null;

	public Finco() {
		dataStore = DAOFacade.getInstance().getDatastore();
	}

	@Override
	public void addPersonalAccount(ICustomer cust, IAccount acc) {
		dataStore.save(acc);
		dataStore.save(cust);
	}

	@Override
	public void addCompanyAccount(ICustomer cust, IAccount acc) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addInterest(Double value) {
		// TODO Auto-generated method stub

	}

	@Override
	public Double deposit(Double amount, String accNo) {
		IAccount acc = dataStore.find(Account.class).field("accountNum").equal(accNo).get();
		acc.deposit(amount);
		dataStore.save(acc);
		return null;
	}

	@Override
	public Double withdraw(Double amount, String accNo) {
		// TODO Auto-generated method stub
		return null;
	}

	public static void main(String[] args) {

	}
}
