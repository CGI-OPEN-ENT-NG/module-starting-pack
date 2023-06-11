import {ng} from "../../models/__mocks__/entcore" //load custom ng model
import * as angular from 'angular'; //important
import 'angular-mocks'; //important
import {todolistController} from "../todolist.controller";

describe('TodolistController', () => {
    let todolistControllerTest: any;

    beforeEach(() => {
        //Registering a module for testing
        const testApp = angular.module('app', []);
        let $controller, $rootScope;

        //Mockup test module
        // angular.mock.module('app');
        // adding 'default' for this version

        angular.mock.module('app');


        //Instantiation of the services, controllers, directives we need
        todolistController;

        //Adding services, controllers, directives in the module
        ng.initMockedModules(testApp);

        //Controller Injection
        // adding 'default' for this version

        angular.mock.inject((_$controller_, _$rootScope_) => {
            // The injector unwraps the underscores (_) from around the parameter names when matching
            $controller = _$controller_;
            $rootScope = _$rootScope_;
        });

        //Creates a new instance of scope
        let $scope = $rootScope.$new();

        //Fetching $location
        testApp.run(($rootScope, $location) => {
            $rootScope.location = $location;
        });

        //Controller Recovery
        todolistControllerTest = $controller('TodolistController', {
            $scope: $scope,
            route: undefined,
        });
        todolistControllerTest.$onInit(); // must be called since we have some instruction onInit with some variable
    })

    it('test the correct functioning the changeText method', done => {
        expect(todolistControllerTest.getText()).toEqual("test var");
        todolistControllerTest.changeText("Oui");
        expect(todolistControllerTest.getText()).toEqual("Oui");
        done();
    });
});
