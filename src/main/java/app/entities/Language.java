package app.entities;

public class Language {

    private String name;
    private String shortname;

    public Language(String name, String shortname) {
        this.name = name;
        this.shortname = shortname;
    }

    public String getName() {
        return name;
    }

    public String getShortname() {
        return shortname;
    }
}
