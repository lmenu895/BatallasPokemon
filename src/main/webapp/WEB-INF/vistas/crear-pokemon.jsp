<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Crear Pokemon</title>
</head>
<body>
	<div class="container">
		<div id="loginbox" style="margin-top: 50px;"
			class="mainbox col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">
			<form:form action="guardar-pokemon" method="POST"
				modelAttribute="pokemon">
				<h3 class="form-signin-heading">Nuevo Pokemon</h3>
				<hr class="colorgraph">
				<br>
				<form:input path="nombre" id="nombre" class="form-control" />
				<form:select path="tipo">
					<form:options />
				</form:select>
				<form:input path="vida" id="vida" class="form-control" />

				<button id="btn-registrarme"
					class="btn btn-lg btn-primary btn-block" Type="Submit">Guardar</button>
			</form:form>

			<c:if test="${not empty error}">
				<h4>
					<span>${error}</span>
				</h4>
				<br>
			</c:if>
		</div>
	</div>
</body>
</html>