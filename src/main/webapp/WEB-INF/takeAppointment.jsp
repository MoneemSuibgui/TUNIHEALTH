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
<title>Take Appointment</title>
<link rel="stylesheet" href="/css/takeAppointment.css">
<link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css">
<style>


</style>
</head>
<body>
<div>

<h1>New Appointment</h1>
<div class="MainContainer">

	<p class="text-danger">${errorTimeMsg}</p>
	<p class="text-danger">${errorMsg}</p>
	
    <form:form action="/create/appointment" method="POST" modelAttribute="appointment">
    
    <p><form:errors path="date"/></p>
    <form:label path="date">Appointment Date</form:label>
    <form:input path="date" type="date"/>
    
    <div>
    	<label for="time">Appointment Time</label>
    	<input name="appointmentTime" type="time" />
    </div>
    
    <p><form:errors path="reason"/></p>
    <p><form:label path="reason">Description of your condition (Reason):  </form:label></p>
    <form:textarea path="reason"/>
    
    <div>    
	    <p><label for="doctor">Choose Doctor:</label></p>
	    <select name="doctor" id="doctor">
	        <c:forEach var="doctor" items="${doctors}">
	            <option value="${doctor.id}"><c:out value="${doctor.fullName}"/>  ---  <c:out value="${doctor.specialty}"/> -- <c:out value="${doctor.location}"/></option>
	        </c:forEach>    
	    </select>
    </div>
    <div>
    	<button>Submit</button>
    </div>
    </form:form>
    
</div>
</div>
</body>
</html>
