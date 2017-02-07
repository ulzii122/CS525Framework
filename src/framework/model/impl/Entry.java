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
	private Date date = new Date();
	private Double amount = 0d;
	private String accountNum = "";
	private String type = "";
	@Version
	@Property("version")
	private Long version;

	public Date getDate() {
		return date;
	}

	public Double getAmount() {
		return amount;
	}

	public String getAccountNum() {
		return accountNum;
	}

	public String getType() {
		return type;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}

	public void setType(String type) {
		this.type = type;
	}
}