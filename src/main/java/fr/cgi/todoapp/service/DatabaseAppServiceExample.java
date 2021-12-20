package fr.cgi.todoapp.service;

import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import fr.wseduc.webutils.Either;

public interface DatabaseAppServiceExample {

    void testGetStuff(Number numberObject, Handler<Either<String, JsonArray>> handler);

    void testGetStuffWithSql(Number numberObject, Handler<Either<String, JsonArray>> handler);

    void getChildrenInformation(String userId, Handler<Either<String, JsonArray>> handler);
}
