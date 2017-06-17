(function() {
    'use strict';

    angular
        .module('app')
        .controller('CaisseDetailController', CaisseDetailController);

    CaisseDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Caisse' ,'Employe'];

    function CaisseDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Caisse ,Employe) {
        var vm = this;

        vm.caisse = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('tkbrApp:caisseUpdate', function(event, result) {
            vm.caisse = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
