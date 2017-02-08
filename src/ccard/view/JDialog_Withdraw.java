package ccard.view;

import javax.swing.JOptionPane;

import ccard.model.BronzeCard;
import ccard.model.GoldCard;
import ccard.model.SilverCard;
import framework.controller.Finco;
import framework.controller.IFinco;
import framework.model.IAccount;

public class JDialog_Withdraw extends javax.swing.JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CardFrm parentframe;
	private String name;
	private IFinco finco = new Finco();

	public JDialog_Withdraw(CardFrm parent, String aname) {
		super(parent);
		parentframe = parent;
		name = aname;

		setTitle("Charge Account");
		setModal(true);
		getContentPane().setLayout(null);
		setSize(277, 134);
		setVisible(false);
		JLabel1.setText("Name");
		getContentPane().add(JLabel1);
		JLabel1.setForeground(java.awt.Color.black);
		JLabel1.setBounds(12, 12, 48, 24);
		JLabel2.setText("Amount");
		getContentPane().add(JLabel2);
		JLabel2.setForeground(java.awt.Color.black);
		JLabel2.setBounds(12, 36, 48, 24);
		JTextField_NAME.setEditable(false);
		getContentPane().add(JTextField_NAME);
		JTextField_NAME.setBounds(84, 12, 156, 20);
		getContentPane().add(JTextField_AMT);
		JTextField_AMT.setBounds(84, 36, 156, 20);
		JButton_OK.setText("OK");
		JButton_OK.setActionCommand("OK");
		getContentPane().add(JButton_OK);
		JButton_OK.setBounds(48, 84, 84, 24);
		JButton_Calcel.setText("Cancel");
		JButton_Calcel.setActionCommand("Cancel");
		getContentPane().add(JButton_Calcel);
		JButton_Calcel.setBounds(156, 84, 84, 24);

		JTextField_NAME.setText(name);

		SymAction lSymAction = new SymAction();
		JButton_OK.addActionListener(lSymAction);
		JButton_Calcel.addActionListener(lSymAction);

	}

	javax.swing.JLabel JLabel1 = new javax.swing.JLabel();
	javax.swing.JLabel JLabel2 = new javax.swing.JLabel();
	javax.swing.JTextField JTextField_NAME = new javax.swing.JTextField();
	javax.swing.JTextField JTextField_AMT = new javax.swing.JTextField();
	javax.swing.JButton JButton_OK = new javax.swing.JButton();
	javax.swing.JButton JButton_Calcel = new javax.swing.JButton();

	class SymAction implements java.awt.event.ActionListener {
		public void actionPerformed(java.awt.event.ActionEvent event) {
			Object object = event.getSource();
			if (object == JButton_OK)
				JButtonOK_actionPerformed(event);
			else if (object == JButton_Calcel)
				JButtonCalcel_actionPerformed(event);
		}
	}

	void JButtonOK_actionPerformed(java.awt.event.ActionEvent event) {
		if (JTextField_AMT.getText() != null && !JTextField_AMT.getText().trim().equals("")) {
			parentframe.amountDeposit = JTextField_AMT.getText();
			Double deposit = Double.parseDouble(parentframe.amountDeposit);

			IAccount acc = finco.getDataStore().find(GoldCard.class).field("accountNum").equal(name).get();
			if (acc == null)
				acc = finco.getDataStore().find(SilverCard.class).field("accountNum").equal(name).get();
			if (acc == null)
				acc = finco.getDataStore().find(BronzeCard.class).field("accountNum").equal(name).get();

			Double currentamount = finco.withdraw(deposit, acc, (x -> x >= 400));
			parentframe.model.setValueAt(String.valueOf(currentamount), parentframe.selection, 4);

			if (currentamount < 0) {
				JOptionPane.showMessageDialog(parentframe.JButton_Withdraw,
						" Account " + name + " : balance is negative: $" + String.valueOf(currentamount) + " !",
						"Warning: negative balance", JOptionPane.WARNING_MESSAGE);
			}
		}
		dispose();
	}

	void JButtonCalcel_actionPerformed(java.awt.event.ActionEvent event) {
		dispose();
	}
}