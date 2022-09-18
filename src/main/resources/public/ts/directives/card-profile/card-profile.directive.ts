import {ng} from "entcore";
import {RootsConst} from "../../core/constants/roots.const";
import {ILocationService, IScope, IWindowService} from "angular";

interface IViewModel extends ng.IController, ICardProfileProps {
    text: string;

    changeText(newText: string): void;
    getText(): string;

    onClickActionNotify(value: string): void;
}

interface ICardProfileProps {
    name: string,
    job: string
}

class Controller implements IViewModel {
    private test: string;

    public text: string;
    public name: string;
    public job: string;

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

    onClickActionNotify(value: string): void {
        console.log("as a parent, im receiving: ", value);
    }

    getText(): string {
        return "";
    }

}

function directive() {
    return {
        restrict: 'E',
        templateUrl: `${RootsConst.directive}card-profile/card-profile.directive.html`,
        scope: {
            name: "=",
            job: "="
        },
        controllerAs: 'vm',
        bindToController: true,
        controller: ['$scope','$location','$window', Controller],
        /* interaction DOM/element */
        link: function (scope: ng.IScope,
                        element: ng.IAugmentedJQuery,
                        attrs: ng.IAttributes,
                        vm: ng.IController) {
        }
    }
}
export const cardProfile = ng.directive('cardProfile', directive)