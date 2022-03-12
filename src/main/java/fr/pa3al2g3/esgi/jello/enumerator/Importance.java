package fr.pa3al2g3.esgi.jello.enumerator;

public enum Importance {
    LOW("Low"),
    MEDIUM("Medium"),
    HIGH("High");

    private final String title;

    private Importance(String title){
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

}
