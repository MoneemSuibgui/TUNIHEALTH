package tunihealth.models;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import tunihealth.abstracts.DBEntity;

@Entity
@Table(name = "doctors")
public class Doctor extends DBEntity {
	// Inherit id ,createdAt,updatedAt from DBEntity

	// member variables
	@NotEmpty(message = "* Doctor full name must be not empty")
	@Size(min = 6, message = "* Full Name must at least 6 characyers")
	private String fullName;

	@NotEmpty(message = "* Doctor full name must be not empty")
	@Email(message = "* Please enter right email ")
	private String email;

	@Min(value = 8, message = "Phone number must at least 8 numbers")
	private Long phoneNumber;

	@NotEmpty(message = "* Location must be not empty")
	@Size(min = 6, message = "* Location must at least 6 characyers")
	private String location;

	@NotEmpty(message = "* Password name must be not empty")
	@Size(min = 6, message = "* Password must at least 8 characyers")
	private String password;

	@NotEmpty(message = "* Confirm PW name must be not empty")
	@Size(min = 6, message = "* Confirm PW must at least 8 characyers")
	private String confirm;

	@NotEmpty(message = "* Registartion number must be not empty")
	@Size(min = 6, message = "* Registartion number must at least 8 characyers")
	private String reg_number;

	@NotEmpty(message = "* Speciality must be not empty")
	@Size(min = 3, message = "* Speciality must at least 3 characters")
	private String specialty;

	@Min(value = 50, message = "* Consultation price must at least 50 DNT")
	private Double price;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@FutureOrPresent(message = "* Date Disponiblity must be in future or present")
	private LocalDate dates;

	private String path_image;

	@NotEmpty(message = "* Description must be not empty")
	@Size(min = 8, message = "* Description must at least 8 characters")
	private String description;

	// 1:n one doctor can have many appointments
	@OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Appointment> appointments;

	// 1:n relationship doctor have hisrory for deleted appointments
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Appointment> deletedAppointments;

	// n:n relationship between doctors and appointments
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "validated_appointments", joinColumns = @JoinColumn(name = "doctor_id"), inverseJoinColumns = @JoinColumn(name = "appointment_id"))
	private List<Appointment> validatedAppointments;

	// n:n relashionship doctor can like patients
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "likes", joinColumns = @JoinColumn(name = "doctor_id"), inverseJoinColumns = @JoinColumn(name = "patient_id"))
	private List<Patient> favouritePatients;

	// relationship between doctor & post
	// (One doctor can add Many post ,One post belongs(created by) to One doctor)
	@OneToMany(fetch = FetchType.LAZY,cascade=CascadeType.ALL)
	private List<Post> posts;

	// beans constructor
	public Doctor() {
	}

	// getters & setters
	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(Long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
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

	public String getReg_number() {
		return reg_number;
	}

	public void setReg_number(String reg_number) {
		this.reg_number = reg_number;
	}

	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String decription) {
		this.description = decription;
	}

	public String getPath_image() {
		return path_image;
	}

	public List<Appointment> getValidatedAppointments() {
		return validatedAppointments;
	}

	public List<Appointment> getDeletedAppointments() {
		return deletedAppointments;
	}

	public void setDeletedAppointments(List<Appointment> deletedAppointments) {
		this.deletedAppointments = deletedAppointments;
	}

	public void setValidatedAppointments(List<Appointment> validatedAppointments) {
		this.validatedAppointments = validatedAppointments;
	}

	public void setPath_image(String path_image) {
		this.path_image = path_image;
	}

	public LocalDate getDates() {
		return dates;
	}

	public List<Appointment> getAppointments() {
		return appointments;
	}

	public void setAppointments(List<Appointment> appointments) {
		this.appointments = appointments;
	}

	public void setDates(LocalDate dates) {
		this.dates = dates;
	}

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	public List<Patient> getFavouritePatients() {
		return favouritePatients;
	}

	public void setFavouritePatients(List<Patient> favouritePatients) {
		this.favouritePatients = favouritePatients;
	}

}
