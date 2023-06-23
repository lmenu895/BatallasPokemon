<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link href="<c:url value="/css/datos-de-usuario.css"/>" rel="stylesheet">
<div class="home">
	<div class="contenedor">
		<div class="containerBox">
			<form class="cambiar-foto-perfil" method="POST"
				enctype="multipart/form-data">
				<div class="boxFoto">
					<div class="perfil">
						<c:choose>
							<c:when test="${not empty usuario.fotoPerfil}">
								<img id="imgPerfil" class="imagenPerfil"
									src="<c:url value="/images/fotosPerfil/${usuario.fotoPerfil}"/>">
							</c:when>
							<c:otherwise>
								<img id="imgPerfil" class="imagenPerfil"
									src="<c:url value="/images/perfilBlanco.jpg"/>">
							</c:otherwise>
						</c:choose>
						<br> <input type="file" id="fotoPerfil" name="fotoPerfil"
							accept="image/*" /> <br> <span>Click en la imagen
							para cambiar la foto de perfil</span>
					</div>
				</div>
			</form>
			<div class="boxCentro">
				<form:form action="cambiar-usuario" method="POST"
					modelAttribute="datosLogin">
					<div class="form-group">
						<label for="usuario">Cambiar nombre de usuario:</label>
						<form:input path="usuario" placeholder="${usuario.usuario}"
							id="usuario" accept="image/*" type="text" class="form-control" />
					</div>
					<div class="boton">
						<button type="submit" class="btn btn-primary ">Aceptar</button>
					</div>
				</form:form>
				<form:form action="cambiar-mail" method="POST"
					modelAttribute="datosLogin">
					<label for="email">Cambiar correo electronico</label>
					<form:input path="email" placeholder="${usuario.email}" id="email"
						type="email" class="form-control" />
					<div class="boton">
						<button type="submit" class="btn btn-primary ">Aceptar</button>
					</div>
				</form:form>
				<form:form action="cambiar-contrasenia" method="POST"
					modelAttribute="datosLogin">
					<label for="oldPassword">Contraseña actual</label>
					<form:input path="oldPassword" type="password" id="oldPassword"
						class="form-control" />
					<label for="password">Nueva contraseña</label>
					<form:input path="password" type="password" id="password"
						class="form-control" />
					<div class="boton">
						<button type="submit" class="btn btn-primary ">Aceptar</button>
					</div>
				</form:form>
				<br>
				<c:if test="${not empty error}">
					<div class="alert alert-danger">
						<strong id="error">${error}</strong>
					</div>
				</c:if>
				<c:if test="${not empty success}">
					<div class="alert alert-success">
						<strong id="success">${success}</strong>
					</div>
					<br>
				</c:if>
			</div>

		</div>
	</div>
</div>

<script>
	document.title = 'Datos de Usuario';
</script>
<script src="<c:url value="/js/datos-de-usuario.js"/>"></script>


