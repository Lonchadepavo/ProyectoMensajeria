package loncha.proyectomensajeria.modelo;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JOptionPane;

import loncha.proyectomensajeria.controlador.Intermediario;
import loncha.proyectomensajeria.modelo.conexiones.ConexionDB;
import loncha.proyectomensajeria.modelo.correo.EmailManager;
import loncha.proyectomensajeria.modelo.sentenciassql.SentenciasSQL;
import loncha.proyectomensajeria.vista.VentanaPrincipal;

public class RegistroYLogin {
	Intermediario interm;
	GestionUsuarios gUsers;
	TransformacionDatos transformDatos = new TransformacionDatos();
	EmailManager eManager = new EmailManager();
	
	Connection conexionDB;
	
	public RegistroYLogin(GestionUsuarios gUsers, Intermediario interm) {
		this.gUsers = gUsers;
		this.interm = interm;
	}
	
	public void registrarUsuario(String mail, String username, String password) {
		//Recibe el mail, el username y la contraseña
		
		boolean usuarioValido;
		int codigoConfirmacion = generarCodigoConfirmacion();
		
		conexionDB = ConexionDB.conectarBBDD("MensajeriaDB", ConexionDB.userDB, ConexionDB.passwordDB);	
		
		//Si el usuario es valido (no está registrado en la base de datos)
		if (usuarioValido = checkUserInDB(username)) {
			if (eManager.emailConfirmacion(mail, codigoConfirmacion)) {
				//Muestra un JOptionPane para pedirte el código de confirmación
				String codigo = JOptionPane.showInputDialog(null, "Introduce el código de confirmación");
				
				if (codigo.equals(String.valueOf(codigoConfirmacion))) {
					//Insertar al usuario en la base de datos
					try {			
						PreparedStatement sentencia = conexionDB.prepareStatement(SentenciasSQL.SQLInsertUser);
						sentencia.setString(1, mail);
						sentencia.setString(2, username);
						sentencia.setBytes(3, transformDatos.cifrarPassword(password));
						sentencia.setBoolean(4, false);
						
						sentencia.executeUpdate();
						
						int id = -1;
						
						sentencia = conexionDB.prepareStatement(SentenciasSQL.SQLSelectIdUser);
						sentencia.setString(1, username);
						ResultSet resultado = sentencia.executeQuery();
						
						while (resultado.next()) {
							id = resultado.getInt(1);
						}
						
						conexionDB = ConexionDB.conectarBBDD("MensajeriaLocal", ConexionDB.userDB, ConexionDB.passwordDB);	
						
						sentencia = conexionDB.prepareStatement(SentenciasSQL.SQLInsertUserLocal);
						
						sentencia.setInt(1, id);
						sentencia.setString(2, mail);
						sentencia.setString(3, username);
						sentencia.setBytes(4, transformDatos.cifrarPassword(password));
						
						sentencia.executeUpdate();
						
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					JOptionPane.showMessageDialog(null, "Código correcto, usuario registrado");
					ConexionDB.connectionDrop(conexionDB);
				} else {
					JOptionPane.showMessageDialog(null, "Código incorrecto");
				}
			}
		} else {
			JOptionPane.showMessageDialog(null, "El nombre de usuario ya está en uso");
		}
		
		ConexionDB.connectionDrop(conexionDB);	
	}
	
	public void loginUsuario(String username, String password) {
		boolean usuarioNoValido;
		boolean loginError = false;
		
		conexionDB = ConexionDB.conectarBBDD("MensajeriaDB", ConexionDB.userDB, ConexionDB.passwordDB);
		usuarioNoValido = checkUserInDB(username);
		
		if (!usuarioNoValido) {
			try {
				PreparedStatement sentencia = conexionDB.prepareStatement(SentenciasSQL.SQLSelectUserPassword);
				sentencia.setString(1, username);
				
				ResultSet resultado = sentencia.executeQuery();
				
				while (resultado.next()) {
					if (transformDatos.descifrarPassword(resultado.getBytes(2)).equals(password)) {
						gUsers.logedUserInfo[0] = (String.valueOf(resultado.getInt(1)));
						gUsers.logedUserInfo[1] = (String.valueOf(resultado.getBytes(2)));
						
						JOptionPane.showMessageDialog(null, "Usuario logeado correctamente");
						VentanaPrincipal.setLayout(VentanaPrincipal.panelPrincipal);
					} else {
						loginError = true;
					}
				}
			} catch (Exception e) {
				loginError = true;
				e.printStackTrace();
			}
		} else {
			loginError = true;
		}
		
		if (loginError) {
			JOptionPane.showMessageDialog(null, "Nombre de usuario o contraseña incorrectos");
		}
		
		ConexionDB.connectionDrop(conexionDB);
	}
	
	public boolean checkUserInDB(String username) {
		try {
			PreparedStatement sentencia = conexionDB.prepareStatement(SentenciasSQL.SQLSelectCheckUser);
			sentencia.setString(1, username);
			
			ResultSet resultado = sentencia.executeQuery();
			
			if (resultado.next()) {
				sentencia.close();
				return false;
			} else {
				sentencia.close();
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	public int generarCodigoConfirmacion() {
		int codigoResultado = 0;
		String codigo = "";
		int largoCodigo = 5;
		
		for (int i = 0; i < largoCodigo; i++) {
			codigo += String.valueOf((int)(Math.random()*9));
		}
		
		codigoResultado = Integer.parseInt(codigo);
		return codigoResultado;
	}
}
