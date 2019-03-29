package iteration1.GUI;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import iteration1.models.User;
import iteration1.repositories.SQLiteConnection;
import iteration1.repositories.UserRepository;

public class AuthorizeReviewers extends JFrame{

	private User user;
	private String username;
	private Integer roleid;
	private JPanel contentPane;
	
	
	
	public AuthorizeReviewers() {
		
	}
	
	
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AuthorizeReviewers frame = new AuthorizeReviewers();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	public void drawWindow() {
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100,100,450,300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5,5,5,5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		JLabel userName = new JLabel("Reviewer: " + this.username);
		userName.setBounds(150,50,300,100);
		contentPane.add(userName);
		
		JLabel approval = new JLabel ("Approval Status: " + approvedToReview(this.roleid));
		approval.setBounds(150,100,400,100);
		contentPane.add(approval);
		
		
		
		
		JButton btnGoBack = new JButton("Go Back");
		btnGoBack.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				contentPane.setVisible(false);
				dispose();
				ManageReviewers mngReviewer = new ManageReviewers();
				mngReviewer.setVisible(true);
			}
			
		});
		btnGoBack.setBounds(50,200,100,25);
		contentPane.add(btnGoBack);
		
		JButton btnSwitchApproval = new JButton ("Change Approval Status");
		btnSwitchApproval.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				updateRoleIDInDatabase();
				contentPane.setVisible(false);
				dispose();
				ManageReviewers mngReviewer = new ManageReviewers();
				mngReviewer.setVisible(true);
				
				
				
			}
			
		});
		btnSwitchApproval.setBounds(175,200,225,25);
		contentPane.add(btnSwitchApproval);
		
		
		
		
		
	}
	
	public void setRoleID(Integer ID) {
		this.roleid = ID;
		
	}
	
	private void updateRoleIDInDatabase() {
		SQLiteConnection conn = new SQLiteConnection();
		try {
			UserRepository.updateUserStatusByName(conn.getConn(), this.username, this.roleid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setUsername(String Username) {
		this.username = Username;
	}
	
	private String approvedToReview(Integer roleid){
		if (roleid == 1) {
			return ("Authorized");
		}
		else {
			return ("Unauthorized");
		}
	}
	
	
	
	
	
	
	
}
