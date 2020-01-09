package loncha.proyectomensajeria.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import loncha.proyectomensajeria.controlador.Intermediario;
import loncha.proyectomensajeria.modelo.conexiones.ConexionDB;
import loncha.proyectomensajeria.modelo.sentenciassql.SentenciasSQL;

public class GestionUsuarios {
	static Intermediario interm;
	
	//Información del usuario logeado (0 = id, 1 = nombre)
	public static String[] logedUserInfo = new String[2];
	
	//Id de la conversación que tienes abierta en el momento (-1 = ninguna)
	public static int idConversacionAbierta = -1;
	
	public static ArrayList<Contacto> listaContactos = new ArrayList<Contacto>();
	
	public GestionUsuarios(Intermediario interm) {
		this.interm = interm;
	}
	
	//Método para leer los contactos de la base de datos
	public ArrayList<Contacto> getContactos() {
		Connection conexionDB = ConexionDB.conectarBBDD("MensajeriaLocal", "adminmensajeria", "admin");
		
		try {
			ArrayList<Contacto> tempList = new ArrayList<Contacto>();
			
			PreparedStatement sentencia = conexionDB.prepareStatement(SentenciasSQL.SQLSelectContactos);
			ResultSet resultado = sentencia.executeQuery();
			
			while (resultado.next()) {
				int id = resultado.getInt(1);
				String nombre = resultado.getString(2);
				
				//Crear el objeto para el contacto y añadirlo al arraylist de contactos
				Contacto c = new Contacto(id, nombre);
				tempList.add(c);
				
				//Mandar el contacto al intermediario para que se dibuje en la interfaz
				interm.enviarContactosAInterfaz(id, nombre);
				System.out.println("prueba");
			}
			
			listaContactos = tempList;
			
			ConexionDB.connectionDrop(conexionDB);
			
			return tempList;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void addContacto(String contacto) {
		
		try {
			String tempNombre;
			int tempID;
			
			Connection conexionDB = ConexionDB.conectarBBDD("MensajeriaDB", "adminmensajeria", "admin");
			
			PreparedStatement sentencia = conexionDB.prepareStatement(SentenciasSQL.SQLCheckUser);
			sentencia.setString(1, contacto);
			
			ResultSet resultado = sentencia.executeQuery();
			
			if (resultado.next()) {
				tempID = resultado.getInt(1);
				tempNombre = resultado.getString(2);
				
				conexionDB = ConexionDB.conectarBBDD("MensajeriaLocal", "adminmensajeria", "admin");
				
				sentencia = conexionDB.prepareStatement(SentenciasSQL.SQLInsertContacto);
				sentencia.setInt(1, tempID);
				sentencia.setString(2, tempNombre);
				
				sentencia.execute();		
			}
			
			ConexionDB.connectionDrop(conexionDB);
			getContactos();
		
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//Método para crear un administrador de grupo
	public boolean hacerAdministrador(int user, int conversacion) {
		Connection conexionDB = ConexionDB.conectarBBDD("MensajeriaDB", "adminmensajeria", "admin");
		
		try {
			PreparedStatement sentencia = conexionDB.prepareStatement(SentenciasSQL.SQLAddAdmin);
			sentencia.setInt(1, user);
			sentencia.setInt(2, conversacion);
			
			sentencia.execute();
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
}
