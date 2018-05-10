(function() {
    'use strict';

    angular
        .module('app')
        .controller('EntrepotDetailController', EntrepotDetailController);

    EntrepotDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Entrepot' ,'Employe'];

    function EntrepotDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Entrepot ,Employe) {
        var vm = this;

        vm.entrepot = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('tkbrApp:entrepotUpdate', function(event, result) {
            vm.entrepot = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
