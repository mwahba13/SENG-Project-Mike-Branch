package iteration1.GUI;

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
import javax.swing.border.EmptyBorder;

import iteration1.models.User;
import iteration1.repositories.SQLiteConnection;
import iteration1.repositories.UserRepository;

public class AuthorizeReviewers extends JInternalFrame{

	private User user;
	private static String reviewerUsername;
	private static Integer reviewerApprovedStatusNumber;
	private JPanel contentPane;
	private static AuthorizeReviewers myInstance;		// Instance of Class | Used in having one frame visible at all times
	private static String reviewerInterest;					//String of reviewers research interests
	
	
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AuthorizeReviewers frame = new AuthorizeReviewers(reviewerUsername, reviewerApprovedStatusNumber,reviewerInterest);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * AuthorizeReviewers: Username and approvedValue as input
	 * 				
	 */
	public AuthorizeReviewers(String SelectedUsername, Integer approvedValue,String interests) {
		reviewerUsername = SelectedUsername;			// Sets username when Reviewer is clicked in ManageReviewers.java
		reviewerApprovedStatusNumber = approvedValue;	// Sets approvedStatusNumber
		reviewerInterest = interests;
		
		SQLiteConnection conn = new SQLiteConnection();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 640, 480);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5,5,5,5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		/**
		 * JLabel | Shows reviewer name
		 */
		JLabel userName = new JLabel("Reviewer: " + this.reviewerUsername);
		userName.setBounds(150,50,300,100);
		contentPane.add(userName);
		
		
		/**
		 * JLabel | Shows approval status of selected Reviewer	
		 */
		JLabel approval = new JLabel ("Approval Status: " + approvedToReview(this.reviewerApprovedStatusNumber));
		approval.setBounds(150,100,400,100);
		contentPane.add(approval);
		
		/**
		 * JLabel | Shows reviewers research intersts
		 */
		
		JLabel researchInterest = new JLabel("Research Interests: " + this.reviewerInterest);
		researchInterest.setBounds(150,125,400,100);
		contentPane.add(researchInterest);
		
		
		/**
		 * JButton | "Go Back" Button		
		 */
		JButton btnGoBack = new JButton("Go Back");						// Create New JButton
		btnGoBack.addActionListener(new ActionListener() {				// Listener for actions
			public void actionPerformed(ActionEvent e) {				// ActionPerformed
				ManageReviewers nw = ManageReviewers.getInstance();	// Creates new Window
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
		btnGoBack.setBounds(324,11,100,25);	// Sets location bounds for button
		contentPane.add(btnGoBack); 		// adds button to pane
		
		
		
		
		
		// JButton | "Accept Reviewer" Button | When pressed, changes the selected reviewers "approved" value to 1 | 1 means allowed to review papers
		JButton btnAcceptReviewer = new JButton ("Accept Reviewer");
		btnAcceptReviewer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				updateRoleIDInDatabase();			// Calls updateRoleIdInDatabase method in this file (It's below)
				JOptionPane.showMessageDialog(contentPane, "The selected user has been ACCEPTED and is now able to review assigned papers");	// Confirmation Message
				
				ManageReviewers nw = ManageReviewers.getInstance();	// Creates new Window
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
		btnAcceptReviewer.setBounds(227,200,157,25);	// Sets location bounds for button
		contentPane.add(btnAcceptReviewer);				// adds button to pane
		
		
		
		
		
		// JButton | "Decline Reviewer" Button | When pressed, changes the selected reviewers "approved" value to 0 | 0 means blacklisted 
		// Might want to change to different value
		JButton btnDeclineReviewer = new JButton ("Decline Reviewer");
		btnDeclineReviewer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				declineReviewer();			// Calls DeclineReviewer method in this file (It's below)
				JOptionPane.showMessageDialog(contentPane, "The selected user has been DECLINED and is unable to review papers");	// Confirmation Message
				
				ManageReviewers nw = ManageReviewers.getInstance();	// Creates new Window
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
		btnDeclineReviewer.setBounds(50,200,157,25);
		contentPane.add(btnDeclineReviewer);
		
		
		
		
		
		
	}
	
	
	
	
	
	
	// Updates the Reviewers "approved" column value to 1 | 1 meaning they can review papers
	private void updateRoleIDInDatabase() {				
		SQLiteConnection conn = new SQLiteConnection();			// Creates connection to SQL database
		try {
			
			System.out.println("FOLLOWING BELONGS TO AuthorizeReviewers.java:\nthis.username:" + this.reviewerUsername + // DEBUG PRINT STATEMENT TO SEE this.username
					"\nthis.roleid: " + this.reviewerApprovedStatusNumber + "\nABOVE BELONGS TO AuthorizeReviewers");					// DEBUG PRINT STATEMENT TO SEE this.roleid
			
//			UserRepository.updateUserStatusByName(conn.getConn(), this.username, this.roleid);			// Used to update database column "approved" with role id, but now changed to static value 1
			
			UserRepository.updateUserStatusByName(conn.getConn(), this.reviewerUsername, 1); // Updates Reviewer approved status to 1  |  1 means they're approved to review
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	
	// DECLINES REVIEWER APPLICATION | i.e. "approved" column value to 0  (0 means blacklisted, might be a problem)
	// Should discuss on whether it should be kept as NULL? or a different value than 0 to specify they were DECLINED and not blacklisted
	private void declineReviewer() {				
		SQLiteConnection conn = new SQLiteConnection();			// Creates connection to SQL database
		try {
			
			System.out.println("\n\nDECLINE REVIEWER\nFOLLOWING BELONGS TO AuthorizeReviewers.java:\nthis.username:" + this.reviewerUsername + // DEBUG PRINT STATEMENT TO SEE this.username
					"\nthis.roleid: " + this.reviewerApprovedStatusNumber + "\nABOVE BELONGS TO AuthorizeReviewers");					// DEBUG PRINT STATEMENT TO SEE this.roleid
			
//			UserRepository.updateUserStatusByName(conn.getConn(), this.username, this.roleid);			// Used to update database column "approved" with role id, but now changed to static value 1
			
			UserRepository.updateUserStatusByName(conn.getConn(), this.reviewerUsername, 0); // Updates Reviewer approved status to 1  |  1 means they're approved to review
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
// Method used to setID (Not used due to constructor setting it up already. Keeping here just incase we need in the future)
	public void setRoleID(Integer ID) {
		this.reviewerApprovedStatusNumber = ID;
		
	}	
	
	
// Method used to setUsername (Not used due to constructor setting it up already. Keeping here just incase we need in the future)
	public void setUsername(String Username) {
		this.reviewerUsername = Username;
	}
	
	
// Method used to see if Reviewer is approved for reviewing papers
	private String approvedToReview(Integer approvedStatus){	// Input is the Reviewers "approved" status number from database
		if (approvedStatus == 1) {								// If number is 1, then user is allowed to review papers
			return ("Authorized");
		}
		else {
			return ("Unauthorized");							// Else they're unauthorized
		}
	}
	
	
	
	
	
	/*
	 * Get Instance Method | Returns Instance of class when called
	 */
	public static AuthorizeReviewers getInstance(String inputUsername, Integer StatusNumber, String reviewerInterest) {	
	    if (myInstance == null) {					// If instance is null, create new instance
	        myInstance = new AuthorizeReviewers(inputUsername, StatusNumber,reviewerInterest);		// Set new instance
	    }
	    return myInstance;							// Return instance
	}
	
	
}
