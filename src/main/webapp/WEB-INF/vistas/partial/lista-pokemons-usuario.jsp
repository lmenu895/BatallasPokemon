<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

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
					src="images/sprites/${pokemonUsuario.pokemon.nombre}/${pokemonUsuario.pokemon.imagenFrente}"></td>
				<td><h4 class="nombre-pokemon vBuscado">${pokemonUsuario.pokemon.nombre}</h4></td>
				<td class="vBuscado">${pokemonUsuario.pokemon.tipo}</td>
				<td><a class="btn btn-primary" href="pokemon-usuario/${pokemonUsuario.pokemon.id}">Detalles</a></td>
			</tr>
		</c:forEach>
	</tbody>
</table>

