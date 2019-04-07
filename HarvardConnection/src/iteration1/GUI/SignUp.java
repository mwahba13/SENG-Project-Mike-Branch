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
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

import iteration1.controllers.UserController;

public class SignUp extends JInternalFrame {

	private JPanel contentPane;
	private JTextField inputtedEmail;
	private JTextField inputtedPassword;
	private JTextField inputtedFirstName;
	private JTextField inputtedLastName;
	private String reviewerInterest;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	public int selectedRole;
	
	private static SignUp myInstance;		// Instance of Class | Used in having one frame visible at all times

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
		setBounds(100, 100, 640, 480);		// Sets bounds for location and size | set for corner of DesktopPane
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
		
		
		
		
		JButton Return = new JButton("Go Back");					// Go Back Button, brings user back to main menu on mouse click
		Return.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
		
				 MainMenu  nw = MainMenu.getInstance();
				    nw.pack();
				    nw.setBounds(100, 100, 460, 314);
				    if (nw.isVisible()) {
				    } else {
				        getDesktopPane().add(nw);
				        nw.setVisible(true);
				    }
				    try {
				        nw.setMaximum(true);
				    } catch (Exception e1) {
				    	System.out.println(e1);
				    }
				    
				    getDesktopPane().revalidate();
				    getDesktopPane().repaint();
				    getDesktopPane().remove(myInstance);
				    myInstance = null;
			}
		});
		Return.setBounds(338, 15, 86, 23);
		contentPane.add(Return);
		
		
		
		JButton SignUp = new JButton("Sign Up");					// Sign Up Button, once pressed the information inputed in 
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
					
					JOptionPane.showMessageDialog(contentPane, "Please enter a valid username/password."
							+ "\nMake sure you've selected to be an author or reviewer.\nCannot have an empty field. \nNo spaces are allowed.");	// Error message
					return;
				}
	
				
				if (inputtedEmail.getText().contains(" ") || inputtedEmail.getText().isEmpty() || !inputtedEmail.getText().contains("@") )
				{
					JOptionPane.showMessageDialog(contentPane, "Please enter a valid email."); // Error Message
					return;
				}
				
				
				
				
				try {
					if (selectedRole == 2) {
						
						reviewerInterest = JOptionPane.showInputDialog("Please tell us what your research interests are.");

						UserController.registerReviewer(inputtedEmail.getText(), inputtedFirstName.getText(), inputtedLastName.getText(), inputtedPassword.getText(), selectedRole,reviewerInterest);
					}
					else {
						System.out.println("Debug 2");

						UserController.register(inputtedEmail.getText(), inputtedFirstName.getText(), inputtedLastName.getText(), inputtedPassword.getText(), selectedRole);				// Register method in UserController class

					}
				
					
				} catch (NoSuchAlgorithmException | InvalidKeySpecException | SQLException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
								
				JOptionPane.showMessageDialog(contentPane, "Congrats! When your registration is confirmed, you'll be notified!");		// Window shows up to show you've registered
				System.out.println(selectedRole);
				dispose();        						//~~~~ disposes window
				MainMenu main = new MainMenu();			// Goes back to main menu
				main.setVisible(true);
				
			}
		});
		SignUp.setBounds(179, 228, 86, 23);
		contentPane.add(SignUp);
		
	}
	
	
	
	public static SignUp getInstance() {
	    if (myInstance == null) {
	        myInstance = new SignUp();
	    }
	    return myInstance;
	}
}
