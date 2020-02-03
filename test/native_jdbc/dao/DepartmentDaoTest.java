package native_jdbc.dao;

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

import native_jdbc.daoimpl.DepartmentDaoImpl;
import native_jdbc.ds.MysqlDataSource;
import native_jdbc.dto.Department;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DepartmentDaoTest {
	private static Logger logger = LogManager.getLogger();
	private Connection con;
	private static DepartmentDao dao;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		logger.debug("setUpBeforeClass");
		dao = DepartmentDaoImpl.getInstace();
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
	public void test01SelectDepartmentByAll() {
		logger.debug("test01SelectDepartmentByAll");
		try {
			List<Department> lists = dao.selectDepartmentByAll(con);
			Assert.assertNotEquals(-1, lists.size());
			for(Department d : lists) {
				logger.trace(d);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void test02InsertDepartment() throws SQLException {
		logger.debug("testInsertDepartment");
		Department department = new Department(5, "마케팅", 4);
		int res = dao.insertDepartment(con, department);
		Assert.assertEquals(1, res);
	}

	@Test
	public void test03UpdateDepartment() throws SQLException {
		logger.debug("testUpdateDepartment");
		Department department = new Department(5, "마케팅3", 4);
		int res = dao.updateDepartment(con, department);
		Assert.assertEquals(1, res);
	}

	@Test
	public void test04DeleteDepartment() throws SQLException {
		logger.debug("testDeleteDepartment");
		Department department = new Department(5, "마케팅3", 4);
		int res = dao.deleteDepartment(con, department);
		Assert.assertEquals(1, res);
	}
	
	@Test
	public void test05SelectDepartmentByNo() throws SQLException {
		logger.debug("test05SelectDepartmentByNo()");
		Department department = dao.selectDepartmentByNo(con, 1);
		Assert.assertNotNull(department);
		logger.trace(department);
	}

}
