package ccard.model;

import java.util.Date;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;
import org.mongodb.morphia.annotations.Reference;
import org.mongodb.morphia.annotations.Version;

@Entity
public class Statement {

	@Id
	public ObjectId id;
	public Double previousBalance = 0d;
	public Double totalCharge = 0d;
	public Double totalCredit = 0d;
	public Double newBalance = 0d;
	public Double totalDue = 0d;
	public Date generateDate = new Date();
	public String cardNumber = "";
	@Reference
	public CreditCard card;
	@Version
	@Property("version")
	private Long version;
}
