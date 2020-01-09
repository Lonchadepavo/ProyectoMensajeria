package loncha.proyectomensajeria.vista;

import javax.swing.JFrame;
import java.awt.GridLayout;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;

import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import loncha.proyectomensajeria.controlador.Intermediario;
import loncha.proyectomensajeria.modelo.AutoRefresh;
import loncha.proyectomensajeria.modelo.GestionUsuarios;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.awt.GridBagConstraints;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.SwingConstants;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.util.Timer;

import javax.swing.JLayeredPane;
import net.miginfocom.swing.MigLayout;
import javax.swing.ScrollPaneConstants;

public class VentanaPrincipal extends JFrame {
	Intermediario interm = new Intermediario(this);
	Listeners ls = new Listeners(this, interm);
	MouseListeners mLs = new MouseListeners(this, interm);
	
	static JLayeredPane layeredPane;
	
	//Panel de login
	static JPanel panelLogin;
	
	JButton btnLogin;
	JButton btnSignUp;
	
	JTextField txtUsernameLogin;
	JPasswordField txtPasswordLogin;
	
	//Panel de registro
	static JPanel panelRegistro;
	
	JButton btnSignUpConfirm;
	JButton btnBack;
	
	JTextField txtEmailSign;
	JTextField txtUsernameSign;
	JPasswordField txtPasswordSign;
	JPasswordField txtConfirmSign;
	
	//Panel principal
	public static JPanel panelPrincipal;
	JPanel panelHeader;
	public static JPanel panelConversaciones;
	JPanel panelChat;
	JPanel panelEnviar;
	JTextField txtCajaMensajes;
	private JButton btnEnviar;
	private JPanel panelHeaderConversacion;
	public static JPanel panelMensajes;
	
	static JScrollPane scrollPane_1;
	private JTextField textField;
	private static JTextField textField_1;
	
	public static int lastMensaje = -1;
	static int numConversaciones = -1;
	static JPanel panelPlaceholder;
	
	//(0 = contactos, 1 = conversaciones)
	public static int panelActual = 0;
	private JTextField textField_2;
	private JLabel lblCerrar;
	private JLabel lblContactos;
	public JLabel lblUsuarioConectado;
	private JLabel lblImagenUsuario;
	private JLabel lblAddContacto;
	private JLabel lblIconConversacion;
	public static JLabel lblNombreConversacion;
	private JLabel lblBorrarConversacion;
	
