package fr.cgi.todoapp.service.impl;

import fr.cgi.todoapp.service.DatabaseAppServiceExample;
import fr.wseduc.webutils.Either;
import io.vertx.core.*;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.entcore.common.neo4j.Neo4j;
import org.entcore.common.neo4j.Neo4jResult;
import org.entcore.common.sql.Sql;
import org.entcore.common.sql.SqlResult;
import org.entcore.common.user.UserInfos;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultDatabaseAppServiceExample implements DatabaseAppServiceExample {

    private final Sql sql;

    public DefaultDatabaseAppServiceExample(Sql sql) {
       this.sql = sql;
    }

    @Override
    public void testGetStuff(Number numberObject, Handler<Either<String, JsonArray>> handler) {
        String query = "SELECT * FROM schema.table WHERE id = ?";
        JsonArray values = new JsonArray().add(numberObject);
        Sql.getInstance().prepared(query, values, SqlResult.validResultHandler(handler));
    }

    public void testGetStuffWithSql(Number numberObject, Handler<Either<String, JsonArray>> handler) {
        String query = "SELECT * FROM schema.table WHERE id = ?";
        JsonArray values = new JsonArray().add(numberObject);
        sql.prepared(query, values, SqlResult.validResultHandler(handler));
    }

    private void privateTestGetStuffWithSql(Number numberObject, Handler<Either<String, JsonArray>> handler) {
        String query = "SELECT * FROM schema.table WHERE id = ?";
        JsonArray values = new JsonArray().add(numberObject);
        sql.prepared(query, values, SqlResult.validResultHandler(handler));
    }

    private void privateTestGetStuffWithSqlInstance(Number numberObject, Handler<Either<String, JsonArray>> handler) {
        String query = "SELECT * FROM schema.table WHERE id = ?";
        JsonArray values = new JsonArray().add(numberObject);
        Sql.getInstance().prepared(query, values, SqlResult.validResultHandler(handler));
    }

    public void getChildrenInformation(String userId, Handler<Either<String, JsonArray>> handler) {
        String query = "MATCH (:User {id:{userId}})<-[RELATED]-(u:User)-[:IN]->(g:ProfileGroup)-[:DEPENDS]->(s:Structure), " +
                "(u)--(m:Group{filter:\"Student\"})--(b:Class) " +
                "RETURN distinct u.id AS id, u.firstName AS firstName, u.lastName AS lastName," +
                " u.displayName AS displayName, u.classes AS classes, collect(DISTINCT b.id) AS idClasses, collect(DISTINCT s.id) AS structures ";

        Neo4j.getInstance().execute(query, new JsonObject().put("userId", userId), Neo4jResult.validResultHandler(handler));
    }

    private void getPrivateChildrenInformation(String userId, Handler<Either<String, JsonArray>> handler) {
        String query = "MATCH (:User {id:{userId}})<-[RELATED]-(u:User)-[:IN]->(g:ProfileGroup)-[:DEPENDS]->(s:Structure), " +
                "(u)--(m:Group{filter:\"Student\"})--(b:Class) " +
                "RETURN distinct u.id AS id, u.firstName AS firstName, u.lastName AS lastName," +
                " u.displayName AS displayName, u.classes AS classes, collect(DISTINCT b.id) AS idClasses, collect(DISTINCT s.id) AS structures ";

        Neo4j.getInstance().execute(query, new JsonObject().put("userId", userId), Neo4jResult.validResultHandler(handler));
    }
}
