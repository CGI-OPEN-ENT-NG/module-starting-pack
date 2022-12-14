package fr.cgi.todoapp.utils;

import fr.wseduc.webutils.Either;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;

public class ExempleServiceForMock {
    public String service(String s) {
        //We don't want to test the service
        assert false;
        return "";
    }

    public void getSqlData(String string, Handler<Either<String, JsonObject>> handler) {
        //We don't want to test the service
        assert false;
    }

    public static void getSqlDataStatic(String string, Handler<Either<String, JsonObject>> handler) {
        //We don't want to test the service
        assert false;
    }
}
