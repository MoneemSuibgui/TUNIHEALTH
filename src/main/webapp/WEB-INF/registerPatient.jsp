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
    <title>Registration Patient</title>
    <link rel="stylesheet" href="/css/registerPatient.css">
    <script type="text/javascript" src="/js/app.js"></script>
   
   <style>
        body {
            background-image: url('https://wallpaperaccess.com/full/3275630.jpg');
        }

    </style>

</head>
<body>

    <div class="registration-container">
        <div class="container-A">
            <div class="container-B">
                <h1 class="Title">Registration as Patient</h1>
                <div class="a">
                    <a href="/login/patient">🔑Login as patient !</a>
                    <a href="/"> 🏠Home</a>
                </div>
            </div>
            <form:form action="/register/patient" method="POST" modelAttribute="patient" enctype="multipart/form-data">
                <p><form:errors path="firstName" class="text-danger" /></p>
                <form:label path="firstName">First Name</form:label>
                <form:input path="firstName" class="forms" />
                <p><form:errors path="lastName" class="text-danger" /></p>
                <form:label path="lastName">Last Name</form:label>
                <form:input path="lastName" class="forms" />
                <p><form:errors path="email" class="text-danger"/></p>
                <form:label path="email">Email</form:label>
                <form:input path="email" class="forms" />
                <p><form:errors path="phoneNumber" class="text-danger" /></p>
                <form:label path="phoneNumber">Phone Number</form:label>
                <form:input path="phoneNumber" class="forms" />
                <p><form:errors path="password" class="text-danger"/></p>
                <form:label path="password">Password</form:label>
                <form:input path="password" type="password" class="forms" />
                <p><form:errors path="confirm" class="text-danger"  /><p>
                <form:label path="confirm">Confirm PW</form:label>
                <form:input path="confirm" type="password" class="forms" />
                <p class="text-danger">${error}</p>
                <div class="margin-top-2">
                    <label for="picture">Picture</label> 
                    <input type="file" name="picture" class="forms" placeholder="Upload your picture" />
                </div>
                <div class="container-C">
                    <button class="btn">Register</button>
                </div>
            </form:form>
        </div>
    </div>
</body>

</html>