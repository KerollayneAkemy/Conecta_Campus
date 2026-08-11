package br.com.conectacampus.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import br.com.conectacampus.util.UploadStorage;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/uploads/*")
public class UploadServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String caminho = request.getPathInfo();
        if (caminho == null || caminho.isBlank() || "/".equals(caminho)) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        Path raiz = UploadStorage.raiz(getServletContext());
        Path arquivo = raiz.resolve(caminho.substring(1)).normalize();
        if (!arquivo.startsWith(raiz) || !Files.isRegularFile(arquivo)) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String tipo = getServletContext().getMimeType(arquivo.getFileName().toString());
        response.setContentType(tipo == null ? "application/octet-stream" : tipo);
        response.setContentLengthLong(Files.size(arquivo));
        response.setHeader("Cache-Control", "public, max-age=86400");
        response.setHeader("X-Content-Type-Options", "nosniff");
        Files.copy(arquivo, response.getOutputStream());
    }
}
