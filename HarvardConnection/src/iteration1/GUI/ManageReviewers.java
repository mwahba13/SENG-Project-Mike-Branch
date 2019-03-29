package iteration1.GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
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
			List.setBounds(0,0,225,300);
			

			contentPane.add(List);
			
			List.addListSelectionListener(new ListSelectionListener() {

				@Override
				public void valueChanged(ListSelectionEvent e) {
					if (!e.getValueIsAdjusting()) {
						int temp = List.getSelectedIndex();
						String selectedUsername = getUserFromIndex(reviewerList,temp);
						try {
							Integer selectedRoleID = UserRepository.getApprovalByName(conn.getConn(), selectedUsername);
							
							AuthorizeReviewers authorize = new AuthorizeReviewers();
							authorize.setUsername(selectedUsername);
							authorize.setRoleID(selectedRoleID);
							authorize.drawWindow();
							contentPane.setVisible(false);
							dispose();
							authorize.setVisible(true);
							
							
							
							
							
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
					
					}
					
					
					
				}
				
				
				
			});

			

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}



				
		
		
		
		
	}
	
	
	private String getUserFromIndex(DefaultListModel<String> list, int index) {
		return list.get(index);
		
		
	}
	
	
	
	
	

}
