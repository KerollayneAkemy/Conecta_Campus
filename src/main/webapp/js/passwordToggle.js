(function () {
	function prepararCamposSenha() {
		document.querySelectorAll('input[type="password"]').forEach(function (campo, indice) {
			if (campo.dataset.passwordToggleReady === 'true') return;

			campo.dataset.passwordToggleReady = 'true';
			if (!campo.id) campo.id = 'campo-senha-' + (indice + 1);

			var grupo = document.createElement('div');
			grupo.className = 'password-field';
			campo.parentNode.insertBefore(grupo, campo);
			grupo.appendChild(campo);

			var botao = document.createElement('button');
			botao.type = 'button';
			botao.className = 'password-toggle';
			botao.setAttribute('aria-label', 'Mostrar senha');
			botao.setAttribute('aria-controls', campo.id);
			botao.setAttribute('aria-pressed', 'false');
			botao.innerHTML = '<i class="bi bi-eye" aria-hidden="true"></i>';
			grupo.appendChild(botao);

			botao.addEventListener('click', function () {
				var mostrar = campo.type === 'password';
				campo.type = mostrar ? 'text' : 'password';
				botao.setAttribute('aria-label', mostrar ? 'Ocultar senha' : 'Mostrar senha');
				botao.setAttribute('aria-pressed', String(mostrar));
				botao.innerHTML = mostrar
					? '<i class="bi bi-eye-slash" aria-hidden="true"></i>'
					: '<i class="bi bi-eye" aria-hidden="true"></i>';
				campo.focus({ preventScroll: true });
			});
		});
	}

	if (document.readyState === 'loading') {
		document.addEventListener('DOMContentLoaded', prepararCamposSenha);
	} else {
		prepararCamposSenha();
	}
})();
