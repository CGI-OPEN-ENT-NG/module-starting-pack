import {ng} from 'entcore';
import {ILocationService, IScope, IWindowService} from "angular";

interface IViewModel {
    changeText(newText: string): void;
    getText(): string;
    text: string;
}

interface IAnotherTodolistScope extends IScope {
    vm: IViewModel;
}

class Controller implements ng.IController, IViewModel {
    text: string;

    constructor(private $scope: IAnotherTodolistScope,
                private $location:ILocationService,
                private $window: IWindowService
                /*  inject service etc..just as we do in controller */) {
        this.$scope.vm = this;
    }

    $onInit() {
        this.text = "test var";
        console.log("AnotherTodolistController's life cycle: ",this.$scope.vm);
        console.log("vm parent (main): ", this.$scope.$parent['vm']);
        console.log("I am built: ", this.$scope);
    }

    $onDestroy() {
        console.log("I destroy AnotherTodolistController");
    }

    changeText(newText: string): void {
    }

    getText(): string {
        return "";
    }

}

export const anotherTodolistController = ng.controller('AnotherTodolistController',
    ['$scope', '$location', '$window', Controller]);
