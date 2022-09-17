import {ng} from 'entcore';
import {IScope} from "angular";

interface IViewModel extends ng.IController {
    changeText(newText: string): void;

    getText(): string;

    text: string;
}

interface ITodolistScope extends IScope {
    vm: IViewModel;
}

class Controller implements IViewModel {

    text: string;

    constructor(private $scope: ITodolistScope
                /*  inject service etc..just as we do in controller */) {
        this.$scope.vm = this;
    }

    $onInit() {
        this.text = "test var";
        console.log("vm parent (main): ", this.$scope.$parent['vm']);
    }

    changeText(newText: string): void {
        this.text = newText;
    }

    getText(): string {
        return this.text;
    }

    $onDestroy() {

    }
}

export const todolistController = ng.controller('TodolistController', ['$scope', Controller]);
