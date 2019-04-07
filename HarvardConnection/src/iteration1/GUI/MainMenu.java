package iteration1.GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Label;

public class MainMenu extends JInternalFrame {

	private JPanel contentPane;
	private static MainMenu myInstance;		// Instance of Class | Used in having one frame visible at all times

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainMenu frame = new MainMenu();
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
	public MainMenu() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 640, 480);		// Sets bounds for location and size | set for corner of DesktopPane
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		// Sign Up Button: When clicked, user is sent to sign up menu
		JButton btnSignUp = new JButton("Sign Up");
		btnSignUp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {	// Click listener
				
				 SignUp  nw = SignUp.getInstance();		// Creates new SignUp Window
				 nw.pack();								// Causes subcomponents of this JInternalFrameto be laid out at their preferred size.
				 getDesktopPane().add(nw);				// Adds Instance of frame to DesktopPane on StartingFrame
				 nw.setVisible(true);					// Sets Instance frame visible
				 
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
		btnSignUp.setBounds(252, 244, 117, 25);	// Set bounds for button
		contentPane.add(btnSignUp);				// add button to content pane
		
		
		// Label "Sign up or sign in"
		JLabel lblSignUpOr = new JLabel("Sign Up or Sign In");
		lblSignUpOr.setForeground(Color.DARK_GRAY);
		lblSignUpOr.setFont(new Font("Arial Black", Font.PLAIN, 16));
		lblSignUpOr.setBounds(230, 168, 157, 40);
		contentPane.add(lblSignUpOr);
		
		
		// Log in Button: When clicked, user is sent to log in frame
		JButton button = new JButton("Log In");
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
//				contentPane.setVisible(false);
//				dispose(); 						//Closes window after opening
//				LogIn log = new LogIn();
//				log.setVisible(true);
				
				 LogIn  nw = LogIn.getInstance();			// Creates new SignUp Window
				    nw.pack();
//				    nw.setBounds(100, 100, 460, 314);
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
//				    dispose();
				    myInstance = null;
//				    getInstance();
//				    dispose();
//				    revalidatePane();
//				    dispose();
//				    myInstance = null;
			}
		});
		button.setBounds(252, 208, 117, 25);
		contentPane.add(button);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(System.getProperty("user.dir") + "\\img\\MainMenuBack.jpg"));
		label.setBounds(142, 110, 342, 225);
		contentPane.add(label);
		
		Label label_1 = new Label("");
		label_1.setBackground(Color.WHITE);
		label_1.setBounds(130, 99, 367, 247);
		contentPane.add(label_1);
	}
	
	
	
	/*
	 * Get Instance Method | Returns Instance of class when called
	 */
	public static MainMenu getInstance() {	
	    if (myInstance == null) {				// If instance is null, create new instance
	        myInstance = new MainMenu();		// Set new instance
	    }
	    return myInstance;						// Return instance
	}
}
