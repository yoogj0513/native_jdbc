package native_jdbc.ui.Service;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import native_jdbc.LogUtil;
import native_jdbc.dto.Department;
import native_jdbc.dto.Employee;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TransactionServiceTest {
	private static TransactionService service;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		service = new TransactionService();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		service = null;
	}

	@Test
	public void test01TransAddEmpAndDept_DeptFail() { //부서테스트
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		int res = 0;
		Department dept = new Department(1, "마케팅", 8); //존재하는 부서
		Employee emp = new Employee(1004, "수지", "사원", new Employee(1003), 150000, dept);
		
		res = service.transAddEmpAndDept(emp, dept);
		Assert.assertNotEquals(2, res);
	}
	
	@Test
	public void test02TransAddEmpAndDept_EmpFail() { //직원 테스트
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		int res = 0;
		Department dept = new Department(5, "마케팅", 8); 
		Employee emp = new Employee(4377, "수지", "사원", new Employee(1003), 150000, dept); // 존재하는 직원
		
		res = service.transAddEmpAndDept(emp, dept);
		Assert.assertNotEquals(2, res);
	}
	
	@Test
	public void test03TransAddEmpAndDept_Success() { //둘다테스트
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		int res = 0;
		Department dept = new Department(5, "마케팅", 8); 
		Employee emp = new Employee(1004, "수지", "사원", new Employee(1003), 150000, dept);
		
		res = service.transAddEmpAndDept(emp, dept);
		Assert.assertEquals(2, res);
	}
	
	@Test
	public void test04TransRemoveEmpAndDept_DeptFail() {
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		int res = -1;
		Department dept = new Department(5);
		Employee emp = new Employee(1001); //존재하지 않는 직원
		
		res = service.transRemoveEmpAndDept(emp, dept);
		Assert.assertNotEquals(2, res);
	}
	
	@Test
	public void test05TransRemoveEmpAndDept_EmployeeFail() {
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		int res = -1;
		Department dept = new Department(7);//존재하지 않는 부서
		Employee emp = new Employee(1004);
		
		res = service.transRemoveEmpAndDept(emp, dept);
		Assert.assertNotEquals(2, res);
	}
	
	@Test
	public void test06TransRemoveEmpAndDept_Success() {
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		int res = -1;
		Department dept = new Department(5);
		Employee emp = new Employee(1004);
		
		res = service.transRemoveEmpAndDept(emp, dept);
		Assert.assertEquals(2, res);
	}
	
	
	@Test(expected = RuntimeException.class) // 예외를 기대함 //당연히 예외가 발생하는 부분
	public void test07TransAddEmpAndDeptWithConnection_DeptFail() {
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		int res = 0;
		Department dept = new Department(1, "마케팅", 8);// 존재하는 부서
		Employee emp = new Employee(1005, "이유영", "사원", new Employee(1003), 1500000, dept);

		service.transAddEmpAndDeptWithConnection(emp, dept);
	}
	
	@Test(expected = RuntimeException.class) //예외를 기대함//당연히 예외가 발생하는 부분
	public void test08TransAddEmpAndDeptWithConnection_EmpFail() {
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		int res = 0;
		Department dept = new Department(6, "마케팅6", 8);
		Employee emp = new Employee(4377, "이유영", "사원", new Employee(1003), 1500000, dept);// 존재하는 사원

		service.transAddEmpAndDeptWithConnection(emp, dept);
		Assert.assertNotEquals(2, res);
	}

	@Test
	public void test09TransAddEmpAndDeptWithConnection_Success() {
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");

		Department dept = new Department(6, "마케팅6", 6);
		Employee emp = new Employee(1005, "이유영", "사원", new Employee(1003), 1500000, dept);

		service.transAddEmpAndDeptWithConnection(emp, dept);
	}
	
	@Test
	public void test10TransRemoveEmpAndDeptWithConnection_DeptFail() {
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		
		Department dept = new Department(6);
		Employee emp = new Employee(1001);
		
		service.transRemoveEmpAndDeptWithConnection(emp, dept);
	}
	
	@Test
	public void test11TransRemoveEmpAndDeptWithConnection_EmployeeFail() {
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		Department dept = new Department(8);
		Employee emp = new Employee(1005);
		
		service.transRemoveEmpAndDeptWithConnection(emp, dept);
	}
	
	@Test
	public void test12TransRemoveEmpAndDeptWithConnection_Success() {
		LogUtil.prnLog(Thread.currentThread().getStackTrace()[1].getMethodName() + "()");
		Department dept = new Department(6);
		Employee emp = new Employee(1005);
		
		service.transRemoveEmpAndDeptWithConnection(emp, dept);
	}

}
