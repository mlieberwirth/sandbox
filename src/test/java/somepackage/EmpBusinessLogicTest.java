package somepackage;

import org.junit.Assert;
import org.junit.Test;

public class EmpBusinessLogicTest {

	EmpBusinessLogic empBusinessLogic = new EmpBusinessLogic();
	EmployeeDetails employee = new EmployeeDetails();

	@Test
	public void calculateAppraisal() {
		employee.setName("Rajeev");
		employee.setAge(25);
		
		employee.setMonthlySalary(8000);

		double appraisal = empBusinessLogic.calculateAppraisal(employee);
		Assert.assertEquals(500, appraisal, 0.0);
	}

	@Test
	public void calculateYearlySalary() {
		employee.setName("Rajeev");
		employee.setAge(25);
		employee.setMonthlySalary(8000);

		double salary = empBusinessLogic.calculateYearlySalary(employee);
		Assert.assertEquals(96000, salary, 0.0);
		
//		Assert.fail();
	}
}
