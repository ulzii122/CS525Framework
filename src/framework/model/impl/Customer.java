package framework.model.impl;

import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;
import org.mongodb.morphia.annotations.Version;

import framework.model.IAccount;
import framework.model.ICustomer;

@Entity
public abstract class Customer implements ICustomer {
	@Id
	public ObjectId id;
	public int numOfEmployee = 0;
	@Version
	@Property("version")
	private Long version;
	public String name = "";
	public String street = "";
	public String city = "";
	public String zip = "";
	public String email = "";
	public List<IAccount> accList = null;

	@Override
	public void addAccount(IAccount acc) {
		accList.add(acc);
	}

	@Override
	public void removeAccount(IAccount acc) {
		accList.remove(acc);
	}
}
