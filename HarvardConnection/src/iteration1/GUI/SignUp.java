package iteration1.GUI;

import java.awt.Button;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

import iteration1.controllers.UserController;

public class SignUp extends JFrame {

	private JPanel contentPane;
	private JTextField inputtedEmail;
	private JTextField inputtedPassword;
	private JTextField inputtedFirstName;
	private JTextField inputtedLastName;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	public int selectedRole;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SignUp frame = new SignUp();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	

	/**
	 * Create the frame.
	 */
	public SignUp() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		
		inputtedEmail = new JTextField();			// TextField for UserName input
		inputtedEmail.setBounds(12, 40, 114, 19);
		contentPane.add(inputtedEmail);
		inputtedEmail.setColumns(10);
		
		
		
		inputtedPassword = new JPasswordField();			// TextField for Password input
		inputtedPassword.setBounds(12, 100, 114, 19);
		contentPane.add(inputtedPassword);
		inputtedPassword.setColumns(10);
		
		
		
		inputtedFirstName = new JTextField();				// TextField for Email input
		inputtedFirstName.setBounds(12, 160, 114, 19);
		contentPane.add(inputtedFirstName);
		inputtedFirstName.setColumns(10);
		
		
		
		inputtedLastName = new JTextField();
		inputtedLastName.setBounds (12, 220, 114, 19);
		contentPane.add(inputtedLastName);
		inputtedLastName.setColumns(10);
		
		
		
		JLabel lblEmail = new JLabel("Email");	// Label above UserName TextField
		lblEmail.setBounds(12, 25, 70, 15);
		contentPane.add(lblEmail);
		
		
		
		JLabel lblPassword = new JLabel("Password");	// Label above Password TextField
		lblPassword.setBounds(12, 85, 70, 15);
		contentPane.add(lblPassword);
		
		
		
		JLabel lblFirstName = new JLabel("Full Name");		// Label above email TextBoxField
		lblFirstName.setBounds(12, 145, 70, 15); 
		contentPane.add(lblFirstName);
		
		JLabel lblLastName = new JLabel("Last Name");
		lblLastName.setBounds(12, 205, 90, 15);
		contentPane.add(lblLastName);
		
		
		JRadioButton rdbtnAuthor = new JRadioButton("Author");		// JRadioButton Author Option
		buttonGroup.add(rdbtnAuthor);
		rdbtnAuthor.setBounds(231, 39, 109, 23);
		contentPane.add(rdbtnAuthor);
		
		
		
		JRadioButton rdbtnReviewer = new JRadioButton("Reviewer");	//JRadioButton Reviewer Option
		buttonGroup.add(rdbtnReviewer);
		rdbtnReviewer.setBounds(231, 65, 109, 23);
		contentPane.add(rdbtnReviewer);
		
		
		
		JTextPane txtpnMessageDisplay = new JTextPane();			// text pane to display messages
		txtpnMessageDisplay.setBackground(Color.WHITE);
		txtpnMessageDisplay.setBounds(179, 120, 245, 96);
		contentPane.add(txtpnMessageDisplay);
		
		
		
		Button Return = new Button("Go Back");					// Go Back Button, brings user back to main menu on mouse click
		Return.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dispose();										// disposes window and goes back to main menu
				contentPane.setVisible(false);
				MainMenu main = new MainMenu();
				main.setVisible(true);
			}
		});
		Return.setBounds(338, 15, 86, 23);
		contentPane.add(Return);
		
		
		
		Button SignUp = new Button("Sign Up");					// Sign Up Button, once pressed the information inputed in 
		SignUp.addMouseListener(new MouseAdapter() {			// TextFields and role option selected are registered into database
			public void mouseClicked(MouseEvent e) {
				
				if (rdbtnAuthor.isSelected()) {					// Checks if Author Role is selected and changes selectedRole variable to 2
					selectedRole = 3;
				}
				
				if (rdbtnReviewer.isSelected()) {				// Checks if Reviewer Role is selected and changes selectedRole variable to 3
					selectedRole = 2;
				}
				
				if ((!rdbtnAuthor.isSelected() && !rdbtnReviewer.isSelected()) 										// If neither roles are chosen or User name/password contains spaces
						|| inputtedEmail.getText().contains(" ") || inputtedEmail.getText().isEmpty()			// Error message is then displayed
						|| inputtedPassword.getText().contains(" ") || inputtedPassword.getText().isEmpty()){
					
					txtpnMessageDisplay.setText("Please enter a valid username/password.\nMake sure you've selected to be an author or reviewer.\nCannot have an empty field. No spaces are allowed.");	// error message
					return;
				}
	
				
				if (inputtedEmail.getText().contains(" ") || inputtedEmail.getText().isEmpty() || !inputtedEmail.getText().contains("@") )
				{
					txtpnMessageDisplay.setText("Please enter a valid email.");
					return;
				}
				
				try {
					UserController.register(inputtedEmail.getText(), inputtedFirstName.getText(), inputtedLastName.getText(), inputtedPassword.getText(), selectedRole);				// Register method in UserController class
				} catch (NoSuchAlgorithmException | InvalidKeySpecException | SQLException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				JOptionPane.showMessageDialog(null, "Congrats! When your registration is confirmed, you'll be notified!");		// Window shows up to show you've registered
				System.out.println(selectedRole);
				dispose();        						//~~~~ disposes window
				MainMenu main = new MainMenu();			// Goes back to main menu
				main.setVisible(true);
				
			}
		});
		SignUp.setBounds(179, 228, 86, 23);
		contentPane.add(SignUp);
		
	}
}
