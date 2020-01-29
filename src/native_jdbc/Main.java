package native_jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import native_jdbc.ds.DBCPDataSource;
import native_jdbc.ds.Hikari_DataSource;
import native_jdbc.ds.Hikari_DataSource2;


public class Main {

	public static void main(String[] args) {
//		connection_hikari();
		
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
