<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- c:out ; c:forEach etc. -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- Formatting (dates) -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!-- form:form -->
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!-- for rendering errors on PUT routes -->
<%@ page isErrorPage="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Doctor : <c:out value="${currentDoctor.fullName}"/> Hold Appointments</title>
<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css">
<!-- Add your custom CSS styles here -->
<style>
</style>
</head>
<body>
	<div class="container mt-4">
		<div class="d-flex justify-content-between m-3">
			<h1>Your Appointments</h1>
			<a href="/doctor/dashboard" class="btn btn-success btn-lg">Doctor Dashboard</a>
		</div>
	
		
			<table class="table table-border ">
				<tr>
					<th>Patient</th>
					<th>Appointment date</th>
					<th>Appointment time</th>
					<th>Actions</th>
				</tr>
				<c:forEach var="appointment" items="${doctorAppointments}">
					<c:if test="${!currentDoctor.validatedAppointments.contains(appointment) && !currentDoctor.deletedAppointments.contains(appointment)}">
						<tr>
							<th><a href="/patient/${appointment.patient.id}"><c:out value="${appointment.patient.firstName}"/> <c:out value="${appointment.patient.lastName}"/></a></th>
							<th><c:out value="${appointment.date}"/></th>
							<th><c:out value="${appointment.time}"/></th>
							<th class="d-flex justify-content-center">
								<form action="/validate/${appointment.id}" method="post">
									<button class="btn btn-success mx-2">Validate</button>
								</form>
								<form action="/delete/appointment/${appointment.id}" method="post">
									<input type="hidden" name="_method" value="delete"/>
									<button class="btn btn-danger mx-2">Delete</button>
								</form>
							</th>
						</tr>
					</c:if>
				</c:forEach>
			</table>
		
	</div>
</body>
</html>