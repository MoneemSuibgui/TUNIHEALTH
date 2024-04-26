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
<title>Home</title>
<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css">
<!-- Add your custom CSS styles here -->
<style>

</style>
</head>
<body>
    <div class="container mt-4 p-4">
    		
    	<div class="d-flex justify-content-between mt-4 mb-2">
			<div class="card border border-3 p-3 border-dark shadow col-8 mx-4">
				<h2 class="mb-2">About Patient :</h2>
				<h4>Patient full name : <c:out value="${patient.firstName }"></c:out> <c:out value="${patient.lastName}"></c:out></h4>
				<h4>Email adresse : <c:out value="${patient.email }"/> </h4>
				<h4>Phone number : <c:out value="${patient.phoneNumber }"/> </h4>
				<h2 class="mt-3">Future Appointments  :</h2>
				<c:forEach var="appointment" items="${patient.appointments}">
					<c:if test="${appointment.doctor.equals(currentDoctor) }">
						<c:choose>
							<c:when test="${appointment.date gt today || appointment.date.equals(today) }">
								<div class="bg-warning rounded-4 border-2 p-3 mb-3">
									<h4>Appointment date : <c:out value="${appointment.date }"/> </h4>
									<h4>Appointment time : <c:out value="${appointment.time }"/> </h4>
									<h4>Description : <c:out value="${appointment.reason }"/> </h4>
									<h4>Doctor : <c:out value="${appointment.doctor.fullName }"/> </h4>
								</div>
							</c:when>
							<c:otherwise>
							<h2>Appointments History :</h2>
								<div class="bg-danger text-light rounded-4 border-2 p-3 mb-3">
									<h4>Appointment date : <c:out value="${appointment.date }"/> </h4>
									<h4>Appointment time : <c:out value="${appointment.time }"/> </h4>
									<h4>Description : <c:out value="${appointment.reason }"/> </h4>
								</div>
							</c:otherwise>
						</c:choose>
					</c:if>
				</c:forEach>
			</div>
			<div>
				<img alt="patient picture" src="${patient.url_picture}" width="200px" height="200px">
			</div>
			
			
		</div>
	</div>
</body>
</html>
