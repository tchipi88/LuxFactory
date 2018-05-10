(function() {
    'use strict';

    angular
        .module('app')
        .controller('EncaissementDetailController', EncaissementDetailController);

    EncaissementDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Encaissement' ,'Caisse'];

    function EncaissementDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Encaissement ,Caisse) {
        var vm = this;

        vm.encaissement = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('tkbrApp:encaissementUpdate', function(event, result) {
            vm.encaissement = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
