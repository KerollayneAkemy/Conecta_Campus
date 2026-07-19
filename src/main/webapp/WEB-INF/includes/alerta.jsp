<%
    String msgSucesso = (String) session.getAttribute("msgSucesso");
    String msgErro = (String) session.getAttribute("msgErro");

    session.removeAttribute("msgSucesso");
    session.removeAttribute("msgErro");
%>

<% if (msgSucesso != null || msgErro != null) { %>
<div id="toastContainer">

    <% if (msgSucesso != null) { %>
    <div class="toast-custom toast-sucesso" role="status" aria-live="polite">
        <span class="toast-icon"><i class="bi bi-check-lg" aria-hidden="true"></i></span>
        <div class="toast-body">
            <p class="toast-title">Tudo certo!</p>
            <p class="toast-message"><%=msgSucesso%></p>
        </div>
        <button type="button" class="toast-close" aria-label="Fechar aviso" onclick="fecharToast(this)">
            <i class="bi bi-x" aria-hidden="true"></i>
        </button>
        <span class="toast-progress"></span>
    </div>
    <% } %>

    <% if (msgErro != null) { %>
    <div class="toast-custom toast-erro" role="alert" aria-live="assertive">
        <span class="toast-icon"><i class="bi bi-exclamation-lg" aria-hidden="true"></i></span>
        <div class="toast-body">
            <p class="toast-title">Ops, algo deu errado</p>
            <p class="toast-message"><%=msgErro%></p>
        </div>
        <button type="button" class="toast-close" aria-label="Fechar aviso" onclick="fecharToast(this)">
            <i class="bi bi-x" aria-hidden="true"></i>
        </button>
        <span class="toast-progress"></span>
    </div>
    <% } %>

</div>
<% } %>