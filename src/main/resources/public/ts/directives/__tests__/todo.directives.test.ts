import {ng} from "../../models/__mocks__/entcore" //load custom ng model
import * as angular from 'angular'; //important
import 'angular-mocks'; //important
import {todoDirective} from "../todo/todo";
import {RootsConst} from "../../core/constants/roots.const";

describe('todoDetentionForm', () => {

    let todoDirectiveTest: any;

    beforeEach(() => {
        let el, scope;
        //Registering a module for testing
        const testApp = angular.module('app', []);

        //Mockup test module
        // angular.mock.module('app');
        // adding 'default' for this version

        // console.log("angularjs test env: ", angular);
        angular.mock.module('app');

        //Instantiation of the services, controllers, directives we need
        todoDirective;

        //Adding services, controllers, directives in the module
        ng.initMockedModules(testApp);

        //Controller Injection
        inject(function ($compile, $rootScope, $templateCache) {
            //Load a custom template for directive url
            $templateCache.put(`${RootsConst.directive}todo/todo.html`, "<div></div>" /*Must be not empty*/);
            //Instantiate directive.
            //gotacha: Controller and link functions will execute.
            el = angular.element("<todo></todo>")
            $compile(el)($rootScope.$new())
            $rootScope.$digest()

            //Grab controller instance
            todoDirectiveTest = el.controller("todo");

            //Grab scope. Depends on type of scope.
            //See angular.element documentation.
            scope = el.isolateScope() || el.scope()
            todoDirectiveTest.$onInit();
        })
    })

    it('test directive', done => {
        expect(todoDirectiveTest.text).toEqual("Bonjour");
        expect(todoDirectiveTest.getText()).toEqual("Bonjour");
        todoDirectiveTest.changeText("Oui");
        expect(todoDirectiveTest.getText()).toEqual("Oui");
        expect(todoDirectiveTest.text).toEqual("Oui");

        done();
    });
});
