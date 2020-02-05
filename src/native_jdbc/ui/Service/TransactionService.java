package native_jdbc.ui.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.commons.dbcp2.SQLExceptionList;

import native_jdbc.LogUtil;
import native_jdbc.dao.DepartmentDao;
import native_jdbc.dao.EmployeeDao;
import native_jdbc.daoimpl.DepartmentDaoImpl;
import native_jdbc.daoimpl.EmployeeDaoImpl;
import native_jdbc.ds.MysqlDataSource;
import native_jdbc.dto.Department;
import native_jdbc.dto.Employee;

//커넥션이 매개변수에 없을 때의 작성법
public class TransactionService {
	private String deptSql = "INSERT INTO department VALUES(?, ?, ?)";
	private String empSql = "insert into employee(empno, empname, title, manager, salary, dno) values(?, ?, ?, ?, ?, ?)";
	private String deptDeleteSql = "delete from department where deptno = ?";
	private String empDeleteSql = "delete from employee where empno = ?";
	
	public int transAddEmpAndDept (Employee emp, Department dept) {
		int res = 0;
		// 초기화
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try{
			con = MysqlDataSource.getConnection();
			
			// 자동커밋방지, 트랜젝션을 쓸때는 꼭 설정
			// 설정하지 않으면 자동으로 커밋되어 롤백이 되지않음
			con.setAutoCommit(false);
			pstmt = con.prepareStatement(deptSql);
			
			//department insert문
			pstmt.setInt(1, dept.getDeptNo());
			pstmt.setString(2, dept.getDeptName());
			pstmt.setInt(3, dept.getFloor());
			LogUtil.prnLog(pstmt);
			res = pstmt.executeUpdate(); //성공하면 1
			
			//employee insert문
			pstmt = con.prepareStatement(empSql);
			pstmt.setInt(1, emp.getEmpNo());
			pstmt.setString(2, emp.getEmpName());
			pstmt.setString(3, emp.getTitle());
			pstmt.setInt(4, emp.getManager().getEmpNo());
			pstmt.setInt(5, emp.getSalary());
			pstmt.setInt(6, emp.getDept().getDeptNo());
			LogUtil.prnLog(pstmt);
			res += pstmt.executeUpdate(); //성공하면 누적되어 2
			
			if(res == 2) {
				// 성공했기 때문에 커밋
				con.commit();
				LogUtil.prnLog("result " + res + " commit()");
			} else {
				// 실패했을 경우 롤백
				// catch문으로 전가한다.
				throw new SQLException();
			}
		} catch (SQLException e) {
			try {
				con.rollback();
				LogUtil.prnLog("result " + res + " rollback()");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			try{
				con.setAutoCommit(true);
				pstmt.close();
				con.close();
			}catch(Exception e){}
		}
		return res;
	}
	
	public void transAddEmpAndDeptWithConnection(Employee emp, Department dept) {
		DepartmentDao deptDao = DepartmentDaoImpl.getInstace();
		EmployeeDao empDao = EmployeeDaoImpl.getInstance();
		Connection con = null;
		
		try{ // catch문에서도 con사용하므로 try리소스로 선언 못함. 밖에서 선언 후 사용
			con = MysqlDataSource.getConnection();
			con.setAutoCommit(false); 
			deptDao.insertDepartment(con, dept);
			empDao.insertEmployee(con, emp);
			con.commit();
			con.setAutoCommit(true);
			LogUtil.prnLog("result commit()");
		} catch (RuntimeException e) {
			try {
				con.rollback();
				con.setAutoCommit(true);
				throw e;
			} catch (SQLException e1) {}
			LogUtil.prnLog("result rollback()");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public int transRemoveEmpAndDept(Employee emp, Department dept) {
		//순서 : 1.사원 삭제	/ 2. 부서삭제(사원이 소속된)
		int res = -1;
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = MysqlDataSource.getConnection();
			con.setAutoCommit(false);
			
			pstmt = con.prepareStatement(empDeleteSql);
			pstmt.setInt(1, emp.getEmpNo());
			LogUtil.prnLog(pstmt);
			res = pstmt.executeUpdate();
			
			pstmt = con.prepareStatement(deptDeleteSql);
			pstmt.setInt(1, dept.getDeptNo());
			LogUtil.prnLog(pstmt);
			res += pstmt.executeUpdate();
			
			if(res == 2) {
				con.commit();
				LogUtil.prnLog("result " + res + " commit()");
			} else {
				throw new SQLException();
			}
			
		} catch(SQLException e) {
			try {
				con.rollback();
				LogUtil.prnLog("result " + res + " commit()");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				con.setAutoCommit(true);
				pstmt.close();
				con.close();
			} catch (Exception e) {}
		}
		
		return res;
	}
	
	public int transRemoveEmpAndDeptWithConnection(Employee emp, Department dept) {
		DepartmentDao deptDao = DepartmentDaoImpl.getInstace();
		EmployeeDao empDao = EmployeeDaoImpl.getInstance();
		int res = -1;
		Connection con = null;
		try {
			con = MysqlDataSource.getConnection();
			con.setAutoCommit(false);
			res = empDao.deleteEmployee(con, emp);
			res += deptDao.deleteDepartment(con, dept);
			if(res == 2) {
				con.commit();
				LogUtil.prnLog("res " + res + " commit()");
			} else {
				throw new RuntimeException();
			}
			
		} catch (RuntimeException | SQLException e) {
			try {
				con.rollback();
				LogUtil.prnLog("res " + res + " commit()");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				con.setAutoCommit(true);
				con.close();
			} catch (Exception e) {}
		}
		return res;
	}
}
