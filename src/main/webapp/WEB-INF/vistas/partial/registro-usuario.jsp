<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="loginbox">
	<form:form method="POST" modelAttribute="usuario">
		<img
			src="https://fontmeme.com/permalink/230515/1710a180ff6e8fbb28b13c9bde843ad0.png"
			alt="fuente-pokemon" border="0">
		<hr class="colorgraph">
		<br>
		<label for="usuario">Nombre de Usuario</label>
		<form:input path="usuario" type="text" id="nickname" class="form-control" />
		<label for="usuario">Direccion de correo electronico</label>
		<form:input path="email" type="email" id="email" class="form-control" autocomplete="on" />
		<label for="usuario">Contraseña</label>
		<form:input path="password" type="password" id="password"
			class="form-control" />
		<br>
		<button id="btn-registrarme" class="btn btn-lg btn-primary btn-block"
			Type="submit">Registrarme</button>
		<button id="login" class="btn btn-primary btn-block mt-2"
			type="button" style="display: block;">Volver al login</button>
	</form:form>
</div>