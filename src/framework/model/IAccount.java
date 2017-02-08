package framework.model;

import java.util.function.Predicate;

public interface IAccount {

	public Double deposit(Double amount, Predicate<Double> amountCheck);

	public Double withdraw(Double amount, Predicate<Double> amountCheck);

	public Double getBalance();

	public ICustomer getCustomer();

	public void setCustomer(ICustomer cust);

	public void setAccountNum(String accountNum);

	public String getAccountNum();
}
