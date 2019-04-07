package iteration1.GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

public class AdminMenu extends JInternalFrame {

	private JPanel contentPane;
	private final JLabel lblNewLabel = new JLabel("ADMIN MENU\n");
	private static AdminMenu myInstance;		// Instance of Class | Used in having one frame visible at all times

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminMenu frame = new AdminMenu();
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
	public AdminMenu()  {
		/**
		 * Sets Bounds for window
		 */
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	// Close Operation
		setBounds(100, 100, 640, 480);						// Sets bounds for window
		contentPane = new JPanel();							// Creates new contentPane
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));	// Sets border
		setContentPane(contentPane);						// Set Content pane
		contentPane.setLayout(null);						// Sets pane layout
		lblNewLabel.setBounds(12, 0, 82, 25);				// sets label bound
		contentPane.add(lblNewLabel);						// add label to content pane
		
		/**
		 * Papers JButton: When clicked, opens up GUI to choose on what to do with certain papers
		 */
		JButton btnPapers = new JButton ("Papers");
		btnPapers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent p) {
				
				JournalList nw = JournalList.getInstance();		// Creates new Window
				nw.pack();									// Causes subcomponents of this JInternalFrameto be laid out at their preferred size.
				getDesktopPane().add(nw);					// Adds Instance of frame to DesktopPane on StartingFrame
				nw.setVisible(true);						// Sets Instance frame visible
				 
				try {
				    nw.setMaximum(true);				// Sets window to max size of DesktopPane
				} catch (Exception e1) {				// Catch Block for Exception
					System.out.println(e1);				// Prints Exception
				}
				 
				getDesktopPane().repaint();				// Repaints the DesktopPane
				getDesktopPane().remove(myInstance);	// Removes the instance of current Class
				myInstance = null;						// Sets instance to null
			}	
		});
		btnPapers.setBounds(12,38,200,25);
		contentPane.add(btnPapers);

		
		/**
		 * Manage Reviewers JButton: When clicked, GUI to edit the status of registering Reviewers shows
		 */
		JButton btnMngReviewer = new JButton ("Manage Reviewers");		// Creates new JButton "Manage Reviewers"
		btnMngReviewer.addActionListener(new ActionListener() {			// Creates ActionPerformed Listener
			public void actionPerformed(ActionEvent arg0) {				// ActionPerformed
				
				ManageReviewers nw = ManageReviewers.getInstance();		// Creates new Window
				nw.pack();									// Causes subcomponents of this JInternalFrameto be laid out at their preferred size.
				getDesktopPane().add(nw);					// Adds Instance of frame to DesktopPane on StartingFrame
				nw.setVisible(true);						// Sets Instance frame visible
				 
				try {
				    nw.setMaximum(true);				// Sets window to max size of DesktopPane
				} catch (Exception e1) {				// Catch Block for Exception
					System.out.println(e1);				// Prints Exception
				}
				 
				getDesktopPane().repaint();				// Repaints the DesktopPane
				getDesktopPane().remove(myInstance);	// Removes the instance of current Class
				myInstance = null;						// Sets instance to null
			}
		});
		btnMngReviewer.setBounds(12,76,200,25);		// Sets bounds of location and size for button
		contentPane.add(btnMngReviewer);			// Add MngReviewer button to content pane
		
		
		
		/**
		 * Logout Button | When pressed, user is logged out
		 */
		JButton btnLogOut = new JButton("Log Out");				// New JButton "Log Out"
		btnLogOut.addActionListener(new ActionListener() {		// Set Listener for Button
			public void actionPerformed(ActionEvent e) {		// ActionPerformedListener
				
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
		btnLogOut.setBounds(315, 0, 117, 25);			// Sets bounds for location and size of button
		contentPane.add(btnLogOut);						// Add button to content pane
	}
	
	
	/*
	 * Returns paper status as an int of the specified paper id
	 * @param int PID is the paper id number
	 * @return int of paper status from database
	 */
	private int checkPaperStatus(int paperID) {
		/*
		 * Get paper status from database
		 * SELECT status FROM PAPERS WHERE pid = paperID
		 * return status
		 */
		return -1;
	}
	
	private void rejectPaper(int paperID) {
		/*
		 * UPDATE PAPERS SET status = 0 WHERE pid = paperID
		 */
	}
	
	private void acceptPaper(int paperID)	{
		/*
		 * UPDATE PAPERS SET status = 1 WHERE pid = paperID
		 */
	}
	
	
	/*
	 * Get Instance Method | Returns Instance of class when called
	 */
	public static AdminMenu getInstance() {	
	    if (myInstance == null) {				// If instance is null, create new instance
	        myInstance = new AdminMenu();		// Set new instance
	    }
	    return myInstance;						// Return instance
	}
}