	public VentanaPrincipal() {
		getContentPane().setBackground(new Color(153, 153, 255));

		//Establecer el look and feel del sistema
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(new Dimension(1280,720));
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Lonch\\Google Drive\\GS DAM\\2º\\Acceso a datos\\ProyectoMensajeria\\Recursos\\logorobado.png"));
		
		layeredPane = new JLayeredPane();
		layeredPane.setBounds(0, 0, 1264, 679);
		getContentPane().add(layeredPane);
		
		panelPrincipal = new JPanel();
		panelPrincipal.setBackground(new Color(153, 153, 255));
		panelPrincipal.setBounds(0, 0, 1264, 679);
		layeredPane.add(panelPrincipal);
		panelPrincipal.setLayout(new BorderLayout(0, 0));
		
		panelHeader = new JPanel();
		panelHeader.setBackground(new Color(204, 153, 255));
		panelPrincipal.add(panelHeader, BorderLayout.NORTH);
		GridBagLayout gbl_panelHeader = new GridBagLayout();
		gbl_panelHeader.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panelHeader.rowHeights = new int[]{0};
		gbl_panelHeader.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panelHeader.rowWeights = new double[]{0.0};
		panelHeader.setLayout(gbl_panelHeader);
		
		lblImagenUsuario = new JLabel(new ImageIcon("C:\\Users\\Lonch\\Google Drive\\GS DAM\\2º\\Acceso a datos\\ProyectoMensajeria\\Recursos\\usericon.png"));
		GridBagConstraints gbc_lblImagenUsuario = new GridBagConstraints();
		gbc_lblImagenUsuario.anchor = GridBagConstraints.WEST;
		gbc_lblImagenUsuario.insets = new Insets(0, 0, 0, 5);
		gbc_lblImagenUsuario.gridx = 2;
		gbc_lblImagenUsuario.gridy = 0;
		panelHeader.add(lblImagenUsuario, gbc_lblImagenUsuario);
		
		lblContactos = new JLabel(new ImageIcon("C:\\Users\\Lonch\\Google Drive\\GS DAM\\2º\\Acceso a datos\\ProyectoMensajeria\\Recursos\\contactos.png"));
		lblContactos.addMouseListener(mLs);
		
		lblUsuarioConectado = new JLabel("USUARIO");
		lblUsuarioConectado.setFont(new Font("DialogInput", Font.BOLD, 17));
		GridBagConstraints gbc_lblUsuarioConectado = new GridBagConstraints();
		gbc_lblUsuarioConectado.anchor = GridBagConstraints.WEST;
		gbc_lblUsuarioConectado.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblUsuarioConectado.insets = new Insets(0, 0, 0, 5);
		gbc_lblUsuarioConectado.gridx = 3;
		gbc_lblUsuarioConectado.gridy = 0;
		panelHeader.add(lblUsuarioConectado, gbc_lblUsuarioConectado);
		
		lblAddContacto = new JLabel(new ImageIcon("C:\\Users\\Lonch\\Google Drive\\GS DAM\\2º\\Acceso a datos\\ProyectoMensajeria\\Recursos\\addcontacto.png"));
		lblAddContacto.addMouseListener(mLs);
		lblAddContacto.setName("addcontacto");
		GridBagConstraints gbc_lblAddContacto = new GridBagConstraints();
		gbc_lblAddContacto.insets = new Insets(0, 0, 0, 5);
		gbc_lblAddContacto.anchor = GridBagConstraints.WEST;
		gbc_lblAddContacto.gridx = 11;
		gbc_lblAddContacto.gridy = 0;
		panelHeader.add(lblAddContacto, gbc_lblAddContacto);
		
		lblContactos.setName("contactos");
		GridBagConstraints gbc_lblContactos = new GridBagConstraints();
		gbc_lblContactos.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblContactos.insets = new Insets(0, 0, 0, 5);
		gbc_lblContactos.gridx = 10;
		gbc_lblContactos.gridy = 0;
		panelHeader.add(lblContactos, gbc_lblContactos);
		
		lblCerrar = new JLabel(new ImageIcon("C:\\Users\\Lonch\\Google Drive\\GS DAM\\2º\\Acceso a datos\\ProyectoMensajeria\\Recursos\\close_red.png"));
		GridBagConstraints gbc_lblCerrar = new GridBagConstraints();
		gbc_lblCerrar.anchor = GridBagConstraints.EAST;
		gbc_lblCerrar.insets = new Insets(0, 0, 0, 5);
		gbc_lblCerrar.gridx = 40;
		gbc_lblCerrar.gridy = 0;
		panelHeader.add(lblCerrar, gbc_lblCerrar);
		
		panelChat = new JPanel();
		panelChat.setBackground(new Color(153, 153, 255));
		panelChat.setPreferredSize(new Dimension(panelChat.getWidth(), panelChat.getHeight()));
		panelPrincipal.add(panelChat, BorderLayout.CENTER);
		panelChat.setLayout(new BorderLayout(0, 0));
		
		panelEnviar = new JPanel();
		panelEnviar.setBackground(new Color(0, 102, 153));
		panelChat.add(panelEnviar, BorderLayout.SOUTH);
		panelEnviar.setLayout(new MigLayout("", "[86px,grow][69px]", "[63px]"));
		
		txtCajaMensajes = new JTextField();
		txtCajaMensajes.addActionListener(ls);
		txtCajaMensajes.setActionCommand("send-message");
		panelEnviar.add(txtCajaMensajes, "cell 0 0,growx");
		txtCajaMensajes.setColumns(10);
		
		btnEnviar = new JButton("ENVIAR");
		btnEnviar.addActionListener(ls);
		btnEnviar.setActionCommand("send-message");
		panelEnviar.add(btnEnviar, "cell 1 0");
		
		panelHeaderConversacion = new JPanel();
		panelHeaderConversacion.setBackground(new Color(102, 204, 153));
		panelChat.add(panelHeaderConversacion, BorderLayout.NORTH);
		GridBagLayout gbl_panelHeaderConversacion = new GridBagLayout();
		gbl_panelHeaderConversacion.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panelHeaderConversacion.rowHeights = new int[]{0, 0};
		gbl_panelHeaderConversacion.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panelHeaderConversacion.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panelHeaderConversacion.setLayout(gbl_panelHeaderConversacion);
		
		lblIconConversacion = new JLabel(new ImageIcon("C:\\Users\\Lonch\\Google Drive\\GS DAM\\2º\\Acceso a datos\\ProyectoMensajeria\\Recursos\\usericon.png"));
		GridBagConstraints gbc_lblIconConversacion = new GridBagConstraints();
		gbc_lblIconConversacion.anchor = GridBagConstraints.WEST;
		gbc_lblIconConversacion.insets = new Insets(0, 0, 0, 5);
		gbc_lblIconConversacion.gridx = 0;
		gbc_lblIconConversacion.gridy = 0;
		panelHeaderConversacion.add(lblIconConversacion, gbc_lblIconConversacion);
		
		lblNombreConversacion = new JLabel("CONVERSACI\u00D3N");
		lblNombreConversacion.setFont(new Font("DialogInput", Font.BOLD, 17));
		GridBagConstraints gbc_lblNombreConversacion = new GridBagConstraints();
		gbc_lblNombreConversacion.insets = new Insets(0, 0, 0, 5);
		gbc_lblNombreConversacion.anchor = GridBagConstraints.WEST;
		gbc_lblNombreConversacion.fill = GridBagConstraints.VERTICAL;
		gbc_lblNombreConversacion.gridx = 1;
		gbc_lblNombreConversacion.gridy = 0;
		panelHeaderConversacion.add(lblNombreConversacion, gbc_lblNombreConversacion);
		
		lblBorrarConversacion = new JLabel(new ImageIcon("C:\\Users\\Lonch\\Google Drive\\GS DAM\\2º\\Acceso a datos\\ProyectoMensajeria\\Recursos\\close_red.png"));
		lblBorrarConversacion.addMouseListener(mLs);
		lblBorrarConversacion.setName("borrarconver");
		GridBagConstraints gbc_lblBorrarConversacion = new GridBagConstraints();
		gbc_lblBorrarConversacion.anchor = GridBagConstraints.EAST;
		gbc_lblBorrarConversacion.gridx = 29;
		gbc_lblBorrarConversacion.gridy = 0;
		panelHeaderConversacion.add(lblBorrarConversacion, gbc_lblBorrarConversacion);
		
		panelMensajes = new JPanel();
		panelMensajes.setBackground(new Color(153, 153, 255));
		JScrollPane scrollPane = new JScrollPane(panelMensajes);
		GridBagLayout gbl_panelMensajes = new GridBagLayout();
		gbl_panelMensajes.columnWidths = new int[]{0, 0};
		gbl_panelMensajes.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panelMensajes.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panelMensajes.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panelMensajes.setLayout(gbl_panelMensajes);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		panelChat.add(scrollPane, BorderLayout.CENTER);
		
		panelConversaciones = new JPanel();
		panelConversaciones.setBackground(new Color(204, 204, 255));
		scrollPane_1 = new JScrollPane(panelConversaciones);
		panelPrincipal.add(scrollPane_1, BorderLayout.WEST);
		GridBagLayout gbl_panelConversaciones = new GridBagLayout();
		gbl_panelConversaciones.columnWidths = new int[] {0};
		gbl_panelConversaciones.rowHeights = new int[] {0};
		gbl_panelConversaciones.columnWeights = new double[]{1.0};
		gbl_panelConversaciones.rowWeights = new double[]{0.0};
		panelConversaciones.setLayout(gbl_panelConversaciones);
		
		panelPlaceholder = new JPanel();
		panelPlaceholder.setBorder(null);
		panelPlaceholder.setBackground(new Color(204, 204, 255));
		panelPlaceholder.setPreferredSize(new Dimension(200, 80));
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.anchor = GridBagConstraints.NORTH;
		gbc_panel_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 0;
		panelConversaciones.add(panelPlaceholder, gbc_panel_1);
		
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		//PANEL DE LOGIN
		panelLogin = new JPanel();
		panelLogin.setBackground(new Color(153, 153, 255));
		panelLogin.setBounds(0, 0, 1264, 679);
		layeredPane.add(panelLogin);
		panelLogin.setLayout(null);
		
		JLabel lblTitleLogin = new JLabel("Welcome");
		lblTitleLogin.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitleLogin.setFont(new Font("MV Boli", Font.BOLD, 47));
		lblTitleLogin.setBounds(455, 127, 317, 137);
		panelLogin.add(lblTitleLogin);
		
		JLabel lblUsernameLogin = new JLabel("Username");
		lblUsernameLogin.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblUsernameLogin.setBounds(537, 250, 108, 33);
		panelLogin.add(lblUsernameLogin);
		
		txtUsernameLogin = new JTextField();
		txtUsernameLogin.setBounds(537, 275, 156, 20);
		txtUsernameLogin.addActionListener(ls);
		txtUsernameLogin.setActionCommand("login");
		panelLogin.add(txtUsernameLogin);
		txtUsernameLogin.setColumns(10);
		
		JLabel lblPasswordLogin = new JLabel("Password");
		lblPasswordLogin.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblPasswordLogin.setBounds(537, 294, 108, 33);
		panelLogin.add(lblPasswordLogin);
		
		txtPasswordLogin = new JPasswordField();
		txtPasswordLogin.setColumns(10);
		txtPasswordLogin.setBounds(537, 319, 156, 20);
		txtPasswordLogin.addActionListener(ls);
		txtPasswordLogin.setActionCommand("login");
		panelLogin.add(txtPasswordLogin);
		
		JLabel lblPasswordRecover = new JLabel("\u00BFHas olvidado tu contrase\u00F1a?");
		lblPasswordRecover.setHorizontalAlignment(SwingConstants.CENTER);
		lblPasswordRecover.setBounds(537, 347, 156, 14);
		panelLogin.add(lblPasswordRecover);
		
		btnLogin = new JButton("Login");
		btnLogin.setBounds(565, 372, 89, 23);
		btnLogin.addActionListener(ls);
		btnLogin.setActionCommand("login");
		panelLogin.add(btnLogin);
		
		btnSignUp = new JButton("Sign up");
		btnSignUp.setBounds(565, 406, 89, 23);
		btnSignUp.addActionListener(ls);
		btnSignUp.setActionCommand("signup-layout");
		panelLogin.add(btnSignUp);
		
		
		//PANEL DE REGISTRO
		panelRegistro = new JPanel();
		panelRegistro.setBackground(new Color(153, 153, 255));
		panelRegistro.setBounds(0, 0, 1264, 679);
		layeredPane.add(panelRegistro);
		panelRegistro.setLayout(null);
		
		JLabel lblTitleRegister = new JLabel("Registration form");
		lblTitleRegister.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitleRegister.setFont(new Font("MV Boli", Font.BOLD, 47));
		lblTitleRegister.setBounds(252, 135, 694, 137);
		panelRegistro.add(lblTitleRegister);
		
		JLabel lblEmailSign = new JLabel("E-mail");
		lblEmailSign.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblEmailSign.setBounds(478, 258, 135, 33);
		panelRegistro.add(lblEmailSign);
		
		txtEmailSign = new JTextField();
		txtEmailSign.setColumns(10);
		txtEmailSign.setBounds(478, 283, 208, 20);
		panelRegistro.add(txtEmailSign);
		
		JLabel lblUsernameSign = new JLabel("Username");
		lblUsernameSign.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblUsernameSign.setBounds(478, 302, 135, 33);
		panelRegistro.add(lblUsernameSign);
		
		txtUsernameSign = new JTextField();
		txtUsernameSign.setColumns(10);
		txtUsernameSign.setBounds(478, 327, 208, 20);
		panelRegistro.add(txtUsernameSign);
		
		btnSignUpConfirm = new JButton("Sign up");
		btnSignUpConfirm.setBounds(596, 474, 90, 23);
		btnSignUpConfirm.addActionListener(ls);
		btnSignUpConfirm.setActionCommand("confirmar-registro");
		panelRegistro.add(btnSignUpConfirm);
		
		btnBack = new JButton("Go back");
		btnBack.setBounds(478, 474, 98, 23);
		btnBack.addActionListener(ls);
		btnBack.setActionCommand("back");
		panelRegistro.add(btnBack);
		
		JLabel lblPasswordSign = new JLabel("Password");
		lblPasswordSign.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblPasswordSign.setBounds(478, 346, 135, 33);
		panelRegistro.add(lblPasswordSign);
		
		txtPasswordSign = new JPasswordField();
		txtPasswordSign.setColumns(10);
		txtPasswordSign.setBounds(478, 371, 208, 20);
		panelRegistro.add(txtPasswordSign);
		
		JLabel lblConfirmationSign = new JLabel("Confirmation");
		lblConfirmationSign.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblConfirmationSign.setBounds(478, 390, 135, 33);
		panelRegistro.add(lblConfirmationSign);
		
		txtConfirmSign = new JPasswordField();
		txtConfirmSign.setColumns(10);
		txtConfirmSign.setBounds(478, 415, 208, 20);
		panelRegistro.add(txtConfirmSign);
		
		
			
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new VentanaPrincipal();
		setLayout(panelLogin);
	}
	
