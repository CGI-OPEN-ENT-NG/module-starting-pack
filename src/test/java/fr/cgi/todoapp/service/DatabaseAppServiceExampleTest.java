package fr.cgi.todoapp.service;

import fr.cgi.todoapp.service.impl.DefaultDatabaseAppServiceExample;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.entcore.common.neo4j.Neo4j;
import org.entcore.common.neo4j.Neo4jRest;
import org.entcore.common.sql.Sql;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.FieldSetter;
import org.mockito.stubbing.Answer;
import org.powermock.reflect.Whitebox;

import static org.mockito.Mockito.mock;

@RunWith(VertxUnitRunner.class)
public class DatabaseAppServiceExampleTest {

    private Vertx vertx;
    private final Sql sql = mock(Sql.class);

    private final Neo4j neo4j = Neo4j.getInstance();
    private final Neo4jRest neo4jRest = mock(Neo4jRest.class);

    private DatabaseAppServiceExample databaseAppServiceExample;

    @Before
    public void setUp() throws NoSuchFieldException {
        vertx = Vertx.vertx();
        Sql.getInstance().init(vertx.eventBus(), "fr.cgi.todoapp");

        FieldSetter.setField(neo4j, neo4j.getClass().getDeclaredField("database"), neo4jRest);

        this.databaseAppServiceExample = new DefaultDatabaseAppServiceExample(sql);
    }

    @Test
    public void getTestGetStuff_Should_Get_Correct_Data_Into_SQLPrepare(TestContext ctx) {
        String expectedQuery = "SELECT * FROM schema.table WHERE id = ?";
        JsonArray expectedParams = new JsonArray().add(5);

        Mockito.doAnswer((Answer<Void>) invocation -> {
            String queryResult = invocation.getArgument(0);
            JsonArray paramsResult = invocation.getArgument(1);
            ctx.assertEquals(queryResult, expectedQuery);
            ctx.assertEquals(paramsResult.toString(), expectedParams.toString());
            return null;
        }).when(sql).prepared(Mockito.anyString(), Mockito.any(JsonArray.class), Mockito.any(Handler.class));
        this.databaseAppServiceExample.testGetStuff(5, event -> {

        });
    }

    @Test
    public void getTestGetStuffWithSql_Should_Get_Correct_Data_Into_SQLPrepare(TestContext ctx) {
        String expectedQuery = "SELECT * FROM schema.table WHERE id = ?";
        JsonArray expectedParams = new JsonArray().add(5);

        Mockito.doAnswer((Answer<Void>) invocation -> {
            String queryResult = invocation.getArgument(0);
            JsonArray paramsResult = invocation.getArgument(1);
            ctx.assertEquals(queryResult, expectedQuery);
            ctx.assertEquals(paramsResult.toString(), expectedParams.toString());
            return null;
        }).when(sql).prepared(Mockito.anyString(), Mockito.any(JsonArray.class), Mockito.any(Handler.class));
        this.databaseAppServiceExample.testGetStuffWithSql(5, null);
    }

    @Test
    public void getPrivateTestGetStuffWithSql_Should_Get_Correct_Data_Into_SQLPrepare(TestContext ctx) {
        String expectedQuery = "SELECT * FROM schema.table WHERE id = ?";
        JsonArray expectedParams = new JsonArray().add(5);

        Mockito.doAnswer((Answer<Void>) invocation -> {
            String queryResult = invocation.getArgument(0);
            JsonArray paramsResult = invocation.getArgument(1);
            ctx.assertEquals(queryResult, expectedQuery);
            ctx.assertEquals(paramsResult.toString(), expectedParams.toString());
            return null;
        }).when(sql).prepared(Mockito.anyString(), Mockito.any(JsonArray.class), Mockito.any(Handler.class));

        try {
            Whitebox.invokeMethod(this.databaseAppServiceExample, "privateTestGetStuffWithSql", 5, null);
        } catch (Exception e) {
            ctx.assertNull(e);
        }
    }

    @Test
    public void getPrivateTestGetStuffWithSqlInstance_Should_Get_Correct_Data_Into_SQLPrepare(TestContext ctx) {
        Async async = ctx.async();

        String expectedQuery = "SELECT * FROM schema.table WHERE id = ?";
        JsonArray expectedParams = new JsonArray().add(5);

        vertx.eventBus().consumer("fr.cgi.todoapp", message -> {
            JsonObject body = (JsonObject) message.body();
            ctx.assertEquals("prepared", body.getString("action"));
            ctx.assertEquals(expectedQuery, body.getString("statement"));
            ctx.assertEquals(expectedParams.toString(), body.getJsonArray("values").toString());
            async.complete();
        });

        try {
            Whitebox.invokeMethod(this.databaseAppServiceExample, "privateTestGetStuffWithSqlInstance", 5, (Handler) e -> {

            });
        } catch (Exception e) {
            ctx.assertNotNull(e);
        }
    }

    @Test
    public void testGetChildrenInformation(TestContext ctx) {
        String expectedQuery = "MATCH (:User {id:{userId}})<-[RELATED]-(u:User)-[:IN]->(g:ProfileGroup)-[:DEPENDS]->(s:Structure), " +
                "(u)--(m:Group{filter:\"Student\"})--(b:Class) " +
                "RETURN distinct u.id AS id, u.firstName AS firstName, u.lastName AS lastName," +
                " u.displayName AS displayName, u.classes AS classes, collect(DISTINCT b.id) AS idClasses, collect(DISTINCT s.id) AS structures ";


        Mockito.doAnswer((Answer<Void>) invocation -> {
            String queryResult = invocation.getArgument(0);
            ctx.assertEquals(queryResult, expectedQuery);
            return null;
        }).when(neo4jRest).execute(Mockito.anyString(), Mockito.any(JsonObject.class), Mockito.any(Handler.class));

        this.databaseAppServiceExample.getChildrenInformation("userId", event -> {});
    }

    @Test
    public void testPrivateGetChildrenInformation(TestContext ctx) {
        String expectedQuery = "MATCH (:User {id:{userId}})<-[RELATED]-(u:User)-[:IN]->(g:ProfileGroup)-[:DEPENDS]->(s:Structure), " +
                "(u)--(m:Group{filter:\"Student\"})--(b:Class) " +
                "RETURN distinct u.id AS id, u.firstName AS firstName, u.lastName AS lastName," +
                " u.displayName AS displayName, u.classes AS classes, collect(DISTINCT b.id) AS idClasses, collect(DISTINCT s.id) AS structures ";


        Mockito.doAnswer((Answer<Void>) invocation -> {
            String queryResult = invocation.getArgument(0);
            ctx.assertEquals(queryResult, expectedQuery);
            return null;
        }).when(neo4jRest).execute(Mockito.anyString(), Mockito.any(JsonObject.class), Mockito.any(Handler.class));

        try {
            Whitebox.invokeMethod(this.databaseAppServiceExample, "getPrivateChildrenInformation", "userId", (Handler) e -> {

            });
        } catch (Exception e) {
            ctx.assertNotNull(e);
        }
    }

}

