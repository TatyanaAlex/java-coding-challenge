package app.servlets;

import app.controllers.TranslationDataController;
import app.entities.LanguagePair;
import app.model.TranslationList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class InitAppServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TranslationDataController tdc = new TranslationDataController();
        List<LanguagePair> languagePairs = tdc.getSupportedLanguages();
        req.setAttribute("languagePairs", languagePairs);

        TranslationList translationList = TranslationList.getInstance();
        req.setAttribute("translationList", translationList.getAll());

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("index.jsp");
        requestDispatcher.forward(req, resp);
    }
}
