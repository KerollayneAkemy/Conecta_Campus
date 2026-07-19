</main>
</div>

<script>
    function toggleSidebar() {
        var appShell = document.getElementById('appShell');
        var toggleBtn = document.getElementById('sidebarToggle');
        if (!appShell) return;

        var isCollapsed = appShell.classList.contains('sidebar-collapsed');
        var willCollapse = !isCollapsed;
        appShell.classList.toggle('sidebar-collapsed', willCollapse);

        if (toggleBtn) {
            toggleBtn.setAttribute('aria-expanded', String(!willCollapse));
            toggleBtn.setAttribute('aria-label', willCollapse ? 'Expandir menu' : 'Recolher menu');
        }
        try {
            localStorage.setItem('conectaCampusSidebarCollapsed', String(willCollapse));
        } catch (e) { /* sem problema */ }
    }

    document.addEventListener('DOMContentLoaded', function () {
        var appShell = document.getElementById('appShell');
        if (!appShell) return;
        var saved = null;
        try {
            saved = localStorage.getItem('conectaCampusSidebarCollapsed');
        } catch (e) { saved = null; }
        if (saved === 'true') {
            appShell.classList.add('sidebar-collapsed');
            var toggleBtn = document.getElementById('sidebarToggle');
            if (toggleBtn) {
                toggleBtn.setAttribute('aria-expanded', 'false');
                toggleBtn.setAttribute('aria-label', 'Expandir menu');
            }
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