import {ng} from "entcore";
import {RootsConst} from "../../core/constants/roots.const";
import {ILocationService, IScope, IWindowService} from "angular";

interface IViewModel {
    text: string;

    changeText(newText: string): void;
    getText(): string;
}

class Controller implements ng.IController, IViewModel {
    public test: string;
    text: string;

    constructor(private $scope: IScope,
                private $location:ILocationService,
                private $window: IWindowService
                /*  inject service etc..just as we do in controller */)
    {}

    $onInit() {
        this.test = "coucou";
        console.log("I am built: ", this.$scope);
    }

    $onDestroy() {
        console.log("I destroy testDirective");
    }

    changeText(newText: string): void {
    }

    getText(): string {
        return "";
    }

}

function directive() {
    return {
        restrict: 'E',
        templateUrl: `${RootsConst.directive}test/test.html`,
        scope: {
            myProps: "="
        },
        controllerAs: 'vm',
        bindToController: true,
        controller: ['$scope','$location','$window', Controller],
        /* interaction DOM/element */
        link: function (scope: ng.IScope,
                        element: ng.IAugmentedJQuery,
                        attrs: ng.IAttributes,
                        vm: ng.IController) {
            console.log("link data: ", vm);
            console.log("link scope: ", scope);
        }
    }
}
export const testDirective = ng.directive('testDirective', directive)