package framework.model.impl;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;
import org.mongodb.morphia.annotations.Version;

import framework.DAOFacade;
import framework.model.IAccount;
import framework.model.ICustomer;
import framework.model.IEntry;

@Entity
public class Account implements IAccount {
	@Id
	public ObjectId id;
	private String accountNum = "";
	private Double currentBalance = 0d;
	private Double interestRate = 0d;
	private ICustomer cust = null;
	@Version
	@Property("version")
	private Long version;

	public Account(String accountNum, Double currentBalance, Double interestRate, ICustomer cust) {
		this.accountNum = accountNum;
		this.currentBalance = currentBalance;
		this.interestRate = interestRate;
		this.cust = cust;
	}

	@Override
	public Double deposit(Double amount) {
		currentBalance += amount;
		setEntry(amount, TransactionType.DEPOSIT);
		return currentBalance;
	}

	@Override
	public Double withdraw(Double amount) {
		currentBalance -= amount;
		setEntry(amount, TransactionType.WITHDRAW);
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

	private void setEntry(Double amount, TransactionType type) {
		IEntry entry = new Entry(amount, accountNum, type);
		DAOFacade.getInstance().getCommonDb_Datastore().save(entry);
	}

	@Override
	public ICustomer getCustomer() {
		return cust;
	}
}