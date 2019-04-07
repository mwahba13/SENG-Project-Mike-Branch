package iteration1.GUI;

import java.awt.Button;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

import iteration1.controllers.UserLoginModule;
import iteration1.models.User;
import iteration1.repositories.LogInDataContainer;
import iteration1.repositories.SQLiteConnection;
import iteration1.repositories.UserRepository;

public class LogIn extends JInternalFrame {

	private JPanel contentPane;
	private JTextField emailInput;
	private JTextField passwordInput;
	private JLabel lblEmail;
	private JLabel lblPassword;
	private JButton LogIn;
	private JButton Return;
	
	private static LogIn myInstance;		// Used for having only 1 frame open at a time
	
	public int selectedRole;
	
	private boolean success;
	
	UserLoginModule login = new UserLoginModule();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LogIn frame = new LogIn();
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
	public LogIn() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 640, 480);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		emailInput = new JTextField();
		emailInput.setBounds(79, 39, 183, 23);
		contentPane.add(emailInput);
		emailInput.setColumns(10);
		
		passwordInput = new JPasswordField();
		passwordInput.setColumns(10);
		passwordInput.setBounds(79, 140, 183, 23);
		contentPane.add(passwordInput);
		
		lblEmail = new JLabel("Email");
		lblEmail.setBounds(12, 12, 106, 15);
		contentPane.add(lblEmail);
		
		lblPassword = new JLabel("Password");
		lblPassword.setBounds(12, 113, 70, 15);
		contentPane.add(lblPassword);
		
		
		/**
		 * Go back button to go to previous window
		 */
		Return = new JButton("Go Back");
		Return.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				 MainMenu nw = MainMenu.getInstance();		// Creates new Window
				 nw.pack();									// Causes subcomponents of this JInternalFrameto be laid out at their preferred size.
				 getDesktopPane().add(nw);					// Adds Instance of frame to DesktopPane on StartingFrame
				 nw.setVisible(true);						// Sets Instance frame visible
				 
				 try {
				     nw.setMaximum(true);				// Sets window to max size of DesktopPane
				 } catch (Exception e1) {
					 System.out.println(e1);
				 }
				 
				 getDesktopPane().repaint();			// Repaints the DesktopPane
				 getDesktopPane().remove(myInstance);	// Removes the instance of current Class
				 myInstance = null;						// Sets instance to null

			}
		});
		Return.setBounds(338, 10, 86, 23);
		contentPane.add(Return);
		
		
		
		
		LogIn = new JButton("Log In\n");
		LogIn.setBounds(180, 228, 86, 23);
		contentPane.add(LogIn);
		LogIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				success = login.validate(emailInput.getText(), passwordInput.getText());					// Logs in and checks if account exists (little sloppy)
				
				SQLiteConnection conn = new SQLiteConnection();												// Connects to database
				try {
					User user = UserRepository.getUserByEmail(conn.getConn(), emailInput.getText());	// logs in again to grab Role ID (Little sloppy, can go and change later on)
					
					selectedRole = user.getRoleID();								// Gets role ID and sets it to selectedRole
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}		// Login selected by Email
				
				
				System.out.println(selectedRole);			// Debug print statement, shows role number
				
				if(!success) {					// If login doesn't succeed 
					JOptionPane.showMessageDialog(contentPane, "Email or Password are incorrect");		// error message
				}
				
				// Setters for LogInInformationContainer
				LogInDataContainer.setEmail(emailInput.getText());
				LogInDataContainer.setRoleID(selectedRole);

				
				
				if(success && selectedRole == 3) {				// If log in succeeds and role number is 3 (aka Reviewer)
					
					 AuthorMenu nw = AuthorMenu.getInstance();	// Creates new Window
					 nw.pack();									// Causes subcomponents of this JInternalFrameto be laid out at their preferred size.
					 getDesktopPane().add(nw);					// Adds Instance of frame to DesktopPane on StartingFrame
					 nw.setVisible(true);						// Sets Instance frame visible
					 
					 try {
					     nw.setMaximum(true);				// Sets window to max size of DesktopPane
					 } catch (Exception e1) {
						 System.out.println(e1);
					 }
					 
					 getDesktopPane().repaint();			// Repaints the DesktopPane
					 getDesktopPane().remove(myInstance);	// Removes the instance of current Class
					 myInstance = null;						// Sets instance to null
				}
				
				else if(success && selectedRole == 2) {				// If log in succeeds and role number is 2 (aka Author)

					 ReviewerMenu nw = ReviewerMenu.getInstance();	// Creates new Window
					 nw.pack();									// Causes subcomponents of this JInternalFrameto be laid out at their preferred size.
					 getDesktopPane().add(nw);					// Adds Instance of frame to DesktopPane on StartingFrame
					 nw.setVisible(true);						// Sets Instance frame visible
					 
					 try {
					     nw.setMaximum(true);				// Sets window to max size of DesktopPane
					 } catch (Exception e1) {
						 System.out.println(e1);
					 }
					 
					 getDesktopPane().repaint();			// Repaints the DesktopPane
					 getDesktopPane().remove(myInstance);	// Removes the instance of current Class
					 myInstance = null;						// Sets instance to null
				}
				
				else if(success && selectedRole == 1) {				// If log in succeeds and role number is 1 (aka Administrator)
					
					 AdminMenu nw = AdminMenu.getInstance();	// Creates new Window
					 nw.pack();									// Causes subcomponents of this JInternalFrameto be laid out at their preferred size.
					 getDesktopPane().add(nw);					// Adds Instance of frame to DesktopPane on StartingFrame
					 nw.setVisible(true);						// Sets Instance frame visible
					 
					 try {
					     nw.setMaximum(true);				// Sets window to max size of DesktopPane
					 } catch (Exception e1) {
						 System.out.println(e1);
					 }
					 
					 getDesktopPane().repaint();			// Repaints the DesktopPane
					 getDesktopPane().remove(myInstance);	// Removes the instance of current Class
					 myInstance = null;						// Sets instance to null
				}
				
			}
		});
	}
	
	/*
	 * Get Instance Method | Returns Instance of class when called
	 */
	public static LogIn getInstance() {	
	    if (myInstance == null) {			// If instance is null, create new instance
	        myInstance = new LogIn();		// Set new instance
	    }
	    return myInstance;					// Return instance
	}
}
