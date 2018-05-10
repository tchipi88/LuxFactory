(function() {
    'use strict';

    angular
        .module('app')
        .controller('DecaissementDetailController', DecaissementDetailController);

    DecaissementDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Decaissement' ,'Caisse'];

    function DecaissementDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Decaissement ,Caisse) {
        var vm = this;

        vm.decaissement = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('tkbrApp:decaissementUpdate', function(event, result) {
            vm.decaissement = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