	public void repaintLayout() {
		getContentPane().repaint();
		getContentPane().revalidate();
	}
	
	public static void setLayout(JPanel panel) {
		layeredPane.removeAll();
		layeredPane.add(panel);
		layeredPane.repaint();
		layeredPane.revalidate();
	}
	
	//Método encargado de mostrar el mensaje que se le pase como parametro de la conversación en el panel de chat (visual, no en la base de datos)
	public void dibujarMensaje(String mensaje, int emisor) {
		JTextArea txtMensaje = new JTextArea();
		
		AffineTransform affinetransform = new AffineTransform();     
		FontRenderContext frc = new FontRenderContext(affinetransform,true,true);     
		Font font = new Font("Tahoma", Font.PLAIN, 12);
		int textwidth = (int)(font.getStringBounds(mensaje, frc).getWidth());
		
		txtMensaje.setEditable(false);
		txtMensaje.setText(mensaje);
		txtMensaje.setMaximumSize(new Dimension(panelChat.getWidth(), panelChat.getHeight()));

		GridBagConstraints gbc_txtMensaje = new GridBagConstraints();
		if (emisor == Integer.valueOf(GestionUsuarios.logedUserInfo[0])) {
			gbc_txtMensaje.anchor = GridBagConstraints.EAST;
		} else {
			gbc_txtMensaje.anchor = GridBagConstraints.WEST;
		}
		
		gbc_txtMensaje.insets = new Insets(0, 0, 5, 0);
		gbc_txtMensaje.gridx = 0;
		gbc_txtMensaje.gridy = lastMensaje+1;
		
		lastMensaje++;
		panelMensajes.add(txtMensaje, gbc_txtMensaje);
		txtMensaje.setColumns(mensaje.length()+1);
		repaintLayout();
	}
	
