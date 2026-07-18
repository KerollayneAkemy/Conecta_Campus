package br.com.conectacampus.service;

import java.util.List;

import br.com.conectacampus.model.Feedback;

public class FeedbackViewService {

    private FeedbackService feedbackService;

    public FeedbackViewService() {
        this.feedbackService = new FeedbackService();
    }

    public Feedback buscarPorId(int id) {
        return feedbackService.buscarPorId(id);
    }

    public List<Feedback> listar() {
        return feedbackService.listar();
    }

    public List<Feedback> listarPorTipo(String tipo) {
        return feedbackService.listar()
                .stream()
                .filter(f -> tipo.equalsIgnoreCase(f.getTipo()))
                .toList();
    }

    public boolean excluir(int id) {
        return feedbackService.excluir(id);
    }

    public int quantidadeTotal() {
        return feedbackService.listar().size();
    }

    public int quantidadePorTipo(String tipo) {
        return (int) feedbackService.listar()
                .stream()
                .filter(f -> tipo.equalsIgnoreCase(f.getTipo()))
                .count();
    }

}