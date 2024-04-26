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
    <div class="container">
        <h1>Welcome <c:out value="${patient.firstName}" /></h1>
        <img alt="patient picture" src="${patient.url_picture}" width="180px" height="180px" class="p-2">
        <h1 class="">Appointment Details :</h1>
        <div class="d-flex justify-content-between">
            <div class="mt-4">
                <h3>* Appointment Date : <c:out value="${appointment.date }"></c:out></h3>
                <h3>* Appointment Time : <c:out value="${appointment.time }"></c:out></h3>
                <h3>* Appointment Reason : <c:out value="${appointment.reason }"></c:out></h3>
                <h3 class="mt-4 mx-2">Doctor Details :</h3>
                <table class="table table-border border-4 bg-light">
                    <tr class="text-center">
                        <th>Doctor Fullname</th>
                        <th>Doctor Email adresse</th>
                        <th>Doctor Location</th>
                        <th>Consultation price</th>
                    </tr>
                    <tr>
                        <td><c:out value="${appointment.doctor.fullName }"/></td>
                        <td><c:out value="${appointment.doctor.email}"/><td>
                        <td><c:out value="${appointment.doctor.location}"/><td>
                        <td><c:out value="${appointment.doctor.price}"/><td>
                    </tr>
                </table>
            </div>
            <div class="mb-4">
                <h4>Doctor Picture</h4>
                <img alt="" src="${appointment.doctor.path_image}" width="140px" height="140px" class="border border-1 border-dark p-2 rounded-5 bg-light">
                <c:choose>
                    <c:when test="${patient.favouriteDoctors.contains(appointment.doctor)}">
                        <form action="/dislike/${appointment.doctor.id}/${appointment.id}" method="post">
                            <button class="btn btn-danger btn-lg text-light mt-4">Dislike doctor</button>
                        </form>
                    </c:when>
                    <c:otherwise>
                        <form action="/like/${appointment.doctor.id}/${appointment.id}" method="post">
                            <button class="mb-4 btn btn-success btn-lg text-light mt-4">Like doctor</button>
                        </form>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
    <div class="d-flex justify-content-center mt-5">
        <a href="/user/dashboard" class="btn btn-primary btn-lg">Cancel</a>
        <a href="/edit/appointment/${appointment.id}" class="btn btn-secondary btn-lg mx-4">Edit</a>
        <div>
            <form action="/delete/${appointment.id}" method="post">
                <input type="hidden" name="_method" value="delete">
                <button class="btn btn-danger btn-lg">Delete</button>
            </form>
        </div>
    </div>
</body>
</html>
