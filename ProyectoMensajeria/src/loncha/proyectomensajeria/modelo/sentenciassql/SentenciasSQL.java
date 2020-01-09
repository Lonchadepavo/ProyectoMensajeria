package loncha.proyectomensajeria.modelo.sentenciassql;

public class SentenciasSQL {
	//Sentencias SQL de GestionConversaciones
		//Get conversaciones
		public static String SQLSelectConversacion = "SELECT id_conversacion FROM participa WHERE id_user = ?";
		public static String SQLSelectIndividual = "SELECT id_conversacion FROM individual WHERE id_conversacion = ?";
		public static String SQLSelectGrupal = "SELECT id_conversacion, nombre_conversacion FROM grupal WHERE id_conversacion = ?";
		
		//Crear conversacion
		public static String SQLNuevaConversacion = "INSERT INTO conversacion VALUES(?)";
		public static String SQLSelectLastConversacion = "SELECT id_conversacion FROM conversacion ORDER BY id_conversacion DESC LIMIT 1";
		public static String SQLInsertTipoConversacionGrupal = "INSERT INTO grupal(id_conversacion, nombre) VALUES(?)";
		public static String SQLInsertTipoConversacionIndividual = "INSERT INTO individual(id_conversacion) VALUES(?)";
		public static String SQLInsertParticipa = "INSERT INTO participa(id_user, id_conversacion) VALUES(?,?)";
		
		//Borrar conversacion
		public static String SQLDeleteConversacion = "DELETE FROM conversacion WHERE id_conversacion = ?";
	
	//Sentencias SQL de GestionMensajes
		//Enviar mensaje y archivo
		public static String SQLSendMessage = "INSERT INTO mensaje(id_user, id_conversacion, mensaje, hora, fecha) VALUES(?,?,?,?,?)";
		public static String SQLSendMessageArchivo = "INSERT INTO mensaje(id_user, id_conversacion, archivo, hora, fecha) VALUES(?,?,?,?,?)";
		
		//Listar mensajes y archivos
		public static String SQLListMessages = "SELECT id_conversacion, id_emisor, mensaje FROM conversacion";
		public static String SQlGetMessages = "SELECT id_emisor, mensaje FROM mensajes WHERE id_conversacion = ?";
		
		//Comprobar si hay mensajes no leídos
		public static String SQLCheckUnreadMessage = "SELECT * FROM mensaje WHERE id_conversacion = ? AND id_user != ?";
		
		//Guardar mensaje en la base de datos local
		public static String SQLSendMessageToLocal = "INSERT INTO mensajes(id_emisor, id_conversacion, id_mensaje, mensaje, hora, fecha) VALUES(?,?,?,?,?,?)";
		public static String SQLSelectLastMessage = "SELECT id_mensaje FROM mensaje ORDER BY id_mensaje DESC LIMIT 1";
		
		//Borrar mensajes de la base de datos
		public static String SQLDeleteMessage = "DELETE FROM mensaje WHERE id_mensaje = ?";
	
	//Sentencias SQL de GestionUsuarios
		//Get contactos
		public static String SQLSelectContactos = "SELECT * FROM contactos";
		
		//Crear contacto
		public static String SQLCheckUser = "SELECT id_user, nombre FROM usuario WHERE nombre = ?";
		public static String SQLInsertContacto = "INSERT INTO contactos VALUES(?,?)";
		
		//Hacer administrador
		public static String SQLAddAdmin = "INSERT INTO administra VALUES(?,?)";
		
		//Get nombre de usuarios
		public static String SQLGetUser = "SELECT nombre FROM usuario WHERE id_user = ?";
		public static String SQLGetParticipantes = "SELECT id_user FROM participa WHERE id_conversacion = ?";
	
	//Sentencias SQL de RegistroYLogin
		//Registrar y logear usuarios
		public static String SQLSelectCheckUser = "SELECT nombre FROM usuario WHERE nombre = ?";
		public static String SQLSelectIdUser = "SELECT id_user FROM usuario WHERE nombre = ?";
		public static String SQLInsertUser = "INSERT INTO usuario (mail, nombre, password, mensajes_nuevos) VALUES(?, ?, ?, ?)";
		public static String SQLInsertUserLocal = "INSERT INTO usuario (id_usuario, mail, nombre, password) VALUES(?, ?, ?, ?)";
		public static String SQLSelectUserPassword = "SELECT id_user, password FROM usuario WHERE nombre = ?";
		
	//Sentencias SQL de TrasbaseDatos
		public static String SQLSelectConversaciones = "SELECT ";
		
	
}
