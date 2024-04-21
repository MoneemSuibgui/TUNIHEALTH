<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page isErrorPage="true"%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>Registration Doctor</title>
    <link rel="stylesheet" href="/css/registerDoctor.css">
    <script type="text/javascript" src="/js/app.js"></script>

    <style>
        body {
            background-image: url('img/image_1 (4).png');
        }
    </style>
    
</head>
<body>
<div class= "main_container">

	<div class="a">
		<a href="/login/patient">🔑Login as patient !</a>
		<a href="/"> 🏠Home</a>
	</div>

</div>
<div class="container1">

		<form:form action="/register/doctor" method="POST"
			modelAttribute="doctor" enctype="multipart/form-data">

			<p class="text-danger"><form:errors path="fullName" /></p>
			<label for="fullName">Full Name</label>
			<div>
				<form:input type="text" class="form-control" id="fullName"
					path="fullName" placeholder="Enter your full name" />
				<p class="text-danger">
					<form:errors path="email" />
				</p>
				<div>
					<label for="email">Email Adresse</label>
					<form:input type="text" class="form-control" id="email"
						path="email" placeholder="Enter your email adresse " />
				</div>
				<p class="text-danger">
					<form:errors path="phoneNumber" />
				</p>
				<div>
					<label for="phoneNumber">Phone number</label>
					<form:input type="number" class="form-control" id="phoneNumber"
						path="phoneNumber" placeholder="Enter your phone number" />
				</div>

				<p class="text-danger">
					<form:errors path="location" />
				</p>
				<div class="form-group row mb-3 mt-3">
					<label for="location">Location</label>
					<form:input type="tel" class="form-control" id="location"
						path="location" placeholder="Enter your location" />
				</div>
				<p class="text-danger">
					<form:errors path="password" />
				</p>
				<div class="form-group row mb-3 mt-3">
					<label for="password">Password</label>
						<form:input type="password" class="form-control" id="password"
							path="password" placeholder="Enter your Password" />
				</div>

				<p class="text-danger">
					<form:errors path="confirm" />
				</p>
				<div class="form-group row mb-3 mt-3">
					<label for="confirm">ConfirmPW</label>
					<form:input type="confirm" class="form-control" id="confirm"
							path="confirm" placeholder="Enter your confirm password" />>
				</div>

			</div>
	</div>


<div class="container2">

		<div>

			<p class="text-danger">
				<form:errors path="reg_number" />
			</p>
			<div>
				<form:label path="reg_number">RNE/M.FISC</form:label>
				<form:input type="text" class="form-control" id="reg_number"
					path="reg_number" placeholder="Enter your registration number" />
			</div>

			<p class="text-danger">
				<form:errors path="specialty" />
			</p>
			<div>
				<form:label path="specialty" class="form-label">Speciality </form:label>
				<form:select path="specialty" id="specialty" class="form-control">
					<form:option value="Generalist">Generalist</form:option>
					<form:option value="Dentist">Dentist</form:option>
					<form:option value="Pediatrician">Pediatrician</form:option>
					<form:option value="Cardiologist">Cardiologist</form:option>
					<form:option value="Neurologist">Neurologist</form:option>
					<form:option value="Pulmonologist">Pulmonologist</form:option>
					<form:option value="Anesthesiologist">Anesthesiologist</form:option>
					<form:option value="Opphtalmologist">Opphtalmologist</form:option>
					<form:option value="Rheumatologist">Rheumatologist</form:option>
					<form:option value="Urologist">Urologist</form:option>
					<form:option value="Dermatologist">Dermatologist</form:option>
					<form:option value="Phsychiatrist">Phsychiatrist</form:option>
				</form:select>
			</div>

			<p class="text-danger">
				<form:errors path="price" />
			</p>
			<div>
				<label for="price">Consultation price</label>
				<form:input type="number" step="0.01" class="form-control"
					id="price" path="price" placeholder="Enter your Consultation price" />
			</div>

			<p class="text-danger">
				<form:errors path="dates" />
			</p>
			<label for="dates">Disponibility dates</label>
			<form:input type="date" id="dates" path="dates" class="form-control"
				placeholder="Enter your Disponibilty date" />

			<p class="text-danger">
				<form:errors path="description" />
			</p>
			<div>
				<label for="description">Description</label>
				<form:textarea type="text" id="description" path="description"
					class="form-control"
					placeholder="Enter few lines for your speciality" />
			</div>
			<p class="text-danger">${error}</p>
			<label for="doctorPic">Picture</label> <input type="file"
				class="form-control" name="doctorPic"
				placeholder="upload your picture" />
			<button class="btn">Register</button>
		</div>
	</div>		
</form:form>
	

</html>
