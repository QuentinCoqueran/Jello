package fr.pa3al2g3.esgi.jello;

import javafx.scene.control.Label;

import java.util.ArrayList;

public class Card {

    private String title;
    private String description;
    private ArrayList<Label> labels;
    private String deadLine;
    private Importance importance;
    private User creator;
    private User assignee;
    private ArrayList<String> documents;
    private ArrayList<String> logs;

    public Card(String title, String description, String deadLine, Importance importance, User creator, User assignee){
        this.title = title;
        this.description = description;
        this.labels = new ArrayList<>();
        this.deadLine = deadLine;
        this.importance= importance;
        this.creator = creator;
        this.assignee = assignee;
        this.documents = new ArrayList<>();
        this.logs = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Label> getLabels() {
        return labels;
    }

    public String getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(String deadLine) {
        this.deadLine = deadLine;
    }

    public Importance getImportance() {
        return importance;
    }

    public void setImportance(Importance importance) {
        this.importance = importance;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public User getAssignee() {
        return assignee;
    }

    public void setAssignee(User assignee) {
        this.assignee = assignee;
    }

    public ArrayList<String> getDocuments() {
        return documents;
    }

    public ArrayList<String> getLogs() {
        return logs;
    }

    public void addLabel(Label label){
        this.labels.add(label);
    }

    public void addDocument(String document){
        this.documents.add(document);
    }

    public void addLog(String log){
        this.logs.add(log);
    }

}
