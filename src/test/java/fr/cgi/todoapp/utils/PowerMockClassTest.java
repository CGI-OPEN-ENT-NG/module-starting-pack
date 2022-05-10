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
@PrepareForTest({PowerMockClass.class, PowerMockService.class}) //Prepare the static class you want to test
public class PowerMockClassTest {

    @Test
    public void testStaticMethod(TestContext ctx) {
        PowerMockito.spy(PowerMockClass.class); //Mock the PowerMockClass class keeping its implementation
        Assert.assertEquals(PowerMockClass.staticMethod(), "1");
        //Change the return of the static method
        PowerMockito.when(PowerMockClass.staticMethod()).thenReturn("2");
        Assert.assertEquals(PowerMockClass.staticMethod(), "2");
    }

    @Test
    public void testPrivateMethod(TestContext ctx) throws Exception {
        PowerMockClass powerMockClass = PowerMockito.spy(new PowerMockClass()); //Mock an object of type PowerMockClass keeping its implementation
        Assert.assertEquals(Whitebox.invokeMethod(powerMockClass, "privateMethod"), "1");
        //Change the return of the private method
        PowerMockito.when(powerMockClass, "privateMethod").thenReturn("2");
        Assert.assertEquals(Whitebox.invokeMethod(powerMockClass, "privateMethod"), "2");
    }

    @Test
    public void testStaticPrivateMethod(TestContext ctx) throws Exception {
        PowerMockito.spy(PowerMockClass.class);
        Assert.assertEquals(Whitebox.invokeMethod(PowerMockClass.class, "staticPrivateMethod"), "1");
        //Change the return of the static private method
        PowerMockito.doReturn("2").when(PowerMockClass.class, "staticPrivateMethod");
        Assert.assertEquals(Whitebox.invokeMethod(PowerMockClass.class, "staticPrivateMethod"), "2");
    }

    @Test
    public void testStaticPrivateField(TestContext ctx) {
        PowerMockito.spy(PowerMockClass.class);
        Assert.assertEquals(Whitebox.getInternalState(PowerMockClass.class, "staticPrivateField"), "1");
        //Changes the return of the static private field
        Whitebox.setInternalState(PowerMockClass.class, "staticPrivateField", "2");
        Assert.assertEquals(Whitebox.getInternalState(PowerMockClass.class, "staticPrivateField"), "2");
    }

    @Test
    public void testStaticPrivateFieldHasBeenChange(TestContext ctx) {
        Assert.assertEquals(Whitebox.getInternalState(PowerMockClass.class, "staticPrivateField"), "2"); //Attention the modifications are applied during all the execution of the tests.
    }

    @Test
    @PrepareForTest({PowerMockClass.class})
    //Allows to reset the modifications made in the previous methods. Adds a slow time, do not use on all methods without reason
    public void testGetStaticPrivateField(TestContext ctx) {
        PowerMockClass powerMockClass = new PowerMockClass();
        Assert.assertEquals(powerMockClass.getStaticPrivateField(), "1");
        Whitebox.setInternalState(PowerMockClass.class, "staticPrivateField", "2");
        Assert.assertEquals(powerMockClass.getStaticPrivateField(), "2");
    }

    @Test
    public void testGetPrivateMethodInInteger(TestContext ctx) throws Exception {
        PowerMockito.spy(PowerMockClass.class);
        PowerMockClass powerMockClass = new PowerMockClass();
        Assert.assertEquals(powerMockClass.getStaticPrivateMethodInInteger(), 1);
        PowerMockito.doReturn("2").when(PowerMockClass.class, "staticPrivateMethod");
        Assert.assertEquals(powerMockClass.getStaticPrivateMethodInInteger(), 2);
    }

    @Test
    public void testCallService(TestContext ctx) throws Exception {
        PowerMockClass powerMockClass = PowerMockito.spy(new PowerMockClass());
        JsonObject jsonObject = PowerMockito.spy(new JsonObject());
        PowerMockito.doReturn("").when(jsonObject).getString(Mockito.any());

        PowerMockService powerMockService = PowerMockito.spy(new PowerMockService());
        Whitebox.setInternalState(powerMockClass, "powerMockService", powerMockService);
        PowerMockito.doReturn("1").when(powerMockService).service(Mockito.any());
        ctx.assertEquals(powerMockClass.callService(jsonObject), "1");
    }

    @Test
    public void testHandler(TestContext ctx) throws Exception {
        Async async = ctx.async();
        PowerMockClass powerMockClass = PowerMockito.spy(new PowerMockClass());
        PowerMockService powerMockService = PowerMockito.spy(new PowerMockService());

        //Allows you to change the way the service works
        PowerMockito.doAnswer((Answer<Void>) invocation -> {
            Handler<Either<String, JsonObject>> handler = invocation.getArgument(1);
            //We return a set of test data in response from the service
            handler.handle(new Either.Right<>(new JsonObject().put("data", "myCustomData")));
            return null;
        }).when(powerMockService).getSqlData(Mockito.any(), Mockito.any());
        Whitebox.setInternalState(powerMockClass, "powerMockService", powerMockService);

        powerMockClass.methodHandler("idSqlData").onSuccess(event -> {
            ctx.assertTrue(event.equals("myCustomData"));
            async.complete();
        });
    }

    @Test
    @PrepareForTest({PowerMockClass.class, PowerMockService.class})
    public void testHandlerFromStaticService(TestContext ctx) throws Exception {
        Async async = ctx.async();
        PowerMockClass powerMockClass = PowerMockito.spy(new PowerMockClass());
        PowerMockito.spy(PowerMockService.class);

        //Allows you to change the way the service works
        PowerMockito.doAnswer((Answer<Void>) invocation -> {
            Handler<Either<String, JsonObject>> handler = invocation.getArgument(1);
            //We return a set of test data in response from the service
            handler.handle(new Either.Right<>(new JsonObject().put("data", "myCustomData")));
            return null;
            //For static void methods we must use this syntax
        }).when(PowerMockService.class, "getSqlDataStatic", Mockito.any(), Mockito.any());

        powerMockClass.methodHandlerStatic("idSqlData").onSuccess(event -> {
            ctx.assertTrue(event.equals("myCustomData"));
            async.complete();
        });
    }
}
