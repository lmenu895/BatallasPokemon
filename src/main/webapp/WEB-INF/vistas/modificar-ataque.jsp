<%@ include file="partial/header.jsp"%>>
<link href="css/style.css" rel="stylesheet">
<title>Modificar Ataque</title>

<body class="fondo">
	<div class="container d-flex justify-content-center align-items-center">
		<div id="loginbox"
			class="mainbox col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">
			<form:form action="guardar-ataque-modificado" method="POST"
				modelAttribute="ataque" class="form">
				<h3 class="form-signin-heading">Modificar Ataque</h3>
				<hr class="colorgraph">
				<br>
				<label>Nombre</label>
				<form:input path="nombre" id="nombre" class="form-control" />
				<br>
				<label>Tipo</label>
				<form:select path="tipo">
					<form:options />
				</form:select>
				<br>
				<label>Potencia</label>
				<form:input path="potencia" id="potencia" class="form-control" />
				<br>
				<label>Precision</label>
				<form:input path="precataque" id="precataque" class="form-control" />
				<br>
				<label>Pp's</label>
				<form:input path="pp" id="pp" class="form-control" />
				<br>
				<div class="form-check">
					<label class="form-check-label">Efecto</label>
					<form:checkbox class="form-check-input" path="efecto" id="efecto" />
					<br>
				</div>
				<form:hidden path="id" />
				<button id="btn-registrarme"
					class="btn btn-lg btn-primary btn-block" Type="Submit">Modificar</button>

				<c:if test="${not empty error}">
					<div class="alert alert-danger">
						<strong>${error}</strong>
					</div>
				</c:if>
			</form:form>


		</div>
	</div>
	<script src="js/bootstrap.min.js"></script>
</body>
</html>