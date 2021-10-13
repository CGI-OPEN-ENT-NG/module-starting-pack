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
- `entcore` (from package.json) current: `3.12.0`
- `entCoreVersion` (from gradle.properties) current : `3.12.0`

What must be changed (if you wish) :
- module name we use `module-starting-pack` as name folder
- `Todoapp` java main class
- Some file(s) named with "todolist"
- (Optional) some HelperClass are implemented to explain some vertx features must be removed if you use it as a true project
