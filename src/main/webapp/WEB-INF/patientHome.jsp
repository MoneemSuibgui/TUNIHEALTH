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
<title>Home Patient :<c:out value="${patient.firstName}" /></title>
<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="/css/patientHome.css">

<script type="text/javascript" src="/js/app.js"></script>

<style>
</style>
</head>
<body>
	<div class="containerA">
		<h1>
			Welcome
			<c:out value="${patient.firstName}" />
		</h1>
		<div class="container1">
			<form action="/search" method="post">
				<p class="text-danger">${errorMsg}</p>
				<p class="text-danger">${errorM}</p>
				<div class="container2">
					<input class="searchInput" type="text"
						placeholder="Enter doctor name to search for in your schedules"
						name="content" class="form-control rounded " />
					<button type="submit" class="btn btn-success btn-lg">
						<i class="fa fa-search">Search</i>
					</button>
				</div>
			</form>
		</div>
		<img alt="patient picture" src="${patient.url_picture}" width="120px"
			height="120px" class="p-2 rounded-5">
	</div>
	<div class="container3">
		<a href="/create/appointment" class="btn btn-success btn-lg">Take
			Appointment</a> <a href="/logout" class="btn btn-danger btn-lg">Logout</a>
	</div>
	<div class="d-flex justify-content-between mx-3">
		<div class="containerB col-5">
			<h2 class="text-start mx-5">
				Today's Appointments :
				<c:out value="${today}"></c:out>
			</h2>
			<table class="containerTab1">
				<tr>
					<th>Appointment Date</th>
					<th>Appointment Time</th>
					<th>Appointment Reason</th>
					<th>Doctor</th>
					<th>Consultation price</th>
					<th>Actions</th>
				</tr>
				<c:forEach var="appointment" items="${patient.appointments}">
					<c:if test="${appointment.date.equals(today) }">
						<tr>
							<td><c:out value="${appointment.date }" /></td>
							<td><c:out value="${appointment.time }" /></td>
							<td><c:out value="${appointment.reason }" /></td>
							<td><c:out value="${appointment.doctor.fullName}" /></td>
							<td><c:out value="${appointment.doctor.price}" /> DNT</td>
							<td class="d-flex justify-content-center"><a
								href="/show/${appointment.id}"
								class="btn btn-success btn-lg mx-2">view</a> <c:if
									test="${! appointment.doctor.validatedAppointments.contains(appointment) || appointment.doctor.deletedAppointments.contains(appointment)}">
									<a href="/edit/appointment/${appointment.id}"
										class="btn btn-secondary btn-lg mx-2">edit</a>
								</c:if> <c:if
									test="${appointment.doctor.deletedAppointments.contains(appointment)}">
									<div>
										<form action="/delete/${appointment.id}" method="post">
											<input type="hidden" name="_method" value="delete">
											<button class="btn btn-danger btn-lg">delete</button>
											<span class="mx-1 text-danger">(Appointment Deleted By
												Doctor)</span>
										</form>
									</div>
								</c:if></td>
						</tr>
					</c:if>
				</c:forEach>
			</table>

			<h2 class="mt-5">Future Appointments</h2>
			<table class="containerTab2">
				<tr>
					<th>Appointment Date</th>
					<th>Appointment Time</th>
					<th>Appointment Reason</th>
					<th>Doctor</th>
					<th>Consultation price</th>
					<th>Actions</th>
				</tr>
				<c:forEach var="appointment" items="${patient.appointments}">
					<c:if test="${appointment.date gt today }">
						<tr>
							<td><c:out value="${appointment.date }" /></td>
							<td><c:out value="${appointment.time }" /></td>
							<td><c:out value="${appointment.reason }" /></td>
							<td><c:out value="${appointment.doctor.fullName}" /></td>
							<td><c:out value="${appointment.doctor.price}" /> DNT</td>

							<td class="d-flex justify-content-center">
								<!-- 1st condition --> <c:choose>
									<c:when
										test="${appointment.doctor.validatedAppointments.contains(appointment)}">
										<a href="/show/${appointment.id}"
											class="btn btn-success btn-lg mx-2">view</a>
										<span class="text-success">Validated by doctor</span>
									</c:when>
									<c:otherwise>
										<div class="d-flex">
											<a href="/show/${appointment.id}"
												class="btn btn-success btn-lg mx-2">view</a>
										</div>
									</c:otherwise>
								</c:choose> <!-- 2nd condition --> <c:if
									test="${! appointment.doctor.validatedAppointments.contains(appointment) && !(appointment.doctor.deletedAppointments.contains(appointment))}">
									<a href="/edit/appointment/${appointment.id}"
										class="btn btn-secondary btn-lg mx-2">edit</a>
									<div>

										<form action="/delete/${appointment.id}" method="post">
											<input type="hidden" name="_method" value="delete">
											<button class="btn btn-danger btn-lg">delete</button>
										</form>
									</div>
								</c:if> <!-- 3rd condition  --> <c:if
									test="${appointment.doctor.deletedAppointments.contains(appointment)}">
									<div>
										<%-- <form action="/delete/${appointment.id}" method="post">
		                                    <input type="hidden" name="_method" value="delete">
		                                    <button class="btn btn-danger btn-lg">delete</button>
		                                </form> --%>
										<span class=" text-danger text-start">(Appointment
											Deleted By Doctor)</span>
									</div>
								</c:if>
							</td>
						</tr>
					</c:if>
				</c:forEach>
			</table>

			<h2 class="mt-5">Finished Appointments</h2>
			<table class="containerTab3">
				<tr>
					<th>Appointment Date</th>
					<th>Appointment Time</th>
					<th>Appointment Reason</th>
					<th>Doctor</th>
					<th>Consultation price</th>
					<th>Actions</th>
				</tr>
				<c:forEach var="appointment" items="${patient.appointments}">
					<c:if test="${appointment.date lt today }">
						<tr>
							<td><c:out value="${appointment.date }" /></td>
							<td><c:out value="${appointment.time }" /></td>
							<td><c:out value="${appointment.reason }" /></td>
							<td><c:out value="${appointment.doctor.fullName}" /></td>
							<td><c:out value="${appointment.doctor.price}" /> DNT</td>

							<td class="d-flex justify-content-center"><a
								href="/show/${appointment.id}" class="btn btn-success mx-2">view</a>
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
		</div>
		<!-- Actualities :all posts info -->
		<div calss="col-4 ">
			<h2 class="mt-2">Actualité</h2>
			<div class="overflow-auto p-2 shadow " style="height: 30rem;">
				<c:forEach var="post" items="${posts}">
					<div class="border border-3 border-primary bg-dark-subtle  mb-2 rounded-4 text-dark  p-3 ">
						<div class="d-flex justify-content-between mb-1 mt-3">
							<img alt="doctor_picture" src="${post.doctor.path_image }"
								class="rounded-5" height="40px">
							<p>Created By : <c:out value="${post.doctor.fullName}" /></p>
						</div>
	
						<div class="d-flex justify-content-between border border-1 border-dark p-2 shadow  rounded-4">
							<p><c:out value="${post.content}" /></p>
							<p><fmt:formatDate value="${post.createdAt}" pattern="yyyy-MM-dd HH:mm" /></p>
						</div>
						<c:choose>
							<c:when test="${post.followers.contains(patient)}">
								<div class="d-flex justify-content-around mb-1">
									<a href="/dislike/${post.id}"  class="text-danger fw-bolder"> Dislike </a>
									<p>Number of like : <span class="fw-bolder"><c:out value="${post.followers.size() }"/></span> </p>
								</div>
							</c:when>
						<c:otherwise>
							<div class="d-flex justify-content-around mb-1">
								<a href="/like/${post.id}" class="fw-bolder">Like</a>
								<p>Number of likes : <span class="fw-bolder"><c:out value="${post.followers.size() }" /></span></p>
							</div>
						</c:otherwise>
							
						</c:choose>
					</div>
				</c:forEach>
			</div>
		</div>
	</div>
</body>
</html>
