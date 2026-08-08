package br.com.conectacampus.service;

import java.util.List;

import br.com.conectacampus.dao.CargoDAO;
import br.com.conectacampus.model.Cargo;

public class CargoService {

    private CargoDAO cargoDAO;

    public CargoService() {
        this.cargoDAO = new CargoDAO();
    }

    public boolean cadastrar(Cargo cargo) {
        return cargoDAO.inserir(cargo);
    }

    public boolean atualizar(Cargo cargo) {
        return cargoDAO.atualizar(cargo);
    }

    public boolean excluir(int id) {
        return cargoDAO.excluir(id);
    }

    public Cargo buscarPorId(int id) {
        return cargoDAO.buscarPorId(id);
    }

    public List<Cargo> listar() {
        return cargoDAO.listar();
    }

}
