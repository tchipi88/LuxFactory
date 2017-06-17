(function() {
    'use strict';

    angular
        .module('app')
        .controller('MouvementStockDetailController', MouvementStockDetailController);

    MouvementStockDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'MouvementStock' ,'Entrepot','Produit'];

    function MouvementStockDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, MouvementStock ,Entrepot,Produit) {
        var vm = this;

        vm.mouvementStock = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('tkbrApp:mouvementStockUpdate', function(event, result) {
            vm.mouvementStock = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
