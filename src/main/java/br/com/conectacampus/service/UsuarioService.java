package br.com.conectacampus.service;

import java.util.List;

import br.com.conectacampus.dao.UsuarioDAO;
import br.com.conectacampus.model.Usuario;

public class UsuarioService {

    private final UsuarioDAO usuarioDAO;

    public UsuarioService() {
        this.usuarioDAO = new UsuarioDAO();
    }

    // ==========================
    // CADASTRAR
    // ==========================
    public boolean cadastrar(Usuario usuario) {

        if (usuario == null) {
            return false;
        }

        if (usuario.getNome() == null || usuario.getNome().trim().isEmpty()) {
            return false;
        }

        if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()) {
            return false;
        }

        if (usuario.getSenha() == null || usuario.getSenha().trim().isEmpty()) {
            return false;
        }

        if (usuario.getPerfil() == null) {
            return false;
        }

        Usuario existente = usuarioDAO.buscarPorEmail(usuario.getEmail().trim());

        if (existente != null) {
            return false;
        }

        return usuarioDAO.inserir(usuario);
    }

    // ==========================
    // ATUALIZAR
    // ==========================
    public boolean atualizar(Usuario usuario) {

        if (usuario == null) {
            return false;
        }

        if (usuario.getIdUsuario() <= 0) {
            return false;
        }

        return usuarioDAO.atualizar(usuario);
    }

    // ==========================
    // EXCLUIR
    // ==========================
    public boolean excluir(int idUsuario) {

        if (idUsuario <= 0) {
            return false;
        }

        return usuarioDAO.excluir(idUsuario);
    }

    // ==========================
    // BUSCAR POR ID
    // ==========================
    public Usuario buscarPorId(int idUsuario) {

        if (idUsuario <= 0) {
            return null;
        }

        return usuarioDAO.buscarPorId(idUsuario);
    }

    // ==========================
    // BUSCAR POR EMAIL
    // ==========================
    public Usuario buscarPorEmail(String email) {

        if (email == null || email.trim().isEmpty()) {
            return null;
        }

        return usuarioDAO.buscarPorEmail(email.trim());
    }

    // ==========================
    // LISTAR
    // ==========================
    public List<Usuario> listar() {
        return usuarioDAO.listar();
    }

    // ==========================
    // QUANTIDADE DE USUÁRIOS
    // ==========================
    public int quantidadeUsuarios() {
        return usuarioDAO.quantidadeUsuarios();
    }

}