package native_jdbc.ds;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

public class MysqlDataSourceTest {
	private static Logger logger = LogManager.getLogger();

	@Test
	public void testGetConnection() {
		try (Connection con = MysqlDataSource.getConnection()){
			// 성공했을때 log에 찍기
			logger.debug(con);
			Assert.assertNotNull(con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
