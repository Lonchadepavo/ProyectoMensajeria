package loncha.proyectomensajeria.vista;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JOptionPane;

import loncha.proyectomensajeria.controlador.Intermediario;
import loncha.proyectomensajeria.modelo.Contacto;
import loncha.proyectomensajeria.modelo.Conversacion;
import loncha.proyectomensajeria.modelo.GestionConversaciones;
import loncha.proyectomensajeria.modelo.GestionUsuarios;

public class MouseListeners implements MouseListener {
	VentanaPrincipal iface;
	Intermediario interm;
	
	public MouseListeners(VentanaPrincipal iface, Intermediario interm) {
		this.iface = iface;
		this.interm = interm;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		if (arg0.getComponent().getName().equals("contactos")) {
			if (iface.panelActual == 0) {
				interm.getConversaciones();
				iface.cambiarPanelLateral(1);
			} else {
				interm.getContactos();
				iface.cambiarPanelLateral(0);
			}
			
		} else if (arg0.getComponent().getName().equals("addcontacto")) {
			String nombre = JOptionPane.showInputDialog("Agregar contacto");
			if (nombre != null && nombre != "") {
				interm.addContacto(nombre);
			}
			
		} else if (arg0.getComponent().getName().equals("borrarconver")) {
			interm.borrarConversacion();
		}
		
		else {
			//Si has hecho click en un contacto de tu lista
			for (Contacto c : GestionUsuarios.listaContactos) {
				String nombre = c.getNombreContacto();
				
				if (arg0.getComponent().getName().equals(nombre)) {
					if (VentanaPrincipal.panelActual == 0) {
						int idContacto = c.getIdContacto();
						int idUser = Integer.valueOf(GestionUsuarios.logedUserInfo[0]);
						
						ArrayList<Integer> participantes = new ArrayList<Integer>(Arrays.asList(idUser, idContacto));
						
						interm.crearConversacion(0, nombre, participantes);
					}
				}
			}
			
			//Si has hecho click en una conversación de tu lista
			for (Conversacion c : GestionConversaciones.listaConversaciones) {
				String nombre = c.getNombreConversacion();
				
				if (arg0.getComponent().getName().equals(nombre)) {
					if (VentanaPrincipal.panelActual == 1) {
						interm.cambiarConversacion(c.getIdConversacion());
					}	
				}
			}
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
