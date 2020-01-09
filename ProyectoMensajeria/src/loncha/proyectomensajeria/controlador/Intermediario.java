package loncha.proyectomensajeria.controlador;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.sql.Date;

import javax.swing.JTextField;

import loncha.proyectomensajeria.modelo.*;
import loncha.proyectomensajeria.vista.VentanaPrincipal;

public class Intermediario {
	VentanaPrincipal iface;
	GestionUsuarios gUsers = new GestionUsuarios(this);
	public GestionMensajes gMensajes = new GestionMensajes(this);
	GestionConversaciones gConvers = new GestionConversaciones(this, gMensajes);
	RegistroYLogin rgUsers = new RegistroYLogin(gUsers, this);
	
	public Intermediario(VentanaPrincipal iface) {
		this.iface = iface;
	}
	
	public int getConversacionActual() {
		return gUsers.idConversacionAbierta;
	}
	
	public void borrarConversacion() {
		int conversacion = gUsers.idConversacionAbierta;
		
		if (conversacion != -1) {
			gConvers.borrarConversacion(conversacion);
			getConversaciones();
			
			iface.panelMensajes.removeAll();
			iface.lastMensaje = -1;
			
			iface.cambiarPanelLateral(1);
		}
	}
	
	//Datos para registrar un usuario
	public void getDataFromIface(JTextField mail, JTextField username, JTextField password) {
		rgUsers.registrarUsuario(mail.getText(), username.getText(), password.getText());
	}
	
	//Datos para logear un usuario
	public void getDataFromIface(JTextField username, JTextField password) {
		rgUsers.loginUsuario(username.getText(), password.getText());
		iface.lblUsuarioConectado.setText(username.getText());
		
		getConversaciones();
		iface.cambiarPanelLateral(1);
	}
	
	public void sendMessageToChat(String mensaje, int emisor) {
		iface.dibujarMensaje(mensaje, emisor);
	}
	
	public void sendMessageToDatabase(String mensaje, int emisor) {
		int conversacion = gUsers.idConversacionAbierta;
		
		Time horaActual = new Time(0L);
		horaActual.setTime(new java.util.Date().getTime());
		
		Date fechaActual = new Date(horaActual.getTime());
		gMensajes.enviarMensaje(emisor, conversacion, mensaje, horaActual, fechaActual);
		gMensajes.checkForMensajes(conversacion);
	}
	
	public void addContacto(String contacto) {
		gUsers.addContacto(contacto);
	}
	
	public void crearConversacion(int tipoConversacion, String nombreConversacion, ArrayList<Integer> id_participantes) {
		gConvers.nuevaConversacion(tipoConversacion, nombreConversacion, id_participantes);
	}
	
	public void getContactos() {
		if (VentanaPrincipal.panelActual == 0) {
			gUsers.getContactos();
		} else {
			VentanaPrincipal.panelActual = 0;
			VentanaPrincipal.panelConversaciones.removeAll();
			gUsers.getContactos();
		}
		
		iface.repaintLayout();
	}
	
	public void getConversaciones() {
		if (VentanaPrincipal.panelActual == 1) {
			GestionConversaciones.getConversaciones();
		} else {
			VentanaPrincipal.panelActual = 1;
			VentanaPrincipal.panelConversaciones.removeAll();;
			GestionConversaciones.getConversaciones();
		}
		
		iface.repaintLayout();
	}
	
	//Método para enviar la lista de contactos a la interfaz
	public void enviarContactosAInterfaz(int id, String nombre) {
		//Este método se llama cuando quieres mostrar la lista de contactos o cuando se crea un contacto nuevo
		iface.agregarContacto(nombre);
	
	}
	
	//Método para enviar la lista de conversaciones a la interfaz
	public void enviarConversacionesAInterfaz(String id, String nombre, String hora, String ultimoMensaje) {
		//Este método se llama cuando quieres mostrar la lista de conversaciones o cuando se crea una conversación nueva
		iface.agregarConversacion(id, nombre, hora, ultimoMensaje);

	}
	
	public void cambiarConversacion(int conversacion) {
		gConvers.cambiarConversacion(conversacion);
	}
	
	public void repaintInterfaz() {
		iface.repaintLayout();
	}
}
