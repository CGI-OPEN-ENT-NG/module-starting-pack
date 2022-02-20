import { ng, routes } from 'entcore';
import * as controllers from './controllers';
import * as directives from './directives';
import * as services from './services';

for(let controller in controllers){
    ng.controllers.push(controllers[controller]);
}

for (let directive in directives) {
	ng.directives.push(directives[directive]);
}

for (let service in services) {
	ng.services.push(services[service]);
}

routes.define(function($routeProvider){
	$routeProvider
		// in your browser : ${host}/${yourApp}#/list
		.when('/list', {
			action: 'list',
		})
		.when('/list2', {
			action: 'list2',
		})
		// in your browser : default ${host}/${yourApp}
		.otherwise({
			action: 'defaultView'
		});
})