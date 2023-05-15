<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<!-- Bootstrap core CSS -->
<link href="css/bootstrap.min.css" rel="stylesheet">
<!-- Bootstrap theme -->
<link href="css/bootstrap-theme.min.css" rel="stylesheet">
<title>Bienvenido a las mejores batallas pokemons</title>
<link rel="icon" type="image/x-icon" href="images/favicon.ico">
<link href="css/login.css" rel="stylesheet">
</head>
<body class="fondo">
	<div class="container">
		<div id="loginbox">
			<%--Definicion de un form asociado a la accion /validar-login por POST. Se indica ademas que el model attribute se--%>
			<%--debe referenciar con el nombre usuario, spring mapea los elementos de la vista con los atributos de dicho objeto--%>
			<%--para eso debe coincidir el valor del elemento path de cada input con el nombre de un atributo del objeto --%>
			<form:form action="validar-login" method="POST"
				modelAttribute="datosLogin">
				<img src="https://fontmeme.com/permalink/230515/1710a180ff6e8fbb28b13c9bde843ad0.png" alt="fuente-pokemon" border="0">
				<hr class="colorgraph">
				<br>

				<%--Elementos de entrada de datos, el elemento path debe indicar en que atributo del objeto usuario se guardan los datos ingresados--%>
				<label for="email">Correo electronico</label>
				<form:input path="email" id="email" type="email"
					class="form-control" />
				<label for="password">Contrase�a</label>
				<form:input path="password" type="password" id="password"
					class="form-control" />
				<br>

				<button class="btn btn-lg btn-primary btn-block" Type="Submit" />Iniciar sesion</button> <a class="btn btn-lg btn-primary btn-block" href="registrar-usuario">Registrarme</a>

			</form:form>
			
			<%--Bloque que es visible si el elemento error no esta vacio	--%>
			<c:if test="${not empty error}">
				<h4>
					<span>${error}</span>
				</h4>
				<br>
			</c:if>
			${msg}
		</div>
	</div>

	<!-- Placed at the end of the document so the pages load faster -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<script>
		window.jQuery
				|| document
						.write('<script src="../../assets/js/vendor/jquery.min.js"><\/script>')
	</script>
	<script src="js/bootstrap.min.js" type="text/javascript"></script>
</body>
</html>
