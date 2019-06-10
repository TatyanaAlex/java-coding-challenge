<%@ page import="app.entities.LanguagePair" %>
<%@ page import="java.util.List" %>
<%@ page import="app.entities.TranslationItem" %><%--
  Created by IntelliJ IDEA.
  User: tatyanafukova
  Date: 05.06.2019
  Time: 21:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Translator</title>
    <style>
      table, th, td {
        border: 1px solid black;
      }
    </style>
  </head>
  <body>
  <h1>Hi, I am your translator</h1>
  <h2>Put the text to translate in the field below</h2>
  <div>
    <form method="post" action="/translate">
      <input type="text" name="textToTranslate" id="textToTranslate">
      <input type="submit" name="buttonTranslate" id="buttonTranslate" value="Translate">
      <select name="languageSelect" id="languageSelect">
        <option value="">--Please choose a language--</option>
        <%
          List<LanguagePair> languagePairs = (List<LanguagePair>) request.getAttribute("languagePairs");

          if (languagePairs != null && !languagePairs.isEmpty()) {
            for (LanguagePair s : languagePairs) {
                String optionValue = s.getSourceLanguage().getShortname() + "-" + s.getTargetLanguage().getShortname();
                String optionText = s.getSourceLanguage().getName() + " - " + s.getTargetLanguage().getName();
              out.println("<option value=\""+ optionValue +"\">" + optionText + "</option>");
            }
          }
        %>
      </select>
    </form>
  </div>
  <div>
    <table style="width:100%">
      <tr>
        <th>From Language</th>
        <th>Original Text</th>
        <th>To Language</th>
        <th>Translated Text</th>
        <th>Status </th>
      </tr>
      <%
        List<TranslationItem> translationList = (List<TranslationItem>) request.getAttribute("translationList");

        if (translationList != null && !translationList.isEmpty()) {
          for (TranslationItem item : translationList) {
            out.println(new StringBuilder("<tr>")
                    .append("<td>").append(item.getFromLanguage()).append("</td>")
                    .append("<td>").append(item.getOriginalText()).append("</td>")
                    .append("<td>").append(item.getToLanguage()).append("</td>")
                    .append("<td>").append(item.getTranslatedText()).append("</td>")
                    .append("<td>").append(item.getStatus()).append("</td>")
            .append("</tr>")
            );
          }
        }
      %>
    </table>
  </div>
  </body>
</html>
