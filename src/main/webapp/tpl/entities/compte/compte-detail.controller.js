(function() {
    'use strict';

    angular
        .module('app')
        .controller('CompteDetailController', CompteDetailController);

    CompteDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Compte' ];

    function CompteDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Compte ) {
        var vm = this;

        vm.compte = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('tkbrApp:compteUpdate', function(event, result) {
            vm.compte = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
