package native_jdbc.dao;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import native_jdbc.daoimpl.EmployeeDaoImpl;
import native_jdbc.ds.MysqlDataSource;
import native_jdbc.dto.Department;
import native_jdbc.dto.Employee;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EmployeeDaoTest {
	private static Logger logger = LogManager.getLogger();
	private Connection con;
	private static EmployeeDao dao;
	private static File imagesDir;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		logger.debug("setUpBeforeClass");
		dao = EmployeeDaoImpl.getInstance();
		
		imagesDir = new File(System.getProperty("user.dir") + File.separator + "\\images" + File.separator);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		logger.debug("tearDownAfterClass");
		dao = null;
	}

	@Before
	public void setUp() throws Exception {
		logger.debug("setUp");
		con = MysqlDataSource.getConnection();
	}

	@After
	public void tearDown() throws Exception {
		logger.debug("tearDown");
		con.close();
	}
	@Test
	public void test01SelectEmployeeByDno() {
		logger.debug("test01SelectEmployeeByDno");
		Employee emp = new Employee(1003);
		try {			
			Employee selectedEmp = dao.selectEmployeeByEmpNo(con, emp);
			Assert.assertNotNull(selectedEmp);
			logger.trace(selectedEmp);
		} catch (RuntimeException e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
	}

	@Test
	public void test02SelectEmployeeByAll() throws SQLException {
		logger.debug("test02SelectEmployeeByAll");
		List<Employee> lists = dao.selectEmployeeByAll(con);
		Assert.assertNotEquals(0, lists.size());
		for(Employee e : lists) logger.trace(e);
	}

	@Test
	public void test03SelectEmployeeGroupByDno() {
		logger.debug("test03SelectEmployeeGroupByDno");
		Department dept = new Department();
		dept.setDeptNo(2);
		List<Employee> lists;
		try {
			lists = dao.selectEmployeeGroupByDno(con, dept);
			Assert.assertNotEquals(0, lists.size());
			for(Employee e : lists) logger.trace(e);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// 에러를 확인하고 싶을땐 try-catch
		
	}

	@Test
	public void test06DeleteEmployee() {
		logger.debug("test06DeleteEmployee");
		fail("Not yet implemented");
	}

	@Test
	public void test04InsertEmployee() {
		logger.debug("test04InsertEmployee");
		Employee emp = new Employee(1004, "서현진", "사원", new Employee(1003), 1500000, new Department(1), getImage("seohyunjin.jpg"));
		logger.debug(emp);
		int res = dao.insertEmployee(con, emp);
		Assert.assertEquals(1, res);
	}

	private byte[] getImage(String imgName) {
		File file = new File(imagesDir, imgName);
//		logger.debug(file.getAbsolutePath());
		try(InputStream is = new FileInputStream(file)){
			byte[] pic = new byte[is.available()];
			is.read(pic);
			return pic;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Test
	public void test05UpdateEmployee() {
		logger.debug("test05UpdateEmployee");
		fail("Not yet implemented");
	}

}