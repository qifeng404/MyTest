package com.test.mytest.service.powermock;

import com.test.mytest.dao.powermock.EmployeeDao;
import com.test.mytest.model.powermock.Employee;

public class EmployeeService {
	
	private EmployeeDao employeeDao;

	public EmployeeService(EmployeeDao employeeDao) {
		this.employeeDao = employeeDao;
	}

	/**
	 * 获取所有员工的数量. * @return
	 */
	public int getTotalEmployee() {
		return employeeDao.getTotal();
	}
	
	public void createEmployee(Employee employee) {
		employeeDao.addEmployee(employee); 
	}
}
