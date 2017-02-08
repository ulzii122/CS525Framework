package ccard.model;

import java.util.ArrayList;
import java.util.List;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.annotations.Reference;

import framework.controller.Finco;
import framework.model.impl.Account;
import framework.model.impl.Customer;
import framework.model.impl.Entry;

public abstract class CreditCard extends Account {
	public String expDate = "";
	public Double creditLimit = 0d;
	@Reference("card")
	public List<Statement> stateList = new ArrayList<>();
	public int minPayment = 12;
	public int monthlyRate = 8;

	public String generateReport(Customer cust, String type) {
		StringBuilder billstring = new StringBuilder();
		Double prevAmount = 0d;

		for (Statement s1 : stateList)
			prevAmount = s1.newBalance;

		Statement st = new Statement();
		st.cardNumber = accountNum;
		st.previousBalance = prevAmount;
		Datastore ds = new Finco().getDataStore();

		List<Entry> entryList = ds.find(Entry.class).field("accountNum").equal(accountNum).asList();

		for (Entry ent : entryList) {
			if (ent.getType().equals("DEPOSIT"))
				st.totalCredit += ent.getAmount();
			else
				st.totalCharge += ent.getAmount();
		}

		st.newBalance = st.previousBalance - st.totalCredit + st.totalCharge
				+ monthlyRate * (st.previousBalance - st.totalCredit) / 100;

		st.totalDue = st.newBalance * minPayment / 100;

		billstring.append("Name= " + cust.name + "\r\n");
		billstring.append("Address= " + cust.street + ", " + cust.city + ", " + cust.state + ", " + cust.zip + "\r\n");
		billstring.append("CC number= " + accountNum + "\r\n");
		billstring.append("CC type= " + type + "\r\n");
		billstring.append("Previous balance = $ " + prevAmount + "\r\n");
		billstring.append("Total Credits = $ " + st.totalCredit + "\r\n");
		billstring.append("Total Charges = $ " + st.totalCharge + "\r\n");
		billstring.append("New balance = $ " + st.newBalance + "\r\n");
		billstring.append("Total amount due = $ " + st.totalDue + "\r\n");
		billstring.append("\r\n");
		billstring.append("\r\n");

		stateList.add(st);
		ds.save(st);
		ds.save(this);

		return billstring.toString();
	}
}
