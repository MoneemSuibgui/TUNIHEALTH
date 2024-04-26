<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page isErrorPage="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Edit Appointment ID : <c:out value="${appointment.id}" /></title>
<link rel="stylesheet" href="/css/Dashboard.css">
<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css">
</head>
<body>
	<div>
		<p class="text-danger">${errorMsg}</p>
		<p class="text-danger">${errorTimeMsg}</p>
		<h1>Edit Appointment</h1>
		
		<div>
			<form:form action="/edit/appointment/${appointment.id}" method="put"
				modelAttribute="appointment">
				
				
				<p><form:errors path="date" /></p>
				<form:label path="date">Appointment Date</form:label>
				<form:input path="date" type="date" />

				<div>
					<label for="time">Appointment Time</label>
					<input name="appointmentTime" type="time" value="${appointment.time }"/>
				</div>
				
				<p><form:errors path="reason" /></p>
				<p><form:label path="reason">Description of your condition</form:label></p>
				<form:textarea path="reason" />

				<div>
					<p><label for="doctor">Choose Doctor:</label></p>
					<select name="doctor" id="doctor">
						<c:forEach var="doctor" items="${doctors}">
							<option value="${doctor.id}">
								<c:out value="${doctor.fullName}" /> --- <c:out value="${doctor.specialty}" /> -- <c:out value="${doctor.location}" /></option>								
						</c:forEach>
					</select>
				</div>
				<div>
					<button class="btn btn-primary mt-2">Update</button>
				</div>
			</form:form>

		</div>
	</div>
</body>
</html>