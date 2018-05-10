(function() {
    'use strict';

    angular
        .module('app')
        .controller('EntrepotProduitDetailController', EntrepotProduitDetailController);

    EntrepotProduitDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'EntrepotProduit' ,'Entrepot','Produit'];

    function EntrepotProduitDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, EntrepotProduit ,Entrepot,Produit) {
        var vm = this;

        vm.entrepotProduit = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('tkbrApp:entrepotProduitUpdate', function(event, result) {
            vm.entrepotProduit = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
