(function () {
	function iniciarIndicador() {
		var senha = document.getElementById('senha');
		var indicador = document.getElementById('password-strength');
		var rotulo = document.getElementById('password-strength-label');

		if (!senha || !indicador || !rotulo) return;

		var regras = {
			length: function (valor) { return Array.from(valor).length >= 8; },
			uppercase: function (valor) { return /\p{Lu}/u.test(valor); },
			lowercase: function (valor) { return /\p{Ll}/u.test(valor); },
			number: function (valor) { return /\p{N}/u.test(valor); },
			special: function (valor) { return /[^\p{L}\p{N}\s]/u.test(valor); }
		};

		function atualizar() {
			var valor = senha.value;
			var atendidas = 0;

			Object.keys(regras).forEach(function (regra) {
				var cumprida = regras[regra](valor);
				var item = indicador.querySelector('[data-password-rule="' + regra + '"]');
				if (cumprida) atendidas++;
				if (!item) return;

				item.classList.toggle('is-met', cumprida);
				var icone = item.querySelector('i');
				if (icone) icone.className = cumprida ? 'bi bi-check-circle-fill' : 'bi bi-circle';
			});

			var forte = atendidas === Object.keys(regras).length;
			var nivel = !valor ? 'empty' : forte ? 'strong' : atendidas >= 3 ? 'medium' : 'weak';
			var texto = !valor ? 'Digite uma senha' : forte ? 'Senha forte' : atendidas >= 3 ? 'Senha média' : 'Senha fraca';

			indicador.dataset.strength = nivel;
			rotulo.textContent = texto;

			var barrasAtivas = valor ? Math.max(1, Math.ceil(atendidas * 4 / Object.keys(regras).length)) : 0;
			indicador.querySelectorAll('.password-strength-bar').forEach(function (barra, indice) {
				barra.classList.toggle('is-active', indice < barrasAtivas);
			});

			senha.setCustomValidity(valor && !forte
				? 'A senha deve atender a todos os requisitos de segurança.'
				: '');
		}

		senha.addEventListener('input', atualizar);
		atualizar();
	}

	if (document.readyState === 'loading') {
		document.addEventListener('DOMContentLoaded', iniciarIndicador);
	} else {
		iniciarIndicador();
	}
})();
