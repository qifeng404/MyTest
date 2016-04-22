package com.test.mytest.service.powermock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.test.mytest.dao.powermock.EmployeeDao;
import com.test.mytest.model.powermock.Employee;
import com.test.mytest.service.powermock.EmployeeService;


@RunWith(PowerMockRunner.class)
@PrepareForTest(EmployeeService.class)
public class EmployeeServiceTest {

	@Test
	public void testGetTotalEmployeeWithMock() {
		EmployeeDao employeeDao = PowerMockito.mock(EmployeeDao.class);
		try {
			PowerMockito.whenNew(EmployeeDao.class).withNoArguments().thenReturn(employeeDao);
			PowerMockito.when(employeeDao.getTotal()).thenReturn(10);
			EmployeeService service = new EmployeeService(employeeDao);
			int total = service.getTotalEmployee();
			assertEquals(10, total);
		} catch (Exception e) {
			fail("测试失败.");
		}
	}

	@Test
	public void testCreateEmployee() {
		EmployeeDao employeeDao = PowerMockito.mock(EmployeeDao.class);
		Employee employee = new Employee(123L, "test");
		PowerMockito.doNothing().when(employeeDao).addEmployee(employee);
		EmployeeService service = new EmployeeService(employeeDao);
		service.createEmployee(employee);
		// verify the method invocation.
		Mockito.verify(employeeDao).addEmployee(employee);
	}

}
