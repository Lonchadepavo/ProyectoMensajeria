package loncha.proyectomensajeria.modelo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.util.ArrayList;

import loncha.proyectomensajeria.controlador.Intermediario;
import loncha.proyectomensajeria.modelo.conexiones.ConexionDB;
import loncha.proyectomensajeria.modelo.sentenciassql.SentenciasSQL;

public class GestionMensajes {
	Intermediario interm;
	
	public GestionMensajes(Intermediario interm) {
		this.interm = interm;
	}
	
	//Método para enviar mensajes a una conversación
	public boolean enviarMensaje(int emisor, int conversacion, String mensaje, Time hora, Date fecha) {
		
		try {
			Connection conexionDB = ConexionDB.conectarBBDD("MensajeriaDB", ConexionDB.userDB, ConexionDB.passwordDB);
			conexionDB.setAutoCommit(false);
			
			PreparedStatement sentencia = conexionDB.prepareStatement(SentenciasSQL.SQLSendMessage); //Se crea la nueva conversacion
			sentencia.setInt(1, emisor);
			sentencia.setInt(2, conversacion);
			sentencia.setString(3, mensaje);
			sentencia.setTime(4, hora);
			sentencia.setDate(5, fecha);
			
			sentencia.execute();
			
			conexionDB.commit();
			System.out.println("Mensaje enviado");
			
			sentencia = conexionDB.prepareStatement(SentenciasSQL.SQLSelectLastMessage);
			ResultSet resultado = sentencia.executeQuery();
			
			int idMensaje = 0;
			
			while (resultado.next()) {
				idMensaje = resultado.getInt(1);
			}
			
			enviarMensajesALocal(emisor, conversacion, idMensaje, mensaje, hora, fecha);
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean enviarArchivo(int emisor, int conversacion, byte[] archivo, Time hora, Date fecha) {
		
		try {
			Connection conexionDB = ConexionDB.conectarBBDD("MensajeriaDB", ConexionDB.userDB, ConexionDB.passwordDB);
			conexionDB.setAutoCommit(false);
			
			PreparedStatement sentencia = conexionDB.prepareStatement(SentenciasSQL.SQLSendMessageArchivo); //Se crea la nueva conversacion
			sentencia.setInt(1, emisor);
			sentencia.setInt(2, conversacion);
			sentencia.setBytes(3, archivo);
			sentencia.setTime(4, hora);
			sentencia.setDate(5, fecha);
			
			sentencia.execute();
			
			conexionDB.commit();
			
			ConexionDB.connectionDrop(conexionDB);
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public void getMensajesFromConversacion(int conversacion) {
		Connection conexionDB = ConexionDB.conectarBBDD("MensajeriaLocal", "adminmensajeria", "admin");
		
		try {
			PreparedStatement sentencia = conexionDB.prepareStatement(SentenciasSQL.SQlGetMessages);
			sentencia.setInt(1, conversacion);
			ResultSet resultado = sentencia.executeQuery();
			
			while(resultado.next()) {
				interm.sendMessageToChat(resultado.getString(2), resultado.getInt(1));
			}
			
			ConexionDB.connectionDrop(conexionDB);
			
		} catch (Exception e) {
			
		}
	}
	
	public void enviarMensajesALocal(int emisor, int conversacion, int idMensaje, String mensaje, Time hora, Date fecha) {
		Connection conexionDB = ConexionDB.conectarBBDD("MensajeriaLocal", "adminmensajeria", "admin");
		
		try {
			PreparedStatement sentencia = conexionDB.prepareStatement(SentenciasSQL.SQLSendMessageToLocal);
			sentencia.setInt(1, emisor);
			sentencia.setInt(2, conversacion);
			sentencia.setInt(3, idMensaje);
			sentencia.setString(4, mensaje);
			sentencia.setTime(5, hora);
			sentencia.setDate(6, fecha);
			
			sentencia.execute();
			
			ConexionDB.connectionDrop(conexionDB);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void checkForMensajes(int conversacion) {
		Connection conexionDB = ConexionDB.conectarBBDD("MensajeriaDB", "adminmensajeria", "admin");
		
		try {
			PreparedStatement sentencia = conexionDB.prepareStatement(SentenciasSQL.SQLCheckUnreadMessage);
			sentencia.setInt(1, GestionUsuarios.idConversacionAbierta);
			sentencia.setInt(2, Integer.parseInt(GestionUsuarios.logedUserInfo[0]));
			ResultSet resultado = sentencia.executeQuery();
			
			while (resultado.next()) {
				int tempEmisor, tempConversacion, tempIdMensaje;
				String tempMensaje;
				Time tempTime;
				Date tempDate;
				
				tempEmisor = resultado.getInt(1);
				tempConversacion = resultado.getInt(2);
				tempIdMensaje = resultado.getInt(3);
				
				tempMensaje = resultado.getString(4);
				
				tempTime = resultado.getTime(5);
				tempDate = resultado.getDate(6);
				
				enviarMensajesALocal(tempEmisor, tempConversacion, tempIdMensaje, tempMensaje, tempTime, tempDate);
				interm.sendMessageToChat(tempMensaje, tempEmisor);
				
				sentencia = conexionDB.prepareStatement(SentenciasSQL.SQLDeleteMessage);
				sentencia.setInt(1, tempIdMensaje);
				sentencia.execute();
			}
			
			ConexionDB.connectionDrop(conexionDB);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
