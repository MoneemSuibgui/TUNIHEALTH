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
<title>Doctor dashboard</title>
<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="/css/main.css">
<!-- change to match your file/naming structure -->
<script src="/webjars/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/js/app.js"></script>
<!-- change to match your file/naming structure -->
</head>
<body>
	<div class="container mt-4">
		<div class="d-flex justify-content-between mb-4">
			<h1>Welcome Doctor :<c:out value="${currentDoctor.fullName}"></c:out></h1>
			<div>
				<a href="/logout" class="btn btn-danger">Logout</a>
			</div>
			<div>
			<a href="/appointments/doctor/${currentDoctor.id}" class="btn btn-success">Hold appointment</a>
				<span class="border border-dark bg-danger rounded-4 p-3 text-light"><c:out value="${count}"/></span>		
			</div>
			
		</div>
		<div class="d-flex justify-content-end">
			<img alt="" src="${currentDoctor.path_image}" height="150px" width="150px" border="2px black solid" class="p-2 rounded-4">
		</div>
		<div class="d-flex justify-content-between">
			<div class="col-6">
				<h1>Date : <fmt:formatDate value="${today}" pattern="dd  MMMM yyyy" /> </h1>
				
				<table class="table table-stripped mt-2 mb-4 bg-warning table-border">
					<tr>
						<th>Patient</th>
						<th>date</th>
						<th>Time</th>
					</tr>
					
					<c:forEach var="appointment" items="${currentDoctor.getValidatedAppointments()}">
						<c:if test="${appointment.date.equals(localDateToday)}">
							<tr>
								<th><a href="/patient/${appointment.patient.id}"><c:out value="${appointment.patient.firstName}"/> <c:out value="${patient.lastName}"/></a></th>
								<th><c:out value="${appointment.date}"/></th>
								<th><c:out value="${appointment.time}"/></th>
							</tr>
						</c:if>
					</c:forEach>
					
				</table>
				
				<h2>Future appointment</h2>
				<table class="table table-stripped mt-2 mb-4 bg-info text-light table-border">
					<tr>
						<th>Patient</th>
						<th>Appointment date</th>
						<th>Time</th>
					</tr>
					
					<c:forEach var="appointment" items="${currentDoctor.validatedAppointments}">
						<c:if test="${!appointment.date.equals(localDateToday)}">
							<tr>
								<th><a href="/patient/${appointment.patient.id}"><c:out value="${appointment.patient.firstName}"/> <c:out value="${patient.lastName}"/></a></th>
								<th><c:out value="${appointment.date}"/></th>
								<th><c:out value="${appointment.time}"/></th>
							</tr>
						</c:if>
					</c:forEach>
				</table>
			</div>
			<div class="col-6 mx-3">
			<!-- Actualities :all posts info -->
				<h2 class="mt-2">Actualité</h2>
				<div  class="overflow-auto border border-3 rounded-3 p-3 shadow " style="height: 30rem;" >
					<c:forEach var="post" items="${posts}">
						<div class="border border-3 bg-success text-light p-3 " >
							<div class="d-flex justify-content-between mb-1 mt-3">
								<img alt="doctor_picture" src="${post.doctor.path_image }" class="rounded-5" height="40px">
								<p>Created By : <c:out value="${post.doctor.fullName}"/></p>
							</div>
							
							<div class="d-flex justify-content-between border border-1 p-2 shadow ">
								<p><c:out value="${post.content}"/></p>
								<fmt:formatDate value="${post.createdAt}" pattern="yyyy-MM-dd HH:mm"/>
								
							</div>
						</div>
					</c:forEach>
					<form:form action="/create/post" method="POST" modelAttribute="post">
						<form:textarea path="content" rows="3" class="form-control mt-3" placeholder="What's on your mind?" />
						<button class="btn btn-primary mt-2">Create post</button>
					</form:form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>