package iteration1.controllers;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

import iteration1.models.Role;
import iteration1.models.User;
import iteration1.repositories.SQLiteConnection;
import iteration1.repositories.UserRepository;

public class UserController {
	
	public static void register(String email, String firstName, String lastName, String textPassword, int selectedRole) throws NoSuchAlgorithmException, InvalidKeySpecException, SQLException, IOException {
		char[] charPassword = textPassword.toCharArray();
		byte[] salt = PBKDF2.getSalt();
		byte[] password = PBKDF2.encrypt(charPassword, salt);   // encrypts inputted password
		Role role = new Role(selectedRole);	
		User newUser = new User(email, firstName, lastName, salt, password, role);
		SQLiteConnection conn = new SQLiteConnection();
		UserRepository.addUser(conn.getConn(), newUser);
	}
			
	public void delete() {
		
	}
}
