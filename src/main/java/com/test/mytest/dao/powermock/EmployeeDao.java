package com.test.mytest.dao.powermock;

import com.test.mytest.model.powermock.Employee;

public class EmployeeDao {
	
	public int getTotal() {
		throw new UnsupportedOperationException();
	}
	
	public void addEmployee(Employee employee) {
		throw new UnsupportedOperationException(); 
	}

}
