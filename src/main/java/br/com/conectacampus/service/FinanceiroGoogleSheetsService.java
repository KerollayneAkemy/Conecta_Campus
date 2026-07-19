package br.com.conectacampus.service;

import java.io.BufferedReader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import br.com.conectacampus.model.MovimentoFinanceiro;

public class FinanceiroGoogleSheetsService {

    public List<MovimentoFinanceiro> listar(String competencia) {

        List<MovimentoFinanceiro> movimentos = new ArrayList<>();

        try {

            String url = System.getenv("GOOGLE_SHEETS_FINANCEIRO_URL");

            System.out.println("URL: " + url);

            if (url == null || url.isBlank()) {
                System.out.println("Variável GOOGLE_SHEETS_FINANCEIRO_URL não encontrada.");
                return movimentos;
            }

            HttpClient client = HttpClient.newBuilder()
                    .followRedirects(Redirect.ALWAYS)
                    .build();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("User-Agent", "Mozilla/5.0")
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(
                    request,
                    HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));

            System.out.println("Status HTTP: " + response.statusCode());

            if (response.statusCode() != 200) {
                System.out.println(response.body());
                return movimentos;
            }

            BufferedReader reader = new BufferedReader(new StringReader(response.body()));

            // Ignora o cabeçalho
            reader.readLine();

            String linha;

            while ((linha = reader.readLine()) != null) {

                if (linha.trim().isEmpty())
                    continue;

                String[] campos = lerCSV(linha);

                if (campos.length < 6)
                    continue;

                if (competencia != null && !competencia.isBlank()) {
                    if (!competencia.equals(campos[0].trim()))
                        continue;
                }

                MovimentoFinanceiro mov = new MovimentoFinanceiro();

                mov.setCompetencia(limpar(campos[0]));
                mov.setData(limpar(campos[1]));
                mov.setTipo(limpar(campos[2]));
                mov.setCategoria(limpar(campos[3]));
                mov.setDescricao(limpar(campos[4]));

                String valor = limpar(campos[5]);

                valor = valor.replace("R$", "");
                valor = valor.replace(",", ".");

                mov.setValor(new BigDecimal(valor));

                movimentos.add(mov);
            }

            System.out.println("Total de movimentos: " + movimentos.size());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return movimentos;
    }

    private String limpar(String texto) {
        return texto.replace("\"", "").trim();
    }

    private String[] lerCSV(String linha) {

        List<String> campos = new ArrayList<>();

        StringBuilder atual = new StringBuilder();

        boolean aspas = false;

        for (int i = 0; i < linha.length(); i++) {

            char c = linha.charAt(i);

            if (c == '"') {
                aspas = !aspas;
            } else if (c == ',' && !aspas) {
                campos.add(atual.toString());
                atual.setLength(0);
            } else {
                atual.append(c);
            }
        }

        campos.add(atual.toString());

        return campos.toArray(new String[0]);
    }

}