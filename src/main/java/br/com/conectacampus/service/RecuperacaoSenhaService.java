package br.com.conectacampus.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import org.mindrot.jbcrypt.BCrypt;

import br.com.conectacampus.dao.RecuperacaoSenhaDAO;
import br.com.conectacampus.model.Usuario;

public class RecuperacaoSenhaService {

    private final RecuperacaoSenhaDAO dao = new RecuperacaoSenhaDAO();
    private final EmailService emailService = new EmailService();

    public boolean solicitar(Usuario usuario, String linkBase) {
        if (usuario == null || !usuario.isAtivo() || linkBase == null || linkBase.isBlank()) return false;
        String token = gerarToken();
        String tokenHash = hash(token);
        if (!dao.criarToken(usuario.getIdUsuario(), tokenHash)) return false;
        boolean enviado = emailService.enviarRecuperacaoSenha(usuario.getEmail(), usuario.getNome(), linkBase + "?token=" + token);
        if (!enviado) dao.invalidarToken(tokenHash);
        return enviado;
    }

    public boolean redefinir(String token, String novaSenha) {
        return token != null && !token.isBlank() && novaSenha != null && novaSenha.length() >= 6
                && dao.redefinirSenha(hash(token), BCrypt.hashpw(novaSenha, BCrypt.gensalt(12)));
    }

    private String gerarToken() {
        byte[] bytes = new byte[32];
        new SecureRandom().nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    private String hash(String valor) {
        try {
            byte[] bytes = MessageDigest.getInstance("SHA-256").digest(valor.getBytes(StandardCharsets.UTF_8));
            StringBuilder resultado = new StringBuilder(64);
            for (byte item : bytes) resultado.append(String.format("%02x", item));
            return resultado.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 não disponível.", e);
        }
    }
}
