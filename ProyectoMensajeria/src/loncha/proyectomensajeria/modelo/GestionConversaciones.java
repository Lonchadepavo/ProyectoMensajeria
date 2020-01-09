package loncha.proyectomensajeria.modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import loncha.proyectomensajeria.controlador.Intermediario;
import loncha.proyectomensajeria.modelo.conexiones.ConexionDB;
import loncha.proyectomensajeria.modelo.sentenciassql.SentenciasSQL;
import loncha.proyectomensajeria.vista.VentanaPrincipal;

public class GestionConversaciones {
	static Intermediario interm;
	GestionMensajes gMensajes;
	
	public static ArrayList<Conversacion> listaConversaciones = new ArrayList<Conversacion>();
	
	public GestionConversaciones(Intermediario interm, GestionMensajes gMensajes) {
		this.interm = interm;
		this.gMensajes = gMensajes;
	}
	
	//Método para crear una nueva conversación
	public boolean nuevaConversacion(int tipoConversacion, String nombreConversacion, ArrayList<Integer> id_participantes) {
		//Crea una nueva conversación
		//Transacción que hace un insert en la tabla conversaciones, otro en la tabla individual o grupal y por último en la tabla participa
		//Relaciona un usuario (el que le da al botón de crear la conversación), con otro (el botón que ha sido clickado es el otro usuario).
		//Hay que hacer un SELECT para coger la ID de la ultima conversacion creada
		
		String idConversacion = String.valueOf(id_participantes.get(0))+String.valueOf(id_participantes.get(1));
		
		try {
			Connection conexionDB = ConexionDB.conectarBBDD("MensajeriaDB", ConexionDB.userDB, ConexionDB.passwordDB);
			conexionDB.setAutoCommit(false);
			
			PreparedStatement sentencia = conexionDB.prepareStatement(SentenciasSQL.SQLNuevaConversacion); //Se crea la nueva conversacion
			sentencia.setInt(1, Integer.valueOf(idConversacion));
			sentencia.execute();
			
			sentencia = conexionDB.prepareStatement(SentenciasSQL.SQLSelectLastConversacion); //Guarda la ID de la conversación recién creada
			ResultSet resultado = sentencia.executeQuery();
			
			resultado.next();
			int idNuevaConversacion = resultado.getInt(1);
			
			if (tipoConversacion == 0) {
				sentencia = conexionDB.prepareStatement(SentenciasSQL.SQLInsertTipoConversacionIndividual); //Inserta la conversación en individual
				sentencia.setInt(1, idNuevaConversacion);
				sentencia.execute();
			} else {
				sentencia = conexionDB.prepareStatement(SentenciasSQL.SQLInsertTipoConversacionGrupal); //Inserta la conversación en grupal
				sentencia.setInt(1, idNuevaConversacion);
				sentencia.setString(2, nombreConversacion);
				sentencia.execute();
			}
			
			for(int i = 0; i < id_participantes.size(); i++) {
				sentencia = conexionDB.prepareStatement(SentenciasSQL.SQLInsertParticipa); //Se inserta en la tabla participa al usuario que crea la conversación, y la conversación que se ha creado
				sentencia.setInt(1, id_participantes.get(i));
				sentencia.setInt(2, idNuevaConversacion);
				sentencia.execute();
			}
			
			conexionDB.commit(); //Hace un commit con la transacción
			ConexionDB.connectionDrop(conexionDB); //Cierra la conexión al terminar
			
			System.out.println("Conversación número: " + idNuevaConversacion + " creada");
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
		
	//Método para borrar una conversación
	public boolean borrarConversacion(int conversacion) {
		//Hay que borrar la conversación de la base de datos
		//Después hay que resetear la lista de conversaciones y volver a cargarla.
		
		try {		
			Connection conexionDB = ConexionDB.conectarBBDD("MensajeriaDB", ConexionDB.userDB, ConexionDB.passwordDB);
			conexionDB.setAutoCommit(false);
			
			PreparedStatement sentencia = conexionDB.prepareStatement(SentenciasSQL.SQLDeleteConversacion); //Se crea la nueva conversacion
			sentencia.setInt(1, conversacion);
			sentencia.execute();
			
			conexionDB.commit(); //Hace un commit con la transacción
			ConexionDB.connectionDrop(conexionDB); //Cierra la conexión al terminar
			
			System.out.println("Conversación número: " + conversacion + " borrada");
			
			listaConversaciones = getConversaciones();
			GestionUsuarios.idConversacionAbierta = -1;
			
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	//Método para cambiar entre conversaciones
	public void cambiarConversacion(int conversacion) {
		GestionUsuarios.idConversacionAbierta = conversacion;
		VentanaPrincipal.panelMensajes.removeAll();
		VentanaPrincipal.lastMensaje = -1;
		
		for (Conversacion c : listaConversaciones) {
			if (c.getIdConversacion() == conversacion) {
				VentanaPrincipal.lblNombreConversacion.setText(c.getNombreConversacion());
			}
		}
		
		gMensajes.getMensajesFromConversacion(conversacion);
		gMensajes.checkForMensajes(conversacion);
		
		interm.repaintInterfaz();
		
	}
	
	//Método para llenar la lista de conversaciones
	public static ArrayList<Conversacion> getConversaciones() {
		try {
			ArrayList<Conversacion> tempList = new ArrayList<Conversacion>();
			
			Connection conexionDB = ConexionDB.conectarBBDD("MensajeriaDB", "adminmensajeria", "admin");
			conexionDB.setAutoCommit(false);
			
			PreparedStatement sentencia = conexionDB.prepareStatement(SentenciasSQL.SQLSelectConversacion);
			sentencia.setInt(1, Integer.valueOf(GestionUsuarios.logedUserInfo[0]));
			ResultSet resultado = sentencia.executeQuery();
			
			ArrayList<Integer> conversacionesTemp = new ArrayList<Integer>();
			
			while(resultado.next()) {
				conversacionesTemp.add(resultado.getInt(1));
			}
			
			for (int conversacion : conversacionesTemp) {
				sentencia = conexionDB.prepareStatement(SentenciasSQL.SQLSelectIndividual);
				sentencia.setInt(1, conversacion);
				
				resultado = sentencia.executeQuery();
				
				while (resultado.next()) {
					Conversacion c = new Conversacion();
					c.setIdConversacion(resultado.getInt(1));
					c.setNombreConversacion(String.valueOf(resultado.getInt(1)));
					c.setTipoConversacion(0);
					
					tempList.add(c);
				}
			}
			
			for (int conversacion : conversacionesTemp) {
				sentencia = conexionDB.prepareStatement(SentenciasSQL.SQLSelectGrupal);
				sentencia.setInt(1, conversacion);
				
				resultado = sentencia.executeQuery();
				
				while (resultado.next()) {
					Conversacion c = new Conversacion();
					c.setIdConversacion(resultado.getInt(1));
					c.setNombreConversacion(resultado.getString(2));
					c.setTipoConversacion(1);
					
					tempList.add(c);
				}
			}
			
			
			
			conexionDB.commit();
			
			
			
			for (int i = 0; i < tempList.size(); i++) {
				String id = String.valueOf(tempList.get(i).getIdConversacion());
				String nombre = "";
				
				sentencia = conexionDB.prepareStatement(SentenciasSQL.SQLGetParticipantes);
				sentencia.setInt(1, tempList.get(i).getIdConversacion());
				
				resultado = sentencia.executeQuery();
				
				int idUsuario = -1;
				
				while (resultado.next()) {
					int tempId = resultado.getInt(1);
					
					if (tempId != Integer.valueOf(GestionUsuarios.logedUserInfo[0])) {
						idUsuario = tempId;
					}
				}
				
				sentencia = conexionDB.prepareStatement(SentenciasSQL.SQLGetUser);
				sentencia.setInt(1, idUsuario);
				
				resultado = sentencia.executeQuery();
				
				while (resultado.next()) {
					nombre = resultado.getString(1);
				}
				
				listaConversaciones = tempList;
				
				interm.enviarConversacionesAInterfaz(id, "Conversación con " + nombre, "00:00", "test");
				
				if (listaConversaciones.size() <= 0) {
					VentanaPrincipal.panelConversaciones.removeAll();
				}
			}
			
			ConexionDB.connectionDrop(conexionDB);
			
			return tempList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		
	}
	
	public static ArrayList<Conversacion> getListaConversaciones() {
		return listaConversaciones;
	}
}
