package iteration1.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import iteration1.models.Upload;

public class UploadRepository {

	public static Upload addUpload(Connection conn, String email, String filepath) throws SQLException {
		String query = "INSERT INTO UPLOADS (email, filepath) VALUES (?, ?)";
		PreparedStatement stmt = conn.prepareStatement(query);
		stmt.setString(1, email);
		stmt.setString(2, filepath);
		stmt.executeUpdate();
		stmt.close();
	
		PreparedStatement autoinc = conn.prepareStatement("SELECT last_insert_rowid()");
		ResultSet result = autoinc.executeQuery();
		Upload upload = new Upload(result.getInt(1), email, filepath, "Submitted");
		
		
		result.close();
		autoinc.close();
		
		return upload;
	}
	
	// FOR VIEWING THE UPLOADS OF A PARTICULAR USER (to see the status of their papers)
	
	public static ArrayList<Upload> getUploadsByEmail(Connection conn, String email) throws SQLException {
		ArrayList<Upload> uploads = new ArrayList<>();
		
		String query = "SELECT * FROM UPLOADS WHERE email = ?";
		PreparedStatement stmt = conn.prepareStatement(query);
		ResultSet result = stmt.executeQuery();
		
		while(result.next()) {
			uploads.add(new Upload(result.getInt(1), result.getString(2), result.getString(3), result.getString(4)));
		}
		
		stmt.close();
		result.close();
		return uploads;
	}
	
	// FOR VIEWING UPLOADS THAT NEED TO BE ASSIGNED A STATUS AND THEN ASSIGNING THAT STATUS
	public static ArrayList<Upload> getSubmittedUploads(Connection conn, String adminStatus) throws SQLException {
		ArrayList<Upload> uploads = new ArrayList<>();
		
		String query = "SELECT * FROM UPLOADS WHERE admin_status = ?";
		PreparedStatement stmt = conn.prepareStatement(query);
		stmt.setString(1, "Submitted");
		ResultSet result = stmt.executeQuery();
		
		while (result.next()) {
			uploads.add(new Upload(result.getInt(1), result.getString(2), result.getString(3), result.getString(4)));
		}
		
		stmt.close();
		result.close();
		return uploads;
	}
	
	public static void updateStatus(Connection conn, int id, String adminStatus) throws SQLException {
		String query = "UPDATE UPLOADS SET admin_status = ? WHERE id = ?";
		PreparedStatement stmt = conn.prepareStatement(query);
		stmt.setString(1, adminStatus);
		stmt.setInt(2, id);
		
		stmt.executeUpdate();
		stmt.close();
	}
	

}
