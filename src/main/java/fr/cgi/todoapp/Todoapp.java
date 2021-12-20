package fr.cgi.todoapp;

import fr.cgi.todoapp.controller.TodoappController;
import fr.cgi.todoapp.service.ServiceFactory;
import fr.wseduc.mongodb.MongoDb;
import org.entcore.common.http.BaseServer;
import org.entcore.common.neo4j.Neo4j;
import org.entcore.common.sql.Sql;
import org.entcore.common.storage.Storage;
import org.entcore.common.storage.StorageFactory;

public class Todoapp extends BaseServer {

	@Override
	public void start() throws Exception {
		// this verticle starts now!
		super.start();

		// (Optional) if your module is needed to fetch storage in your springboard (interaction file/storage writing etc...)
		Storage storage = new StorageFactory(vertx, config).getStorage();

		// (Optional) if you are needed to factorize all your service into one to prevent multiple object instantiation
		// since this object will be called once you use that simple and only one instance
		ServiceFactory serviceFactory = new ServiceFactory(vertx, storage, Neo4j.getInstance(), Sql.getInstance(), MongoDb.getInstance());

		// we add a controller (where API and other will be accessible from our client)
		addController(new TodoappController(serviceFactory));
	}

}
