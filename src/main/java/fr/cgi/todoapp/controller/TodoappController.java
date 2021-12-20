package fr.cgi.todoapp.controller;

import fr.cgi.todoapp.Todoapp;
import fr.cgi.todoapp.service.ServiceFactory;
import fr.cgi.todoapp.service.TodoappServiceExample;
import fr.wseduc.rs.ApiDoc;
import fr.wseduc.rs.Get;
import fr.wseduc.rs.Post;
import fr.wseduc.security.SecuredAction;
import fr.wseduc.webutils.request.RequestUtils;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.json.JsonObject;
import org.entcore.common.controller.ControllerHelper;
import org.entcore.common.events.EventStore;
import org.entcore.common.events.EventStoreFactory;
import org.entcore.common.user.UserUtils;

public class TodoappController extends ControllerHelper {

    private final EventStore eventStore;
    private final TodoappServiceExample todoappService;

    public TodoappController(ServiceFactory serviceFactory) {
        this.todoappService = serviceFactory.todoappService();
        this.eventStore = EventStoreFactory.getFactory().getEventStore(Todoapp.class.getSimpleName());
    }

    @Get("")
    @ApiDoc("Render view")
    @SecuredAction("view")
    public void view(HttpServerRequest request) {
        // fetch user data (this syntax is synchronous, meaning that the 3rd parameter is the callback,
        // in other words, your handler.
        UserUtils.getUserInfos(eb, request, user -> {
            // renderView -> access to todoApp html (so the main view)
            // we can send an object that our html will be able to access
            JsonObject context = new JsonObject().put("myId", user.getUserId());
            renderView(request, context);
        });

        // Increment each time this endpoint is called -> stored somewhere in mongoDb
        // notice this instruction is done "at the same time" as the instruction above
        // (since this static method getUserInfos has its next instruction callback)
        eventStore.createAndStoreEvent("ACCESS", request);
    }

    @Get("/test")
    @ApiDoc("Render view")
    @SecuredAction("test")
    public void test(HttpServerRequest request) {
        // Please read services to understand how it is implemented
        this.todoappService.testGetStuff("item", 5)
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

    @Post("/test")
    @ApiDoc("Render view")
    @SecuredAction("createTest")
    public void createTest(HttpServerRequest request) {
        // introducing bodyToJson, notice the second parameter that will points out your body to check (validation etc...)
        RequestUtils.bodyToJson(request, pathPrefix + "createTest", body -> {
            String value = body.getString("value");
            Number numberObject = body.getNumber("numberObject");

            this.todoappService.testGetStuff(value, numberObject)
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
}
