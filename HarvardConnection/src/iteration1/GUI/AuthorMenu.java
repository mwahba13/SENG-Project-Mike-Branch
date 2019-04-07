package iteration1.GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.*; // Added for copy options i.e. REPLACE_EXISTING (replaces file if already exists)

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import iteration1.repositories.LogInDataContainer;

import javax.swing.JLabel;

public class AuthorMenu extends JInternalFrame {

	private JPanel contentPane;
	private static AuthorMenu myInstance;		// Instance of Class | Used in having one frame visible at all times

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AuthorMenu frame = new AuthorMenu();
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
	public AuthorMenu() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 640, 480);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		
		// Label to show the Authors's Email
		JLabel lblAuthorMenu = new JLabel(LogInDataContainer.getEmail());
		lblAuthorMenu.setBounds(10, 25, 179, 104);
		contentPane.add(lblAuthorMenu);
		
		
		
		// Logout button: When pressed, user is logged out
		JButton btnLogOut = new JButton("Log Out");
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				 LogIn nw = LogIn.getInstance();		// Creates new Window
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
		btnLogOut.setBounds(321, 0, 117, 25);
		contentPane.add(btnLogOut);
		
		
		
		// Upload paper button: When clicked, opens AuthorPicReviewer,
		// and allows the author to choose reviewers they prefer
		JButton btnUploadPaper = new JButton("Upload Paper");
		btnUploadPaper.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				AuthorPickReviewer pick = new AuthorPickReviewer();
//				pick.setVisible(true);
				
				AuthorPickReviewer nw = AuthorPickReviewer.getInstance();		// Creates new Window
				nw.pack();								// Causes subcomponents of this JInternalFrameto be laid out at their preferred size.
				getDesktopPane().add(nw);				// Adds Instance of frame to DesktopPane on StartingFrame
				nw.setVisible(true);					// Sets Instance frame visible
				 
				try {
				    nw.setMaximum(true);				// Sets window to max size of DesktopPane
				} catch (Exception e1) {
					System.out.println(e1);
				}
				 
				getDesktopPane().repaint();				// Repaints the DesktopPane
				getDesktopPane().remove(myInstance);	// Removes the instance of current Class
				myInstance = null;						// Sets instance to null
			}
		});
		btnUploadPaper.setBounds(10, 100, 150, 23);
		contentPane.add(btnUploadPaper);
		
		
		
		// Download Paper Button: When clicked, the user can choose to download their submitted paper, or reviewed papers
		JButton btnDownloadReviewedPaper = new JButton("Download Paper");
		btnDownloadReviewedPaper.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {				// When button is clicked
			    
				
		    	JFileChooser chooser = new JFileChooser();
			    String userFolder;
			    userFolder = (System.getProperty("user.dir") + "\\uploads\\" 
			    				+ LogInDataContainer.getEmail());	// user folder path
		    	chooser.setCurrentDirectory(new File(userFolder));	// sets the directory
			    chooser.setDialogTitle("Download File");			// Sets title of JFileChooser

			    
			    int returnVal = chooser.showOpenDialog(null);
			    if(returnVal == JFileChooser.APPROVE_OPTION) {		// When file chosen if valid

			    	
			       System.out.println("You chose to open this file: " +				// Debug print Statement to show
			    		   chooser.getSelectedFile().getName() + 					// what file is chosen
			    		   "\nThis is the directory:" + chooser.getSelectedFile());	// and its directory
			       
			       
			       String copyToDirectory1 = System.getProperty("user.home") + "\\Downloads\\" + chooser.getSelectedFile().getName();	// Different Copy Directory
			       Path copyToDirectory = Paths.get(copyToDirectory1); // path set up to copy file
			       
			       
			       try {
			    	Files.copy(chooser.getSelectedFile().toPath(), copyToDirectory, REPLACE_EXISTING);		// final copy statement. first parameter is file, second is where to copy.
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			       
			    }
			}

		});
		btnDownloadReviewedPaper.setBounds(200, 100, 150, 23);
		contentPane.add(btnDownloadReviewedPaper);
	}
	
	
	/*
	 * Get Instance Method | Returns Instance of class when called
	 */
	public static AuthorMenu getInstance() {	
	    if (myInstance == null) {			// If instance is null, create new instance
	        myInstance = new AuthorMenu();		// Set new instance
	    }
	    return myInstance;					// Return instance
	}
}
