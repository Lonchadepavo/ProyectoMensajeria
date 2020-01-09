package loncha.proyectomensajeria.vista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import loncha.proyectomensajeria.controlador.Intermediario;
import loncha.proyectomensajeria.modelo.GestionUsuarios;

public class Listeners implements ActionListener {
	VentanaPrincipal iface;
	Intermediario interm;
	
	public Listeners(VentanaPrincipal iface, Intermediario interm) {
		this.iface = iface;
		this.interm = interm;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		
		switch(actionCommand) {
		case "signup-layout":
			iface.setLayout(iface.panelRegistro);
			break;
			
		case "back":
			iface.setLayout(iface.panelLogin);
			break;
			
		case "confirmar-registro":
			interm.getDataFromIface(iface.txtEmailSign, iface.txtUsernameSign, iface.txtPasswordSign);
			break;
			
		case "login":
			interm.getDataFromIface(iface.txtUsernameLogin, iface.txtPasswordLogin);
			iface.txtPasswordLogin.setText("");
			break;
			
		case "send-message":
			if (interm.getConversacionActual() != -1) {
				interm.sendMessageToDatabase(iface.txtCajaMensajes.getText(), Integer.parseInt(GestionUsuarios.logedUserInfo[0]));
				iface.dibujarMensaje(iface.txtCajaMensajes.getText(), Integer.parseInt(GestionUsuarios.logedUserInfo[0]));
				iface.txtCajaMensajes.setText("");
			}
			break;
			
		}
		
	}

}
