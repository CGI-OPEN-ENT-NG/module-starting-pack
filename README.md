# module-starting-pack

Starting pack for a verticle with AngularJS.
In this example we take a todolistapp as an example.

This project includes :
- Java Vertx features (Promise/Future)
- Some example using HttpClient (WebClient)
- MVVM Architecture (Front-end part)
- Package with proper deps to work with Typescript and its configuration
- Unit Test
- CI for gitlab and github test


What must be updated :
- `entcore` (from package.json) current: `4.7.2`
- `entCoreVersion` (from gradle.properties) current : `4.10.2`

What must be changed (if you wish) :
- module name we use `module-starting-pack` as name folder
- `Todoapp` java main class
- Some file(s) named with "todolist"
- (Optional) some HelperClass are implemented to explain some vertx features must be removed if you use it as a true project
- Deployment file (/deployment/${yourApp}/conf.json.template) :
<pre>
{
      "name": "fr.${packageName}~${yourApp}~0.1-SNAPSHOT",
      "config": {
        "main" : "fr.${packageName}~${yourApp}",
        "port" : xxxx,
        "sql" : true,
        "mongodb" : false,
        "neo4j" : true,
        "app-name" : ${yourApp},
        "app-address" : `/${yourApp}`,
        "app-icon" : "${yourApp}-large",
        "host": "http://localhost:8090",
        "ssl" : false,
        "auto-redeploy": false,
        "userbook-host": "http://localhost:8090",
        "integration-mode" : "HTTP",
        "mode" : "dev"
      }
}
</pre>
- gradle.properties :
<pre>
# E.g. your domain name
modowner=fr.${packageName}
modname=${yourApp}
</pre>
- mod.json :
<pre>
{
	"main" : "fr.${packageName}.${yourApp}.${YourApp}",
	"port" : xxxx,
	"app-name" : "${YourApp}",
	"app-address" : "/${yourApp}",
	"app-icon" : "${yourApp}-large",
	"app-displayName" : "${YourApp}",
	"mode": "dev",
	"entcore.port" : 8090,
	"auto-redeploy": false
}
</pre>