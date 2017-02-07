package framework.model.impl;

import java.util.Date;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;
import org.mongodb.morphia.annotations.Version;

import framework.model.IEntry;

@Entity
public class Entry implements IEntry {
	@Id
	public ObjectId id;
	private Date date = null;
	private Double amount = 0d;
	private String accountNum = "";
	private TransactionType type = null;
	@Version
	@Property("version")
	private Long version;

	public Entry(Double amount, String accountNum, TransactionType type) {
		this.date = new Date();
		this.amount = amount;
		this.accountNum = accountNum;
		this.type = type;
	}

	public Date getDate() {
		return date;
	}

	public Double getAmount() {
		return amount;
	}

	public String getAccountNum() {
		return accountNum;
	}

	public TransactionType getType() {
		return type;
	}
}