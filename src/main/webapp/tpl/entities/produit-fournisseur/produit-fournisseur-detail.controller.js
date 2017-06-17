(function() {
    'use strict';

    angular
        .module('app')
        .controller('ProduitFournisseurDetailController', ProduitFournisseurDetailController);

    ProduitFournisseurDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'ProduitFournisseur' ,'Fournisseur','Produit'];

    function ProduitFournisseurDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, ProduitFournisseur ,Fournisseur,Produit) {
        var vm = this;

        vm.produitFournisseur = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('tkbrApp:produitFournisseurUpdate', function(event, result) {
            vm.produitFournisseur = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
