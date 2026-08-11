package br.com.conectacampus.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import jakarta.servlet.ServletContext;

public final class UploadStorage {

    private static final String VARIAVEL_DIRETORIO = "UPLOAD_DIR";

    private UploadStorage() {
    }

    public static Path raiz(ServletContext contexto) throws IOException {
        String diretorio = System.getenv(VARIAVEL_DIRETORIO);
        if (diretorio == null || diretorio.isBlank()) {
            diretorio = System.getProperty(VARIAVEL_DIRETORIO);
        }
        if (diretorio == null || diretorio.isBlank()) {
            diretorio = contexto.getRealPath("/uploads");
        }
        if (diretorio == null || diretorio.isBlank()) {
            throw new IOException("Diretorio de uploads nao configurado.");
        }

        Path raiz = Path.of(diretorio).toAbsolutePath().normalize();
        Files.createDirectories(raiz);
        return raiz;
    }

    public static Path pasta(ServletContext contexto, String nome) throws IOException {
        Path raiz = raiz(contexto);
        Path pasta = raiz.resolve(nome).normalize();
        if (!pasta.startsWith(raiz)) {
            throw new IOException("Subdiretorio de upload invalido.");
        }
        Files.createDirectories(pasta);
        return pasta;
    }
}
