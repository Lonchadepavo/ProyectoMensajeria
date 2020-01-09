package loncha.proyectomensajeria.modelo.conexiones;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexionDB {
	static Connection conexionDB = null;
	
	public static String userDB = "adminmensajeria";
	public static String passwordDB = "admin";
	
	//Método para conectar a la base de datos
	public static Connection conectarBBDD(String db, String user, String password) {
		try {
			Class.forName("org.postgresql.Driver").newInstance();
			conexionDB = DriverManager.getConnection("jdbc:postgresql://localhost:5433/" + db, user, password);
			
			return conexionDB;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	//Método para cerrar la conexión a la base de datos
	public static boolean connectionDrop(Connection conexion) {
		try {
			conexion.close();
			conexion = null;
			return true;
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
