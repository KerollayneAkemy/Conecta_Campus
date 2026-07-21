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

/** Consulta lançamentos financeiros publicados em uma planilha CSV. */
public class FinanceiroGoogleSheetsService {

    private static final String VARIAVEL_URL = "GOOGLE_SHEETS_FINANCEIRO_URL";

    public List<MovimentoFinanceiro> listar(String competencia) {
        
    	List<MovimentoFinanceiro> movimentos = new ArrayList<>();
        String url = System.getenv(VARIAVEL_URL);
        
        if (url == null || url.isBlank()) return movimentos;

        try {
            HttpClient cliente = HttpClient.newBuilder().followRedirects(Redirect.ALWAYS).build();
            
            HttpRequest requisicao = HttpRequest.newBuilder().uri(URI.create(url)).header("User-Agent", "Conecta-Campus").GET().build();
            
            HttpResponse<String> resposta = cliente.send(requisicao, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
            
            if (resposta.statusCode() != 200) return movimentos;

            try (BufferedReader leitor = new BufferedReader(new StringReader(resposta.body()))) {
            	
                leitor.readLine(); // Cabeçalho do CSV.
                String linha;
               
                while ((linha = leitor.readLine()) != null) {
                    MovimentoFinanceiro movimento = converterLinha(linha, competencia);
                    if (movimento != null) movimentos.add(movimento);
                }
            }
        } catch (Exception e) {
            // A planilha é uma integração externa: a tela exibe estado vazio se ela estiver indisponível.
        }
        return movimentos;
    }

    private MovimentoFinanceiro converterLinha(String linha, String competencia) {
       
    	if (linha == null || linha.isBlank())
    		return null;
       
    	String[] campos = lerCsv(linha);
       
        if (campos.length < 6 || (competencia != null && !competencia.isBlank() && !competencia.equals(campos[0].trim()))) return null;

        try {
            MovimentoFinanceiro movimento = new MovimentoFinanceiro();
            movimento.setCompetencia(limpar(campos[0]));
            movimento.setData(limpar(campos[1]));
            movimento.setTipo(limpar(campos[2]));
            movimento.setCategoria(limpar(campos[3]));
            movimento.setDescricao(limpar(campos[4]));
            movimento.setValor(new BigDecimal(limpar(campos[5]).replace("R$", "").replace(",", ".")));
           
            return movimento;
        
        } catch (NumberFormatException e) {
           
        	return null;
        }
    }

    private String limpar(String texto) {
      
    	return texto.replace("\"", "").trim();
    }

    private String[] lerCsv(String linha) {
        List<String> campos = new ArrayList<>();
        StringBuilder atual = new StringBuilder();
        boolean entreAspas = false;
        
        for (int i = 0; i < linha.length(); i++) {
           
        	char caractere = linha.charAt(i);
            if (caractere == '"') entreAspas = !entreAspas;
            else if (caractere == ',' && !entreAspas) { campos.add(atual.toString()); atual.setLength(0); }
            else atual.append(caractere);
        }
        
        campos.add(atual.toString());
        
        return campos.toArray(new String[0]);
    }
}
