(function() {
    'use strict';

    angular
        .module('app')
        .controller('ProduitDetailController', ProduitDetailController);

    ProduitDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Produit' ,'Unite','ProduitCategorie'];

    function ProduitDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Produit ,Unite,ProduitCategorie) {
        var vm = this;

        vm.produit = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('tkbrApp:produitUpdate', function(event, result) {
            vm.produit = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
