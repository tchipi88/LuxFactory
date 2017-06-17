(function() {
    'use strict';

    angular
        .module('app')
        .controller('ReglementDetailController', ReglementDetailController);

    ReglementDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Reglement' ,'Commande'];

    function ReglementDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Reglement ,Commande) {
        var vm = this;

        vm.reglement = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('tkbrApp:reglementUpdate', function(event, result) {
            vm.reglement = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
