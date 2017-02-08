package ccard.view;

import java.awt.Frame;

import ccard.model.BronzeCard;
import ccard.model.CreditCard;
import ccard.model.GoldCard;
import ccard.model.SilverCard;
import framework.controller.Finco;
import framework.controller.IFinco;
import framework.model.IAccount;
import framework.model.impl.Customer;

public class JDialogGenBill extends javax.swing.JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JDialogGenBill(Frame parent) {
		super(parent);

		getContentPane().setLayout(null);
		setSize(405, 367);
		setVisible(false);
		getContentPane().add(JScrollPane1);
		JScrollPane1.setBounds(24, 24, 358, 240);
		JScrollPane1.getViewport().add(JTextField1);
		JTextField1.setBounds(0, 0, 355, 237);
		JButton_OK.setText("OK");
		JButton_OK.setActionCommand("OK");
		getContentPane().add(JButton_OK);
		JButton_OK.setBounds(156, 276, 96, 24);

		IFinco finco = new Finco();
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

		// generate the string for the monthly bill

		JTextField1.setText(sb.toString());
		// }}

		// {{REGISTER_LISTENERS
		SymAction lSymAction = new SymAction();
		JButton_OK.addActionListener(lSymAction);
		// }}
	}

	// {{DECLARE_CONTROLS
	javax.swing.JScrollPane JScrollPane1 = new javax.swing.JScrollPane();
	javax.swing.JTextField JTextField1 = new javax.swing.JTextField();
	javax.swing.JButton JButton_OK = new javax.swing.JButton();
	// }}

	class SymAction implements java.awt.event.ActionListener {
		public void actionPerformed(java.awt.event.ActionEvent event) {
			Object object = event.getSource();
			if (object == JButton_OK)
				JButtonOK_actionPerformed(event);
		}
	}

	void JButtonOK_actionPerformed(java.awt.event.ActionEvent event) {
		dispose();

	}
}
