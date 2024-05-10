package tunihealth.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import tunihealth.models.Appointment;
import tunihealth.models.Doctor;
import tunihealth.models.LoggedUser;
import tunihealth.models.Patient;
import tunihealth.models.Post;
import tunihealth.services.AppointmentService;
import tunihealth.services.DoctorService;
import tunihealth.services.PatientService;
import tunihealth.services.PostService;

@Controller
public class DoctorController {

	// inject services using @Autowired annotation
	@Autowired
	private DoctorService service;

	@Autowired
	private PatientService patientService;

	@Autowired
	private AppointmentService appointmentService;

	@Autowired
	private PostService postService;

	private static String doctor_pictures_path = "src/main/resources/static/doctors_pictures/";

	// Display route for main dasshboard page
	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("patients", patientService.allPatients());
		model.addAttribute("doctors", service.getAll());
		return "dashboard.jsp";
	}

	// Display route Home App page
	@GetMapping("/home")
	public String homeApp() {
		return "homeApp.jsp";
	}

	// Display route register doctor page
	@GetMapping("/register/doctor")
	public String registerForm(@ModelAttribute("doctor") Doctor doctor) {
		return "registerDoctor.jsp";
	}

	// Action route register doctor and saved to db
	@PostMapping("/register/doctor")
	public String registerDoctor(@Valid @ModelAttribute("doctor") @NonNull Doctor doctor, BindingResult result,
			@RequestParam("doctorPic") MultipartFile file, HttpSession session, RedirectAttributes redirectAttr) {

		this.service.register(doctor, result);
		if (result.hasErrors()) {
			return "registerDoctor.jsp";
		}
		if (file.isEmpty()) {
			redirectAttr.addFlashAttribute("error", "Your picture is required,please upload your photo !");
			return "redirect:/register/doctor";
		}
		try {
			byte[] bytes = file.getBytes();
			Path path = Paths.get(doctor_pictures_path, file.getOriginalFilename());
			// store the picture into the server
			Files.write(path, bytes);
			// store the path of doctor picture in the database
			String url_image = "/doctors_pictures/" + file.getOriginalFilename();
			doctor.setPath_image(url_image);

		} catch (IOException e) {
			e.printStackTrace();
		}
		this.service.hashPasswordDoctor(doctor);
		this.service.add(doctor);
		session.setAttribute("doctor_id", doctor.getId());

		return "redirect:/doctor/dashboard";
	}

	// Display route doctor login page
	@GetMapping("/login/doctor")
	public String loginForm(@ModelAttribute("loggedDoctor") LoggedUser loggedDoctor) {
		return "loginDoctor.jsp";
	}

	// Action route Doctor login
	@PostMapping("/login/doctor")
	public String loginDoctor(@Valid @ModelAttribute("loggedDoctor") LoggedUser loggedDoctor, BindingResult result,
			HttpSession session) {
		Doctor doctor = this.service.login(loggedDoctor, result);
		if (result.hasErrors()) {
			return "loginDoctor.jsp";
		}
		session.setAttribute("doctor_id", doctor.getId());
		return "redirect:/doctor/dashboard";
	}

	// Display route doctor home page
	@GetMapping("/doctor/dashboard")
	public String doctorHome(HttpSession session, Model model, @ModelAttribute("post") Post post) {
		if (session.getAttribute("doctor_id") != null) {
			Doctor currentDoctor = this.service.GetById((Long) session.getAttribute("doctor_id"));
			model.addAttribute("currentDoctor", currentDoctor);
			model.addAttribute("today", new Date());
			model.addAttribute("localDateToday", LocalDate.now());
			model.addAttribute("count", calculateHoldAppointment(currentDoctor));
			model.addAttribute("posts",postService.getAll() );
			System.out.println(currentDoctor.getAppointments().size());
			return "doctorHome.jsp";
		}
		return "redirect:/";
	}

	public int calculateHoldAppointment(Doctor doctor) {
		return doctor.getAppointments().size()
				- (doctor.getValidatedAppointments().size() + doctor.getDeletedAppointments().size());

	}

	// Display hold appointment for doctor
	@GetMapping("/appointments/doctor/{id}")
	public String getHoldAppointmentForDoctor(@PathVariable("id") Long id, HttpSession session, Model model) {
		if (session.getAttribute("doctor_id") != null) {
			Doctor doctor = this.service.GetById(id);
			model.addAttribute("currentDoctor", doctor);
			model.addAttribute("doctorAppointments", appointmentService.getAllAppointmentByDoctor(doctor));
			return "holdAppointment.jsp";
		}
		return "redirect:/";
	}

	// Display route patient page
	@GetMapping("/patient/{id}")
	public String showInfo(@PathVariable("id") Long id, Model model, HttpSession session) {
		if (session.getAttribute("doctor_id") != null) {
			Patient patient = patientService.GetById(id);
			model.addAttribute("patient", patient);
			model.addAttribute("currentDoctor", this.service.GetById((Long) session.getAttribute("doctor_id")));
			model.addAttribute("today", LocalDate.now());
			return "patientDetails.jsp";
		}
		return "redirect:/";
	}

	// add appointment to doctor hold appointments list
	@PostMapping("/validate/{id}")
	public String validateAppointment(@PathVariable("id") Long appointmentId, HttpSession session) {
		Long doctorId = (Long) session.getAttribute("doctor_id");
		Doctor doctor = this.service.GetById(doctorId);
		Appointment appointment = this.appointmentService.GetById(appointmentId);
		// add the appointment to the validated appointment list and removed from hold
		// appointments list doctor
		doctor.getValidatedAppointments().add(appointment);
		this.service.update(doctor);
		return "redirect:/appointments/doctor/" + doctorId;
	}

	// delete appointment to doctor hold appointments list
	@DeleteMapping("/delete/appointment/{id}")
	public String deleteAppointmentDoctor(@PathVariable("id") Long appointmentId, HttpSession session) {
		Long doctorId = (Long) session.getAttribute("doctor_id");
		Doctor currentDoctor = this.service.GetById(doctorId);
		Appointment appointment = this.appointmentService.GetById(appointmentId);

		// add appointment to the history of deletedAppointments list of doctor
		currentDoctor.getDeletedAppointments().add(appointment);
		System.out.println(currentDoctor.getDeletedAppointments().size());
		this.service.update(currentDoctor);
		return "redirect:/appointments/doctor/" + doctorId;
	}

	// Action route add new post
	@PostMapping("/create/post")
	public String addPost(@Valid @ModelAttribute("post") Post post, BindingResult result, HttpSession session) {
		if (result.hasErrors()) {
			// display errors message in jsp page
			return "doctorHome.jsp";
		}
		// get the current doctor(logged doctor)
		Long doctor_id = (Long) session.getAttribute("doctor_id");
		Doctor loggedDoctor = this.service.GetById(doctor_id);
		// set the creator of the post(logged doctor)and save it
		post.setDoctor(loggedDoctor);
		postService.create(post);		
		return "redirect:/doctor/dashboard";
	}
	
	// Dispaly route Actualities jsp page
	@GetMapping("/actualities")
	public String actualities(Model model) {
		model.addAttribute("posts", postService.getAll());
		model.addAttribute("doctors", service.getAll());
		model.addAttribute("patients", patientService.allPatients());
		return "actualities.jsp";
	}

	// Logout route
	@GetMapping("/logout/doctor")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
}
