package tunihealth.controllers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import tunihealth.models.Appointment;
import tunihealth.models.Doctor;
import tunihealth.models.Patient;
import tunihealth.services.AppointmentService;
import tunihealth.services.DoctorService;
import tunihealth.services.PatientService;

@Controller
public class AppointmentController {

	// inject all needed services into AppointmentController using @Autowired annotation
	@Autowired
	private DoctorService doctorService;

	@Autowired
	private PatientService patientService;

	@Autowired
	private AppointmentService appointmentService;

	// Display route take Appointment page
	@GetMapping("/create/appointment")
	public String appointment(@ModelAttribute("appointment") Appointment appointment, HttpSession session,
			Model model) {
		if (session.getAttribute("patient_id") != null) {
			List<Doctor> doctors = this.doctorService.getAll();
			Patient currentPatient = this.patientService.GetById((Long) session.getAttribute("patient_id"));
			model.addAttribute("patient", currentPatient);
			model.addAttribute("doctors", doctors);
			return "takeAppointment.jsp";
		}
		return "redirect:/";
	}

	// Action route : create new appointment & saved to database
	@PostMapping("/create/appointment")
	public String saveAppointment(@Valid @ModelAttribute("appointment") Appointment appointment, BindingResult result,@RequestParam("appointmentTime")String time,
			@RequestParam("doctor") Long doctorId, HttpSession session, Model model, RedirectAttributes redirectAttr) {

		if (result.hasErrors()) {
			List<Doctor> doctors = this.doctorService.getAll();
			model.addAttribute("doctors", doctors);
			return "takeAppointment.jsp";

		}
		if (doctorId == null) {
			redirectAttr.addFlashAttribute("errorMsg", "You should Choose doctor to submit your appointment");
			model.addAttribute("doctors", this.doctorService.getAll());
			return "redirect:/create/appointment";
		}
		if (time.isEmpty()) {
			redirectAttr.addFlashAttribute("errorTimeMsg", "You should Choose Time to submit your appointment");
			model.addAttribute("doctors", this.doctorService.getAll());
			return "redirect:/create/appointment";
		}
		Doctor doctor = this.doctorService.GetById(doctorId);
		Patient currentPatient = this.patientService.GetById((Long) session.getAttribute("patient_id"));

		LocalTime appointmentTime = LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"));
		 
		this.appointmentService.add(appointment.getDate(), appointmentTime, appointment.getReason(),
				currentPatient,doctor);
		return "redirect:/user/dashboard";
	}

	// Action route : search appointments
	@PostMapping("/search")
	public String searchAppointment(@RequestParam("content") String content, HttpSession session, Model model,
			RedirectAttributes redirectAttr) {
		session.setAttribute("searchedAppointments", null);
		if (content.isBlank()) {
			redirectAttr.addFlashAttribute("errorMsg", "* Enter name of doctor field to search for");
			return "redirect:/user/dashboard";
		}
		List<Appointment> appointments = this.appointmentService
				.getAllAppointmentByDoctor(this.doctorService.GetByFullName(content));
		if (appointments != null && appointments.size() > 0) {
			session.setAttribute("searchedAppointments", appointments);
			return "redirect:/show_appointments";
		}
		redirectAttr.addFlashAttribute("errorM", "You haven't doctor with this name in your appointment schedule !!");
		return "redirect:/user/dashboard";

	}

	// Display route : searched appointments by doctor fullName field
	@GetMapping("/show_appointments")
	public String searchForAppointmentsByDoctor(Model model, HttpSession session, RedirectAttributes redirectAttr) {
		if (session.getAttribute("patient_id") != null) {

			@SuppressWarnings("unchecked")
			List<Appointment> appointments = (List<Appointment>) session.getAttribute("searchedAppointments");

			model.addAttribute("appointments", appointments);
			Patient currentPatient = this.patientService.GetById((Long) session.getAttribute("patient_id"));
			model.addAttribute("patient", currentPatient);
			model.addAttribute("today", LocalDate.now());
			return "AppointmentsByDoctor.jsp";
		}
		return "redirect:/";
	}

	// Display route edit appointment page
	@GetMapping("/edit/appointment/{id}")
	public String editAppointment(@ModelAttribute("appointment") Appointment appointment,@PathVariable("id") Long id,
			HttpSession session, Model model) {
		if (session.getAttribute("patient_id") == null) {
			return "redirect:/";
		}
		model.addAttribute("appointment", this.appointmentService.GetById(id));
		model.addAttribute("doctors", this.doctorService.getAll());
		return "editAppointment.jsp";
	}

	// Action route update appointment using Annotation for mapping HTTP PUT requests
	@PutMapping("/edit/appointment/{id}")
	public String updateAppointment(
			@Valid @ModelAttribute("appointment") Appointment appointment,BindingResult result,
			@RequestParam("appointmentTime")String time,@PathVariable("id") Long id, @RequestParam("doctor") Long doctorId,RedirectAttributes redirectAttr,
			 HttpSession session,Model model) {
		// display validations errors for appointment model if result is not empty
		if (result.hasErrors()) {
			model.addAttribute("doctors", doctorService.getAll());
			return "editAppointment.jsp";
		}
		// check if doctorId  equal to null display error message
		if (doctorId == null) {
			redirectAttr.addFlashAttribute("errorMsg", "You should Choose doctor to submit your appointment");
			model.addAttribute("doctors", this.doctorService.getAll());
			return "redirect:/edit/appointment/"+id;
		}
		// check if time is empty -> display error message to client
		if (time.isEmpty()) {
			redirectAttr.addFlashAttribute("errorTimeMsg", "You should Choose Time to submit your appointment");
			model.addAttribute("doctors", this.doctorService.getAll());
			return "redirect:/edit/appointment/"+id;
		}
		Doctor doctor = this.doctorService.GetById(doctorId);
		Patient currentPatient = this.patientService.GetById((Long)session.getAttribute("patient_id"));
		
		// set the appointment doctor & format appointment time 
		appointment.setDoctor(doctor);
		LocalTime appointmentTime= LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm"));
		appointment.setTime(appointmentTime);
		// update the appointment
		this.appointmentService.update(appointment,currentPatient);
		
		return "redirect:/user/dashboard";
	}

	// Delete route :delete appointment
	@DeleteMapping("/delete/{id}")
	public String destroyAppointment(@PathVariable("id") Long appointmentId, HttpSession session) {
		if (session.getAttribute("patient_id") == null) {
			return "redirect:/";
		}
		Patient patient = this.patientService.GetById((Long) session.getAttribute("patient_id"));
		this.appointmentService.deleteAppointment(patient, appointmentId);
		return "redirect:/user/dashboard";
	}

	// Display route : display appointment details
	@GetMapping("/show/{appointment_id}")
	public String displayInfo(Model model, HttpSession session, @PathVariable("appointment_id") Long id) {
		if (session.getAttribute("patient_id") != null) {
			Patient currentPatient = this.patientService.GetById((Long) session.getAttribute("patient_id"));
			Appointment currentAppointment = this.appointmentService.GetById(id);
			model.addAttribute("patient",currentPatient);
			model.addAttribute("appointment",currentAppointment);
			return "displayAppointment.jsp";
		}
		return "redirect:/";
	}

}