	//Método para agregar una conversación a la lista de conversaciones (visual, no en la base de datos)
	public void agregarConversacion(String id, String nombre, String hora, String ultimoMensaje) {
		panelConversaciones.remove(panelPlaceholder);
		
		JPanel panelConversacion = new JPanel();
		panelConversacion.addMouseListener(mLs);
		panelConversacion.setName(id);
		
		panelConversacion.setBackground(new Color(255, 204, 255));
		panelConversacion.setBorder(BorderFactory.createLineBorder(Color.black));
		panelConversacion.setPreferredSize(new Dimension(200, 80));
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.anchor = GridBagConstraints.NORTH;
		gbc_panel_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = numConversaciones+1;
		numConversaciones++;
		panelConversaciones.add(panelConversacion, gbc_panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{132, 46, 0};
		gbl_panel_1.rowHeights = new int[]{14, 16, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panelConversacion.setLayout(gbl_panel_1);
		
		JLabel lblNombreConversacion = new JLabel("New label");
		lblNombreConversacion.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNombreConversacion.setText(nombre);
		GridBagConstraints gbc_lblNombreConversacion = new GridBagConstraints();
		gbc_lblNombreConversacion.fill = GridBagConstraints.BOTH;
		gbc_lblNombreConversacion.insets = new Insets(0, 0, 5, 0);
		gbc_lblNombreConversacion.gridwidth = 2;
		gbc_lblNombreConversacion.gridx = 0;
		gbc_lblNombreConversacion.gridy = 0;
		panelConversacion.add(lblNombreConversacion, gbc_lblNombreConversacion);
		
		JLabel lblUltimoMensaje = new JLabel("New label");
		lblUltimoMensaje.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GridBagConstraints gbc_lblUltimoMensaje = new GridBagConstraints();
		gbc_lblUltimoMensaje.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblUltimoMensaje.insets = new Insets(0, 0, 0, 5);
		gbc_lblUltimoMensaje.gridx = 0;
		gbc_lblUltimoMensaje.gridy = 1;
		panelConversacion.add(lblUltimoMensaje, gbc_lblUltimoMensaje);
		
		JLabel lblHora = new JLabel("New label");
		lblHora.setText(hora);
		GridBagConstraints gbc_lblHora = new GridBagConstraints();
		gbc_lblHora.anchor = GridBagConstraints.SOUTHWEST;
		gbc_lblHora.gridx = 1;
		gbc_lblHora.gridy = 1;
		panelConversacion.add(lblHora, gbc_lblHora);	
	
	}
	
	//Método para agregar contacto a la lista de contactos (visual, no en la base de datos)
	public void agregarContacto(String nombre) {
		panelConversaciones.remove(panelPlaceholder);
		
		JPanel panelContacto = new JPanel();
		panelContacto.addMouseListener(mLs);
		panelContacto.setName(nombre);
		
		panelContacto.setBackground(new Color(255, 204, 255));
		panelContacto.setBorder(BorderFactory.createLineBorder(Color.black));
		panelContacto.setPreferredSize(new Dimension(200, 80));
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.anchor = GridBagConstraints.NORTH;
		gbc_panel_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = numConversaciones+1;
		numConversaciones++;
		panelConversaciones.add(panelContacto, gbc_panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{132, 46, 0};
		gbl_panel_1.rowHeights = new int[]{14, 16, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panelContacto.setLayout(gbl_panel_1);
		
		JLabel lblNombreContacto = new JLabel("New label");
		lblNombreContacto.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNombreContacto.setText(nombre);
		GridBagConstraints gbc_lblNombreConversacion = new GridBagConstraints();
		gbc_lblNombreConversacion.fill = GridBagConstraints.BOTH;
		gbc_lblNombreConversacion.insets = new Insets(0, 0, 5, 0);
		gbc_lblNombreConversacion.gridwidth = 2;
		gbc_lblNombreConversacion.gridx = 0;
		gbc_lblNombreConversacion.gridy = 0;
		panelContacto.add(lblNombreContacto, gbc_lblNombreConversacion);
	}
	
	//Método para establecer que panel se verá en el lateral (contactos o conversaciones)
	public void cambiarPanelLateral(int panel) {
		panelActual = panel;
		repaintLayout();
		
		Timer timer = new Timer();
		timer.schedule(new AutoRefresh(interm.gMensajes), 0, 5000);
	}
}
