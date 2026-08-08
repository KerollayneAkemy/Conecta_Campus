<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
</main>
</div>

<script>
    function toggleSidebar(forcarEstado) {
        var appShell = document.getElementById('appShell');
        var toggleBtn = document.getElementById('sidebarToggle');
        if (!appShell) return;

        var estaAberta = appShell.classList.contains('sidebar-aberta');
        var deveAbrir = (typeof forcarEstado === 'boolean') ? forcarEstado : !estaAberta;

        appShell.classList.toggle('sidebar-aberta', deveAbrir);

		try {
			sessionStorage.setItem('conectaCampus.sidebarAberta', String(deveAbrir));
		} catch (e) {
			/* Mantém o menu funcional quando o armazenamento está indisponível. */
		}

        if (toggleBtn) {
            toggleBtn.setAttribute('aria-expanded', String(deveAbrir));
            toggleBtn.setAttribute('aria-label', deveAbrir ? 'Fechar menu' : 'Abrir menu');
        }
    }

    document.addEventListener('keydown', function (e) {
        if (e.key === 'Escape') {
            toggleSidebar(false);
        }
    });

	document.addEventListener('click', function (e) {
		var linkSidebar = e.target.closest('#sidebarMenu a');
		if (!linkSidebar || !window.matchMedia('(max-width: 992px)').matches) return;
		try {
			sessionStorage.setItem('conectaCampus.sidebarAberta', 'false');
		} catch (erro) {
			/* A navegação continua normalmente sem armazenamento. */
		}
	});

    function fecharToast(botao) {
        var toast = botao.closest('.toast-custom');
        if (!toast) return;
        toast.classList.add('toast-saindo');
        setTimeout(function () {
            toast.remove();
        }, 280);
    }

    document.addEventListener('DOMContentLoaded', function () {
        document.querySelectorAll('.toast-custom').forEach(function (toast) {
            setTimeout(function () {
                if (document.body.contains(toast)) {
                    toast.classList.add('toast-saindo');
                    setTimeout(function () {
                        toast.remove();
                    }, 280);
                }
            }, 6000);
        });
    });

    (function () {
        var overlay = document.getElementById('confirmOverlay');
        var btnOk = document.getElementById('confirmOk');
        var btnCancelar = document.getElementById('confirmCancelar');
        var msgEl = document.getElementById('confirmMessage');
        var linkAlvo = null;

        document.addEventListener('click', function (e) {
            var link = e.target.closest('[data-confirm="true"]');
            if (!link) return;

            e.preventDefault();
            linkAlvo = link;

            var mensagem = link.getAttribute('data-confirm-message') || 'Tem certeza que deseja continuar?';
            msgEl.textContent = mensagem;

            overlay.classList.add('ativo');
        });

        btnOk.addEventListener('click', function () {
            overlay.classList.remove('ativo');
            if (linkAlvo) {
                window.location.href = linkAlvo.getAttribute('href');
            }
        });

        btnCancelar.addEventListener('click', function () {
            overlay.classList.remove('ativo');
            linkAlvo = null;
        });

        overlay.addEventListener('click', function (e) {
            if (e.target === overlay) {
                overlay.classList.remove('ativo');
                linkAlvo = null;
            }
        });

        document.addEventListener('keydown', function (e) {
            if (e.key === 'Escape' && overlay.classList.contains('ativo')) {
                overlay.classList.remove('ativo');
                linkAlvo = null;
            }
        });
    })();
</script>
</body>
</html>
