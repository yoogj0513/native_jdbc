package native_jdbc.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import native_jdbc.dao.DepartmentDao;
import native_jdbc.dto.Department;

public class DepartmentDaoImpl implements DepartmentDao {
	private static Logger logger = LogManager.getLogger();
	
	// singleton pattern (하나만 생성)
	// 팩토리 패턴
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
			logger.trace(pstmt);
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

	@Override
	public int insertDepartment(Connection con, Department department) throws SQLException {
		String sql = "INSERT INTO department VALUES(?, ?, ?)";
		int res = -1;
		try(PreparedStatement pstmt = con.prepareStatement(sql)){
			pstmt.setInt(1, department.getDeptNo());
			pstmt.setString(2, department.getDeptName());
			pstmt.setInt(3, department.getFloor());
			logger.trace(pstmt);
			res = pstmt.executeUpdate();
		}
		return res;
	}

	@Override
	public int updateDepartment(Connection con, Department department) throws SQLException {
		String sql = "update department set deptname = ?, floor = ? where deptno = ?";
		int res = -1;
		try(PreparedStatement pstmt = con.prepareStatement(sql)){
			pstmt.setString(1, department.getDeptName());
			pstmt.setInt(2, department.getFloor());
			pstmt.setInt(3, department.getDeptNo());
			logger.trace(pstmt);
			res = pstmt.executeUpdate();
		}
		return res;
	}

	@Override
	public int deleteDepartment(Connection con, Department department) throws SQLException {
		String sql = "delete from department where deptno = ?";
		int res = -1;
		try(PreparedStatement pstmt = con.prepareStatement(sql)){
			pstmt.setInt(1, department.getDeptNo());
			logger.trace(pstmt);
			res = pstmt.executeUpdate();
		}
		return res;
	}

	@Override
	public Department selectDepartmentByNo(Connection con, int dno) throws SQLException {
		String sql = "select deptno, deptname, floor from department where deptno=?";
		try(PreparedStatement pstmt = con.prepareStatement(sql)){
			pstmt.setInt(1, dno);
			logger.trace(pstmt);
			try(ResultSet rs = pstmt.executeQuery()){
				if(rs.next()) {
					return getDepartment(rs);
				}
			}
		}
		return null;
	}

}
