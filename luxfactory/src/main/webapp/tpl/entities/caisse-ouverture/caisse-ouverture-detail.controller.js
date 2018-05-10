(function() {
    'use strict';

    angular
        .module('app')
        .controller('CaisseOuvertureDetailController', CaisseOuvertureDetailController);

    CaisseOuvertureDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'CaisseOuverture' ,'Caisse'];

    function CaisseOuvertureDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, CaisseOuverture ,Caisse) {
        var vm = this;

        vm.caisseOuverture = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('tkbrApp:caisseOuvertureUpdate', function(event, result) {
            vm.caisseOuverture = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
