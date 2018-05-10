(function() {
    'use strict';

    angular
        .module('app')
        .controller('RoleDetailController', RoleDetailController);

    RoleDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Role' ];

    function RoleDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Role ) {
        var vm = this;

        vm.role = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('tkbrApp:roleUpdate', function(event, result) {
            vm.role = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
