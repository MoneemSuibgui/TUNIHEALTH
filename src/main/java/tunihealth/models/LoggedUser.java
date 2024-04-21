package tunihealth.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class LoggedUser {

	// member variables
	@Email
	@NotEmpty(message = "Email adress must be not empty")
	private String email;

	@NotEmpty(message = "Password must be not empty")
	@Size(min = 8, max = 20, message = "Password must be between 8 & 20 characters")
	private String password;

	// beans constructor
	public LoggedUser() {
	}
	
	
	// getters & setters
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}



}
