package iteration1.GUI;

import java.awt.EventQueue;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;

import iteration1.repositories.SQLiteConnection;

import javax.swing.JDesktopPane;

public class StartingFrame {

	private JFrame frame;
	private static JDesktopPane desktopPane; //Desktop Pane where all other windows are stored

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StartingFrame window = new StartingFrame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public StartingFrame() {
		initialize();
	}
	


	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 640, 480);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JDesktopPane desktopPane = new JDesktopPane();		//Creates desktop pane
		desktopPane.setBounds(-7, -30, 640, 480);			// Sets location to be outside of window
		frame.getContentPane().add(desktopPane);			// adds desktop pane to frame
		
//		MainMenu MainMenu = new MainMenu();		// Creates new MainMenu
//		MainMenu.setVisible(true);				// Sets MainMenu Visible
//		desktopPane.add(MainMenu);				// Adds MainMenu to DesktopPane
		
		
		 MainMenu  nw = MainMenu.getInstance();
		    nw.pack();
//		    if (nw.isVisible()) {
//		    } else {
			nw.setBounds(100, 100, 400, 200);
		        desktopPane.add(nw);
		        nw.setVisible(true);
		        
//		    }
		    try {
		        nw.setMaximum(true);
		    } catch (Exception e1) {
		    	System.out.println(e1);
		    }
	}
	
	public void revalidatePane() {
	    getDesktopPane().revalidate();
	    getDesktopPane().repaint();
	}
	
	// Function used for internal frames to keep everything on one window
	public JDesktopPane getDesktopPane() {
	    return desktopPane;
	}
}
