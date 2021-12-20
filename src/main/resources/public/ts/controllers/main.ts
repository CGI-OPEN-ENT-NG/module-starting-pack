import {ng, template} from 'entcore';

declare let window: any;

interface ViewModel {
	$onInit(): any;
	$onDestroy(): any;

	userId: string;
}

/**
	Wrapper controller
	------------------
	Main controller.
**/

// we use function instead of arrow function to apply life's cycle hook

export const mainController = ng.controller('MainController', ['$scope', 'route', function ($scope, route) {
	const vm: ViewModel = this;

	// init life's cycle hook
	vm.$onInit = () => {
		vm.userId = window.myUserId;
		$scope.userId = vm.userId;
		console.log("MainController's life cycle");
	};

	// destruction cycle hook
	vm.$onDestroy = () => {

	};

	route({
		list: () => {
			template.open('main', `second-page`);
		},
		defaultView: () => {
			template.open('main', `main`);
		}
	});
}]);
