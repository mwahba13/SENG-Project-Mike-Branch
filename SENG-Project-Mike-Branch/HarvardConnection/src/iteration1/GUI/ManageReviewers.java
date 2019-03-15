package iteration1.GUI;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
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

public class ManageReviewers extends JFrame {
	
	private JPanel contentPane;
	private JList reviewerList;
	
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
		setBounds(100,100,450,300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5,5,5,5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		/*SQLiteConnection conn = new SQLiteConnection();
		System.out.println(conn.getUrl());
		try {
			ArrayList<User> ReviewerList = UserRepository.listReviewers(conn.getConn());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		//fake users for testing purposes
		Role reviewer = new Role(2);
		Role admin = new Role(1);

		User tempUser1 = new User("Randolph Hearst III",reviewer,true);
		User tempUser2 = new User("Billy Jacking",reviewer,false);
		User tempUser3 = new User("Johnny Two-Nose",reviewer,false);
		User tempUser4 = new User("Jimmy Page",reviewer,true);
		//
		
		
		DefaultListModel<User> listModel = new DefaultListModel();
		listModel.addElement(tempUser1);
		listModel.addElement(tempUser2);
		listModel.addElement(tempUser3);
		listModel.addElement(tempUser4);
		//

		JList<User> reviewerList = new JList(listModel);
		reviewerList.setBounds(0, 0, 225 ,300);
		contentPane.add(reviewerList);
		
		reviewerList.addListSelectionListener(new ListSelectionListener() {
		
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting()==false) {
					System.out.print(reviewerList.getSelectedIndex());
					
					
				}
			}
			
		});
		
		JLabel approvalStatus = new JLabel("Test");
		approvalStatus.setBounds(300,0,100,50);
		contentPane.add(approvalStatus);
		
		JLabel approvalTag = new JLabel("Approval:");
		approvalTag.setBounds(225, 0, 100, 50);
		contentPane.add(approvalTag);
			
		
		
		
	}
	
	
	

}
