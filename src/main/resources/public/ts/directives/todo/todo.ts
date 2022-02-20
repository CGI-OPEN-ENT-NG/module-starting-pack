import {ng} from "entcore";
import {RootsConst} from "../../core/constants/roots.const";

interface IViewModel {
    $onInit(): any;
    $onDestroy(): any;

    changeText(newText: string): void;

    getText(): string;

    text: string;
}

export const todoDirective = ng.directive('todo', () => {
        return {
            restrict: 'E',
            transclude: true,
            scope: {
                form: '=',
                timeSlots: '=',
                punishment: '='
            },
            templateUrl: `${RootsConst.directive}todo/todo.html`,
            controllerAs: 'vm',
            bindToController: true,
            replace: true,
            controller: ['$scope', function ($scope) {
                const vm: IViewModel = <IViewModel>this;


                vm.$onInit = async () => {
                    vm.text = "Bonjour"
                };

                vm.getText = () => {
                    return vm.text
                }
            }],
            link: function ($scope, $element: HTMLDivElement) {
                const vm: IViewModel = $scope.vm;
                vm.changeText = (text: string) => {
                    vm.text = text
                }

                vm.$onDestroy = () => {

                };
            }
        };
    }
);