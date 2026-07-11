package br.com.conectacampus.service;

import br.com.conectacampus.dao.UsuarioDAO;
import org.mindrot.jbcrypt.BCrypt;
import br.com.conectacampus.model.Usuario;

public class LoginService {

    private final UsuarioDAO usuarioDAO;

    public LoginService() {
        this.usuarioDAO = new UsuarioDAO();
    }

    // ==========================
    // AUTENTICAR
    // ==========================
    public Usuario autenticar(String email, String senha) {

        if (email == null || email.trim().isEmpty()) {
            return null;
        }

        if (senha == null || senha.trim().isEmpty()) {
            return null;
        }

        Usuario usuario = usuarioDAO.buscarPorEmail(email.trim());

        if (usuario == null) {
            return null;
        }

        if (!usuario.isAtivo()) {
            return null;
        }

        // Verifica a senha utilizando BCrypt
        if (!BCrypt.checkpw(senha, usuario.getSenha())) {
            return null;
        }

        usuarioDAO.atualizarUltimoAcesso(usuario.getIdUsuario());

        return usuario;
    }
}