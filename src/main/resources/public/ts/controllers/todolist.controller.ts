import {ng} from 'entcore';

interface ViewModel {
    $onInit(): any;
    $onDestroy(): any;

    changeText(newText: string): void;

    getText(): string;

    text: string;
}

// we use function instead of arrow function to apply life's cycle hook
export const todolistController = ng.controller('TodolistController', ['$scope', 'route', function ($scope, route) {
    const vm: ViewModel = this;

    // init life's cycle hook
    vm.$onInit = () => {
        vm.text = "test var";
        console.log("todolistController's life cycle: ", vm);
        console.log("vm parent (main): ", $scope.$parent.vm);
    };

    vm.changeText = (newText: string) => {
        vm.text = newText;
    }

    vm.getText = () => {
        return vm.text;
    }

    // destruction cycle hook
    vm.$onDestroy = () => {

    };

}]);
