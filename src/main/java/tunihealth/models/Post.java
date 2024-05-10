package tunihealth.models;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import tunihealth.abstracts.DBEntity;

@Entity
@Table(name = "posts")
public class Post extends DBEntity {
	// Inherit id ,createdAt,updatedAt from DBEntity

	@NotEmpty
	@Size(min = 8, message = "Post content must at least 8 characters")
	private String content;

	// relationship between doctor & post(One doctor can add Many post ,One post
	// belongs(created by) to One doctor)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "doctor_id")
	private Doctor doctor;
	
	// create middle table likes posts
	// posts can liked by many patients
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(
			name="likes_posts",
			joinColumns=@JoinColumn(name="post_id"),
			inverseJoinColumns=@JoinColumn(name="patient_id")
			)
	List<Patient> followers;
	

	// Empty constructor
	public Post() {
	}

	// getters & setters
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public List<Patient> getFollowers() {
		return followers;
	}

	public void setFollowers(List<Patient> followers) {
		this.followers = followers;
	}
}
