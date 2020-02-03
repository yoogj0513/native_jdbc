package native_jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JDialog;

import native_jdbc.dao.DepartmentDao;
import native_jdbc.dao.EmployeeDao;
import native_jdbc.daoimpl.DepartmentDaoImpl;
import native_jdbc.daoimpl.EmployeeDaoImpl;
import native_jdbc.ds.C3P0DataSource;
import native_jdbc.ds.DBCPDataSource;
import native_jdbc.ds.Hikari_DataSource;
import native_jdbc.ds.Hikari_DataSource2;
import native_jdbc.dto.Department;
import native_jdbc.dto.Employee;
import native_jdbc.ui.DlgEmployee;


public class Main {

	public static void main(String[] args) throws SQLException {
		
		try (Connection con = Hikari_DataSource2.getConnection();){
//			소속부서사원검색테스트(con);
			
			DepartmentDao dao = DepartmentDaoImpl.getInstace();
			//추가할 부서 정보
			Department newDept = new Department(6, "지원", 60);
//			dao.insertDepartment(con, newDept);
//			dao.updateDepartment(con, newDept);
			dao.deleteDepartment(con, newDept);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			System.out.println(e.getErrorCode());
		}
		
		
		
		
		
		
		
		
		
		
		
		
		// 객체생성 2개가 생김. 사용할 때마다 새로운 객체가 생성됨. 메모리 사용이 많아짐
		// 실수로 쓰기 않게하기 위해 EmployeeDao를 private으로 설정
//		EmployeeDao dao1 = new EmployeeDaoImpl();
//		EmployeeDao dao2 = new EmployeeDaoImpl();
//		System.out.println(dao1);
//		System.out.println(dao2);
		
		// 싱클톤(singleton pattern) : 하나의 객체를 생성되어 재활용. 메모리를 효율적으로 사용하기 위해
//		EmployeeDao dao3 = EmployeeDaoImpl.getInstance();
//		EmployeeDao dao4 = EmployeeDaoImpl.getInstance();
//		System.out.println(dao3);
//		System.out.println(dao4);
		
		
//		EmployeeDao dao3 = EmployeeDaoImpl.getInstance();
//		List<Employee> list = dao3.selectEmployeeByAll(Hikari_DataSource2.getConnection());
//		for(Employee e:list) {
//			System.out.println(e);
//		}
	
//		for(Employee e:dao3.selectEmployeeByAll(Hikari_DataSource2.getConnection())) {
//			System.out.println(e);
//		}
		
//		connection_hikari();
//		connection_dbcp();
//		connection_c3p0();
	}

	private static void 소속부서사원검색테스트(Connection con) throws SQLException {
		EmployeeDao dao = EmployeeDaoImpl.getInstance();
		Department dept = new Department();
		dept.setDeptNo(2);
		List<Employee> list = dao.selectEmployeeGroupByDno(con, dept);
		
		for(Employee e:list) {
			System.out.println(e);
		}
		
		DlgEmployee dialog = new DlgEmployee();
		dialog.setEmpList(list);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setVisible(true);
	}

	private static void connection_c3p0() {
		try (Connection con = C3P0DataSource.getConnection();){
			System.out.println(con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void connection_dbcp() {
		try (Connection con = DBCPDataSource.getConnection();){
			System.out.println(con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void connection_hikari() {
		try (Connection con = Hikari_DataSource.getConnection();){
			System.out.println(con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try (Connection con2 = Hikari_DataSource2.getConnection();){
			System.out.println(con2);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
