package framework.model;

public interface IAccount {

	public Double deposit(Double amount);

	public Double withdraw(Double amount);

	public void addInterest(Double amount);

	public Double getBalance();

	public ICustomer getCustomer();
}
