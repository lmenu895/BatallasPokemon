import Buscador from './buscador.js';

$(document).ready(function() {

	var buscador = new Buscador('.buscar', '.clear', '.objetos', '.pokemons-tienda', 'vBuscado');

	$(document).on('keyup', '.buscar', function() {
		buscador.buscar(this.value);
	});
	$(document).on('click', '.clear', function() {
		buscador.limpiarBuscar(true);
	});
	$(document).keyup(e => {
		if (e.key === 'Escape' && $('.buscar').is(':focus') && $('.buscar').val() !== '') buscador.limpiarBuscar(true);
	});

	$(".slider").slick({
		infinite: true,
		slidesToShow: 1,
		slidesToScroll: 1,
		speed: 500,
		centerMode: true,
		centerPadding: "0",
	});
});

$('.center').slick({
	centerMode: true,
	centerPadding: '60px',
	slidesToShow: 3,
	responsive: [
		{
			breakpoint: 768,
			settings: {
				arrows: false,
				centerMode: true,
				centerPadding: '40px',
				slidesToShow: 3
			}
		},
		{
			breakpoint: 480,
			settings: {
				arrows: false,
				centerMode: true,
				centerPadding: '40px',
				slidesToShow: 1
			}
		}
	]
});

setInterval(() => {
	$(function() {

		if (($("#pokeball").css("opacity")) == 1) {
			$("#tirar").val(100);
		}
		else if (($("#superball").css("opacity")) == 1) {
			$("#tirar").val(500);
		}
		else if (($("#ultraball").css("opacity")) == 1) {
			$("#tirar").val(1000);
		}
		else if (($("#masterball").css("opacity")) == 1) {
			$("#tirar").val(10000);
		}
		else if (($("#nestball").css("opacity")) == 1) {
			$("#tirar").val(150);
		}

	})
}, 1);


function openModal() {
	document.getElementById("myModal").style.display = "block"; // Muestra el modal
}

function closeModal() {
	document.getElementById("myModal").style.display = "none"; // Oculta el modal
}
function openModal2() {
	document.getElementById("myModal2").style.display = "block"; // Muestra el modal
}

function closeModal2() {
	document.getElementById("myModal2").style.display = "none"; // Oculta el modal
}
