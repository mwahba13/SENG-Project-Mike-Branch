package iteration1.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import iteration1.models.Review;

public class ReviewRepository {
	
	// FOR VIEWING REVIEWS THAT ARE RELATED TO A SPECIFIC UPLOAD (I think this is getting used when an admin sees the statuses reviewers have been assigned to a paper)
	public static ArrayList<Review> getReviewsByUploadId(Connection conn, int uploadId) throws SQLException {
		ArrayList<Review> reviews = new ArrayList<>();
		
		String query = "SELECT * FROM REVIEWS WHERE upload_id = ?";
		PreparedStatement stmt = conn.prepareStatement(query);
		stmt.setInt(1, uploadId);
		ResultSet result = stmt.executeQuery();
		
		while(result.next()) {
			reviews.add(new Review(result.getString(1), result.getInt(2), result.getString(3), result.getString(4)));
		}
		
		stmt.close();
		result.close();
		
		return reviews;
	}
	
	// FOR VIEWING THE REVIEWS THAT A REVIEWER NEEDS TO REVIEW
	public static ArrayList<Review> getPendingReviewsByReviewer(Connection conn, String email) throws SQLException {
		ArrayList<Review> reviews = new ArrayList<>();
		
		String query = "SELECT * FROM REVIEWS WHERE reviewer = ? AND status IS NULL";
		PreparedStatement stmt = conn.prepareStatement(query);
		stmt.setString(1, email);
		ResultSet result = stmt.executeQuery();
		
		while(result.next()) {
			reviews.add(new Review(result.getString(1), result.getInt(2), result.getString(3), result.getString(4)));
		}
		
		stmt.close();
		result.close();
		return reviews;
	}
	
	// FOR PROVIDING FEEDBACK (in the form of a textbox) OR SETTING A STATUS FOR A REVIEW
	public static void updateFeedbackByIdAndReviewer(Connection conn, int uploadId, String email, String feedback) throws SQLException {
		String query = "UPDATE REVIEW SET feedback = ? WHERE reviewer = ? AND upload_id = ?";
		PreparedStatement stmt = conn.prepareStatement(query);
		stmt.setString(1, feedback);
		stmt.setString(2, email);
		stmt.setInt(3, uploadId);
		
		stmt.executeUpdate();
		stmt.close();
	}
	
	public static void updateStatusByIdAndReviewer(Connection conn, int uploadId, String email, String status) throws SQLException {
		String query = "UPDATE REVIEW SET status = ? WHERE reviewer = ? AND upload_id = ?";
		PreparedStatement stmt = conn.prepareStatement(query);
		stmt.setString(1, status);
		stmt.setString(2, email);
		stmt.setInt(3, uploadId);
		
		stmt.executeUpdate();
		stmt.close();
	}
	
	// FOR VIEWING ALL THE REVIEWS A REVIEWER HAS REVIEWED OR NEEDS TO REVIEW (I'm not actually sure this is necessary...)
	public static ArrayList<Review> getReviewsByReviewer(Connection conn, String email) throws SQLException {
		ArrayList<Review> reviews = new ArrayList<>();
		
		String query = "SELECT * FROM REVIEWS WHERE reviewer = ?";
		PreparedStatement stmt = conn.prepareStatement(query);
		stmt.setString(1, email);
		ResultSet result = stmt.executeQuery();
		
		while(result.next()) {
			reviews.add(new Review(result.getString(1), result.getInt(2), result.getString(3), result.getString(4)));
		}
		
		stmt.close();
		result.close();
		return reviews;
	}
}
