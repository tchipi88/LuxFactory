(function() {
    'use strict';

    angular
        .module('app')
        .controller('ClientDetailController', ClientDetailController);

    ClientDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Client' ,'Employe'];

    function ClientDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Client ,Employe) {
        var vm = this;

        vm.client = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('tkbrApp:clientUpdate', function(event, result) {
            vm.client = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
