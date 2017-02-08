package framework.model.impl;

import framework.model.ICompany;

public class Company extends Customer implements ICompany {
	private int numOfEmployee = 0;

	public int getNumOfEmployee() {
		return numOfEmployee;
	}

	public void setNumOfEmployee(int numOfEmployee) {
		this.numOfEmployee = numOfEmployee;
	}
}