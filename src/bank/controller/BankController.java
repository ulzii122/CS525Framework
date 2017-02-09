package bank.controller;

import javax.swing.JOptionPane;

import bank.model.CheckingAccount;
import bank.model.SavingAccount;
import bank.view.BankFrm;
import framework.controller.Finco;
import framework.controller.IFinco;
import framework.model.IAccount;
import framework.model.impl.Company;
import framework.model.impl.Customer;
import framework.model.impl.Person;

public class BankController {
	private IFinco finco;
	private BankFrm parentframe;

	public BankController(BankFrm parentframe) {
		this.parentframe = parentframe;
		finco = new Finco();
	}

	public void createNewAccount(String type) {
		IAccount acc = null;
		if (parentframe.accountType.equals("Ch"))
			acc = new CheckingAccount();
		else
			acc = new SavingAccount();

		acc.setAccountNum(parentframe.accountnr);

		Customer cust = null;
		if (type.equals("COMPANY"))
			cust = new Company();
		else
			cust = new Person();

		cust.city = parentframe.city;
		cust.email = parentframe.email;
		cust.name = parentframe.clientName;
		cust.street = parentframe.street;
		cust.zip = parentframe.zip;
		cust.state = parentframe.state;
		cust.addAccount(acc);

		// =======Calls Finco method for adding account:
		finco.addAccount(cust, acc);
	}

	public void deposit(String accNum, Double amount) {
		IAccount acc = finco.getDataStore().find(SavingAccount.class).field("accountNum").equal(accNum).get();
		if (acc == null)
			acc = finco.getDataStore().find(CheckingAccount.class).field("accountNum").equal(accNum).get();

		// =======Calls Finco method for deposit:
		Double currentamount = finco.deposit(amount, acc, (x -> x >= 500));

		parentframe.model.setValueAt(String.valueOf(currentamount), parentframe.selection, 5);
	}

	public void withdraw(String accNum, Double amount) {
		IAccount acc = finco.getDataStore().find(SavingAccount.class).field("accountNum").equal(accNum).get();
		if (acc == null)
			acc = finco.getDataStore().find(CheckingAccount.class).field("accountNum").equal(accNum).get();

		// =======Calls Finco method for withdrawal:
		Double currentamount = finco.withdraw(amount, acc, (x -> x >= 500));

		parentframe.model.setValueAt(String.valueOf(currentamount), parentframe.selection, 5);

		if (currentamount < 0) {
			JOptionPane.showMessageDialog(parentframe.JButton_Withdraw,
					" Account " + accNum + " : balance is negative: $" + String.valueOf(currentamount) + " !",
					"Warning: negative balance", JOptionPane.WARNING_MESSAGE);
		}
	}
}
