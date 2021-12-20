import {ng} from 'entcore';

interface ViewModel {
    $onInit(): any;
    $onDestroy(): any;

    text: string;
}

// we use function instead of arrow function to apply life's cycle hook
export const todolistController = ng.controller('TodolistController', ['$scope', 'route', function ($scope, route) {
    const vm: ViewModel = this;

    // init life's cycle hook
    vm.$onInit = () => {
        vm.text = "test var";
        console.log("todolistController's life cycle");
        console.log("vm parent (main): ", $scope.$parent.vm);
    };

    // destruction cycle hook
    vm.$onDestroy = () => {

    };

}]);
