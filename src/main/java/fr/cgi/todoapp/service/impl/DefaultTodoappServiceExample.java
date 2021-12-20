package fr.cgi.todoapp.service.impl;

import fr.cgi.todoapp.service.TodoappServiceExample;
import fr.wseduc.webutils.Either;
import io.vertx.core.*;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.entcore.common.sql.Sql;

public class DefaultTodoappServiceExample implements TodoappServiceExample {

    private final Sql sql;

    public DefaultTodoappServiceExample(Sql sql) {
       this.sql = sql;
    }

    @Override
    public void testGetStuff(String value, Number numberObject, Handler<Either<String, JsonArray>> handler) {
        JsonArray myJsonArray = new JsonArray();
        for (int i = 0; i < numberObject.intValue(); i++) {
            JsonObject myNewObject = new JsonObject().put("name", value);
            myJsonArray.add(myNewObject);
        }
        handler.handle(new Either.Right<>(myJsonArray));
        // if you want to send an error, use :
        // handler.handle(new Either.Left<>("an error has occurred"));
    }

    @Override
    public void testGetStuffAsync(String value, Number numberObject, Handler<AsyncResult<JsonArray>> handler) {
        JsonArray myJsonArray = new JsonArray();
        for (int i = 0; i < numberObject.intValue(); i++) {
            JsonObject myNewObject = new JsonObject().put("name", value);
            myJsonArray.add(myNewObject);
        }
        handler.handle(Future.succeededFuture(myJsonArray));
        // if you want to send an error, use :
        // handler.handle(Future.failedFuture("an error has occurred"));
    }

    @Override
    public Future<JsonArray> testGetStuff(String value, Number numberObject) {
        Promise<JsonArray> promise = Promise.promise();
        JsonArray myJsonArray = new JsonArray();
        for (int i = 0; i < numberObject.intValue(); i++) {
            JsonObject myNewObject = new JsonObject().put("name", value);
            myJsonArray.add(myNewObject);
        }
        promise.complete(myJsonArray);
        return promise.future();
        // if you want to send an error, use :
        // promise.fail("an error has occurred");
    }

    @Override
    public void consumeMethodExample(String value, Number numberObject, Handler<Either<String, JsonArray>> handler) {
        /* testGetStuff(value, numberObject, new Handler<Either<String, JsonArray>>() {
            @Override
            public void handle(Either<String, JsonArray> event) {
                if (event.isLeft()) {
                    handler.handle(new Either.Left<>(event.left().getValue()));
                } else {
                    handler.handle(new Either.Right<>(event.right().getValue()));
                }
            }
        }); old way to do it but since java 8 we use lambda */

        // we call its method (notice the 3rd parameter, it becomes the handler callback event when you can interact after its method
        testGetStuff(value, numberObject, event -> {
            // isLeft is the Either method that check if it has failed (event.isRight() also works)
            if (event.isLeft()) {
                // we send an error handler
                handler.handle(new Either.Left<>(event.left().getValue()));
            } else {
                // we send a success handler
                handler.handle(new Either.Right<>(event.right().getValue()));
            }
        });
    }

    @Override
    public void consumeMethodExampleAsync(String value, Number numberObject, Handler<AsyncResult<JsonArray>> handler) {
        /* testGetStuffAsync(value, numberObject, new Handler<AsyncResult<JsonArray>>() {
            @Override
            public void handle(AsyncResult<JsonArray> event) {
                if (event.failed()) {
                    handler.handle(Future.succeededFuture(event.result()));
                } else {
                    handler.handle(Future.failedFuture(event.cause().getMessage()));
                }
            }
        }); old way to do it but since java 8 we use lambda */

        // we call its method (notice the 3rd parameter, it becomes the handler callback event when you can interact after its method
        // this time this is AsyncResult event
        testGetStuffAsync(value, numberObject, event -> {
            // isLeft is the Either method that check if it has failed (event.isRight() also works)
            if (event.failed()) {
                handler.handle(Future.failedFuture(event.cause().getMessage()));
            } else {
                handler.handle(Future.succeededFuture(event.result()));
            }
        });
    }

