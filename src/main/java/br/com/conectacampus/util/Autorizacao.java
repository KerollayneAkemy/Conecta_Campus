package br.com.conectacampus.util;

import br.com.conectacampus.model.Usuario;

public final class Autorizacao {

    public static final String ADMINISTRADOR = "ADMINISTRADOR";
    public static final String EQUIPE = "EQUIPE_INSTITUCIONAL";
    public static final String ALUNO = "ALUNO";

    private Autorizacao() {
    }

    public static boolean ehAdministrador(Usuario usuario) {
        return temPerfil(usuario, ADMINISTRADOR);
    }

    public static boolean ehEquipe(Usuario usuario) {
        return temPerfil(usuario, EQUIPE);
    }

    public static boolean ehAluno(Usuario usuario) {
        return temPerfil(usuario, ALUNO);
    }

    public static boolean podePublicarInstitucional(Usuario usuario) {
        return ehAdministrador(usuario) || ehEquipe(usuario);
    }

    public static boolean podeGerenciarComunicados(Usuario usuario) {
        return podePublicarInstitucional(usuario);
    }

    public static boolean podeGerenciarForum(Usuario usuario) {
        return podePublicarInstitucional(usuario);
    }

    public static boolean podeVisualizarFeedbacks(Usuario usuario) {
        return ehAdministrador(usuario) || ehEquipe(usuario);
    }

    public static boolean podeVisualizarDashboard(Usuario usuario) {
        return ehAdministrador(usuario) || ehEquipe(usuario) || ehAluno(usuario);
    }

    public static boolean podeEnviarFeedback(Usuario usuario) {
        return ehAluno(usuario);
    }

    public static boolean ehRepresentante(Usuario usuario) {
        return ehEquipe(usuario)
                && ("REITORIA".equalsIgnoreCase(usuario.getSetorInstitucional())
                || "GREMIO".equalsIgnoreCase(usuario.getSetorInstitucional()));
    }

    private static boolean temPerfil(Usuario usuario, String perfil) {
        return usuario != null && usuario.getPerfil() != null
                && perfil.equalsIgnoreCase(usuario.getPerfil().getNome());
    }
}
