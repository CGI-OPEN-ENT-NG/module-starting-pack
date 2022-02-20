package fr.cgi.todoapp.service;

import fr.cgi.todoapp.model.ExerciceTodo;
import io.vertx.core.Future;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.List;

public interface ExerciceTodoService {

    Future<JsonArray> get(List<Integer> ids);

    Future<JsonObject> create(ExerciceTodo exerciceTodo);

    Future<JsonObject> update(Integer id, ExerciceTodo exerciceTodo);

    Future<JsonObject> delete(Integer id);
}
