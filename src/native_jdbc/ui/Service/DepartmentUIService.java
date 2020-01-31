package native_jdbc.ui.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;

import native_jdbc.dao.DepartmentDao;
import native_jdbc.dao.DepartmentDaoImpl;
import native_jdbc.dao.EmployeeDao;
import native_jdbc.dao.EmployeeDaoImpl;
import native_jdbc.ds.Hikari_DataSource2;
import native_jdbc.dto.Department;
import native_jdbc.dto.Employee;

public class DepartmentUIService {
	private Connection con;
	private DepartmentDao deptDao;
	private EmployeeDao empDao;

	public DepartmentUIService() {
		try {
			con = Hikari_DataSource2.getConnection();
			deptDao = DepartmentDaoImpl.getInstace();
			empDao = EmployeeDaoImpl.getInstance();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "접속 정보 확인");
		}
	}
	
	public List<Employee> showEmployeeGroupByDno(Department dept){
		try {
			return empDao.selectEmployeeGroupByDno(con, dept);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Department> showDepartments(){
		try {
			return deptDao.selectDepartmentByAll(con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void addDepartment(Department newDept) throws SQLException {
		deptDao.insertDepartment(con, newDept);
	}
	
	public void modifyDepartment(Department dept) throws SQLException {
		deptDao.updateDepartment(con, dept);
	}
	
	public void removeDepartment(Department dept) throws SQLException {
		deptDao.deleteDepartment(con, dept);
	}
}
