package tunihealth.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import tunihealth.models.Doctor;
import tunihealth.models.LoggedUser;
import tunihealth.models.Patient;
import tunihealth.services.DoctorService;
import tunihealth.services.PatientService;

@Controller
public class PatientController {

	// inject services
	@Autowired
	private PatientService service;
	
	@Autowired
	private DoctorService doctorService;
	

	// path folder to store images in the server not into the database
	public static String path_image = "src/main/resources/static/patients_pictures/";

	// display route for register patient page
	@GetMapping("/register/patient")
	public String index(@ModelAttribute("patient") Patient patient) {
		return "registerPatient.jsp";
	}

	// Action route :submit the patient form & add to db
	@PostMapping("/register/patient")
	public String registerPatient(@Valid @ModelAttribute("patient") Patient patient, BindingResult result,
			@RequestParam("picture") MultipartFile file, HttpSession session, RedirectAttributes redirectAttr) {

		this.service.register(patient, result);

		if (result.hasErrors()) {
			return "registerPatient.jsp";
		}

//		System.out.println(file.getName());
		if (file.isEmpty()) {
			redirectAttr.addFlashAttribute("error", "* Picture cannot be empty");
			return "redirect:/register/patient";
		}
		try {
			byte[] bytes = file.getBytes();
			Path path = Paths.get(path_image + file.getOriginalFilename());
			Files.write(path, bytes);

			// get the URL of the file we just uploaded and set to image_url of patient
			// belongs into it
			String url = "/patients_pictures/" + file.getOriginalFilename();
			patient.setUrl_picture(url);
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.service.hashPassword(patient);
		Patient newPatient = this.service.savePatient(patient);

		session.setAttribute("patient_id", newPatient.getId());
		return "redirect:/user/dashboard";
	}

	// Display route : User dashboard page
	@GetMapping("/user/dashboard")
	public String patientHome(HttpSession session, Model modelView) {
		if (session.getAttribute("patient_id") == null) {
			return "redirect:/";
		}
		Patient patient = this.service.GetById((Long) session.getAttribute("patient_id"));
		modelView.addAttribute("patient", patient);
		modelView.addAttribute("today",LocalDate.now());
		return "patientHome.jsp";
	}

	// Display route for login patient form page
	@GetMapping("/login/patient")
	public String loginpatient(@ModelAttribute("loggedPatient") LoggedUser patient) {
		return "loginPatient.jsp";
	}

	// Action route login patient form
	@PostMapping("/login/patient")
	public String loginForm(@Valid @ModelAttribute("loggedPatient") LoggedUser loggedPatient, BindingResult result,
			HttpSession session, Model modelView) {

		Patient patient = this.service.login(loggedPatient, result);
		if (result.hasErrors()) {
			return "loginPatient.jsp";
		}
		session.setAttribute("patient_id", patient.getId());
		return "redirect:/user/dashboard";

	}
	
	// Action route add like
	@PostMapping("/like/{doctorId}/{appointmentId}")
	public String like(@PathVariable("doctorId") Long doctorId,@PathVariable("appointmentId") Long appointmentId,HttpSession session) {
		Patient currentPatient=this.service.GetById((Long)session.getAttribute("patient_id"));
		Doctor doctor=this.doctorService.GetById(doctorId);
		currentPatient.getFavouriteDoctors().add(doctor);
		this.service.savePatient(currentPatient);
		return "redirect:/show/"+appointmentId;
	}
	
	// Action route dislike
	@PostMapping("/dislike/{id}/{appointmentId}")
	public String dislike(@PathVariable("id")Long id,@PathVariable("appointmentId") Long appointmentId,HttpSession session) {
		Patient currentPatient=this.service.GetById((Long)session.getAttribute("patient_id"));
		Doctor doctor=this.doctorService.GetById(id);
		currentPatient.getFavouriteDoctors().remove(doctor);
		this.service.savePatient(currentPatient);
		return "redirect:/show/"+appointmentId;
	}

	// logout route
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("patient_id");
		return "redirect:/";
	}

}
