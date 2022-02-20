package fr.cgi.todoapp.controller;

import fr.cgi.todoapp.model.ExerciceTodo;
import fr.cgi.todoapp.service.ExerciceTodoService;
import fr.cgi.todoapp.service.ServiceFactory;
import fr.cgi.todoapp.service.TodoappServiceExample;
import fr.cgi.todoapp.service.impl.DefaultExerciceTodoService;
import fr.wseduc.rs.*;
import fr.wseduc.security.ActionType;
import fr.wseduc.security.SecuredAction;
import fr.wseduc.webutils.request.RequestUtils;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.json.JsonObject;
import org.entcore.common.controller.ControllerHelper;

import java.util.List;
import java.util.stream.Collectors;

public class ExerciceTodoController extends ControllerHelper {

    private ExerciceTodoService exerciceTodoService;

    public ExerciceTodoController() {
        exerciceTodoService = new DefaultExerciceTodoService();
    }

    @Get("/todolists")
    @ApiDoc("List all todolists")
    @SecuredAction(value = "", type = ActionType.AUTHENTICATED)
    public void getTodolists(HttpServerRequest request) {
        List<Integer> ids = request.params().getAll("id")
                .stream()
                .map(Integer::valueOf)
                .collect(Collectors.toList());

        this.exerciceTodoService.get(ids)
                .onSuccess(res -> {
                    JsonObject object = new JsonObject()
                            .put("result", res)
                            .put("status", "ok");
                    renderJson(request, object);
                })
                .onFailure(err -> {
                    JsonObject object = new JsonObject()
                            .put("reason", err.getMessage())
                            .put("status", "ko");
                    renderError(request, object);
                });
    }

    @Post("/todolists")
    @ApiDoc("Create a todolist")
    @SecuredAction(value = "", type = ActionType.AUTHENTICATED)
    public void createTodolist(HttpServerRequest request) {
        RequestUtils.bodyToJson(request, body -> {
            ExerciceTodo exerciceTodo = new ExerciceTodo(body);
            this.exerciceTodoService.create(exerciceTodo)
                    .onSuccess(res -> {
                        JsonObject object = new JsonObject()
                                .put("result", res)
                                .put("status", "ok");
                        renderJson(request, object);
                    })
                    .onFailure(err -> {
                        JsonObject object = new JsonObject()
                                .put("reason", err.getMessage())
                                .put("status", "ko");
                        renderError(request, object);
                    });
        });
    }

    @Put("/todolists/:id")
    @ApiDoc("Update given todolist")
    @SecuredAction(value = "", type = ActionType.AUTHENTICATED)
    public void updateTodolist(HttpServerRequest request) {
        Integer id = Integer.parseInt(request.params().get("id"));
        RequestUtils.bodyToJson(request, body -> {
            ExerciceTodo exerciceTodo = new ExerciceTodo(body);
            this.exerciceTodoService.update(id, exerciceTodo)
                    .onSuccess(res -> {
                        JsonObject object = new JsonObject()
                                .put("result", res)
                                .put("status", "ok");
                        renderJson(request, object);
                    })
                    .onFailure(err -> {
                        JsonObject object = new JsonObject()
                                .put("reason", err.getMessage())
                                .put("status", "ko");
                        renderError(request, object);
                    });
        });
    }

    @Delete("/todolists/:id")
    @ApiDoc("Delete given todolist")
    @SecuredAction(value = "", type = ActionType.AUTHENTICATED)
    public void deleteTodolist(HttpServerRequest request) {
        Integer id = Integer.parseInt(request.params().get("id"));
        this.exerciceTodoService.delete(id)
                .onSuccess(res -> {
                    JsonObject object = new JsonObject()
                            .put("result", res)
                            .put("status", "ok");
                    renderJson(request, object);
                })
                .onFailure(err -> {
                    JsonObject object = new JsonObject()
                            .put("reason", err.getMessage())
                            .put("status", "ko");
                    renderError(request, object);
               });
    }
}