    @Override
    public Future<JsonArray> consumeMethodExampleAsync(String value, Number numberObject) {
        Promise<JsonArray> promise = Promise.promise();
        /*testGetStuff(value, numberObject)
                .onSuccess(res -> {
                    // insert code
                    promise.complete(res);
                })
                .onFailure(error -> {
                    // insert code
                    promise.fail(error.getMessage());
                });
        */ // first way to interact

        // as we call this method, there's some native method inside this future
        // second way to interact (use the first if you need to do some more instruction
        testGetStuff(value, numberObject)
                .onSuccess(promise::complete)
                .onFailure(promise::fail);
        return promise.future();
    }

    @Override
    public void consumeMultipleMethodExample(String value, Number numberObject, Handler<Either<String, JsonArray>> handler) {
        JsonArray mergedArray = new JsonArray();
        // first way to do multiple call synchronously

        // first call
        testGetStuff(value, numberObject, event -> {
            if (event.isLeft()) {
                handler.handle(new Either.Left<>(event.left().getValue()));
            } else {
                // fetching its first JsonArray result that we store in mergedArray
                mergedArray.addAll(event.right().getValue());
                // second call
                // We can use an another method (Async or Future as second call) as long as we respect the handler's rule
                testGetStuff(value, numberObject, anotherEvent -> {
                    if (anotherEvent.isLeft()) {
                        handler.handle(new Either.Left<>(anotherEvent.left().getValue()));
                    } else {
                        mergedArray.addAll(anotherEvent.right().getValue());
                        handler.handle(new Either.Right<>(mergedArray));
                    }
                });
            }
        });
    }

    @Override
    public void consumeMultipleMethodExampleSync(String value, Number numberObject, Handler<AsyncResult<JsonArray>> handler) {
        JsonArray mergedArray = new JsonArray();
        // second way to do multiple call synchronously (using AsyncResult)

        // first call
        testGetStuffAsync(value, numberObject, event -> {
            if (event.failed()) {
                handler.handle(Future.failedFuture(event.cause().getMessage()));
            } else {
                // fetching its first JsonArray result that we store in mergedArray
                mergedArray.addAll(event.result());
                // second call
                // We can use an another method (Either or Future as second call) as long as we respect the handler's rule
                testGetStuffAsync(value, numberObject, anotherEvent -> {
                    if (anotherEvent.failed()) {
                        handler.handle(Future.failedFuture(anotherEvent.cause().getMessage()));
                    } else {
                        mergedArray.addAll(anotherEvent.result());
                        handler.handle(Future.succeededFuture(mergedArray));
                    }
                });
            }
        });
    }

    @Override
    public Future<JsonArray> consumeMultipleMethodExampleAsync(String value, Number numberObject) {
        Promise<JsonArray> promise = Promise.promise();
        JsonArray mergedArray = new JsonArray();

        testGetStuff(value, numberObject)
                .compose(firstRes -> {
                    mergedArray.addAll(firstRes);
                    return testGetStuff(value, numberObject);
                })
                .onSuccess(finalRes -> {
                    mergedArray.addAll(finalRes);
                    promise.complete(mergedArray);
                })
                .onFailure(err -> promise.fail(err.getMessage()));

        return promise.future();
    }


    @Override
    public void consumeMultipleMethodExampleAsync(String value, Number numberObject, Handler<AsyncResult<JsonArray>> handler) {

        JsonArray mergedArray = new JsonArray();

        // these calls are called asynchronously
        Future<JsonArray> firstCallFuture = testGetStuff(value, numberObject);
        Future<JsonArray> secondCallFuture = testGetStuff(value, numberObject);

        // callback at the end
        /* CompositeFuture.all(firstCallFuture, secondCallFuture).setHandler(event -> {
            if (event.failed()) {
                handler.handle(Future.failedFuture(event.cause().getMessage()));
            } else {
                mergedArray.addAll(firstCallFuture.result());
                mergedArray.addAll(secondCallFuture.result());
                handler.handle(Future.succeededFuture(mergedArray));
            }
        }); Old way but setHandler is deprecated */

        CompositeFuture.all(firstCallFuture, secondCallFuture)
                .onSuccess(res -> {
                    mergedArray.addAll(firstCallFuture.result());
                    mergedArray.addAll(secondCallFuture.result());
                    handler.handle(Future.succeededFuture(mergedArray));
                })
                .onFailure(err -> handler.handle(Future.failedFuture(err.getMessage())));
    }
}
