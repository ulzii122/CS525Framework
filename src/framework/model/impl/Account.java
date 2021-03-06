package framework.model.impl;

import java.util.function.Predicate;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;
import org.mongodb.morphia.annotations.Reference;
import org.mongodb.morphia.annotations.Version;

import framework.DAOFacade;
import framework.model.IAccount;
import framework.model.ICustomer;
import framework.model.IEntry;
import framework.model.IObserver;

@Entity
public class Account implements IAccount, IObserver {
	@Id
	public ObjectId id;
	protected String accountNum = "";
	protected Double currentBalance = 0d;
	protected Double interestRate = 0d;
	@Reference
	protected ICustomer customer;
	@Version
	@Property("version")
	private Long version;

	@Override
	public Double deposit(Double amount, Predicate<Double> amountCheck) {
		currentBalance += amount;
		DAOFacade.getInstance().getDatastore().save(this);
		setEntry(amount, "DEPOSIT");

		return currentBalance;
	}

	@Override
	public Double withdraw(Double amount, Predicate<Double> amountCheck) {
		currentBalance -= amount;
		DAOFacade.getInstance().getDatastore().save(this);
		setEntry(amount, "WITHDRAW");

		return currentBalance;
	}

	@Override
	public void addInterest(Double amount) {
		interestRate += amount;
	}

	@Override
	public Double getBalance() {
		return currentBalance;
	}

	private void setEntry(Double amount, String type) {
		IEntry entry = new Entry();
		entry.setAmount(amount);
		entry.setAccountNum(accountNum);
		entry.setType(type);
		DAOFacade.getInstance().getDatastore().save(entry);
	}

	@Override
	public ICustomer getCustomer() {
		return customer;
	}

	public void setCustomer(ICustomer cust) {
		this.customer = cust;
	}

	public String getAccountNum() {
		return accountNum;
	}

	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}

	public Double getCurrentBalance() {
		return currentBalance;
	}

	public void setCurrentBalance(Double currentBalance) {
		this.currentBalance = currentBalance;
	}

	public Double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(Double interestRate) {
		this.interestRate = interestRate;
	}
}