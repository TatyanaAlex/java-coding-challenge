package app.model;

import app.entities.TranslationItem;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TranslationList {

    private static TranslationList instance = new TranslationList();

    private Map<String, TranslationItem> translations;

    private TranslationList() {
        translations = new HashMap<String, TranslationItem>();
    }

    public static TranslationList getInstance() {
        return instance;
    }

    public void add(TranslationItem translationItem) {
        this.translations.put(translationItem.getOriginalText(), translationItem);
    }

    public TranslationItem getTranslation(String key) {
        return this.translations.get(key);
    }

    public List getAll() {
        return Arrays.asList(this.translations.values().toArray());
    }
}
