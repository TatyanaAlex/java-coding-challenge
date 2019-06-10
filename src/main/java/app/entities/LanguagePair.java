package app.entities;

public class LanguagePair {

    private Language sourceLanguage;
    private Language targetLanguage;

    public LanguagePair(Language sourceLanguage, Language targetLanguage) {
        this.sourceLanguage = sourceLanguage;
        this.targetLanguage = targetLanguage;
    }

    public Language getSourceLanguage() {
        return sourceLanguage;
    }

    public Language getTargetLanguage() {
        return targetLanguage;
    }
}
