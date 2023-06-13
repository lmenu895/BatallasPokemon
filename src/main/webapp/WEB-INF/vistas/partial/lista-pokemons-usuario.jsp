<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<table class="table table-historial" >
	<thead>
		<tr class="text-center align-left">
			<th scope="col" style="width: 350px; padding-left: 35px;" class="table-text">Pokemon</th>
			<th scope="col" style="width: 350px; padding-left: 90px">Nombre</th>
			<th scope="col" style="width: 260px; padding-left: 50px">Tipo</th>
			<th scope="col" style="padding-right: 45px;" class="table-text">Detalles</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${lista}" var="pokemon">
			<tr class='text-center align-left trbody' id="pokemon${pokemon.id}">
				<td><img alt="${pokemon.pokemon.nombre}" style=""
					class="img-fluid imgPokemon mb-2"
					src="images/sprites/${pokemon.pokemon.nombre}/${pokemon.pokemon.imagenFrente}"></td>
				<td><h4 class="nombre-pokemon vBuscado">${pokemon.pokemon.nombre}</h4></td>
				<td class="table-text"><span class="vBuscado" style="">${pokemon.pokemon.tipo}</span></td>
				<td class="table-text">Detalles</td>
			</tr>
		</c:forEach>
	</tbody>
</table>

