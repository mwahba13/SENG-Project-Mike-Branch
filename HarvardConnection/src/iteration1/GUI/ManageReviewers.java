package iteration1.GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import iteration1.models.Role;
import iteration1.models.User;
import iteration1.repositories.SQLiteConnection;
import iteration1.repositories.UserRepository;

//GUI interface where the admin will view reviewers and be able to accept/reject them as reviewers

public class ManageReviewers extends JInternalFrame {
	
	private JPanel contentPane;
	private JList reviewerList;
	private static ManageReviewers myInstance;		// Instance of Class | Used in having one frame visible at all times
	
	public static void main(String[] args ) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ManageReviewers frame = new ManageReviewers();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	public ManageReviewers()  {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 640, 480);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5,5,5,5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		
		//////////////////////////////////
		//		Exit Button				//
		// JButton | "Go Back" Button	//
		//////////////////////////////////
		JButton btnGoBack = new JButton("Go Back");
		btnGoBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
		});
		btnGoBack.setBounds(335, 11, 89, 23);
		contentPane.add(btnGoBack);
		// sets bounds to button i.e. location and whatnot
		
		
		
		
		
		SQLiteConnection conn = new SQLiteConnection();
		
		try {
			ArrayList<User> reviewerArray = UserRepository.getUnapprovedUsers(conn.getConn());
			DefaultListModel<String> reviewerList = new DefaultListModel();

			
			for (int i = 0; i < reviewerArray.size();i++) {
				String tempName = (reviewerArray.get(i).getFirstName() + " " + reviewerArray.get(i).getLastName());
				reviewerList.addElement(tempName);
				
			}
			
		
			JList<User> List = new JList(reviewerList);
			List.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			List.setLayoutOrientation(JList.VERTICAL);
			List.setBounds(392,199,42,62);
			

			contentPane.add(List);
			
			
			
			List.addListSelectionListener(new ListSelectionListener() {

				@Override
				public void valueChanged(ListSelectionEvent e) {
					if (!e.getValueIsAdjusting()) {
						int temp = List.getSelectedIndex();
						
						
						System.out.println("List.getSelectedIndex: " + List.getSelectedIndex());   // DEBUG PRINT STATEMENT
						System.out.println("Before Try Block");			           // DEBUG PRINT STATEMENT
						
						
						String selectedUsername = getUserFromIndex(reviewerList,temp);
						String selectedUsersInterests = null;
						try {
							selectedUsersInterests = UserRepository.getInterestsByName(conn.getConn(),selectedUsername);
						} catch (SQLException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
						
						
						System.out.println("getUserFromIndex(reviewerList, temp) | selectedUsername: " + selectedUsername);  // DEBUG PRINT STATEMENT
						
						try {
							
							
							System.out.println("Before getApprovalByName");							// DEBUG PRINT STATEMENT
							
							
							Integer selectedApprovalStatus = UserRepository.getApprovalByName(conn.getConn(), selectedUsername);		// Problem here
							
							System.out.println("After getApprovalByName");							// DEBUG PRINT STATEMENT
							

//							contentPane.setVisible(false);
//							dispose();
							
							System.out.println("\nnew AuthorizeReviewers() was not reached\n"); 	// DEBUG PRINT STATEMENT
							
//							AuthorizeReviewers authorize = new AuthorizeReviewers(selectedUsername, selectedApprovalStatus);
							
							System.out.println("\nnew AuthorizeReviewers() was reached\n");			// DEBUG PRINT STATEMENT
							
//							authorize.setVisible(true);
							
							
							
							AuthorizeReviewers nw =  AuthorizeReviewers.getInstance(selectedUsername, selectedApprovalStatus,selectedUsersInterests);		// Creates new Window
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
							
							
							
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							System.out.println("ValueChangedListSelection Event Error");		// DEBUG PRINT STATEMENT
							e1.printStackTrace();
						}
						
					
					}
					
					
					
				}
				
				
				
			});
			
			
			// Creation of Scroll Pane
			JScrollPane scrollPane = new JScrollPane(List);	// Puts JList of reviewers into scroll pane
			scrollPane.setBounds(20, 60, 155, 179);			// NOTE: ERROR WHEN MOVING AROUND IN DESIGN PAGE. IDK WHY
			contentPane.add(scrollPane);					// IF YOU WANNA MOVE IT, REMOVE "List" from "new JScrollPane(List);" So that it's just
			
			JLabel lblApplyingReviewers = new JLabel("Applying Reviewers");
			lblApplyingReviewers.setBounds(20, 35, 155, 14);
			contentPane.add(lblApplyingReviewers);
															// "new JScrollPane();" then Save your code. You then should be able to move the scroll pane around
															// And the JList will show up temporarily separate on the window

			

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}


				
		
		
		
	}
	
	
	private String getUserFromIndex(DefaultListModel<String> list, int index) {
		return list.get(index);
		
		
	}
	
	
	/*
	 * Get Instance Method | Returns Instance of class when called
	 */
	public static ManageReviewers getInstance() {	
	    if (myInstance == null) {					// If instance is null, create new instance
	        myInstance = new ManageReviewers();		// Set new instance
	    }
	    return myInstance;							// Return instance
	}
	
	
}
