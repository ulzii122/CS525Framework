package ccard.controller;

import javax.swing.JOptionPane;

import ccard.model.BronzeCard;
import ccard.model.CreditCard;
import ccard.model.GoldCard;
import ccard.model.SilverCard;
import ccard.view.CardFrm;
import framework.controller.Finco;
import framework.controller.IFinco;
import framework.model.IAccount;
import framework.model.impl.Customer;
import framework.model.impl.Person;

public class CardController {
	private IFinco finco;
	private CardFrm parentframe;

	public CardController(CardFrm parentframe) {
		this.parentframe = parentframe;
		finco = new Finco();
	}

	public void createNewCCard() {
		CreditCard card = null;
		if (parentframe.accountType.equals("Gold")) {
			card = new GoldCard();
			card.minPayment = 10;
			card.monthlyRate = 6;
		} else if (parentframe.accountType.equals("Silver")) {
			card = new SilverCard();
			card.minPayment = 12;
			card.monthlyRate = 8;
		} else {
			card = new BronzeCard();
			card.minPayment = 14;
			card.monthlyRate = 10;
		}

		card.expDate = parentframe.expdate;

		IAccount acc = card;
		acc.setAccountNum(parentframe.ccnumber);

		Customer cust = new Person();
		cust.city = parentframe.city;
		cust.email = parentframe.email;
		cust.name = parentframe.clientName;
		cust.street = parentframe.street;
		cust.zip = parentframe.zip;
		cust.state = parentframe.state;
		cust.addAccount(acc);

		finco.addAccount(cust, acc);
	}

	public void deposit(String accNum, Double amount) {
		IAccount acc = finco.getDataStore().find(GoldCard.class).field("accountNum").equal(accNum).get();
		if (acc == null)
			acc = finco.getDataStore().find(SilverCard.class).field("accountNum").equal(accNum).get();
		if (acc == null)
			acc = finco.getDataStore().find(BronzeCard.class).field("accountNum").equal(accNum).get();

		Double currentamount = finco.deposit(amount, acc, (x -> x >= 400));
		parentframe.model.setValueAt(String.valueOf(currentamount), parentframe.selection, 4);
	}

	public void withdraw(String accNum, Double amount) {
		IAccount acc = finco.getDataStore().find(GoldCard.class).field("accountNum").equal(accNum).get();
		if (acc == null)
			acc = finco.getDataStore().find(SilverCard.class).field("accountNum").equal(accNum).get();
		if (acc == null)
			acc = finco.getDataStore().find(BronzeCard.class).field("accountNum").equal(accNum).get();

		Double currentamount = finco.withdraw(amount, acc, (x -> x >= 400));
		parentframe.model.setValueAt(String.valueOf(currentamount), parentframe.selection, 4);

		if (currentamount < 0) {
			JOptionPane.showMessageDialog(parentframe.JButton_Withdraw,
					" Account " + accNum + " : balance is negative: $" + String.valueOf(currentamount) + " !",
					"Warning: negative balance", JOptionPane.WARNING_MESSAGE);
		}
	}

	public String generateReport() {
		StringBuilder sb = new StringBuilder();
		CreditCard card = null;

		for (Customer cust : finco.getCustomerList()) {
			for (IAccount acc : cust.getAccList()) {
				if (acc instanceof GoldCard) {
					card = finco.getDataStore().find(GoldCard.class).field("accountNum").equal(acc.getAccountNum())
							.get();
				} else if (acc instanceof BronzeCard) {
					card = finco.getDataStore().find(BronzeCard.class).field("accountNum").equal(acc.getAccountNum())
							.get();
				} else if (acc instanceof SilverCard) {
					card = finco.getDataStore().find(SilverCard.class).field("accountNum").equal(acc.getAccountNum())
							.get();
				}
				if (card != null)
					sb.append(card.generateReport(cust,
							(acc instanceof GoldCard ? "Gold" : (acc instanceof SilverCard ? "Silver" : "Bronze"))));
			}
		}

		return sb.toString();
	}
}
