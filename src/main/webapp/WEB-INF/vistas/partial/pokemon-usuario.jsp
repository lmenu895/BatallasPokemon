<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" href="<c:url value="/css/pokemon-usuario.css"/>">
<div class="pokemon">
	<div class="data">
		<div>
			<div class="img-box">
				<img class="sprite" alt="${pokemon.nombre}" style="display: none;"
					src="<c:url value="/images/sprites/${pokemon.nombre}/${pokemon.imagenFrente}"/>">
			</div>
			<input type="hidden" class="activo" value="${pokemon.id}">
			<h4>${pokemon.nombre}</h4>
			<h5>${pokemon.tipo}</h5>
		</div>
		<div class="puntos">
			<img class="pokeball" src="<c:url value="/images/pokeballU.png"/>"
				alt="pokeball"><strong id="puntos">${puntosUsuario}</strong>
		</div>
	</div>
	<div class="ataques">
		<c:forEach items="${ataquesPokemon}" var="ataquePokemon">
			<div
				class="card <c:if test="${ataquePokemon.bloqueado}">bloqueado</c:if>">
				<div clasS="card-header">
					<h5>${ataquePokemon.ataque.nombre}</h5>
				</div>
				<div class="card-body">
					<p>
						<strong>Tipo:</strong> ${ataquePokemon.ataque.tipo}
					</p>
					<p>
						<strong>Potencia:</strong> ${ataquePokemon.ataque.potencia}
					</p>
					<p>
						<strong>Precision:</strong> ${ataquePokemon.ataque.precataque}
					</p>
					<c:if test="${ataquePokemon.bloqueado}">
						<div id="desbloquear">
							<button type="button"
								title="Consume 80 puntos para desbloquearlo"
								value="${ataquePokemon.id}" class="btn btn-primary desbloquear">Desbloquear</button>
							80pts.
						</div>
					</c:if>
					<div class="form-check form-switch switch"
						<c:if test="${ataquePokemon.bloqueado}">style="display: none;"</c:if>>
						<input class="form-check-input activar" type="checkbox"
							<c:if test="${ataquePokemon.activo}">checked</c:if>
							id="ataque${ataquePokemon.id}" value="${ataquePokemon.id}">
						<label class="form-check-label" for="ataque${ataquePokemon.id}">
							<c:choose>
								<c:when test="${ataquePokemon.activo}">Activado</c:when>
								<c:otherwise>Desactivado</c:otherwise>
							</c:choose>
						</label>
					</div>
				</div>
			</div>
		</c:forEach>
	</div>
</div>
<script>
	document.title = `${pokemon.nombre}`;
	var root = <c:url value="/"/>;
</script>
<script src="<c:url value="/js/pokemon-usuario.js"/>"></script>