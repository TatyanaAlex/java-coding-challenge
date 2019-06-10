package app.servlets;

import app.controllers.TranslationDataController;
import app.entities.LanguagePair;
import app.entities.TranslationItem;
import app.model.TranslationList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class TranslateServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //RequestDispatcher requestDispatcher = req.getRequestDispatcher("index.jsp");
        resp.sendRedirect("/init");
        //requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("index.jsp");
        String textToTranslate = req.getParameter("textToTranslate");
        String languageSelect = req.getParameter("languageSelect");
        String textFrom = "";
        String textTo = "";

        if (textToTranslate != null && languageSelect != null && languageSelect.length() > 4) {
            textTo = languageSelect.split("-")[1];
            textFrom = languageSelect.split("-")[0];
        } else {
            resp.sendRedirect("/init");
            return;
        }

        TranslationDataController tdc = new TranslationDataController();
        List<LanguagePair> languagePairs = tdc.getSupportedLanguages();
        req.setAttribute("languagePairs", languagePairs);

        TranslationList translationList = TranslationList.getInstance();
        TranslationItem translationItem = null;
        if (textToTranslate != null) {
            translationItem = translationList.getTranslation(textToTranslate.trim());
        }
        if (translationItem == null) {
            translationItem = tdc.getTranslation(textFrom, textTo, textToTranslate);
            if (translationItem != null) {
                translationList.add(translationItem);
            }
        }
        req.setAttribute("translationList", translationList.getAll());
        requestDispatcher.forward(req, resp);

    }

}
