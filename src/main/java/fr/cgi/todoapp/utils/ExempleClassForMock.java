package fr.cgi.todoapp.utils;

import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;

public class ExempleClassForMock {
    static private String staticPrivateField = "1";
    private ExempleServiceForMock exempleServiceForMock = new ExempleServiceForMock();

    static public String staticMethod() {
        return "1";
    }

    private String privateMethod() {
        return "1";
    }

    private static String staticPrivateMethod() {
        return "1";
    }

    public String getStaticPrivateField() {
        return staticPrivateField;
    }

    public int getStaticPrivateMethodInInteger() {
        return Integer.parseInt(staticPrivateMethod());
    }

    public String callService(JsonObject jsonObject) {
        assert jsonObject.getString("string1") != null;
        return exempleServiceForMock.service(jsonObject.getString("string1"));
    }

    //Get data from id
    public Future<String> methodHandler(String id) {
        Promise<String> promise = Promise.promise();

        //Call the Sql service to retrieve the data
        exempleServiceForMock.getSqlData(id, event -> {
            //We assume that the service is functional and that it necessarily returns a JsonObject with this structure {"data": String}
            //We want to test the processing done in the handler
            promise.complete(event.right().getValue().getString("data"));
        });

        return promise.future();
    }

    public Future<String> methodHandlerStatic(String id) {
        Promise<String> promise = Promise.promise();

        //Call the Sql service to retrieve the data
        ExempleServiceForMock.getSqlDataStatic(id, event -> {
            //We assume that the service is functional and that it necessarily returns a JsonObject with this structure {"data": String}
            //We want to test the processing done in the handler
            promise.complete(event.right().getValue().getString("data"));
        });

        return promise.future();
    }
}
