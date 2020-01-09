package loncha.proyectomensajeria.modelo;

import java.util.TimerTask;

public class AutoRefresh extends TimerTask{
	GestionMensajes gMensajes;
	
	public AutoRefresh(GestionMensajes gMensajes) {
		this.gMensajes = gMensajes;
	}

	@Override
	public void run() {
		if (GestionUsuarios.idConversacionAbierta != -1) {
			gMensajes.checkForMensajes(GestionUsuarios.idConversacionAbierta);
		}
		
	}

}
