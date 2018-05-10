(function() {
    'use strict';

    angular
        .module('app')
        .controller('CommandeDetailController', CommandeDetailController);

    CommandeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Commande' ,'Fournisseur','Employe','Client'];

    function CommandeDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Commande ,Fournisseur,Employe,Client) {
        var vm = this;

        vm.commande = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('tkbrApp:commandeUpdate', function(event, result) {
            vm.commande = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
