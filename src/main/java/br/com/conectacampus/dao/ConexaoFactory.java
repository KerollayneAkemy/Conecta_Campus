package br.com.conectacampus.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexaoFactory {

    private static final String URL = configuracao(
            "DB_URL",
            "jdbc:mysql://localhost:3306/conectacampus"
                    + "?useSSL=false"
                    + "&allowPublicKeyRetrieval=true"
                    + "&serverTimezone=America%2FManaus"
                    + "&characterEncoding=UTF-8");

    private static final String USUARIO = configuracao("DB_USER", "root");
    private static final String SENHA = configuracao("DB_PASSWORD", "root");

    public static Connection getConnection() {

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            return DriverManager.getConnection(URL, USUARIO, SENHA);

        } catch (Exception e) {
            throw new IllegalStateException("Nao foi possivel conectar ao banco de dados.", e);

        }

    }

    private static String configuracao(String nome, String padrao) {
        String valor = System.getenv(nome);
        if (valor == null || valor.isBlank()) {
            valor = System.getProperty(nome);
        }
        return valor == null || valor.isBlank() ? padrao : valor.trim();
    }

}
