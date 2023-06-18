<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link href="<c:url value="/css/lista-pokemons-usuario.css"/>" rel="stylesheet">
<table class="table table-historial">
	<thead>
		<tr class="text-center align-middle">
			<th scope="col">Pokemon</th>
			<th scope="col">Nombre</th>
			<th scope="col">Tipo</th>
			<th scope="col">Detalles</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${lista}" var="pokemonUsuario">
			<tr class='text-center align-middle trbody'>
				<td><img alt="${pokemonUsuario.pokemon.nombre}"
					class="img-fluid mb-2"
					src="<c:url value="/images/sprites/${pokemonUsuario.pokemon.nombre}/${pokemonUsuario.pokemon.imagenFrente}"/>"></td>
				<td><h4 class="nombre-pokemon vBuscado">${pokemonUsuario.pokemon.nombre}</h4></td>
				<td class="vBuscado">${pokemonUsuario.pokemon.tipo}</td>
				<td><button class="btn btn-primary detalles" id="detalles${pokemonUsuario.pokemon.id}"
						value="${pokemonUsuario.pokemon.id}">Detalles</button></td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<script type="text/javascript">
	document.title = "Lista de Pokemons";
</script>

