package native_jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import native_jdbc.ds.C3P0DataSource;
import native_jdbc.ds.DBCPDataSource;
import native_jdbc.ds.Hikari_DataSource;
import native_jdbc.ds.Hikari_DataSource2;

public class DepartmentMain {

	public static void main(String[] args) {
		connection_hikari();
		connection_dbcp();
		connection_c3p0();
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
		String deleteSql = "delete from department where deptno=?";
		String updateSql = "update department set deptname=?, floor=? where deptno=?";
		String insertSql = "insert into department values(?,?,?)";
		try (Connection con = Hikari_DataSource.getConnection();
				PreparedStatement pstmt = con.prepareStatement(insertSql);
				PreparedStatement pstmt2 = con.prepareStatement(updateSql);
				PreparedStatement pstmt3 = con.prepareStatement(deleteSql)){
			pstmt.setInt(1, 7);
			pstmt.setString(2, "행정");
			pstmt.setInt(3, 30);
			
			pstmt2.setString(1, "마케팅");
			pstmt2.setInt(2, 12);
			pstmt2.setInt(3, 7);
			
			pstmt3.setInt(1, 7);
			
			System.out.println("연결 성공 " + pstmt + "\n" + pstmt2 + "\n" + pstmt3);
			
			int res = pstmt.executeUpdate();
			int res2 = pstmt2.executeUpdate();
			int res3 = pstmt3.executeUpdate();
			System.out.println("수정 성공 " + res + ", " + res2 + ", " + res3);
		} catch (SQLException e) {
			System.err.println("해당 데이터베이스가 존재하지 않거나 계정 및 비밀번호 확인 요망" + e.getErrorCode());
			e.printStackTrace();
		}
		
		try (Connection con2 = Hikari_DataSource2.getConnection();){
			System.out.println(con2);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
