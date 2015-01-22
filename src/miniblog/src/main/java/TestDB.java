import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;




public class TestDB {
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException
	{
		try
		{
		String url = "jdbc:mysql://192.168.56.2:3306/mini_blog";
		String user = "root";
		String password = "123456";

		// Load the Connector/J driver
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		// Establish connection to MySQL
		Connection conn = DriverManager.getConnection(url, user, password);
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

	}
}
