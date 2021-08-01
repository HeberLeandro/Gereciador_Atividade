package br.edu.ifpe.tads.pdm.gerenciador.model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.List;

@IgnoreExtraProperties
public class Queue {

    private String name;
    private String description;
    private User owner;
    private List<Task> tasks;

    public Queue() {
    }

    public Queue(String name, String description, User owner, List<Task> tasks) {
        this.name = name;
        this.description = description;
        this.owner = owner;
        this.tasks = tasks;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
