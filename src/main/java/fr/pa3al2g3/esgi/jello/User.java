package fr.pa3al2g3.esgi.jello;

public class User {

    private String name;
    private String firstname;
    private Status status;

    public User(String name, String firstname, Status status){
        this.name = name;
        this.firstname = firstname;
        this.status = status;
    }

    public String getName() {
        return this.name;
    }

    public String getFirstname() {
        return this.firstname;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status){
        this.status = status;
    }
}
