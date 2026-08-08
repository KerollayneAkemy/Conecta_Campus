package br.com.conectacampus.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.conectacampus.model.Perfil;

public class PerfilDAO {

    // Listar todos os perfis
    public List<Perfil> listar() {

        List<Perfil> lista = new ArrayList<>();

        String sql = "SELECT * FROM perfis ORDER BY nome";

        try (
            Connection conexao = ConexaoFactory.getConnection();
            PreparedStatement stmt = conexao.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()
        ) {

            while (rs.next()) {

                Perfil perfil = new Perfil();

                perfil.setIdPerfil(rs.getInt("id_perfil"));
                perfil.setNome(rs.getString("nome"));

                lista.add(perfil);

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        return lista;

    }

}
