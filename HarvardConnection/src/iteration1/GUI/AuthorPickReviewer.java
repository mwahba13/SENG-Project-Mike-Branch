package iteration1.GUI;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ListModel;
import javax.swing.border.EmptyBorder;

import iteration1.models.Upload;
import iteration1.models.User;
import iteration1.repositories.SQLiteConnection;
import iteration1.repositories.UploadRepository;
import iteration1.repositories.UserRepository;

import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
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

public class AuthorPickReviewer extends JFrame {

	private JPanel contentPane;

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
			reviewers[i] = users.get(i).getFirstName() + " " + users.get(i).getLastName();
		}
		
		return reviewers;
	}
	

	/**
	 * Create the frame.
	 */
	public AuthorPickReviewer() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JList reviewerList = new JList(getData());
		reviewerList.setBounds(54, 58, 92, 145);
		contentPane.add(reviewerList);
		
		JList selectedList = new JList();
		selectedList.setBounds(277, 58, 92, 145);
		contentPane.add(selectedList);
		
		JButton btnMove = new JButton("move -->");
		btnMove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedList.setListData(reviewerList.getSelectedValues());
			}
		});
		
		btnMove.setBounds(160, 102, 105, 25);
		contentPane.add(btnMove);
		
		JButton btnChooseFile = new JButton("Choose file");
		btnChooseFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {			// THIS IS WHEN UPLOAD BUTTON IS CLICKED
				contentPane.setVisible(false);
				dispose();
			    	System.out.println("working Directory:" + System.getProperty("user.dir"));		// DEBUG PRINT STATEMENT TO SHOW WORKING DIRECTORY
				    JFileChooser chooser = new JFileChooser();							// J FILE CHOOSER TO CHOOSE FILE TO COPY
			    	System.out.println("Directory after JFileChooser" + chooser.getCurrentDirectory());					// SHOWS CURRENT DIRECTORY AFTER OPENING JFILECHOOSER
				    chooser.setDialogTitle("Upload File");				// SETS TITLE OF NEW FILE CHOOSER WINDOW

				    int returnVal = chooser.showOpenDialog(null);						// HONESTLY DONT KNOW
				    if(returnVal == JFileChooser.APPROVE_OPTION) {						// I guess if the file chosen is valid, run through?

				       System.out.println("You chose to open this file: " +													// Debug print Statement to show
				            chooser.getSelectedFile().getName() + "\nThis is the directory:" + chooser.getSelectedFile());	// what file is chosen and its directory
				       
				       System.out.println("THIS IS WHAT I'M COPYING:" + chooser.getSelectedFile().toPath());			// debug print statement to show what i'm copying

				       Path copyToDirectory = Paths.get(System.getProperty("user.dir")+ "/uploads/" + chooser.getSelectedFile().getName());	// path set up to copy file
				       try {
				    	Files.copy(chooser.getSelectedFile().toPath(), copyToDirectory, REPLACE_EXISTING);		// final copy statement. first parameter is file, second is where to copy.
				    	Upload upload = UploadRepository.addUpload(new SQLiteConnection().getConn(), "alacazette@ucalgary.ca", copyToDirectory.toString().substring(copyToDirectory.toString().indexOf("upload")));
				    	
				    	// NEED TO ADD REVIEW TO DB WITH ID FROM upload AND REVIEWS FROM selectedList // 
				    	
				       } catch (IOException | SQLException e) {
				    	   System.out.println(e.getMessage());
					}
				       
				    }
				    
			    
			}
		});
		
		
		
		btnChooseFile.setBounds(39, 233, 117, 25);
		contentPane.add(btnChooseFile);
		
		JLabel lblSelectReviewerYou = new JLabel("Select reviewer you would like to review your paper");
		lblSelectReviewerYou.setBounds(39, 12, 384, 15);
		contentPane.add(lblSelectReviewerYou);
	}
}
