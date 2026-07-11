package br.com.conectacampus.service;

import java.util.List;

import br.com.conectacampus.dao.FeedbackDAO;
import br.com.conectacampus.model.Feedback;

public class FeedbackService {

    private FeedbackDAO feedbackDAO;

    public FeedbackService() {

        feedbackDAO = new FeedbackDAO();

    }

    // ==========================
    // CADASTRAR
    // ==========================

    public boolean cadastrar(Feedback feedback) {

        if (feedback == null)
            return false;

        if (feedback.getTipo() == null || feedback.getTipo().isBlank())
            return false;

        if (feedback.getMensagem() == null || feedback.getMensagem().isBlank())
            return false;

        return feedbackDAO.inserir(feedback);

    }

    // ==========================
    // ATUALIZAR
    // ==========================

    public boolean atualizar(Feedback feedback) {

        if (feedback == null)
            return false;

        if (feedback.getIdFeedback() <= 0)
            return false;

        return feedbackDAO.atualizar(feedback);

    }

    // ==========================
    // EXCLUIR
    // ==========================

    public boolean excluir(int idFeedback) {

        if (idFeedback <= 0)
            return false;

        return feedbackDAO.excluir(idFeedback);

    }

    // ==========================
    // BUSCAR POR ID
    // ==========================

    public Feedback buscarPorId(int idFeedback) {

        if (idFeedback <= 0)
            return null;

        return feedbackDAO.buscarPorId(idFeedback);

    }

    // ==========================
    // LISTAR
    // ==========================

    public List<Feedback> listar() {

        return feedbackDAO.listar();

    }

}