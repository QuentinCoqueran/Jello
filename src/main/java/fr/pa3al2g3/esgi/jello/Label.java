package fr.pa3al2g3.esgi.jello;

public enum Label {
    DEFAULT("default", "#FFFFFF");

    private final String title;
    private final String color;

    private Label(String title, String color){
        this.title = title;
        this.color = color;
    }

    public String getTitle() {
        return title;
    }

    public String getColor() {
        return color;
    }
}
