package iteration1.GUI;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import iteration1.models.Upload;
import iteration1.models.User;
import iteration1.repositories.LogInDataContainer;
import iteration1.repositories.ReviewRepository;
import iteration1.repositories.SQLiteConnection;
import iteration1.repositories.UploadRepository;
import iteration1.repositories.UserRepository;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AuthorPickReviewer extends JInternalFrame {

	private JPanel contentPane;
	private static AuthorPickReviewer myInstance;		// Instance of Class | Used in having one frame visible at all times
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AuthorPickReviewer frame = new AuthorPickReviewer();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public String[] getData() {
		SQLiteConnection conn = new SQLiteConnection();	
		ArrayList<User> users = null;
		
		try {
			users = UserRepository.getReviewers(conn.getConn());
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		
		String[] reviewers = new String[users.size()];
		
		for (int i = 0; i < users.size(); i++) {
			//reviewers[i] = users.get(i).getFirstName() + " " + users.get(i).getLastName();
			reviewers[i] = users.get(i).getEmail();
		}
		
		return reviewers;
	}
	

	/**
	 * Create the frame.
	 */
	public AuthorPickReviewer() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 640, 480);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JList reviewerList = new JList(getData());
		reviewerList.setBounds(12, 85, 92, 145);
		contentPane.add(reviewerList);
		
		JList selectedList = new JList();
		selectedList.setBounds(234, 85, 92, 145);
		contentPane.add(selectedList);
		
		JButton btnMove = new JButton("select -->");
		btnMove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedList.setListData(reviewerList.getSelectedValues());
			}
		});
		
		btnMove.setBounds(116, 113, 101, 25);
		contentPane.add(btnMove);
		
		JButton btnChooseFile = new JButton("Choose file");
		btnChooseFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {												// THIS IS WHEN UPLOAD BUTTON IS CLICKED
		    	System.out.println("working Directory:" + System.getProperty("user.dir"));			// DEBUG PRINT STATEMENT TO SHOW WORKING DIRECTORY
		    	
			    JFileChooser chooser = new JFileChooser();											// J FILE CHOOSER TO CHOOSE FILE TO COPY
			    
		    	System.out.println("Directory after JFileChooser" + chooser.getCurrentDirectory());		// SHOWS CURRENT DIRECTORY AFTER OPENING JFILECHOOSER
		    	
			    chooser.setDialogTitle("Upload File");													// SETS TITLE OF NEW FILE CHOOSER WINDOW
			    int returnVal = chooser.showOpenDialog(null);				    
			    if(returnVal == JFileChooser.APPROVE_OPTION) {						// I guess if the file chosen is valid, run through?

			       System.out.println("You chose to open this file: " +	chooser.getSelectedFile().getName());	// what file is chosen and its directory
			       
			       System.out.println("THIS IS WHAT I'M COPYING:" + chooser.getSelectedFile().toPath());			// debug print statement to show what i'm copying	       
			       
			       
			       Path emailDirectory = Paths.get(System.getProperty("user.dir") + "\\uploads\\"		// Creates Path to Email
				    		  + LogInDataContainer.getEmail());											// Used in making the directory
			       
			       Path copyToDirectory = Paths.get(System.getProperty("user.dir") + "\\uploads\\"			// Creates path to Uploads Directory
			    		  + LogInDataContainer.getEmail() + "\\" + chooser.getSelectedFile().getName());	//
			       
			       File destFolder = new File(emailDirectory.toString());									// Directory Folder Created with Users Email (Uses LogInDataContainer so you need to log in to test correctly)			       
			       destFolder.mkdir();																		// Creates a new directory with Users Email in Uploads Folder	
				       
			       		try {
				    	Files.copy(chooser.getSelectedFile().toPath(), copyToDirectory, REPLACE_EXISTING);		// final copy statement. first parameter is file, second is where to copy.
				    	Upload upload = UploadRepository.addUpload(new SQLiteConnection().getConn(), LogInDataContainer.getEmail(), copyToDirectory.toString().substring(copyToDirectory.toString().indexOf("upload")));
				    	int id = upload.getId();
				    	for(int i=0; i < selectedList.getModel().getSize(); i++) {
				    		ReviewRepository.addReviewer(new SQLiteConnection().getConn(),(String)selectedList.getModel().getElementAt(i), id);
				    	}
						JOptionPane.showMessageDialog(contentPane, "Congratulations! Your paper has been submitted. \ncheck back for updates on your submission"); 	// Pop-up Confirmation of Upload
				    	
				    	// NEED TO ADD REVIEW TO DB WITH ID FROM upload AND REVIEWS FROM selectedList // 
				    	
				       } catch (IOException | SQLException e) {
				    	   System.out.println(e.getMessage());
				       }
				       
				   }
				    
			    
			}
		});
		
		
		
		btnChooseFile.setBounds(321, 233, 117, 25);
		contentPane.add(btnChooseFile);
		
		JLabel lblSelectReviewerYou = new JLabel("Select reviewer you would like to review your paper\n");
		lblSelectReviewerYou.setBounds(22, 0, 411, 25);
		contentPane.add(lblSelectReviewerYou);
		
		JButton button = new JButton("restart");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				AuthorPickReviewer pick = new AuthorPickReviewer();
				pick.setVisible(true);
			}
		});
		button.setBounds(116, 161, 106, 25);
		contentPane.add(button);
		
		
		JButton GoBackButton = new JButton("Go Back");
		GoBackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AuthorMenu nw = AuthorMenu.getInstance();	// Creates new Window
				nw.pack();									// Causes subcomponents of this JInternalFrameto be laid out at their preferred size.
				getDesktopPane().add(nw);					// Adds Instance of frame to DesktopPane on StartingFrame
				nw.setVisible(true);						// Sets Instance frame visible
				 
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
		GoBackButton.setBounds(508, 18, 106, 25);
		contentPane.add(GoBackButton);
		
		
		JLabel lblToSelectMultiple = new JLabel("To select multiple users hold down ctrl when selecting");
		lblToSelectMultiple.setBounds(22, 23, 401, 15);
		contentPane.add(lblToSelectMultiple);
		
		JLabel lblSelectRestartTo = new JLabel("Select restart to start again");
		lblSelectRestartTo.setBounds(22, 42, 368, 15);
		contentPane.add(lblSelectRestartTo);
	}
	
	
	/*
	 * Get Instance Method | Returns Instance of class when called
	 */
	public static AuthorPickReviewer getInstance() {	
	    if (myInstance == null) {			// If instance is null, create new instance
	        myInstance = new AuthorPickReviewer();		// Set new instance
	    }
	    return myInstance;					// Return instance
	}
}
