package fr.cgi.todoapp.utils;

import fr.wseduc.webutils.Either;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.powermock.reflect.Whitebox;

@RunWith(PowerMockRunner.class) //Using the PowerMock runner
@PowerMockRunnerDelegate(VertxUnitRunner.class) //And the Vertx runner
@PrepareForTest({ExempleClassForMock.class, ExempleServiceForMock.class}) //Prepare the static class you want to test
public class PowerMockClassTest {

    @Test
    public void testStaticMethod(TestContext ctx) {
        PowerMockito.spy(ExempleClassForMock.class); //Mock the PowerMockClass class keeping its implementation
        Assert.assertEquals(ExempleClassForMock.staticMethod(), "1");
        //Change the return of the static method
        PowerMockito.when(ExempleClassForMock.staticMethod()).thenReturn("2");
        Assert.assertEquals(ExempleClassForMock.staticMethod(), "2");
    }

    @Test
    public void testPrivateMethod(TestContext ctx) throws Exception {
        ExempleClassForMock exempleClassForMock = PowerMockito.spy(new ExempleClassForMock()); //Mock an object of type PowerMockClass keeping its implementation
        Assert.assertEquals(Whitebox.invokeMethod(exempleClassForMock, "privateMethod"), "1");
        //Change the return of the private method
        PowerMockito.when(exempleClassForMock, "privateMethod").thenReturn("2");
        Assert.assertEquals(Whitebox.invokeMethod(exempleClassForMock, "privateMethod"), "2");
    }

    @Test
    public void testStaticPrivateMethod(TestContext ctx) throws Exception {
        PowerMockito.spy(ExempleClassForMock.class);
        Assert.assertEquals(Whitebox.invokeMethod(ExempleClassForMock.class, "staticPrivateMethod"), "1");
        //Change the return of the static private method
        PowerMockito.doReturn("2").when(ExempleClassForMock.class, "staticPrivateMethod");
        Assert.assertEquals(Whitebox.invokeMethod(ExempleClassForMock.class, "staticPrivateMethod"), "2");
    }

    @Test
    public void testStaticPrivateField(TestContext ctx) {
        PowerMockito.spy(ExempleClassForMock.class);
        Assert.assertEquals(Whitebox.getInternalState(ExempleClassForMock.class, "staticPrivateField"), "1");
        //Changes the return of the static private field
        Whitebox.setInternalState(ExempleClassForMock.class, "staticPrivateField", "2");
        Assert.assertEquals(Whitebox.getInternalState(ExempleClassForMock.class, "staticPrivateField"), "2");
    }

    @Test
    public void testStaticPrivateFieldHasBeenChange(TestContext ctx) {
        Assert.assertEquals(Whitebox.getInternalState(ExempleClassForMock.class, "staticPrivateField"), "2"); //Attention the modifications are applied during all the execution of the tests.
    }

    @Test
    @PrepareForTest({ExempleClassForMock.class})
    //Allows to reset the modifications made in the previous methods. Adds a slow time, do not use on all methods without reason
    public void testGetStaticPrivateField(TestContext ctx) {
        ExempleClassForMock exempleClassForMock = new ExempleClassForMock();
        Assert.assertEquals(exempleClassForMock.getStaticPrivateField(), "1");
        Whitebox.setInternalState(ExempleClassForMock.class, "staticPrivateField", "2");
        Assert.assertEquals(exempleClassForMock.getStaticPrivateField(), "2");
    }

    @Test
    public void testGetPrivateMethodInInteger(TestContext ctx) throws Exception {
        PowerMockito.spy(ExempleClassForMock.class);
        ExempleClassForMock exempleClassForMock = new ExempleClassForMock();
        Assert.assertEquals(exempleClassForMock.getStaticPrivateMethodInInteger(), 1);
        PowerMockito.doReturn("2").when(ExempleClassForMock.class, "staticPrivateMethod");
        Assert.assertEquals(exempleClassForMock.getStaticPrivateMethodInInteger(), 2);
    }

    @Test
    public void testCallService(TestContext ctx) throws Exception {
        ExempleClassForMock exempleClassForMock = PowerMockito.spy(new ExempleClassForMock());
        JsonObject jsonObject = PowerMockito.spy(new JsonObject());
        PowerMockito.doReturn("").when(jsonObject).getString(Mockito.any());

        ExempleServiceForMock exempleServiceForMock = PowerMockito.spy(new ExempleServiceForMock());
        Whitebox.setInternalState(exempleClassForMock, "exempleServiceForMock", exempleServiceForMock);
        PowerMockito.doReturn("1").when(exempleServiceForMock).service(Mockito.any());
        ctx.assertEquals(exempleClassForMock.callService(jsonObject), "1");
    }

    @Test
    public void testHandler(TestContext ctx) throws Exception {
        Async async = ctx.async();
        ExempleClassForMock exempleClassForMock = PowerMockito.spy(new ExempleClassForMock());
        ExempleServiceForMock exempleServiceForMock = PowerMockito.spy(new ExempleServiceForMock());

        //Allows you to change the way the service works
        PowerMockito.doAnswer((Answer<Void>) invocation -> {
            Handler<Either<String, JsonObject>> handler = invocation.getArgument(1);
            //We return a set of test data in response from the service
            handler.handle(new Either.Right<>(new JsonObject().put("data", "myCustomData")));
            return null;
        }).when(exempleServiceForMock).getSqlData(Mockito.any(), Mockito.any());
        Whitebox.setInternalState(exempleClassForMock, "exempleServiceForMock", exempleServiceForMock);

        exempleClassForMock.methodHandler("idSqlData").onSuccess(event -> {
            ctx.assertTrue(event.equals("myCustomData"));
            async.complete();
        });
    }

    @Test
    @PrepareForTest({ExempleClassForMock.class, ExempleServiceForMock.class})
    public void testHandlerFromStaticService(TestContext ctx) throws Exception {
        Async async = ctx.async();
        ExempleClassForMock exempleClassForMock = PowerMockito.spy(new ExempleClassForMock());
        PowerMockito.spy(ExempleServiceForMock.class);

        //Allows you to change the way the service works
        PowerMockito.doAnswer((Answer<Void>) invocation -> {
            Handler<Either<String, JsonObject>> handler = invocation.getArgument(1);
            //We return a set of test data in response from the service
            handler.handle(new Either.Right<>(new JsonObject().put("data", "myCustomData")));
            return null;
            //For static void methods we must use this syntax
        }).when(ExempleServiceForMock.class, "getSqlDataStatic", Mockito.any(), Mockito.any());

        exempleClassForMock.methodHandlerStatic("idSqlData").onSuccess(event -> {
            ctx.assertTrue(event.equals("myCustomData"));
            async.complete();
        });
    }
}
