package app.controllers;

import app.entities.Language;
import app.entities.LanguagePair;
import app.entities.TranslationItem;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TranslationDataController {

    private final String username = "fullstack-challenge";

    private final String apikey = "9db71b322d43a6ac0f681784ebdcc6409bb83359";

    private final String apiUrl = "https://sandbox.unbabel.com/tapi/v2/translation/";

    private RestApiController api = new RestApiController(username, apikey, apiUrl);

   /* public static void main(String[] args) {
        TranslationDataController tdc = new TranslationDataController();
        System.out.println(tdc.getTranslation("en", "pt", "Hello, Darling!").getTranslatedText());

    }*/

    public TranslationItem getTranslation(String fromLanguage, String toLanguage, String textToTranslate) {

        JSONObject params = new JSONObject();
        params.put("text", textToTranslate);
        params.put("source_language", fromLanguage); //"en"
        params.put("target_language", toLanguage); //pt
        params.put("text_format", "text");

        String jsonData = params.toString();

        JSONObject initTranslateResponse = api.doPostRequest(jsonData);

        JSONObject completed = null;
        if (initTranslateResponse != null && "new".equalsIgnoreCase(initTranslateResponse.get("status").toString())) {
            completed = getTranslationCompleted(initTranslateResponse.get("uid").toString());
        }

        TranslationItem translationItem = null;
        if (completed != null) {
            translationItem = parseJson(completed);
        }

        return translationItem;
    }

    public List<LanguagePair> getSupportedLanguages() {
        //Sandbox env does not allow to retrieve the language pairs
        List<LanguagePair> pairList = new ArrayList<LanguagePair>();
        pairList.add(new LanguagePair(new Language("English", "en"),
                new Language("Portuguese", "pt")));
        pairList.add(new LanguagePair(new Language("English", "en"),
                new Language("Japanese", "ja")));
        pairList.add(new LanguagePair(new Language("English", "en"),
                new Language("Russian", "ru")));

        return pairList;
    }

    private TranslationItem parseJson(JSONObject completed) {
        TranslationItem translationItem = new TranslationItem();
        translationItem.setTranslatedText(completed.get("translatedText").toString());
        translationItem.setOriginalText(completed.get("text").toString());
        translationItem.setStatus(completed.get("status").toString());
        translationItem.setToLanguage(completed.get("target_language").toString());
        translationItem.setFromLanguage(completed.get("source_language").toString());

        return translationItem;
    }

    private JSONObject getTranslationCompleted(String uid) {
        JSONObject response = null;
        for (int i = 0; i < 20; i++) {
            response = api.doGetRequest(uid);
            if (response != null && response.get("status") != null &&
                    "completed".equalsIgnoreCase(response.get("status").toString())) {
                break;
            } else {
                response = null;
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return response;
    }

}
