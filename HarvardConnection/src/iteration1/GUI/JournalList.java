package iteration1.GUI;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import iteration1.models.Role;
import iteration1.models.Upload;
import iteration1.models.User;
import iteration1.repositories.*;


public class JournalList extends JInternalFrame{
	private JPanel contentPane;
	private JList<Upload> journalList;
	private String display = "";
	private ListSelectionModel listSelectionModel;
	private JTextArea info;
	private static JournalList myInstance;		// Instance of Class | Used in having one frame visible at all times
	
	
	public static void main(String[] args ) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JournalList frame = new JournalList();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	
	public JournalList()  {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 640, 480);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5,5,5,5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		SQLiteConnection conn = new SQLiteConnection();
		ArrayList<Upload> uploads = null;
		try {
			uploads = UploadRepository.getSubmittedUploads(conn.getConn(), "Submitted");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		journalList = new JList(uploads.toArray());
		
		
		journalList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component renderer = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (renderer instanceof JLabel && value instanceof Upload) {
                    // Here value will be of the Type 'Upload'
                    ((JLabel) renderer).setText(String.valueOf(((Upload) value).getId()));
                }
                return renderer;
            }
        });
		
		contentPane.add(journalList);
		journalList.setBounds(12, 13, 165 ,170);
		journalList.setVisible(true);
		/*
		journalList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		journalList.setLayoutOrientation(JList.VERTICAL);
		journalList.setVisibleRowCount(10);
		
		JScrollPane listScroller = new JScrollPane(journalList)	;
		*/
	    class ListListener implements ListSelectionListener {
	        public void valueChanged(ListSelectionEvent e) { 
	        	info.setText("ID: " + journalList.getSelectedValue().getId());
	        	info.append(System.getProperty("line.separator"));
	        	info.append("File Path: " + journalList.getSelectedValue().getFilepath());
	        	info.append(System.getProperty("line.separator"));
	        	info.append("Email: " + journalList.getSelectedValue().getEmail());
	        	info.append(System.getProperty("line.separator"));
	        	info.append("Status: " + journalList.getSelectedValue().getAdminStatus());
	        }
	    }
	    
		ListListener listener = new ListListener();
        journalList.addListSelectionListener(listener);
		info = new JTextArea();
		info.setEditable(false);
		info.setLineWrap(true);
		contentPane.add(info);
		info.setText(display);
		info.setBounds(189, 23, 243, 160);
		info.setVisible(true);
		info.setText("Please select a journal." + System.getProperty("line.separator") + "Then you may assign a status.");
		
		JButton btnAccept = new JButton ("Accept");
		btnAccept.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					UploadRepository.updateStatus(conn.getConn(), journalList.getSelectedValue().getId(), "Accepted");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
			
			
		});
		btnAccept.setBounds(20,220,90,25);
		contentPane.add(btnAccept);
		
		JButton btnReject = new JButton ("Reject");
		btnReject.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					UploadRepository.updateStatus(conn.getConn(), journalList.getSelectedValue().getId(), "Rejected");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}			
			
		});
		btnReject.setBounds(120,220,90,25);
		contentPane.add(btnReject);
		
		JButton btnMajor = new JButton ("Major");
		btnMajor.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					UploadRepository.updateStatus(conn.getConn(), journalList.getSelectedValue().getId(), "Major Revision");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}			
			
		});
		btnMajor.setBounds(220,220,90,25);
		contentPane.add(btnMajor);
		
		JButton btnMinor = new JButton ("Minor");
		btnMinor.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					UploadRepository.updateStatus(conn.getConn(), journalList.getSelectedValue().getId(), "Minor Revision");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}			
			
		});
		btnMinor.setBounds(320,220,90,25);
		contentPane.add(btnMinor);	
		
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
		btnGoBack.setBounds(335, -3, 97, 25);
		contentPane.add(btnGoBack);
	}
	
	/*
	 * Get Instance Method | Returns Instance of class when called
	 */
	public static JournalList getInstance() {	
	    if (myInstance == null) {				// If instance is null, create new instance
	        myInstance = new JournalList();		// Set new instance
	    }
	    return myInstance;						// Return instance
	}
}
