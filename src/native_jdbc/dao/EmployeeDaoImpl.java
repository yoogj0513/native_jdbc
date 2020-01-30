package native_jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import native_jdbc.dto.Department;
import native_jdbc.dto.Employee;

public class EmployeeDaoImpl implements EmployeeDao {
	private static final EmployeeDaoImpl instance = new EmployeeDaoImpl();
	
	private EmployeeDaoImpl() {}
	
	public static EmployeeDaoImpl getInstance() {
		return instance;
	}

	@Override
	public Employee selectEmployeeByDao(Connection con, Department dept) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Employee> selectEmployeeByAll(Connection con) throws SQLException {
		String sql = "select empno, empname, title, manager, salary, dno from employee";
		List<Employee> list = new ArrayList<>();
		try(PreparedStatement pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()){
			while(rs.next()) {
				list.add(getEmployee(rs));
			}
		}
		return list;
	}

	private Employee getEmployee(ResultSet rs) throws SQLException {
		int empNo = rs.getInt("empno");
		String empName = rs.getString("empname");
		String title = rs.getString("title");
		Employee manager = new Employee(rs.getInt("manager"));
		int salary = rs.getInt("salary");
		Department dept = new Department();
		dept.setDeptNo(rs.getInt("dno"));
		return new Employee(empNo, empName, title, manager, salary, dept);
	}

}
