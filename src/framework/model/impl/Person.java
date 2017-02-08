package framework.model.impl;

import framework.model.IPerson;

public class Person extends Customer implements IPerson {
	private String birthDate = "";

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
}