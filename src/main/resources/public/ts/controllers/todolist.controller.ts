import { ng } from 'entcore';

interface ViewModel {
    $onInit(): any;
    $onDestroy(): any;
}

export const todolistController = ng.controller('TodolistController', ['$scope', 'route', ($scope, route) => {
    const vm: ViewModel = this;

    // init life's cycle hook
    vm.$onInit = () => {

    };

    // destruction cycle hook
    vm.$onDestroy = () => {

    };

}]);
