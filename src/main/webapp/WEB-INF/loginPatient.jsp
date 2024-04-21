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
    <title>Login Patient</title>
    <link rel="stylesheet" href="/css/loginPatient.css">
    
</head>

<body>

    <div class="login-container">
        <div>
            <div class="Container1">
                <h1 class="title">Login Patient</h1>
                <div class="a">
                    <a href="/login/patient">🔑Login as patient !</a>
                    <a href="/"> 🏠Home</a>
                </div>
            </div>

            <form:form action="/login/patient" method="POST" modelAttribute="loggedPatient">
                <p><form:errors path="email" class="error-text" /></p>
                <form:label path="email">Email</form:label>
                <form:input path="email" class="input-control" />

                <p><form:errors path="password" class="error-text" /></p>
                <form:label path="password">Password</form:label>
                <form:input path="password" type="password" class="input-control" />

                <div class="Container2">
                    <button class="login-btn">Login</button>
                </div>
            </form:form>
        </div>
    </div>
</body>

</html>
