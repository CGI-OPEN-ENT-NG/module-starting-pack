package fr.cgi.todoapp.service;

import fr.wseduc.webutils.Either;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;

public interface TodoappServiceExample {

    /**
     * Get stuff (FIRST way to do it sync/asynchronously)
     * @param value         insert this value as parameter and for each object you entered as second parameter,
     *                      you will receive its name
     * @param numberObject  number of object you want to insert
     * @param handler       Handler that contain a {@link Either} object
     *                      {@link Either} has 2 functions handler ->
     *                      {@link String} then {@link Either.Left} -> send Left callback
     *                      (meaning sending/throwing an error response callback)
     *                      {@link JsonArray} then {@link Either.Right} -> send Right callback
     *                      (meaning successful callback)
     *                      Note: {@link Either.Right} can handle any type than JsonArray (any Object Model/primitive)
     *                      (e.g {@link Either.Right<io.vertx.core.json.JsonObject>},{@link Either.Right<String>},
     *                      {@link Either.Right<int>} etc...
     */
    void testGetStuff(String value, Number numberObject, Handler<Either<String, JsonArray>> handler);


    /**
     * Get stuff (SECOND way to do it sync/asynchronously)
     * @param value         insert this value as parameter and for each object you entered as second parameter,
     *                      you will receive its name
     * @param numberObject  number of object you want to insert
     * @param handler       Handler that contain a {@link AsyncResult} object
     *                      {@link AsyncResult} unlike Either, you only need to pass its result's type ->
     *                      AsyncResult has its own failure method to handle your code
     */
    void testGetStuffAsync(String value, Number numberObject, Handler<AsyncResult<JsonArray>> handler);


    /**
     * Get stuff (THIRD way !/AND BETTER WAY FOR BETTER READ\! to do it sync/asynchronously)
     * @param value         insert this value as parameter and for each object you entered as second parameter,
     *                      you will receive its name
     * @param numberObject  number of object you want to insert
     * @return Future of {@link JsonArray}, a json containing any type of object/primitive types
     */
    Future<JsonArray> testGetStuff(String value, Number numberObject);


    /**
     * consume stuff (first way to consume it sync/asynchronously)
     * @param value         insert this value as parameter and for each object you entered as second parameter,
     *                      you will receive its name
     * @param numberObject  number of object you want to insert
     * @param handler       handler response data in {@link Either} way (we will know its usage)
     */
     void consumeMethodExample(String value, Number numberObject, Handler<Either<String, JsonArray>> handler);

    /**
     * consome stuff (second way to consume it sync/asynchronously)
     * @param value         insert this value as parameter and for each object you entered as second parameter,
     *                      you will receive its name
     * @param numberObject  number of object you want to insert
     * @param handler       handler response data but in {@link AsyncResult} style (we will know its usage)
     */
    void consumeMethodExampleAsync(String value, Number numberObject, Handler<AsyncResult<JsonArray>> handler);

    /**
     * consume stuff (third way to consume it sync/asynchronously)
     * @param value         insert this value as parameter and for each object you entered as second parameter,
     *                      you will receive its name
     * @param numberObject  number of object you want to insert
     * @return Future of {@link JsonArray}, a json containing any type of object/primitive types
     */
    Future<JsonArray> consumeMethodExampleAsync(String value, Number numberObject);

    // Multiple interaction part

    /**
     * consume multiple stuff (consume it sync)
     * @param value         insert this value as parameter and for each object you entered as second parameter,
     *                      you will receive its name
     * @param numberObject  number of object you want to insert
     * @param handler       handler response data in {@link Either} way (we will know its usage)
     */
    void consumeMultipleMethodExample(String value, Number numberObject, Handler<Either<String, JsonArray>> handler);

    /**
     * consume multiple stuff (second way to consume it sync)
     * @param value         insert this value as parameter and for each object you entered as second parameter,
     *                      you will receive its name
     * @param numberObject  number of object you want to insert
     * @param handler       handler response data but in {@link AsyncResult} style (we will know its usage)
     */
    void consumeMultipleMethodExampleSync(String value, Number numberObject, Handler<AsyncResult<JsonArray>> handler);

    /**
     * consume stuff (third way to consume it sync)
     * @param value         insert this value as parameter and for each object you entered as second parameter,
     *                      you will receive its name
     * @param numberObject  number of object you want to insert
     * @return Future of {@link JsonArray}, a json containing any type of object/primitive types
     */
    Future<JsonArray> consumeMultipleMethodExampleAsync(String value, Number numberObject);

    /**
     * consume multiple stuff (consume it asynchronously)
     * @param value         insert this value as parameter and for each object you entered as second parameter,
     *                      you will receive its name
     * @param numberObject  number of object you want to insert
     * @param handler       handler response data but in {@link AsyncResult} style (we will know its usage)
     */
    void consumeMultipleMethodExampleAsync(String value, Number numberObject, Handler<AsyncResult<JsonArray>> handler);
}
