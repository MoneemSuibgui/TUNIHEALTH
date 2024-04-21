package tunihealth.models;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "patients")
public class Patient {

	// member variables
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty(message = "First name must be not empty")
	@Size(min = 3, max = 20, message = "First name must be between 3 & 20 characters")
	private String firstName;

	@NotEmpty(message = "Last name must be not empty")
	@Size(min = 3, max = 20, message = "Last name must be between 3 & 20 characters")
	private String lastName;

	@Email
	@NotEmpty(message = "Email adress must be not empty")
	private String email;

	@Min(value = 8, message = "Phone number must be 8 numbers")
	private Integer phoneNumber;

	@NotEmpty(message = "Password must be not empty")
	@Size(min = 8, message = "Password must at least 8 characters")
	private String password;

	@NotEmpty(message = "Confirm Password must be not empty")
	@Size(min = 8, message = "Confirm Password must at least 8 characters")
	@Transient
	private String confirm;

	private String url_picture;

	@Column(updatable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createdAt;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date updatedAt;

	// One patient can have create appointments
	@OneToMany(mappedBy = "patient", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Appointment> appointments;


	// n:n relashionship patient can like many doctors
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "likes", joinColumns = @JoinColumn(name = "patient_id"), inverseJoinColumns = @JoinColumn(name = "doctor_id"))
	private List<Doctor> favouriteDoctors;

	// beans constructor
	public Patient() {
	}

	// getters & setters

	@PrePersist
	protected void crearedOn() {
		this.createdAt = new Date();
	}

	@PreUpdate
	protected void updatedOn() {
		this.updatedAt = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(Integer phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirm() {
		return confirm;
	}

	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getUrl_picture() {
		return url_picture;
	}

	public void setUrl_picture(String url_picture) {
		this.url_picture = url_picture;
	}

	public List<Appointment> getAppointments() {
		return appointments;
	}

	public void setAppointments(List<Appointment> appointments) {
		this.appointments = appointments;
	}

	public List<Doctor> getFavouriteDoctors() {
		return favouriteDoctors;
	}

	public void setFavouriteDoctors(List<Doctor> favouriteDoctors) {
		this.favouriteDoctors = favouriteDoctors;
	}



}
