package fr.cgi.todoapp.model;

import io.vertx.core.json.JsonObject;

public class ExerciceTodo {

    private String id;
    private String name;
    private final Boolean isDone;

    public ExerciceTodo(JsonObject exerciceTodo) {
        this.id = exerciceTodo.getString("id");
        this.name = exerciceTodo.getString("name");
        this.isDone = exerciceTodo.getBoolean("isDone", false);
    }

    public String id() {
        return id;
    }

    public String name() {
        return name;
    }

    public Boolean done() {
        return isDone;
    }

    public ExerciceTodo setName(String name) {
        this.name = name;
        return this;
    }

    public ExerciceTodo setId(String id) {
        this.id = id;
        return this;
    }
}
