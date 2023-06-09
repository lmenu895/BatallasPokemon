<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<h1>Estos son los datos de usuario</h1>
<button type="button" class="cambiar-mail btn btn-info">Cambiar mail</button>
<button type="button" class="cambiar-pass btn btn-danger">Cambiar contraseña</button>
<dialog class="cambiar-mail-dialog"> <form:form action="validar-login"
	method="POST" modelAttribute="datosLogin">
	<%--Elementos de entrada de datos, el elemento path debe indicar en que atributo del objeto usuario se guardan los datos ingresados--%>
	<label for="email">Correo electronico</label>
	<form:input path="email" id="email" type="email" class="form-control" />
	<label for="password">Contraseña</label>
	<form:input path="password" type="password" id="password"
		class="form-control" />
	<br>

	<button class="btn btn-lg btn-primary btn-block" Type="Submit">Iniciar
		sesion</button>
	<button id="registrarme" class="btn btn-lg btn-primary btn-block"
		type="button">Registrarme</button>
	<%--Bloque que es visible si el elemento error no esta vacio	--%>
	<c:if test="${not empty error}">
		<h4>
			<span id="error">${error}</span>
		</h4>
		<br>
	</c:if>
</form:form> </dialog>
<dialog class="cambiar-pass-dialog"></dialog>
<script type="text/javascript" src="js/datos-de-usuario.js"></script>