package native_jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import native_jdbc.dto.Department;

public class DepartmentDaoImpl implements DepartmentDao {
	// singleton pattern (하나만 생성)
	private static final DepartmentDaoImpl instace = new DepartmentDaoImpl();
	
	private DepartmentDaoImpl() {}

	public static DepartmentDaoImpl getInstace() {
		return instace;
	}

	@Override
	public List<Department> selectDepartmentByAll(Connection con) throws SQLException {
		String sql = "select deptno, deptname, floor from department";
		List<Department> list = new ArrayList<Department>();

		try (PreparedStatement pstmt = con.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
			while (rs.next()) {
				list.add(getDepartment(rs));
			}
		}
		return list;
	}

	private Department getDepartment(ResultSet rs) throws SQLException {
		int deptNo = rs.getInt("deptno");
		String deptName = rs.getString("deptname");
		int floor = rs.getInt("floor");
		return new Department(deptNo, deptName, floor);
	}

}
