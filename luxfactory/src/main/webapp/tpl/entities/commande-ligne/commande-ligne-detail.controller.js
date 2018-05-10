(function() {
    'use strict';

    angular
        .module('app')
        .controller('CommandeLigneDetailController', CommandeLigneDetailController);

    CommandeLigneDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'CommandeLigne' ,'Commande','Produit'];

    function CommandeLigneDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, CommandeLigne ,Commande,Produit) {
        var vm = this;

        vm.commandeLigne = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('tkbrApp:commandeLigneUpdate', function(event, result) {
            vm.commandeLigne = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
