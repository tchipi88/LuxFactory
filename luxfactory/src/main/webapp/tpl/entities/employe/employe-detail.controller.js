(function() {
    'use strict';

    angular
        .module('app')
        .controller('EmployeDetailController', EmployeDetailController);

    EmployeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Employe' ,'EmployeFonction','EmployeDepartement'];

    function EmployeDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Employe ,EmployeFonction,EmployeDepartement) {
        var vm = this;

        vm.employe = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('tkbrApp:employeUpdate', function(event, result) {
            vm.employe = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
