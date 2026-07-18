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
</script>
</body>
</html>