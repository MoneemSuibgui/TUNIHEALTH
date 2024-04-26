<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<title>Search Appointments</title>
<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="/css/main.css">
<!-- change to match your file/naming structure -->
<script src="/webjars/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/js/app.js"></script>
<!-- change to match your file/naming structure -->
</head>
<body>
	<div class="d-flex justify-content-between">
		<h1>
			Welcome
			<c:out value="${patient.firstName}" />
		</h1>
		<img alt="patient picture" src="${patient.url_picture}" width="180px"
			height="180px" class="p-2">
	</div>
	<div class="d-flex justify-content-between mt-5">
		<div>
			<a href="/user/dashboard" class="mx-2 btn btn-secondary btn-lg">Dashboard</a>
			<a href="/create/appointment" class="btn btn-success btn-lg">Take
				Appointment</a>
		</div>
		<a href="/logout" class="btn btn-danger btn-lg">Logout</a>
	</div>
	<div class="d-flex justify-content-center mt-4">
		<div class="col-8">
			<h2>
				Your Appointments Today :
				<c:out value="${today}"></c:out>
			</h2>
			<table class="table table-hover table-border bg-info">

				<tr>
					<th>Appointment Date</th>
					<th>Appointment Time</th>
					<th>Appointment Reason</th>
					<th>Doctor</th>
					<th>Actions</th>
				</tr>

				<c:forEach var="appointment" items="${appointments}">
					<c:if test="${appointment.date.equals(today) }">
						<tr>
							<td><c:out value="${appointment.date }" /></td>
							<td><c:out value="${appointment.time }" /></td>
							<td><c:out value="${appointment.reason }" /></td>
							<td><c:out value="${appointment.doctor.fullName}" /></td>
							<td class="d-flex">
								<!-- to do  --> <a href="/show/${appointment.id}"
								class="btn btn-success mx-2">view</a> <a
								href="/edit/appointment/${appointment.id}"
								class="btn btn-secondary mx-2">edit</a>
								<div>
									<form action="/delete/${appointment.id}" method="post">
										<input type="hidden" name="_method" value="delete">
										<button class="btn btn-danger">delete</button>
									</form>
								</div>
							</td>
						</tr>
					</c:if>
				</c:forEach>

			</table>

			<h2>Your Future Appointments</h2>
			<table class="table table-hover table-border bg-warning">
				<tr>
					<th>Appointment Date</th>
					<th>Appointment Time</th>
					<th>Appointment Reason</th>
					<th>Doctor</th>
					<th>Consultation price</th>
					<th>Actions</th>
				</tr>
				<c:forEach var="appointment" items="${appointments}">
					<c:if test="${appointment.date gt today }">
						<tr>
							<td><c:out value="${appointment.date }" /></td>
							<td><c:out value="${appointment.time }" /></td>
							<td><c:out value="${appointment.reason }" /></td>
							<td><c:out value="${appointment.doctor.fullName}" /></td>
							<td><c:out value="${appointment.doctor.price}" /></td>
							<td class="d-flex"><a href="/show/${appointment.id}"
								class="btn btn-success mx-2">view</a> <a
								href="/edit/appointment/${appointment.id}"
								class="btn btn-secondary mx-2">edit</a>
								<div>
									<form action="/delete/${appointment.id}" method="post">
										<input type="hidden" name="_method" value="delete">
										<button class="btn btn-danger">delete</button>
									</form>
								</div></td>
						</tr>
					</c:if>
				</c:forEach>
			</table>




			<h2>Your Full Appointments</h2>
			<table class="table table-hover table-border bg-dark text-light">
				<tr>
					<th>Appointment Date</th>
					<th>Appointment Time</th>
					<th>Appointment Reason</th>
					<th>Doctor</th>
					<th>Consultation price</th>
					<th>Actions</th>
				</tr>
				<c:forEach var="appointment" items="${appointments}">
					<c:if test="${appointment.date lt today }">
						<tr>
							<td><c:out value="${appointment.date }" /></td>
							<td><c:out value="${appointment.time }" /></td>
							<td><c:out value="${appointment.reason }" /></td>
							<td><c:out value="${appointment.doctor.fullName}" /></td>
							<td><c:out value="${appointment.doctor.price}" /></td>

							<td class="d-flex justify-content-center">
								<!-- to do  --> <a href="/show/${appointment.id}"
								class="btn btn-success mx-2">view</a>
								<div>
									<form action="/delete/${appointment.id}" method="post">
										<input type="hidden" name="_method" value="delete">
										<button class="btn btn-danger">delete</button>
									</form>
								</div>
							</td>
						</tr>
					</c:if>
				</c:forEach>
			</table>
		</div>
	</div>
</body>
</html>