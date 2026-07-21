package br.com.conectacampus.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexaoFactory {

    private static final String URL =
            "jdbc:mysql://localhost/database"
            + "?useSSL=false"
            + "&allowPublicKeyRetrieval=true"
            + "&serverTimezone=America/Sao_Paulo"
            + "&characterEncoding=UTF-8";

    private static final String USUARIO = "usuario";
    private static final String SENHA = "senha";

    public static Connection getConnection() {

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conexao = DriverManager.getConnection(
                    URL,
                    USUARIO,
                    SENHA);

            return conexao;

        } catch (Exception e) {

            e.printStackTrace();

            return null;

        }

    }

}
