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
    <title>Login Doctor</title>
    <link rel="stylesheet" href="/css/homeApp.css">
    <script type="text/javascript" src="/js/app.js"></script>

    <style>
        body {
            background-image: url('img/image_1 (4).png');
        }
    </style>
</head>

<body>
    <div class="mainContainer">
        <div class="navBar">
       
            <img src="/img/logo.png" alt="Logo" id="navLogo">
            <a href="/home" id="HomeTitle">TUNIHEALTH</a>
        
        </div>
    <div class="containerWrapper">
    
        <div class="container1">
        <div class="card">
            <h1>Patient space</h1>
            <div>
                <p>If you look for a good doctor. Create an account<p>
                        <a href="/register/patient" class="Button  primary lg">Register as Patient</a>
                        <p>If you have an account. Login and pick your first appointment</p>
                        <a href="/login/patient" class="Button  Success lg">Login as Patient</a>
                    </div>
                </div>
            </div>

            <div class="container2">
                <div class="card">
                    <h1>Doctor space </h1>
                    <div>
                        <p>If you're the first time. Create an account</p>
                        <a href="/register/doctor" class="Button  primary lg">Register as Doctor</a>
                        <p>If you have an account. Login and see your work schedule</p>
                        <a href="/login/doctor" class="Button  Success lg ">Login as Doctor</a>
                    </div>
                </div>
            </div>
            
    </div>


    </div>
</body>

</html>