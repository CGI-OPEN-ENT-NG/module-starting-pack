package fr.cgi.todoapp.service.impl;

import fr.cgi.todoapp.model.ExerciceTodo;
import fr.cgi.todoapp.service.ExerciceTodoService;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.entcore.common.sql.Sql;
import org.entcore.common.sql.SqlResult;

import java.util.List;

public class DefaultExerciceTodoService implements ExerciceTodoService {

    @Override
    public Future<JsonArray> get(List<Integer> ids) {
        Promise<JsonArray> promise = Promise.promise();

        JsonArray values = new JsonArray();
        String query = "SELECT * FROM module_starting_pack.todo_item ";

        if (ids != null && !ids.isEmpty()) {
            query += " WHERE id IN " + Sql.listPrepared(ids);
            values.addAll(new JsonArray(ids));
        }

        Sql.getInstance().prepared(query, values, SqlResult.validResultHandler(event -> {
            if (event.isLeft()) {
                promise.fail(event.left().getValue());
            } else {
               promise.complete(event.right().getValue());
            }
        }));

        return promise.future();
    }

    @Override
    public Future<JsonObject> create(ExerciceTodo exerciceTodo) {
        Promise<JsonObject> promise = Promise.promise();

        String query = "INSERT INTO module_starting_pack.todo_item " +
                "(is_done, label)" +
                " VALUES (?, ?) RETURNING id";
        JsonArray values = new JsonArray()
                .add(exerciceTodo.done())
                .add(exerciceTodo.name());
        Sql.getInstance().prepared(query, values, SqlResult.validUniqueResultHandler(event -> {
            if (event.isLeft()) {
                promise.fail(event.left().getValue());
            } else {
                promise.complete(event.right().getValue());
            }
        }));
        return promise.future();
    }

    @Override
    public Future<JsonObject> update(Integer id, ExerciceTodo exerciceTodo) {
        Promise<JsonObject> promise = Promise.promise();
        String query = "UPDATE module_starting_pack.todo_item " +
                "SET is_done = ?, label = ? WHERE id = ? RETURNING id";
        JsonArray values = new JsonArray()
                .add(exerciceTodo.done())
                .add(exerciceTodo.name())
                .add(id);
        Sql.getInstance().prepared(query, values, SqlResult.validUniqueResultHandler(event -> {
            if (event.isLeft()) {
                promise.fail(event.left().getValue());
            } else {
                promise.complete(event.right().getValue());
            }
        }));
        return promise.future();
    }

    @Override
    public Future<JsonObject> delete(Integer id) {
        Promise<JsonObject> promise = Promise.promise();
        String query = "DELETE FROM module_starting_pack.todo_item WHERE id = ? ";
        JsonArray values = new JsonArray().add(id);
        Sql.getInstance().prepared(query, values, SqlResult.validUniqueResultHandler(event -> {
            if (event.isLeft()) {
                promise.fail(event.left().getValue());
            } else {
                promise.complete(event.right().getValue());
            }
        }));
        return promise.future();
    }
}
