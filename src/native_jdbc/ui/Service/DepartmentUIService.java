package native_jdbc.ui.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;

import native_jdbc.dao.DepartmentDao;
import native_jdbc.dao.DepartmentDaoImpl;
import native_jdbc.ds.Hikari_DataSource2;
import native_jdbc.dto.Department;

public class DepartmentUIService {
	private Connection con;
	private DepartmentDao deptDao;

	public DepartmentUIService() {
		try {
			con = Hikari_DataSource2.getConnection();
			deptDao = DepartmentDaoImpl.getInstace();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "접속 정보 확인");
		}
	}
	
	public List<Department> showDepartments(){
		try {
			return deptDao.selectDepartmentByAll(con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
