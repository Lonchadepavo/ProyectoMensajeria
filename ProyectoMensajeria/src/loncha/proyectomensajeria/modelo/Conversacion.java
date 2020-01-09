package loncha.proyectomensajeria.modelo;

import java.util.ArrayList;

public class Conversacion {
	int idConversacion; //ID que tiene la conversación en la base de datos
	int tipoConversacion; //Tipo de conversación (0 = individual, 1 = grupal)
	
	int numeroParticipantes; //Número de participantes de la conversación
	ArrayList<Integer> idParticipantes = new ArrayList<Integer>(); //ArrayList con las id de todos los participantes
	
	String nombreConversacion; //Nombre de la conversación (solo para grupos)
	
	ArrayList<String> mensajesEmisor = new ArrayList<String>();
	ArrayList<String> mensajesAjenos = new ArrayList<String>();
	
	//Getters y setters
	public int getIdConversacion() {
		return idConversacion;
	}
	
	public int getTipoConversacion() {
		return tipoConversacion;
	}
	
	public int getNumeroParticipantes() {
		return numeroParticipantes;
	}
	
	public ArrayList<Integer> getIdParticipantes() {
		return idParticipantes;
	}
	
	public String getNombreConversacion() {
		return nombreConversacion;
	}
	
	public void setIdConversacion(int idConversacion) {
		this.idConversacion = idConversacion;
	}
	
	public void setTipoConversacion(int tipoConversacion) {
		this.tipoConversacion = tipoConversacion;
	}
	
	public void setNumeroParticipantes(int numeroParticipantes) {
		this.numeroParticipantes = numeroParticipantes;
	}
	
	public void setIdParticipantes(ArrayList<Integer> idParticipantes) {
		this.idParticipantes = idParticipantes;
	}
	
	public void setNombreConversacion(String nombreConversacion) {
		this.nombreConversacion = nombreConversacion;
	}
	
}
