package loncha.proyectomensajeria.modelo;

public class Contacto {
	int idContacto;
	String nombreContacto;
	
	public Contacto(int id, String nombre) {
		idContacto = id;
		nombreContacto = nombre;
	}
	
	public int getIdContacto() {
		return idContacto;
	}
	public void setIdContacto(int idContacto) {
		this.idContacto = idContacto;
	}
	public String getNombreContacto() {
		return nombreContacto;
	}
	public void setNombreContacto(String nombreContacto) {
		this.nombreContacto = nombreContacto;
	}
	
}
