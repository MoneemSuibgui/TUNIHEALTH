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
    <title>TUNIHEALTH</title>
    <link rel="stylesheet" href="/webjars/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="/css/Dashboard.css">
</head>
<style>

body{
background-image: url('img/image_1 (2).png');
}

</style>
<body>
    <div class="mainContainer">
        <div class="navBar">
            <img src="/img/logo.png" alt="Logo" id="navLogo">
            <a href="/" id="HomeTitle">TUNIHEALTH</a>
            <form action="/search">
                <input type="text" id="search" name="search" placeholder="search">
                <button type="submit" id="searchButton">Search</button>
            </form>
            <a href="/home" id="LogReg">Login & Registration</a>
        </div>

        <div class="bodycontainer col-12">
            <div class="info" >
                <div>
                    <a href="/" class="btn btn-primary btn-lg mx-2 " >Doctors</a>
                    <a href="/actualities" class="btn btn-primary btn-lg">Actualities</a>
                </div>
                <div class="bodyInfo">
                	<div  class="overflow-auto border border-3 bg-info rounded-3 p-3 shadow " style="height: 40rem;" >
                	<c:forEach var="post" items="${posts}">
						<div class="border border-3 bg-secondary text-light p-3 " >
							<div class="d-flex justify-content-between mb-1 mt-3">
								<img alt="doctor_picture" src="${post.doctor.path_image }" class="rounded-5" height="40px">
								<p>Created By : <c:out value="${post.doctor.fullName}"/></p>
							</div>
							
							<div class="d-flex justify-content-between border border-1 p-2 shadow ">
								<p><c:out value="${post.content}"/></p>
								<fmt:formatDate value="${post.createdAt}" pattern="yyyy-MM-dd HH:mm"/>
								
							</div>
							<div class="d-flex justify-content-end">
								<p>Number of like : <span class="fw-bolder text-info"><c:out value="${post.followers.size() }"/></span> </p>
							</div>
						</div>
					</c:forEach>
					<a href="/home" class="fw-bolder text-primary">Create account to comment or likes post ?</a>
					</div>
                </div>
            </div>

            <!------------------------- Map -------------------------------->
            <div class="col-6">
                <iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d25580.99459375575!2d10.15057482012718!3d36.80363563556058!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x12eddf0d740305ef%3A0x23c4e1ed7033a753!2sTunisia%20Center!5e0!3m2!1sen!2sin!4v1585040658255!5m2!1sen!2sin" width="100%" height="100%" frameborder="0" style="border:0;" allowfullscreen="" aria-hidden="false" tabindex="0"></iframe>
            </div>
        </div>

        <div class="Status">
            <p id="numberOfDoctors">Number Of Doctors : <c:out value="${doctors.size() }"/></p>
            <p id="numberOfUsers" >Number Of Patients : <c:out value="${patients.size() }"/></p>
        </div>
    </div>
</body>

</html>
