package br.com.conectacampus.service;

import java.util.List;

import br.com.conectacampus.dao.UsuarioDAO;
import br.com.conectacampus.model.Usuario;

public class UsuarioService {

    private final UsuarioDAO usuarioDAO;

    public UsuarioService() {
        this.usuarioDAO = new UsuarioDAO();
    }

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

    public boolean atualizar(Usuario usuario) {

        if (usuario == null) {
            return false;
        }

        if (usuario.getIdUsuario() <= 0) {
            return false;
        }

        if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()
                || usuario.getSenha() == null || usuario.getSenha().isBlank()
                || usuario.getPerfil() == null) {
            return false;
        }

        Usuario existente = usuarioDAO.buscarPorEmail(usuario.getEmail().trim());
        
        if (existente != null && existente.getIdUsuario() != usuario.getIdUsuario()) {
            return false;
        }
        return usuarioDAO.atualizar(usuario);
    }

    public boolean atualizarPerfil(Usuario usuario) {
        
    	if (usuario == null || usuario.getIdUsuario() <= 0
                || usuario.getNome() == null || usuario.getNome().trim().isEmpty()
                || usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()) {
            return false;
        }

        Usuario existente = usuarioDAO.buscarPorEmail(usuario.getEmail().trim());
       
        if (existente != null && existente.getIdUsuario() != usuario.getIdUsuario()) {
            return false;
        }
        return usuarioDAO.atualizarPerfil(usuario);
    }

    public boolean atualizarSenha(int idUsuario, String senhaCriptografada) {
      
    	return idUsuario > 0 && senhaCriptografada != null
                && !senhaCriptografada.isBlank()
                && usuarioDAO.atualizarSenha(idUsuario, senhaCriptografada);
    }

    public boolean atualizarAtivo(int idUsuario, boolean ativo) {
        return idUsuario > 0 && usuarioDAO.atualizarAtivo(idUsuario, ativo);
    }

    public Usuario buscarPorId(int idUsuario) {

        if (idUsuario <= 0) {
            return null;
        }

        return usuarioDAO.buscarPorId(idUsuario);
    }

    public Usuario buscarPorEmail(String email) {

        if (email == null || email.trim().isEmpty()) {
            return null;
        }

        return usuarioDAO.buscarPorEmail(email.trim());
    }

    public List<Usuario> listar() {
        return usuarioDAO.listar();
    }

    // QUANTIDADE DE USUÁRIOS
    public int quantidadeUsuarios() {
        return usuarioDAO.quantidadeUsuarios();
    }

}
