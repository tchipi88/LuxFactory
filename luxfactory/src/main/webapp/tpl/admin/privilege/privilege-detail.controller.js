(function() {
    'use strict';

    angular
        .module('app')
        .controller('PrivilegeDetailController', PrivilegeDetailController);

    PrivilegeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Privilege' ];

    function PrivilegeDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Privilege ) {
        var vm = this;

        vm.privilege = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('tkbrApp:privilegeUpdate', function(event, result) {
            vm.privilege = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
