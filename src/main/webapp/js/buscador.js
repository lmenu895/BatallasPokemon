function Buscador(claseBuscador, claseLimpiador, elementosBusqueda, containerResultado, claseBuscada) {

	this.claseBuscador = $(claseBuscador);
	this.claseLimpiador = $(claseLimpiador);
	this.elementosBusqueda = $(elementosBusqueda);
	this.containerResultado = $(containerResultado);
	this.claseBuscada = claseBuscada;

	this.buscar = (value, ...regexObjs) => {
		if (value !== '') {
			this.claseLimpiador.show();
			var busqueda = [];
			this.elementosBusqueda.each((i, elemento) => {
				$(elemento).children(this.clase).each(function() {
					if ($(this).html().toLowerCase().includes(value.toLowerCase())) {
						busqueda.push(elemento);
					}
				});
				if (regexObjs.length > 0) {
					for (var i = 0; i < regexObjs.length; i++) {
						if (new RegExp('^' + regexObjs[i].regex + $(elemento).children(regexObjs[i].clase).html().toLowerCase() + '$')
							.test(value.toLowerCase())) {
							busqueda.push(elemento);
						}
					}
				}
			});
			this.containerResultado.html(busqueda);
		} else {
			this.containerResultado.html(this.elementosBusqueda);
			this.claseLimpiador.hide();
		}
	};
	this.limpiarBuscar = (llenarContainer) => {
		llenarContainer ? this.containerResultado.html(this.elementosBusqueda) : this.containerResultado.html('');
		this.claseBuscador.val('');
		this.claseBuscador.focus();
		this.claseLimpiador.hide();
	}
};