package iteration1.GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;			//   Added for copying files
import java.nio.file.Path;				// Added for copying files
import static java.nio.file.StandardCopyOption.*; // Added for copy options
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;			// File visitor for visiting trees
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;		// Added to choose files that are going to be copied
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import iteration1.models.Review;
import iteration1.models.Upload;
import iteration1.models.User;
import iteration1.repositories.LogInDataContainer;
import iteration1.repositories.ReviewRepository;
import iteration1.repositories.SQLiteConnection;
import iteration1.repositories.UploadRepository;
import iteration1.repositories.UserRepository;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

public class ReviewerMenu extends JInternalFrame {
	
	private static ReviewerMenu myInstance;		// Instance of Class | Used in having one frame visible at all times
	private JPanel contentPane;
	DefaultListModel<Integer> uploadIDList = new DefaultListModel();		// Tried to make a list
	DefaultListModel<String> authorList = new DefaultListModel();			// TO show assigned papers

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ReviewerMenu frame = new ReviewerMenu();
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
	public ReviewerMenu() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 640, 480);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		SQLiteConnection conn = new SQLiteConnection();
		
		
		// The following Try block is here to get the Logged In Reviewers assigned PaperList and AuthorList
		try {
			ArrayList<Review> uploadIDArray = ReviewRepository.getReviewsByReviewer(conn.getConn(), LogInDataContainer.getEmail());	// Takes Logged In Reviwers email and puts all Reviews into uploadIDArray		
			// System.out.println(uploadIDArray);

			
			for (int i = 0; i < uploadIDArray.size();i++) {		// Iterate through uploadIDArray and put uploadID's into uploadIDList
				int tempID = (uploadIDArray.get(i).getUploadId());
				uploadIDList.addElement(tempID);					//Resulting list is the upload ID's assigned to the logged in Reviewer
			}
			
			
			for (int x = 0; x < uploadIDList.size();x++) {
				System.out.println("\nThe Val of X: " + x);
				ArrayList<Upload> authorEmailArray = UploadRepository.getUploadsByID(conn.getConn(), uploadIDList.get(x));
				System.out.println("AUTHOR EMAIL: " + authorEmailArray.get(0).getEmail());
				String tempEmail = (authorEmailArray.get(0).getEmail());
				authorList.addElement(tempEmail);
			}
			System.out.println("\nUploadIDList: " + uploadIDList);		// DEBUG PRINT: Prints out a list of uploadID's of assigned papers for the logged in User
			System.out.println("authorList Emails: " + authorList);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
		//Make the assigned AuthorList into a JList
		JList assignedAuthorList = new JList(authorList.toArray());
		assignedAuthorList.setBounds(225, 239, -81, -82);
		contentPane.add(assignedAuthorList);
		
		// Insert JList into scrollable Pane
		JScrollPane scrollPane = new JScrollPane(assignedAuthorList);
		scrollPane.setBounds(10, 107, 147, 133);
		contentPane.add(scrollPane);
		
		// Label above Scrollable Pane
		JLabel lblSelectAssignedAuthor = new JLabel("Select Assigned Author");
		lblSelectAssignedAuthor.setBounds(10, 83, 147, 16);
		contentPane.add(lblSelectAssignedAuthor);
		
		
		
		
		JLabel lblReviewerMenu = new JLabel("REVIEWER MENU");
		lblReviewerMenu.setBounds(10, 11, 128, 23);
		contentPane.add(lblReviewerMenu);
		
		
		
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
		
		
		
		JButton btnUploadReviewedPaper = new JButton("Upload Reviewed Paper");
		btnUploadReviewedPaper.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				// THIS IS WHEN UPLOAD BUTTON IS CLICKED
		    	System.out.println("working Directory:" + System.getProperty("user.dir"));		// DEBUG PRINT STATEMENT TO SHOW WORKING DIRECTORY
			    JFileChooser chooser = new JFileChooser();							// J FILE CHOOSER TO CHOOSE FILE TO COPY
		    	System.out.println("Directory after JFileCHooser" + chooser.getCurrentDirectory());					// SHOWS CURRENT DIRECTORY AFTER OPENING JFILECHOOSER
			    chooser.setDialogTitle("Upload File");				// SETS TITLE OF NEW FILE CHOOSER WINDOW

			    int returnVal = chooser.showOpenDialog(null);						// HONESTLY DONT KNOW
			    if(returnVal == JFileChooser.APPROVE_OPTION) {						// I guess if the file chosen is valid, run through?

			       System.out.println("You chose to open this file: " +													// Debug print Statement to show
			            chooser.getSelectedFile().getName() + "\nThis is the directory:" + chooser.getSelectedFile());	// what file is chosen and its directory
			       
			       System.out.println("THIS IS WHAT I'M COPYING:" + chooser.getSelectedFile().toPath());			// debug print statement to show what i'm copying
			       
			       Path copyToDirectory = Paths.get(System.getProperty("user.dir")+ "\\uploads\\" + assignedAuthorList.getSelectedValue() + "/" + chooser.getSelectedFile().getName());	// path set up to copy file
			       try {
			    	   
			    	Files.copy(chooser.getSelectedFile().toPath(), copyToDirectory, REPLACE_EXISTING);		// final copy statement. first parameter is file, second is where to copy.
				    JOptionPane.showMessageDialog(contentPane, "Your REVIEW has been uploaded! The User can now view it.");		// Upload Review Paper Confirmation
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					}
			    }
			    
			    
			}
		});
		btnUploadReviewedPaper.setBounds(10, 47, 147, 23);
		contentPane.add(btnUploadReviewedPaper);
		
		
		
		
		
		
		
		
		
		JButton btnDownloadPapers = new JButton("Download Papers");
		btnDownloadPapers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				// THIS IS WHEN UPLOAD BUTTON IS CLICKED
		    	System.out.println("working Directory:" + System.getProperty("user.dir"));		// DEBUG PRINT STATEMENT TO SHOW WORKING DIRECTORY
			    JFileChooser chooser = new JFileChooser();		
			    String userFolder;
			    
			    // Sets up path for Downloading Selecting Authors Files
			    userFolder = System.getProperty("user.dir")+ "\\uploads\\" + assignedAuthorList.getSelectedValue();// J FILE CHOOSER TO CHOOSE FILE TO COPY
		    	chooser.setCurrentDirectory(new File(userFolder));// SHOWS CURRENT DIRECTORY AFTER OPENING JFILECHOOSER
			    chooser.setDialogTitle("Download File");				// SETS TITLE OF NEW FILE CHOOSER WINDOW
			    System.out.println("Directory after JFileChooser" + chooser.getCurrentDirectory() );

			    int returnVal = chooser.showOpenDialog(null);						// HONESTLY DONT KNOW
			    if(returnVal == JFileChooser.APPROVE_OPTION) {						// I guess if the file chosen is valid, run through?

			       System.out.println("You chose to open this file: " +													// Debug print Statement to show
			            chooser.getSelectedFile().getName() + "\nThis is the directory:" + chooser.getSelectedFile());	// what file is chosen and its directory
			       
			       System.out.println("THIS IS WHAT I'M COPYING:" + chooser.getSelectedFile().toPath());			// debug print statement to show what i'm copying
			       
			       String copyToDirectory1 = System.getProperty("user.home") + "\\Downloads\\" + chooser.getSelectedFile().getName();	// Different Copy Directory
			       System.out.println("This is the copyToDirectory path" + copyToDirectory1);		// copyDirectory 1
			       Path copyToDirectory = Paths.get(copyToDirectory1); // path set up to copy file Paths.get(System.getProperty("user.dir")+ "\\SavedFiles\\" + chooser.getSelectedFile().getName())
			       try {
			    	   
			    	Files.copy(chooser.getSelectedFile().toPath(), copyToDirectory, REPLACE_EXISTING);		// final copy statement. first parameter is file, second is where to copy.
					JOptionPane.showMessageDialog(contentPane, "Your selected File has been downloaded."); // Download Confirmation
				} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

			    }
			    				
			}
		});
		btnDownloadPapers.setBounds(169, 47, 147, 23);
		contentPane.add(btnDownloadPapers);
		
		
	} 	// ReviewerMenu Method Bracket
	
	
	/*
	 * Get Instance Method | Returns Instance of class when called
	 */
	public static ReviewerMenu getInstance() {	
	    if (myInstance == null) {			// If instance is null, create new instance
	        myInstance = new ReviewerMenu();		// Set new instance
	    }
	    return myInstance;					// Return instance
	}
}
