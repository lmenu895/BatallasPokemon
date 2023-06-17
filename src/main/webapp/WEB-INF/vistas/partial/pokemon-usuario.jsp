<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="pokemon">
	<p class="activo" id="detalles${pokemonUsuario.pokemon.id}" style="display: none;">
	<h1>${pokemon.nombre}</h1>
	<c:forEach items="${ataquesPokemon}" var="ataquePokemon">
		<h1>${ataquePokemon.activo}</h1>
	</c:forEach>
</div>